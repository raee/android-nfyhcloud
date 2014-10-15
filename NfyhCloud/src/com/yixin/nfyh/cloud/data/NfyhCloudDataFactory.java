/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;

import android.content.Context;

import com.yixin.nfyh.cloud.i.IPhotoCategory;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * @author MrChenrui
 * 
 */
public class NfyhCloudDataFactory extends DataFactory
{
	private static NfyhCloudDataFactory	instance	= null;
	private DataQuery					query;
	private ISignDevice					signQuery;
	private IPhotoCategory				photocategory;
	private Context						context;

	private NfyhCloudDataFactory(Context context) throws SQLException
	{
		this.context = context;
		this.query = new DataQuery(context);
		signQuery = new SignDataQuery(context);
//		clock = new ClockImpl(context);
	}

	/**
	 * 获取数据库工厂，该工厂提供数据接口
	 * 
	 * @param context
	 * @return
	 * @throws SQLException
	 */
	public static NfyhCloudDataFactory getFactory(Context context)
	{

		try
		{
			if (instance == null)
				instance = new NfyhCloudDataFactory(context);
		}
		catch (SQLException e)
		{
			LogUtil.getLog().setExcetion("NfyhCloudDataFactory", e);
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.DataFactory#getUser()
	 */
	@Override
	public IUser getUser()
	{
		return query;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.DataFactory#getDict()
	 */
	@Override
	public IDict getDict()
	{
		return query;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.DataFactory#getSignDevice()
	 */
	@Override
	public ISignDevice getSignDevice()
	{
		return signQuery;
	}

	public IPhotoCategory getPhotocategory()
	{
		if (photocategory == null)
		{
			photocategory = new PhotocategoryImpl(context);
		}

		return photocategory;
	}

	public IPushMessage getPushMessage()
	{
		return new PushMessage(context);
	}


}
