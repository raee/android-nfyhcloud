package com.yixin.nfyh.cloud.bll;

import android.content.Context;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.data.IUser;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.ILogin;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

public class Account implements SoapConnectionCallback<Users> {
	private Context			mContext;
	private ILoginCallback	mListener;
	private GlobalSetting	mSetting;
	private NfyhApplication	mApplication;
	private IUser			mDbUser;
	
	public Account(Context context) {
		this.mContext = context;
		this.mSetting = new GlobalSetting(context);
		this.mApplication = (NfyhApplication) mContext.getApplicationContext();
		mDbUser = NfyhCloudDataFactory.getFactory(mContext).getUser();
	}
	
	public void setLoginCallbackListener(ILoginCallback l) {
		this.mListener = l;
	}
	
	public boolean loginInLocal(String username, String pwd) {
		if (mDbUser == null) return false;
		return mDbUser.login(username, pwd);
	}
	
	/**
	 * 登录
	 * 
	 * @param username
	 * @param pwd
	 */
	public void login(String username, String pwd) {
		ILogin loginApi = NfyhWebserviceFactory.getFactory(mContext).getLogin();
		loginApi.setOnConnectonCallback(this);
		loginApi.login(username, pwd);
		
	}
	
	@Override
	public void onSoapConnectSuccess(Users data) {
		this.mApplication.setUserInfo(data);
		this.mApplication.setIsLogin(true);
		this.mSetting.setUser(data);
		mListener.OnLoginSuccess(data.getUsername(), data.getPwd());
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		if (e.getCode() == WebServerException.CODE_NULL_DATA) {
			mListener.OnLoginFaild("用户名或密码错误！");
		}
		else {
			this.mListener.OnLoginFaild(e.getMessage());
		}
	}
}
