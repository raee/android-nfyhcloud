package com.yixin.nfyh.cloud.sos;

/**
 * 一键呼救回调接口
 * 
 * @author MrChenrui
 * 
 */
public interface OnekeySosCallback {
	/**
	 * 开始呼救
	 */
	void sosStarted();

	/**
	 * 定位
	 * 
	 * @param jwd
	 *            经纬度
	 * @param address
	 *            地址
	 */
	void sosLocated(String jwd, String address);

	/**
	 * 位置、地址、信息上传到服务器成功
	 * 
	 * @param response
	 *            服务器返回
	 */
	void sosUploadCloudSuccess(String response);

	/**
	 * 拨打电话
	 * 
	 * @param num
	 *            号码
	 */
	void sosCalled(String num);

	/**
	 * 急救完成
	 * 
	 * @param msg
	 *            提示信息
	 */
	void sosFinsh(String msg);

	/**
	 * 急救失败
	 * 
	 * @param code
	 * @param msg
	 *            提示信息
	 */
	void sosError(int code, String msg);
}