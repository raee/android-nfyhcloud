package com.yixin.nfyh.cloud.device;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.yixin.device.bluetooth.BluetoothCallback;
import com.yixin.device.core.BluetoothDeviceFactory;
import com.yixin.device.core.DeviceReceviceCallback;
import com.yixin.device.core.IBluetoothDevice;
import com.yx.model.DefiniteData;
import com.yx.model.DefiniteEcgData;
import com.yx.model.FinishPackageData;
import com.yx.model.FinishPackageData.litPackDataType;

/**
 * 迈瑞监测设备接口
 * 
 * @author MrChenrui
 */
public class MindrayAPI implements IDevice {

	private IDeviceListener listener;
	private IBluetoothDevice device;
	private boolean isReceviceing = false; // 是否正在接收数据

	public MindrayAPI(Context context) {
		device = BluetoothDeviceFactory.getDevice(context,
				BluetoothDeviceFactory.PRODUCT_MINARAY);
		device.setOnBluetoothDeviceListener(new BluetoothDeviceListener());
		device.setOnReceviceListener(new ReceviceListener());
	}

	@Override
	public void open() {
		// device.connect();
	}

	@Override
	public void connect() {
		device.connect();
	}

	@Override
	public void disConnect() {
		device.disconnect();
	}

	@Override
	public void setListener(IDeviceListener l) {
		this.listener = l;
	}

	@Override
	public boolean isConnected() {
		return device.isConnected();
	}

	private class ReceviceListener implements DeviceReceviceCallback {
		@Override
		public void onRecevieData(FinishPackageData data) {
			List<litPackDataType> datas = data.getLitPackDataType();

			for (litPackDataType litPackDataType : datas) {
				if (litPackDataType == null) {
					continue;
				}

				List<DefiniteData> models = litPackDataType
						.getLitDefiniteData(); // 普通包
				List<DefiniteEcgData> ecgDatas = litPackDataType
						.getLitECGData(); // 心电包

				if (models == null || ecgDatas != null && ecgDatas.size() > 0) // 解析心电包
				{
					for (DefiniteEcgData ecg : ecgDatas) {
						if (ecg == null) {
							continue;
						}
						String name = "心电";
						String valueString = ecg.getEcgData(); // 心电数组，格式：
																// 0.2072,0.2072,0.2072,0.2072,
						DeviceCallbakModel callbackModel = new DeviceCallbakModel();
						callbackModel.setDataName(name);
						callbackModel.setValue(valueString);
						callbackModel.setDate(litPackDataType.getPackDataType()
								.getReceiveDate());
						listener.onReceive(callbackModel);
					}
					continue;
				}

				for (DefiniteData model : models) {
					if (model == null) {
						continue;
					}

					String name = model.getItemTypeName();
					String valueString = model.getDataItemValue();

					DeviceCallbakModel callbackModel = new DeviceCallbakModel();
					callbackModel.setDataName(name);
					callbackModel.setValue(valueString);
					callbackModel.setDate(litPackDataType.getPackDataType()
							.getReceiveDate());
					listener.onReceive(callbackModel);
				}
			}

			isReceviceing = false; // 设置接收数据状态
			listener.onReceiveSuccess();
		}
	}

	private class BluetoothDeviceListener implements BluetoothCallback {

		@Override
		public void onBluetooth(BluetoothDevice arg0) {
			listener.onConnected();
		}

		@Override
		public void onBluetoothDisconnect() {
			listener.onDisConnect();
		}

		@Override
		public void onBluetoothFail(int code, String msg) {
			listener.onConnectError(code, msg);
		}

		@Override
		public void onBluetoothRecevice(byte[] data) {
		}

		@Override
		public void onFindBluetooth() {
			listener.onConnect();
		}

		@Override
		public void onFindFinshBluetooth() {
			listener.onConnect();
		}

		@Override
		public void onBluetoothReceviceing() {
			if (isReceviceing)
				return;
			listener.onProgress(-1, 0);
			isReceviceing = true;

		}

		@Override
		public void onBluetoothSendData(byte[] data) {
			listener.onProgress(STEP_SEND_DATA, 0);
		}

		@Override
		public void onBluetoothBound(BluetoothDevice device, int state,
				String msg) {
			listener.onBound(msg);
		}

	}
}
