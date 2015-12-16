package com.rae.im.sdk.rongcloud;

/**
 * 融云服务端API
 * 
 * @author ChenRui
 * 
 */
public interface IRongServerApi {

	/**
	 * 获取用户Token
	 * 
	 * <p>
	 * 用于用户登录。
	 * 
	 * <p>
	 * 建议这个方法可以由您的服务端去做。因为这个App Secret 是会发生改变，由服务端去处理比较简单。
	 * <p>
	 * 首先第一步先调用你自己服务端的API 进行登录，登录成功后，返回用户Token直接登录融云就不用该方法处理。
	 * 
	 * @param appKey
	 *            开发者KEY
	 * @param appSecret
	 *            App Secret，在融云控制台 APP KEY 下面的，建议这个从您的服务器获取。
	 * @param uid
	 *            您自己平台的用户ID
	 * @param userName
	 *            您自己平台的用户名
	 * @param headUrl
	 *            您自己平台的用户头像网址路径。
	 */
	void getUserToken(String appKey, String appSecret, String uid,
			String userName, String headUrl, IRongUserTokenCallback l);
}
