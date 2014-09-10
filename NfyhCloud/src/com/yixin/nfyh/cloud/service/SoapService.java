package com.yixin.nfyh.cloud.service;

import java.util.Map;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import cn.rui.framework.utils.SoapCallback;
import cn.rui.framework.utils.SoapConnection;

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
		String defaultUrl = context.getResources().getString(
				R.string.url_webservice);
		String namespace = context.getResources().getString(
				R.string.url_webservice_namespace);

		SoapConnection conn = new SoapConnection();
		conn.setSoapCallbackListener(callback);
		conn.request(defaultUrl, namespace, method, params);
	}

}
