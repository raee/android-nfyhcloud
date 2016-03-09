package com.yixin.nfyh.cloud.bll.sign;

/**
 * 体征监听者
 * 
 * @author MrChenrui
 * 
 */
public interface SignCoreListener {
	/**
	 * 体征上传失败
	 * 
	 * @param code
	 * @param msg
	 */
	void onSignCoreError(int code, String msg);

	/**
	 * 体征上传成功
	 * 
	 * @param code
	 * @param msg
	 */
	void onSignCoreSuccess(int code, String msg);

	/**
	 * 正在上传
	 */
	void onUploading();
}
