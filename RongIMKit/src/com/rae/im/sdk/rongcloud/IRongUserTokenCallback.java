package com.rae.im.sdk.rongcloud;

/**
 * �û�TOKEN �ص�
 * 
 * @author ChenRui
 * 
 */
public interface IRongUserTokenCallback {

	void onUserTokenSuccess(String uid, String token);

	void onUserTokenErrror(int code, String msg);
}
