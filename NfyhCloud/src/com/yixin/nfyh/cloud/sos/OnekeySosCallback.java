package com.yixin.nfyh.cloud.sos;

/**
 * 一键呼救回调接口
 * 
 * @author MrChenrui
 * 
 */
public interface OnekeySosCallback
{
	void sosStarted();

	void sosLocated(String jwd, String address);

	void sosUploadCloudSuccess(String response);

	void sosCalled(String num);

	void sosFinsh(String msg);

	void sosError(int code, String msg);
}