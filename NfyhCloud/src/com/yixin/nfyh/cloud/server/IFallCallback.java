package com.yixin.nfyh.cloud.server;

/**
 * 跌倒服务回调
 * 
 * @author ChenRui
 * 
 */
public interface IFallCallback {
	/**
	 * 跌倒仪绑定成功
	 * 
	 * @param msg
	 */
	void onBindSuccess(String msg);

	/**
	 * 跌倒仪绑定失败
	 * 
	 * @param msg
	 */
	void onBindError(String msg);

}
