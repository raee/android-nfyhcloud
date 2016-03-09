package com.yixin.nfyh.cloud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yixin.nfyh.cloud.bll.Account;
import com.yixin.nfyh.cloud.bll.ILoginCallback;

/**
 * 绑定QQ
 * 
 * @author ChenRui
 * 
 */
public class QQBindActivity extends BaseActivity implements ILoginCallback {
	private String openId;
	private TextView mPassword;
	private TextView mUserNameTextView;
	private Account mAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq_bind);
		initView();
		this.openId = getIntent().getStringExtra("openId");
		mAccount = new Account(this);
		mAccount.setLoginCallbackListener(this);
		if (TextUtils.isEmpty(openId)) {
			showMsg("获取用户信息错误，请重新登录授权！");
			finish();
		}
	}

	private void initView() {
		findViewById(R.id.btn_qq_bind).setOnClickListener(this);
		mUserNameTextView = (TextView) findViewById(R.id.et_login_username);
		mPassword = (TextView) findViewById(R.id.et_login_password);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		String userName = mUserNameTextView.getText().toString().trim();
		String pwd = mPassword.getText().toString().trim();
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
			showMsg("请填写用户名和密码！");
			return;
		}

		mAccount.bindQQ(userName, pwd, openId);
	}

	@Override
	public void OnLoginSuccess(String username, String pwd) {
		startActivity(new Intent(this, MainActivity.class));
		this.finish();
	}

	@Override
	public void OnLoginFaild(String msg) {
		showMsg(msg);
	}
}
