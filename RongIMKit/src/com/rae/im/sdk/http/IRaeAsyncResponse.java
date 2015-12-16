package com.rae.im.sdk.http;

/**
 * HTTP异步回调
 * 
 * @author ChenRui
 * 
 */
public interface IRaeAsyncResponse {

	/**
	 * 成功
	 * 
	 * @param statusCode
	 * @param html
	 */
	void onHttpSuccess(int statusCode, String html);

	/**
	 * 失败
	 * 
	 * @param statusCode
	 * @param html
	 * @param e
	 */
	void onHttpFaild(int statusCode, String html, Throwable e);
}
