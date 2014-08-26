package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.rui.framework.utils.IInputValidate;
import cn.rui.framework.utils.InputUtils;

import com.yixin.nfyh.cloud.bll.Account;
import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.bll.ILoginCallback;
import com.yixin.nfyh.cloud.ui.TimerProgressDialog;
import com.yixin.nfyh.cloud.ui.TimerToast;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 登录界面
 * 
 * @author MrChenrui
 * 
 */
public class LoginActivity extends Activity implements IInputValidate, ILoginCallback, OnClickListener
{
	
	private EditText			etUserName, etPwd;
	
	private Button				btnLogin;
	
	private TimerProgressDialog	dialog;
	
	private Account				account;
	
	private GlobalSetting		setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_login);
			findView();
			setLinsener();
			account = new Account(this);
			account.setLoginCallbackListener(this);
			if (etUserName.getText().toString().length() > 0 && etPwd.getText().toString().length() > 0)
			{
				btnLogin.setEnabled(true);
				btnLogin.setBackgroundResource(R.drawable.btn_green);
			}
			// 退出登录，返回到登录界面
			if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Intent.EXTRA_TEXT))
			{
				return;
			}
			loadSave();
			
			// 已经登录
			if (getNfyhApplication().isLogin())
			{
				gotoMainActivity();
			}
		}
		catch (Exception e)
		{
			LogUtil.getLog().setExcetion("LoginActivity", e);
			gotoMainActivity();
		}
	}
	
	protected void findView()
	{
		etUserName = (EditText) findViewById(R.id.et_login_username);
		etPwd = (EditText) findViewById(R.id.et_login_password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		dialog = new TimerProgressDialog(this);
		dialog.setMessage("正在登录，请稍候...");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return false;
	}
	
	/**
	 * 加载保存好的用户名和密码
	 */
	private void loadSave()
	{
		String username = getSetting().getUser().getUsername();
		String pwd = getSetting().getUser().getPwd();
		if (username != null) this.etUserName.setText(username);
		if (pwd != null) this.etPwd.setText(pwd);
	}
	
	public GlobalSetting getSetting()
	{
		if (setting == null) setting = new GlobalSetting(this);
		return setting;
	}
	
	protected void setLinsener()
	{
		btnLogin.setOnClickListener(this);
		InputUtils inputUtil = new InputUtils();
		inputUtil.setButtonEnableOnEditTextChange(btnLogin, etPwd, etUserName);
		inputUtil.setInputValidateLinsener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_login:
				login();
				break;
			default:
				break;
		}
	}
	
	private void login()
	{
		String username = etUserName.getText().toString().trim();
		String pwd = etPwd.getText().toString().trim();
		btnLogin.setEnabled(false);
		btnLogin.setBackgroundResource(R.drawable.btn_disable);
		dialog.show();
		// TODO：先本地登录
		if (!loginInLocal(username, pwd))
		{
			account.login(username, pwd);
		}
	}
	
	private boolean loginInLocal(String username, String pwd)
	{
		if (!getNfyhApplication().isLogin() && this.account.loginInLocal(username, pwd))
		{
			TimerToast toast = TimerToast.makeText(this, "离线登录成功", Toast.LENGTH_SHORT);
			toast.setType(TimerToast.TYPE_SUCCESS);
			toast.show();
			gotoMainActivity();
			return true;
		}
		return false;
	}
	
	private void gotoMainActivity()
	{
		getNfyhApplication().setIsLogin(true);
		startActivity(new Intent(this, MainActivity.class));
		this.finish();
	}
	
	private NfyhApplication getNfyhApplication()
	{
		return (NfyhApplication) getApplication();
	}
	
	@Override
	public void OnSuccees()
	{
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
	}
	
	@Override
	public void OnFaild(EditText et)
	{
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_disable);
	}
	
	@Override
	public void OnLoginSuccess(String username, String pwd)
	{
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
		dialog.dismiss();
		gotoMainActivity();
	}
	
	@Override
	public void OnLoginFaild(String msg)
	{
		btnLogin.setEnabled(true);
		btnLogin.setBackgroundResource(R.drawable.btn_green);
		dialog.dismiss();
		TimerToast toast = TimerToast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setType(TimerToast.TYPE_FIALID);
		toast.show();
		loginInLocal(etUserName.getText().toString().trim(), etPwd.getText().toString().trim());
	}
}
