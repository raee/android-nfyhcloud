/**
 * 
 */
package com.yixin.nfyh.cloud.device;

import java.sql.SQLException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import cn.rui.framework.ui.RuiDialog;

import com.yixin.monitors.sdk.MonitorSdkFactory;
import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.nfyh.cloud.DeviceConnectActivity;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Devices;

/**
 * 默认监测设备获取
 * 
 * @author MrChenrui
 * 
 */
public class DefaultDevice {
	
	/**
	 * 获取设备接口的实例
	 * 
	 * @param context
	 * @return
	 * @throws SQLException
	 */
	public static ApiMonitor getInstance(Context context) {
		ApiMonitor api = null;
		try {
			ISignDevice dbDevice = NfyhCloudDataFactory.getFactory(context).getSignDevice();
			Devices device = dbDevice.getCurrentDevices();
			int sdk = dbDevice.getCurrentDevices().getSdk();
			Log.i("DefaultDevice", "获取到设备接口为：" + device.getDeviceName());
			api = MonitorSdkFactory.getApiMonitor(context, sdk);
			api.configDevice(device.getDeviceName(), device.getDevicePin());
			// api.setBluetoothListener(l);
			// }
		}
		catch (SQLException e) {
			e.printStackTrace();
			api = MonitorSdkFactory.getApiMonitor(context, MonitorSdkFactory.MINDRAY);
			// api.setBluetoothListener(l);
			Log.i("DefaultDevice", "没有获取到设备接口，默认为迈瑞设备！");
		}
		return api;
	}
	
	public static void setCurrentDevice(Context context, int sdk) {
		try {
			ISignDevice dbDevice = NfyhCloudDataFactory.getFactory(context).getSignDevice();
			String name = sdk == MonitorSdkFactory.MINDRAY ? "Mindray-H900" : "HEM-7081-IT";
			
			if (name != null) {
				dbDevice.setCurrentDevices(name);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 私有构造函数
	 * 
	 * @param context
	 * @throws SQLException
	 */
	private DefaultDevice() throws SQLException {
	}
	
	public static void connect(Context context, final ApiMonitor api) {
		if (api.isConnected()) {
			new RuiDialog.Builder(context).buildTitle("断开连接").buildMessage("设备已经连接，是否要断开监测设备？").buildLeftButton("否", null).buildRight("断开", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					api.disconnect();
					dialog.dismiss();
				}
			}).show();
		}
		else {
			api.connect();
			context.startActivity(new Intent(context, DeviceConnectActivity.class));
		}
	}
}
