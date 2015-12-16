package com.yixin.nfyh.cloud.w;

import java.util.List;

import com.yixin.nfyh.cloud.model.GanyuInfo;

/**
 * 干预回调
 * 
 * @author ChenRui
 * 
 */
public interface IGetIntervenesLinstener
{
	void onGetIntervenesSuccess(List<GanyuInfo> datas);
	
	void onGetIntervenesError(int code, String msg);
	
	void onNotIntervenes();
}
