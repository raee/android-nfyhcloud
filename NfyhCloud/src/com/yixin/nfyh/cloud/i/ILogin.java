package com.yixin.nfyh.cloud.i;

import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;

/**
 * 登录接口
 * 
 * @author ChenRui
 * 
 */
public interface ILogin {
	/**
	 * 登录
	 * 
	 * @param userName
	 * @param pwd
	 */
	void login(String userName, String pwd);

	/**
	 * 登录QQ
	 * 
	 * @param openId
	 *            开放平台ID
	 */
	void loginByQQ(String openId);

	/**
	 * 绑定QQ
	 * 
	 * @param username
	 * @param pwd
	 * @param openId
	 *            开放平台ID
	 */
	void bindQQ(String username, String pwd, String openId);

	/**
	 * 网站接口回调
	 * 
	 * @param l
	 */
	void setOnConnectonCallback(SoapConnectionCallback<Users> l);
}
