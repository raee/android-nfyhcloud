package com.rae.im.sdk.rongcloud;

/**
 * 用户TOKEN 回调
 * 
 * @author ChenRui
 * 
 */
public interface IRongUserTokenCallback {

	void onUserTokenSuccess(String uid, String token);

	void onUserTokenErrror(int code, String msg);
}
