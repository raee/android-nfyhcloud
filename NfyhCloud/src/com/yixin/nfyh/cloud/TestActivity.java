package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.yixin.nfyh.cloud.bll.Account;
import com.yixin.nfyh.cloud.bll.ILoginCallback;

public class TestActivity extends Activity implements OnClickListener {
	//private static final String	APPID	= "1103162244";
	private String				Tag		= "TestActivity";
	private Account				account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		findViewById(R.id.btn_test_login).setOnClickListener(this);
		findViewById(R.id.btn_test_userinfo).setOnClickListener(this);
		account = new Account(this);
		account.setLoginCallbackListener(new ILoginCallback() {

			@Override
			public void OnLoginSuccess(String username, String pwd) {
				Log.i(Tag, "登录成功！");
			}

			@Override
			public void OnLoginFaild(String msg) {
				Log.e(Tag, msg);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		account.getTencent().onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_test_login:
				login();
				break;
			case R.id.btn_test_userinfo:
				break;

			default:
				break;
		}
	}

	private void login() {
		account.loginByQQ();
	}

}
