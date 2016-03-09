package com.yixin.nfyh.cloud.i;

import java.util.List;

import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;

/**
 * 上传体征接口
 * 
 * @author ChenRui
 * 
 */
public interface ISignServer extends IAuthentication {
	/**
	 * 上传体征数据
	 * 
	 * @param model
	 */
	void upload(List<UserSigns> model);

	/**
	 * 设置回调
	 * 
	 * @param l
	 */
	void setOnConnectonCallback(SoapConnectionCallback<List<UserSigns>> l);
}
