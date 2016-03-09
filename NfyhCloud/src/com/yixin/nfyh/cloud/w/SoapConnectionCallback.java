package com.yixin.nfyh.cloud.w;

/**
 * web services 服务器返回回调
 * 
 * @author ChenRui
 * 
 * @param <T>
 */
public interface SoapConnectionCallback<T> {
	/**
	 * 成功
	 * 
	 * @param data
	 *            服务器返回数据
	 */
	void onSoapConnectSuccess(T data);

	/**
	 * 失败
	 * 
	 * @param e
	 */
	void onSoapConnectedFalid(WebServerException e);
}
