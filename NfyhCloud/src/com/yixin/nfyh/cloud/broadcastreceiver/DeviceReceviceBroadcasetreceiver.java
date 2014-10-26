//package com.yixin.nfyh.cloud.broadcastreceiver;
//
//import java.io.IOException;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import cn.rui.framework.utils.MediaUtil;
//
//import com.yixin.nfyh.cloud.R;
//import com.yixin.nfyh.cloud.service.CoreServerBinder;
//import com.yixin.nfyh.cloud.ui.DeviceMsgView;
//
///**
// * 蓝牙设备连接广播
// * 
// * @author MrChenrui
// * 
// */
//public class DeviceReceviceBroadcasetreceiver extends BroadcastReceiver {
//	
//	//	private View				mActionView;
//	
//	private Context				mContext;
//	
//	private static MediaPlayer	player;
//	
//	private DeviceMsgView		mDeviceMsgView;
//	
//	//	public DeviceReceviceBroadcasetreceiver(Context context, View actionView) {
//	//		this.mActionView = actionView;
//	//	}
//	
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		this.mContext = context;
//		String action = intent.getAction();
//		String msg = intent.getStringExtra(Intent.EXTRA_TEXT);
//		String name = intent.getStringExtra("name");
//		if (mDeviceMsgView == null) {
//			mDeviceMsgView = new DeviceMsgView(mContext);
//		}
//		
//		mDeviceMsgView.setName(name);
//		
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTED.equals(action)) // 已经连接
//		{
//			showSuccess("连接成功", "");
//		}
//		
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTING.equals(action)) // 正在连接
//		{
//			show(msg);
//		}
//		if (action.equals(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_DISCONNECTED)) // 断开连接
//		{
//			showError("设备断开", "设备已经断开连接，您可以重新连接。");
//			stopMusic();
//		}
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_FAILD.equals(action)) // 连接失败
//		{
//			showError("连接失败", msg);
//			stopMusic();
//		}
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICED.equals(action)) // 接收到数据
//		{
//			dismiss();
//		}
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_MODEL.equals(action)) {
//			dismiss();
//		}
//		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICEING.equals(action))// 接收数据中
//		{
//			playMusic();
//			show("接收数据中");
//		}
//		
//	}
//	
////	public boolean isShow(){
////		return mDeviceMsgView.isShowing();
////	}
////	
//	private void show(String msg) {
//		mDeviceMsgView.setTips(msg);
//		;
//		mDeviceMsgView.show();
//	}
//	
//	private void showError(String tips, String msg) {
//		mDeviceMsgView.setTips(tips);
//		mDeviceMsgView.setMsg(msg);
//		mDeviceMsgView.showError();
//	}
//	
//	private void showSuccess(String tips, String msg) {
//		mDeviceMsgView.setTips(tips);
//		mDeviceMsgView.setMsg(msg);
//		mDeviceMsgView.showSuccess();
//		
//	}
//	
//	public void dismiss() {
//		stopMusic();
//		mDeviceMsgView.dismiss();
//	}
//	
// }
