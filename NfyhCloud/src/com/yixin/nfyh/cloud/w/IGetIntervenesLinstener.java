package com.yixin.nfyh.cloud.w;

import java.util.List;

import com.yixin.nfyh.cloud.model.GanyuInfo;

/**
 * 干预回调
 * 
 * @author ChenRui
 * 
 */
public interface IGetIntervenesLinstener {
	/**
	 * 成功
	 * 
	 * @param datas
	 */
	void onGetIntervenesSuccess(List<GanyuInfo> datas);

	/**
	 * 失败
	 * 
	 * @param code
	 * @param msg
	 */
	void onGetIntervenesError(int code, String msg);

	/**
	 * 没有数据
	 */
	void onNotIntervenes();
}
