package com.yixin.nfyh.cloud.bll.sign;

/**
 * 体征监听者
 * 
 * @author MrChenrui
 * 
 */
public interface SignCoreListener
{
	void onSignCoreError(int code, String msg);

	void onSignCoreSuccess(int code, String msg);
	
	void onUploading();
}
