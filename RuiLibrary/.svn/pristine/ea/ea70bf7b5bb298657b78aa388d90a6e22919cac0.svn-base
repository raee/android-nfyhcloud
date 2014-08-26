package com.yixin.nfyh.cloud.w;

import android.content.Context;

import com.yixin.nfyh.cloud.i.ILogin;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.i.ISignServer;

/**
 * 该工厂提供 webservice 提供的所有接口
 * 
 * @author MrChenrui
 * 
 */
public class NfyhWebserviceFactory
{

	public static NfyhWebserviceFactory	factory	= null;

	public static NfyhWebserviceFactory getFactory(Context context)
	{
		if (factory == null)
			factory = new NfyhWebserviceFactory(context);
		return factory;
	}

	private Context	context;

	private NfyhWebserviceFactory(Context context)
	{
		this.context = context;
	}

	/**
	 * 获取登录接口
	 * 
	 * @return
	 */
	public ILogin getLogin()
	{
		return new LoginServer(context);
	}

	/**
	 * 获取满意度接口
	 * 
	 * @return
	 */
	public IManyidu getManyidu()
	{
		return new ManyiduServer(context);
	}

	/**
	 * 获取院后照片接口
	 * 
	 * @return
	 */
	public PhotoCategoryServer getPhotoCategory()
	{
		return new PhotoCategoryServer(context);
	}

	/**
	 * 获取体征接口
	 * 
	 * @return
	 */
	public ISignServer getSignServer()
	{
		return new SignServer(context);
	}

	/**
	 * 获取消息推送接口
	 * 
	 * @return
	 */
	public IPushMessage getPushMessage()
	{
		return new PushMessageServer(context);
	}
}
