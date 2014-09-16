//package com.yixin.nfyh.cloud.device;
//
//import java.util.List;
//
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//
//import com.yixin.monitors.sdk.MonitorSdkFactory;
//import com.yixin.monitors.sdk.api.ApiMonitor;
//import com.yixin.monitors.sdk.api.BluetoothListener;
//import com.yixin.monitors.sdk.model.PackageModel;
//import com.yx.model.DefiniteData;
//import com.yx.model.DefiniteEcgData;
//import com.yx.model.FinishPackageData;
//import com.yx.model.FinishPackageData.litPackDataType;
//
///**
// * 迈瑞监测设备接口
// * 
// * @author MrChenrui
// */
//public class MindrayAPI implements IDevice {
//
//	private IDeviceListener listener;
//	private ApiMonitor device;
//	private boolean isReceviceing = false; // 是否正在接收数据
//
//	public MindrayAPI(Context context) {
//		device = MonitorSdkFactory.getApiMonitor(context, MonitorSdkFactory.MINDRAY);
//		device.setBluetoothListener(listener)
//	}
//
//	@Override
//	public void open() {
//		// device.connect();
//	}
//
//	@Override
//	public void connect() {
//		device.connect();
//	}
//
//	@Override
//	public void disConnect() {
//		device.disconnect();
//	}
//
//	@Override
//	public void setListener(IDeviceListener l) {
//		this.listener = l;
//	}
//
//	@Override
//	public boolean isConnected() {
//		return device.isConnected();
//	}
//
//	private class ReceviceListener implements DeviceReceviceCallback {
//		@Override
//		public void onRecevieData(FinishPackageData data) {
//			List<litPackDataType> datas = data.getLitPackDataType();
//
//			for (litPackDataType litPackDataType : datas) {
//				if (litPackDataType == null) {
//					continue;
//				}
//
//				List<DefiniteData> models = litPackDataType
//						.getLitDefiniteData(); // 普通包
//				List<DefiniteEcgData> ecgDatas = litPackDataType
//						.getLitECGData(); // 心电包
//
//				if (models == null || ecgDatas != null && ecgDatas.size() > 0) // 解析心电包
//				{
//					for (DefiniteEcgData ecg : ecgDatas) {
//						if (ecg == null) {
//							continue;
//						}
//						String name = "心电";
//						String valueString = ecg.getEcgData(); // 心电数组，格式：
//																// 0.2072,0.2072,0.2072,0.2072,
//						DeviceCallbakModel callbackModel = new DeviceCallbakModel();
//						callbackModel.setDataName(name);
//						callbackModel.setValue(valueString);
//						callbackModel.setDate(litPackDataType.getPackDataType()
//								.getReceiveDate());
//						listener.onReceive(callbackModel);
//					}
//					continue;
//				}
//
//				for (DefiniteData model : models) {
//					if (model == null) {
//						continue;
//					}
//
//					String name = model.getItemTypeName();
//					String valueString = model.getDataItemValue();
//
//					DeviceCallbakModel callbackModel = new DeviceCallbakModel();
//					callbackModel.setDataName(name);
//					callbackModel.setValue(valueString);
//					callbackModel.setDate(litPackDataType.getPackDataType()
//							.getReceiveDate());
//					listener.onReceive(callbackModel);
//				}
//			}
//
//			isReceviceing = false; // 设置接收数据状态
//			listener.onReceiveSuccess();
//		}
//	}
//
//	private class BluetoothDeviceListener implements BluetoothListener {
//
//		@Override
//		public void onBluetooth(BluetoothDevice arg0) {
//			listener.onConnected();
//		}
//
//		@Override
//		public void onBluetoothDisconnect() {
//			listener.onDisConnect();
//		}
//
//		@Override
//		public void onBluetoothFail(int code, String msg) {
//			listener.onConnectError(code, msg);
//		}
//
//		@Override
//		public void onBluetoothRecevice(byte[] data) {
//		}
//
//		@Override
//		public void onFindBluetooth() {
//			listener.onConnect();
//		}
//
//		@Override
//		public void onFindFinshBluetooth() {
//			listener.onConnect();
//		}
//
//		@Override
//		public void onBluetoothReceviceing() {
//			if (isReceviceing)
//				return;
//			listener.onProgress(-1, 0);
//			isReceviceing = true;
//
//		}
//
//
//		@Override
//		public void onBluetoothBound(BluetoothDevice device, int state,
//				String msg) {
//		}
//
//		@Override
//		public void onStartDiscovery() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onStopDiscovery() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onOpenBluetooth() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onCloseBluetooth() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onBluetoothStateChange(int state, BluetoothDevice device) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean onFindBluetooth(BluetoothDevice device, boolean isBonded) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public void onBluetoothBonding(BluetoothDevice device) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onBluetoothSetPin(BluetoothDevice device) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onBluetoothBonded(BluetoothDevice device) {
//			listener.onBound(msg);
//			
//		}
//
//		@Override
//		public void onBluetoothBondNone(BluetoothDevice device) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onStartReceive() {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onReceiving(byte[] data) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onReceived(byte[] data) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onReceived(PackageModel model) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onConnected(BluetoothDevice device) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onError(int errorCode, String msg) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onBluetoothCancle() {
//			// TODO Auto-generated method stub
//			
//		}
//
//	}
//}
