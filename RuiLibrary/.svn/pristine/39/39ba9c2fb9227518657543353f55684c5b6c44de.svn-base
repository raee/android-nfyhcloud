/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import android.content.Context;

/**
 * 数据库访问对象获取类
 * 
 * @author MrChenrui
 * 
 */
public class NfyhCloudDataBase
{
	private NfyhCloudDataBase()
	{
	}

	private static NfyhCloudDataOpenHelp	instance	= null;

	/**
	 * 获取数据访问对象
	 * 
	 * @param context
	 * @return
	 */
	public static NfyhCloudDataOpenHelp getDataOpenHelp(Context context)
	{
		// 不为空，但是用不同的上下文提供的时候重新构造
		if (instance != null && !instance.equals(context))
			instance = new NfyhCloudDataOpenHelp(context);
		else if (instance == null)
			instance = new NfyhCloudDataOpenHelp(context);
		else
			return instance;
		return instance;
	}
}
