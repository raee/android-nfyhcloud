package com.yixin.nfyh.cloud.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;

import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.monitors.sdk.api.BluetoothListener;
import com.yixin.monitors.sdk.model.PackageModel;
import com.yixin.nfyh.cloud.activity.SignDetailActivity;
import com.yixin.nfyh.cloud.device.DefaultDevice;

/**
 * 核心服务，设备接收
 * 
 * @author MrChenrui
 */
public class CoreServerBinder extends Binder {
	private class DeviceConnectListener implements BluetoothListener {
		
		private int		connectTime;
		private Intent	intent;
		private int		maxConnectTime	= 1;	// 重试一次
												
		public DeviceConnectListener() {
			intent = new Intent();
		}
		
		/**
		 * 发送蓝牙广播
		 */
		private void sendIntent(String action) {
			this.intent.setAction(action);
			context.sendBroadcast(intent);
		}
		
		/**
		 * 发送蓝牙广播
		 */
		private void sendIntent(String action, String msg) {
			this.intent.setAction(action);
			if (msg != null) this.intent.putExtra(Intent.EXTRA_TEXT, msg);
			context.sendBroadcast(intent);
		}
		
		@Override
		public void onStartDiscovery() {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, "开始蓝牙扫描...");
		}
		
		@Override
		public void onStopDiscovery() {
			
		}
		
		@Override
		public void onOpenBluetooth() {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, "打开蓝牙...");
			
		}
		
		@Override
		public void onCloseBluetooth() {
			
		}
		
		@Override
		public void onBluetoothStateChange(int state, BluetoothDevice device) {
			
		}
		
		@Override
		public boolean onFindBluetooth(BluetoothDevice device, boolean isBonded) {
			return false;
		}
		
		@Override
		public void onBluetoothBonding(BluetoothDevice device) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, device.getName() + "正在配对！");
		}
		
		@Override
		public void onBluetoothSetPin(BluetoothDevice device) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, device.getName() + "正在设置配对码！");
		}
		
		@Override
		public void onBluetoothBonded(BluetoothDevice device) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, device.getName() + "配对成功！");
		}
		
		@Override
		public void onBluetoothBondNone(BluetoothDevice device) {
			
		}
		
		@Override
		public void onStartReceive() {
			sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICEING, "正在接收蓝牙数据...");
			
		}
		
		@Override
		public void onReceiving(byte[] data) {
			
		}
		
		@Override
		public void onReceived(byte[] data) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICED);
		}
		
		@Override
		public void onReceived(PackageModel model) {
			
			Intent intent = new Intent(context, SignDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle bundle = new Bundle();
			bundle.putString(Intent.EXTRA_TEXT, "-1");
			bundle.putParcelable("data", model);
			intent.putExtras(bundle);
			context.startActivity(intent);
			
			intent.putExtras(bundle);
			sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICED);
		}
		
		@Override
		public void onConnected(BluetoothDevice device) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTED, device.getName() + "连接成功，请开始测量！");
		}
		
		@Override
		public void onError(int errorCode, String msg) {
			if (connectTime < maxConnectTime) {
				connectTime++;
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						device.connect(); // 连接
					}
				}, 3000);
				
			}
			
			sendIntent(ACTION_BLUETOOTH_DEVICE_FAILD, msg);
			
		}
		
		@Override
		public void onBluetoothCancle() {
			sendIntent(ACTION_BLUETOOTH_DEVICE_DISCONNECTED, "监测设备已经断开，请重新连接！");
			
		}
	}
	
	/**
	 * 蓝牙连接成功！
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_CONNECTED		= "com.yixin.nfyh.cloud.bluetoth.device.connected.actoin";
	
	/**
	 * 蓝牙正在连接
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_CONNECTING		= "com.yixin.nfyh.cloud.bluetoth.device.connecting.actoin";
	
	/**
	 * 　蓝牙断开
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_DISCONNECTED	= "com.yixin.nfyh.cloud.bluetoth.device.disconnected.actoin";
	
	/**
	 * 蓝牙连接失败
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_FAILD			= "com.yixin.nfyh.cloud.bluetoth.device.faild.actoin";
	/**
	 * 　蓝牙完成接收数据
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_RECEVICED		= "com.yixin.nfyh.cloud.bluetoth.device.receviced.actoin";
	
	/**
	 * 　蓝牙正在接收数据
	 */
	public static final String	ACTION_BLUETOOTH_DEVICE_RECEVICEING		= "com.yixin.nfyh.cloud.bluetoth.device.receviceing.actoin";
	private Context				context;
	private ApiMonitor			device;
	private BluetoothListener	listener;
	
	public CoreServerBinder(Context context) {
		this.context = context;
		listener = new DeviceConnectListener();
	}
	
	/**
	 * 设置默认的监测设备
	 */
	public void setDefaultDevice() {
		if (device == null) {
		}
	}
	
	public void conncet() {
		this.device = DefaultDevice.getInstance(context, listener);
		device.connect();
	}
	
	public void disconnect() {
		if (device != null) {
			device.disconnect();
		}
		device = null;
	}
	
	public ApiMonitor getDevice() {
		return device;
	}
}
