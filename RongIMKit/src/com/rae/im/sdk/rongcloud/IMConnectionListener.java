package com.rae.im.sdk.rongcloud;

import io.rong.imlib.RongIMClient.ErrorCode;

public interface IMConnectionListener {
	/**
	 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
	 */
	public void onTokenIncorrect();

	/**
	 * 连接融云失败
	 * 
	 * @param errorCode
	 *            错误码，可到官网 查看错误码对应的注释
	 */
	public void onError(ErrorCode code);

	/**
	 * 连接融云成功
	 * 
	 * @param userid
	 *            当前 token
	 */
	public void onSuccess(String token);
}
