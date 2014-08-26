package com.yixin.nfyh.cloud.w;

import java.util.List;

import com.yixin.nfyh.cloud.i.IAuthentication;
import com.yixin.nfyh.cloud.model.view.MydcViewModel;

/**
 * 满意度调查接口
 * 
 * @author MrChenrui
 * 
 */
public interface IManyidu extends IAuthentication
{

	/**
	 * 获取所有的满意度调查
	 * 
	 * @return
	 */
	List<MydcViewModel> getList();

	/**
	 * 设置回调
	 * 
	 * @param l
	 */
	void setOnConnectonCallback(SoapConnectionCallback<List<MydcViewModel>> l);
}
