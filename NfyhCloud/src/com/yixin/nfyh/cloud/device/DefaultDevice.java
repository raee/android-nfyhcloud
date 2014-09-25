/**
 * 
 */
package com.yixin.nfyh.cloud.device;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.yixin.monitors.sdk.MonitorSdkFactory;
import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.monitors.sdk.api.BluetoothListener;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;

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
	 * @throws DeviceException
	 * @throws SQLException
	 */
	public static ApiMonitor getInstance(Context context, BluetoothListener l) {
		ApiMonitor api = null;
		try {
			ISignDevice dbDevice = NfyhCloudDataFactory.getFactory(context).getSignDevice();
			String name = dbDevice.getCurrentDevices().getDevId();
			int sdk = dbDevice.getCurrentDevices().getSdk();
			Log.i("DefaultDevice", "获取到设备接口为：" + name);
			//			if ("HEM-7081-IT".equals(name)) {
			//				api = MonitorSdkFactory.getApiMonitor(context, MonitorSdkFactory.OMRON);
			//				api.setBluetoothListener(l);
			//			}
			//			else {
			api = MonitorSdkFactory.getApiMonitor(context, sdk);
			api.setBluetoothListener(l);
			//			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			api = MonitorSdkFactory.getApiMonitor(context, MonitorSdkFactory.MINDRAY);
			api.setBluetoothListener(l);
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
	
	//	/**
	//	 * 获取配置，从用户偏好中
	//	 * 
	//	 * @return
	//	 * @throws DeviceException
	//	 *             设备错误
	//	 * @throws SQLException
	//	 *             数据库错误
	//	 */
	//	public IDevice getDevice() throws DeviceException, SQLException {
	//		if (DEFAUT_DEVICE != null) { return DEFAUT_DEVICE; }
	//		
	//		Devices dev = this.signDevice.getCurrentDevices();
	//		
	//		if (dev == null) { throw new DeviceException("默认设备获取失败"); }
	//		
	//		String id = dev.getDevId();
	//		
	//		if (id.equals("devices01")) {
	//			// 迈瑞
	//			//DEFAUT_DEVICE = new MindrayAPI(context);
	//		}
	//		
	//		return DEFAUT_DEVICE;
	//	}
}
