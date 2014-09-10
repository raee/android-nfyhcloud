package com.yixin.nfyh.cloud.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;

import com.yixin.device.bluetooth.BluetoothCallback;
import com.yixin.nfyh.cloud.activity.SignDetailActivity;
import com.yixin.nfyh.cloud.device.DefaultDevice;
import com.yixin.nfyh.cloud.device.DeviceCallbakModel;
import com.yixin.nfyh.cloud.device.DeviceException;
import com.yixin.nfyh.cloud.device.IDevice;
import com.yixin.nfyh.cloud.device.IDeviceListener;

/**
 * 核心服务，设备接收
 * 
 * @author MrChenrui
 */
public class CoreServerBinder extends Binder {
	private class DeviceConnectListener implements IDeviceListener {

		private int connectTime;
		private Intent intent;
		private int maxConnectTime = 1; // 重试一次

		public DeviceConnectListener() {
			intent = new Intent();
		}

		@Override
		public void onBound(String msg) {
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, msg);
		}

		@Override
		public void onConnect() {
			show("设备开始连接");
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTING, "正在扫描设备...");
		}

		@Override
		public void onConnected() {
			show("设备连接成功");
			sendIntent(ACTION_BLUETOOTH_DEVICE_CONNECTED, "蓝牙设备连接成功！");
		}

		@Override
		public void onConnectError(int code, String msg) {
			show("设备连接异常:" + msg);
			if (code == BluetoothCallback.CODE_BLUETOOTH_EXCTION) {
				if (connectTime < maxConnectTime) {
					show("重试连接：" + connectTime);
					connectTime++;
					new Timer().schedule(new TimerTask() {

						@Override
						public void run() {
							device.connect(); // 连接
						}
					}, 3000);

				}
			} else if (code == BluetoothCallback.CODE_BLUETOOTH_NOT_FOUND) {
			} else if (code == BluetoothCallback.CODE_BLUETOOTH_DISCONNETED) {
				msg = "设备已经断开连接，请重新连接！";
			} else {
				Log.e(tag, msg);
				msg = "设备连接出错,请尝试重新连接！";
			}
			sendIntent(ACTION_BLUETOOTH_DEVICE_FAILD, msg);

		}

		@Override
		public void onDisConnect() {
			show("设备断开连接");
			sendIntent(ACTION_BLUETOOTH_DEVICE_DISCONNECTED, "监测设备已经断开，请重新连接！");
		}

		@Override
		public void onProgress(int step, int progress) {
			show("开始接收数据：");
			signModels.clear();
			if (step == IDevice.STEP_SEND_DATA) {
				sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICED, "发送数据完毕");
			} else {
				sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICEING, "正在传输数据...");
			}
		}

		@Override
		public void onReceive(DeviceCallbakModel model) {
			signModels.add(model);
			show("当前接收-->" + model.getDataName() + ";" + model.getValue());
			sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICED, "等待传输数据..");
		}

		@Override
		public void onReceiveSuccess() {
			show("接收到数据");
			if (signModels.size() < 1) {
				show("没有接收到数据...");
				return;
			}
			for (DeviceCallbakModel m : signModels) {
				show(m.getDataName() + ";" + m.getValue());
			}
			Intent intent = new Intent(context, SignDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle bundle = new Bundle();
			bundle.putString(Intent.EXTRA_TEXT, "-1");
			bundle.putSerializable("data", (Serializable) signModels);
			intent.putExtras(bundle);
			context.startActivity(intent);

			intent.putExtras(bundle);
			sendIntent(ACTION_BLUETOOTH_DEVICE_RECEVICED);

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
			if (msg != null)
				this.intent.putExtra(Intent.EXTRA_TEXT, msg);
			context.sendBroadcast(intent);
			//
			// Intent deviceIntent = new Intent(context,
			// ReceiveDataActivity.class);
			// deviceIntent.setClass(context, ReceiveDataActivity.class);
			// deviceIntent.putExtra(Intent.EXTRA_TEXT, msg);
			// deviceIntent.setAction(action);
			// deviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(deviceIntent);

		}

		private void show(Object msg) {
			Log.i(tag, msg.toString());
		}
	}

	/**
	 * 蓝牙连接成功！
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_CONNECTED = "com.yixin.nfyh.cloud.bluetoth.device.connected.actoin";

	/**
	 * 蓝牙正在连接
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_CONNECTING = "com.yixin.nfyh.cloud.bluetoth.device.connecting.actoin";

	/**
	 * 　蓝牙断开
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_DISCONNECTED = "com.yixin.nfyh.cloud.bluetoth.device.disconnected.actoin";

	/**
	 * 蓝牙连接失败
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_FAILD = "com.yixin.nfyh.cloud.bluetoth.device.faild.actoin";
	/**
	 * 　蓝牙完成接收数据
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_RECEVICED = "com.yixin.nfyh.cloud.bluetoth.device.receviced.actoin";

	/**
	 * 　蓝牙正在接收数据
	 */
	public static final String ACTION_BLUETOOTH_DEVICE_RECEVICEING = "com.yixin.nfyh.cloud.bluetoth.device.receviceing.actoin";
	private Context context;
	private IDevice device;
	private IDeviceListener listener;
	private List<DeviceCallbakModel> signModels;

	private String tag = "CoreServerBinder";

	public CoreServerBinder(Context context) {
		this.context = context;
		listener = new DeviceConnectListener();
		signModels = new ArrayList<DeviceCallbakModel>();
		setDefaultDevice();
	}

	/**
	 * 设置默认的监测设备
	 */
	public void setDefaultDevice() {
		try {
			this.device = DefaultDevice.getInstance(context, listener);
		} catch (DeviceException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置设备连接
	 * 
	 * @param device
	 */
	public void setDevice(IDevice device) {
		this.device = device;
	}

	public void conncet() {
		device.open();
		device.connect();
	}

	public void conncet(IDeviceListener l) {
		if (l != null) {
			device.setListener(l);
			device.open();
			device.connect();
		}
	}

	public IDevice getDevice() {
		return device;
	}
}
