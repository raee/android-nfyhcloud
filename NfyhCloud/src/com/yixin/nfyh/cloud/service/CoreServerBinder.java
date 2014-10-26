//package com.yixin.nfyh.cloud.service;
//
//import android.content.Context;
//import android.os.Binder;
//
//import com.yixin.monitors.sdk.api.ApiMonitor;
//import com.yixin.monitors.sdk.api.BluetoothListener;
//import com.yixin.nfyh.cloud.device.DefaultDevice;
//import com.yixin.nfyh.cloud.device.DeviceReceiverListener;
//
///**
// * 核心服务，设备接收
// * 
// * @author MrChenrui
// */
//public class CoreServerBinder extends Binder {
//	private Context				context;
//	private ApiMonitor			device;
////	private BluetoothListener	mListener;
//
//	public CoreServerBinder(Context context) {
//		this.context = context;
//	}
//
//	public void conncet() {
//		this.device = DefaultDevice.getInstance(context);
////		if (mListener == null) {
////			mListener = new DeviceReceiverListener(context, device);
////			this.device.setBluetoothListener(mListener);
////		}
//		device.connect();
//	}
//
//	public void disconnect() {
//		if (device != null) {
//			device.disconnect();
//		}
//		device = null;
//	}
//
//	public ApiMonitor getDevice() {
//		return device;
//	}
//
//}
