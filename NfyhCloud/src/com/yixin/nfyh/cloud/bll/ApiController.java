package com.yixin.nfyh.cloud.bll;

import com.yixin.nfyh.cloud.R;

import android.content.Context;

/**
 * API 控制器，控制不同平台
 * 
 * @author ChenRui
 * 
 */
public class ApiController
{
	static Context					applicationContext;
	private static IApiController	api;
	
	/**
	 * 初始化接口
	 * 
	 * @param context
	 */
	public static void init(Context context)
	{
		applicationContext = context;
		String p = context.getString(R.string.platfrom).trim();
		
		if ("院后总平台".equals(p))
		{
			api = new FimmuApiController(context);
		}
		else
		{
			api = new KermatelmedApiController(context);
		}
		
	}
	
	public static IApiController get()
	{
		return api;
	}
}
