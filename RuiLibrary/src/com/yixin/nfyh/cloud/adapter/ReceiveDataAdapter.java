///**
// * 
// */
//package com.yixin.nfyh.cloud.adapter;
//
//import android.app.Activity;
//
//import com.yixin.nfyh.cloud.device.DefaultDevice;
//import com.yixin.nfyh.cloud.device.IDevice;
//
///**
// * 数据接收适配器
// * 
// * @author MrChenrui
// * 
// */
//public class ReceiveDataAdapter
//{
//	private Activity	context;
//	private IDevice		deviceApi;
//	
//	/**
//	 * 获取deviceApi
//	 * 
//	 * @return the deviceApi
//	 */
//	public IDevice getDeviceApi()
//	{
//		return deviceApi;
//	}
//	
//	/**
//	 * @param contextActivity
//	 * @throws Exception
//	 */
//	public ReceiveDataAdapter(Activity contextActivity) throws Exception
//	{
//		super();
//		this.context = contextActivity;
//		
//		// 初始化设备
//		deviceApi = DefaultDevice.getInstance(context);
//	}
//	
// }
