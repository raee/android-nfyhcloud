package com.rae.im.sdk.rongcloud;

import android.app.Application;

public abstract class RaeRongCloudIM {

	private static final RaeRongCloudIM instance = new RongCloudIM();

	public static RaeRongCloudIM getInstance() {
		return instance;
	}

	/**
	 * 初始化聊天服务器接口，<b>注意：</b>该方法只能被调用一次。
	 * 
	 * @param application
	 *            应用程序，请写一个类继承。
	 */
	public abstract void init(Application application);

	/**
	 * 连接服务器
	 * 
	 * @param token
	 *            登录凭证
	 * @param l
	 *            连接回调
	 */
	public abstract void connect(String token, IMConnectionListener l);

	/**
	 * 登录聊天服务器
	 * 
	 * @param appSecret
	 *            开发者帐号里面的appSecret
	 * @param username
	 *            用户名
	 * @param passwrod
	 *            密码
	 * @param extra
	 *            其他信息，JSON 格式。
	 */
	public abstract void login(String appSecret, String username,
			String passwrod, String extra, IMLoginListener l);

	/**
	 * 断开连接
	 */
	public abstract void disconnect();

	/**
	 * 登出
	 */
	public abstract void logout();
}
