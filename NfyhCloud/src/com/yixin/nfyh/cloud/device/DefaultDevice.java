/**
 * 
 */
package com.yixin.nfyh.cloud.device;

import java.sql.SQLException;

import android.content.Context;

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
	private static IDevice DEFAUT_DEVICE = null;

	private ISignDevice signDevice;
	private Context context;

	/**
	 * 获取设备接口的实例
	 * 
	 * @param context
	 * @return
	 * @throws DeviceException
	 * @throws SQLException
	 */
	public static IDevice getInstance(Context context, IDeviceListener l)
			throws DeviceException, SQLException {
		return new OrmonAPI(context, l);
		// DefaultDevice dev = new DefaultDevice(context);
		// return dev.getDevice();
	}

	/**
	 * 私有构造函数
	 * 
	 * @param context
	 * @throws SQLException
	 */
	private DefaultDevice(Context context) throws SQLException {
		this.context = context;
		this.signDevice = NfyhCloudDataFactory.getFactory(context)
				.getSignDevice();
	}

	/**
	 * 获取配置，从用户偏好中
	 * 
	 * @return
	 * @throws DeviceException
	 *             设备错误
	 * @throws SQLException
	 *             数据库错误
	 */
	public IDevice getDevice() throws DeviceException, SQLException {
		if (DEFAUT_DEVICE != null) {
			return DEFAUT_DEVICE;
		}

		Devices dev = this.signDevice.getCurrentDevices();

		if (dev == null) {
			throw new DeviceException("默认设备获取失败");
		}

		String id = dev.getDevId();

		if (id.equals("devices01")) {
			// 迈瑞
			DEFAUT_DEVICE = new MindrayAPI(context);
		}

		return DEFAUT_DEVICE;
	}
}
