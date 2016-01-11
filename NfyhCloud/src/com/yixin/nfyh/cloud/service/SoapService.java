package com.yixin.nfyh.cloud.service;

import java.util.Map;

import android.content.Context;
import cn.rui.framework.utils.SoapCallback;
import cn.rui.framework.utils.SoapConnection;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ApiController;

/**
 * 调用WebSerivce类
 * 
 * @author MrChenrui
 * 
 */
public class SoapService
{
	
	private Map<String, Object>	params;
	private Context				context;
	private SoapCallback		callback;
	
	public SoapService(Context context)
	{
		this.context = context;
	}
	
	/**
	 * 设置参数
	 * 
	 * @param params
	 */
	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}
	
	/**
	 * 设置回调
	 * 
	 * @param callback
	 */
	public void setCallbackListener(SoapCallback callback)
	{
		this.callback = callback;
	}
	
	/**
	 * 调用
	 * 
	 * @param method
	 */
	public void call(String method)
	{
		String defaultUrl = ApiController.get().getApiUrl();
		String namespace = ApiController.get().getNameSpace();
		
		SoapConnection conn = new SoapConnection();
		conn.setSoapCallbackListener(callback);
		conn.request(defaultUrl, namespace, method, params);
	}
	
}
