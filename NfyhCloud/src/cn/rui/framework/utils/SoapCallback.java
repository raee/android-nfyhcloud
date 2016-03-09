package cn.rui.framework.utils;

/**
 * WebService 回调
 * 
 * @author ChenRui
 * 
 */
public interface SoapCallback {
	/**
	 * 调用成功
	 * 
	 * @param response
	 *            服务器返回的内容
	 */
	void onSoapResponse(Object response);

	/**
	 * 调用失败
	 * 
	 * @param code
	 *            失败代码
	 * @param msg
	 *            失败消息
	 */
	void onSoapError(int code, String msg);
}
