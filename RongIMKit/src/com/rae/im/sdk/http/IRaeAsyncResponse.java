package com.rae.im.sdk.http;

/**
 * HTTP�첽�ص�
 * 
 * @author ChenRui
 * 
 */
public interface IRaeAsyncResponse {

	/**
	 * �ɹ�
	 * 
	 * @param statusCode
	 * @param html
	 */
	void onHttpSuccess(int statusCode, String html);

	/**
	 * ʧ��
	 * 
	 * @param statusCode
	 * @param html
	 * @param e
	 */
	void onHttpFaild(int statusCode, String html, Throwable e);
}
