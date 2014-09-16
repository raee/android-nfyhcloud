//package com.yixin.nfyh.cloud.device;
//
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//
//import com.yixin.device.bluetooth.BluetoothCallback;
//import com.yixin.device.core.BluetoothDeviceFactory;
//import com.yixin.device.core.DeviceReceviceCallback;
//import com.yixin.device.core.IBluetoothDevice;
//import com.yx.model.DefiniteData;
//import com.yx.model.FinishPackageData;
//import com.yx.model.FinishPackageData.litPackDataType;
//
///**
// * 欧姆龙接口
// * 
// * @author ChenRui
// * 
// */
//public class OrmonAPI implements IDevice, DeviceReceviceCallback,
//		BluetoothCallback {
//	private IBluetoothDevice mDevice;
//	private IDeviceListener mListener;
//
//	public OrmonAPI(Context context, IDeviceListener l) {
//		mDevice = BluetoothDeviceFactory.getDevice(context,
//				BluetoothDeviceFactory.PRODUCT_OMRON);
//		mDevice.setOnBluetoothDeviceListener(this);
//		mDevice.setOnReceviceListener(this);
//		if (l == null) {
//			throw new NullPointerException("欧姆龙回调接口为空！");
//		}
//		setListener(l);
//	}
//
//	@Override
//	public void open() {
//		// mDevice.connect();
//	}
//
//	@Override
//	public void connect() {
//		mDevice.connect();
//	}
//
//	@Override
//	public void disConnect() {
//		mDevice.disconnect();
//	}
//
//	@Override
//	public void setListener(IDeviceListener l) {
//		this.mListener = l;
//	}
//
//	@Override
//	public boolean isConnected() {
//		// return mDevice.isConnected();
//		return false;
//	}
//
//	@Override
//	public void onBluetooth(BluetoothDevice device) {
//		mListener.onConnect();
//	}
//
//	@Override
//	public void onBluetoothBound(BluetoothDevice device, int state, String msg) {
//		mListener.onBound(msg);
//		mListener.onConnected();
//	}
//
//	@Override
//	public void onBluetoothDisconnect() {
//		mListener.onDisConnect();
//	}
//
//	@Override
//	public void onBluetoothFail(int code, String msg) {
//		mListener.onConnectError(code, msg);
//	}
//
//	@Override
//	public void onBluetoothRecevice(byte[] data) {
//	}
//
//	@Override
//	public void onBluetoothReceviceing() {
//	}
//
//	@Override
//	public void onBluetoothSendData(byte[] arg0) {
//	}
//
//	@Override
//	public void onFindBluetooth() {
//	}
//
//	@Override
//	public void onFindFinshBluetooth() {
//	}
//
//	@Override
//	public void onRecevieData(FinishPackageData datas) {
//		litPackDataType items = datas.getLitPackDataType().get(0);
//		for (DefiniteData data : items.getLitDefiniteData()) {
//			DeviceCallbakModel model = new DeviceCallbakModel();
//			model.setDataName(data.getItemTypeName());
//			model.setDate(items.getPackDataType().getReceiveDate());
//			model.setValue(data.getDataItemValue());
//			mListener.onReceive(model);
//		}
//		mListener.onReceiveSuccess();
//	}
//}
