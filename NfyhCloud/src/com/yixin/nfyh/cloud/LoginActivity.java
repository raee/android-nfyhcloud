package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import cn.rui.framework.utils.IInputValidate;
import cn.rui.framework.utils.InputUtils;

import com.yixin.nfyh.cloud.bll.Account;
import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.bll.ILoginCallback;
import com.yixin.nfyh.cloud.ui.TimerToast;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 登录界面
 * 
 * @author MrChenrui
 * 
 */
public class LoginActivity extends Activity implements IInputValidate,
		ILoginCallback, OnClickListener, OnEditorActionListener {

	private EditText etUserName, etPwd;

	private Button btnLogin;

	// private TimerProgressDialog dialog;

	private Account account;

	private GlobalSetting setting;

	private Button btnLoginOffline;

	private ImageButton btnLoginQQ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			findView();
			setLinsener();
			account = new Account(this);
			account.setLoginCallbackListener(this);
			loadSave();

			// 退出登录，返回到登录界面
			if (getIntent() != null && getIntent().getExtras() != null
					&& getIntent().getExtras().containsKey(Intent.EXTRA_TEXT)) {
				return;
			}
		} catch (Exception e) {
			LogUtil.getLog().setExcetion("LoginActivity", e);
			gotoMainActivity();
		}

		// if (Config.isDebug) {
		// this.btnLogin.performClick();
		// }
	}

	protected void findView() {
		etUserName = (EditText) findViewById(R.id.et_login_username);
		etPwd = (EditText) findViewById(R.id.et_login_password);
		etUserName.setOnEditorActionListener(this);
		etPwd.setOnEditorActionListener(this);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLoginOffline = (Button) findViewById(R.id.btn_login_offline);
		btnLoginQQ = (ImageButton) findViewById(R.id.btn_login_qq);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	/**
	 * 加载保存好的用户名和密码
	 */
	private void loadSave() {
		String username = getSetting().getUser().getUsername();
		String pwd = getSetting().getUser().getPwd();

		if (!TextUtils.isEmpty(username)) {
			this.etUserName.setText(username);
		} else {
			etUserName.setText("test");
		}
		if (!TextUtils.isEmpty(pwd)) {
			this.etPwd.setText(pwd);
		} else {
			etPwd.setText("123");
		}
	}

	public GlobalSetting getSetting() {
		if (setting == null)
			setting = new GlobalSetting(this);
		return setting;
	}

	protected void setLinsener() {
		btnLogin.setOnClickListener(this);
		btnLoginOffline.setOnClickListener(this);
		btnLoginQQ.setOnClickListener(this);

		InputUtils inputUtil = new InputUtils();
		inputUtil.setButtonEnableOnEditTextChange(btnLogin, etPwd, etUserName);
		inputUtil.setInputValidateLinsener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			login();
			break;
		case R.id.btn_login_offline:
			loginInLocal();
			break;
		case R.id.btn_login_qq:
			loginByQQ();
			break;
		default:
			break;
		}
	}

	// qq 登录
	private void loginByQQ() {
		account.loginByQQ();
	}

	/**
	 * 登录
	 */
	private void login() {
		if (!btnLogin.isEnabled()) {
			return;
		}
		String username = etUserName.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "请输入用户名！", Toast.LENGTH_SHORT).show();
			etUserName.requestFocus();
			return;
		}

		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
			etPwd.requestFocus();
			return;
		}

		btnLogin.setEnabled(false);
		btnLogin.setBackgroundResource(R.drawable.btn_disable);
		btnLogin.setText("登录中...");
		account.login(username, pwd);
	}

	/**
	 * 离线登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	private boolean loginInLocal() {
		account.loginInLocal("guest", "guest");
		TimerToast toast = TimerToast.makeText(this, "离线登录成功！",
				Toast.LENGTH_SHORT);
		toast.setType(TimerToast.TYPE_SUCCESS);
		toast.show();
		gotoMainActivity();
		return true;
	}

	private void gotoMainActivity() {
		startActivity(new Intent(this, MainActivity.class));
		this.finish();
	}

	@Override
	public void OnSuccees() {
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
		btnLogin.setText("登录");
	}

	@Override
	public void OnFaild(EditText et) {
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_disable);
		btnLogin.setText("登录");
	}

	@Override
	public void OnLoginSuccess(String username, String pwd) {
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
		// dialog.dismiss();
		gotoMainActivity();
	}

	@Override
	public void OnLoginFaild(String msg) {
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
		// dialog.dismiss();
		btnLogin.setText("登录");
		TimerToast toast = TimerToast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setType(TimerToast.TYPE_FIALID);
		toast.show();

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		login();
		return false;
	}
}
