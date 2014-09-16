///**
// * 
// */
//package com.yixin.nfyh.cloud.device;
//
///**
// * 设备接收监听者
// * 
// * @author MrChenrui
// * 
// */
//public interface IDeviceListener {
//
//	/**
//	 * 接收数据
//	 * 
//	 * @param data
//	 */
//	void onReceive(DeviceCallbakModel model);
//
//	/**
//	 * 设备接收出错
//	 * 
//	 * @param ex
//	 */
//	void onConnectError(int code, String msg);
//
//	/**
//	 * 设备断开时候
//	 */
//	void onDisConnect();
//
//	/**
//	 * 正在通讯
//	 * 
//	 * @param step
//	 *            正在进行的步骤
//	 * @param progress
//	 *            总进度
//	 */
//	void onProgress(int step, int progress);
//
//	/**
//	 * 正在连接设备
//	 */
//	void onConnect();
//
//	/**
//	 * 已经连接
//	 */
//	void onConnected();
//
//	/**
//	 * 正在配对
//	 * 
//	 * @param msg
//	 */
//	void onBound(String msg);
//
//	/**
//	 * 数据接收成功
//	 */
//	void onReceiveSuccess();
//}
