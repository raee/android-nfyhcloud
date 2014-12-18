package com.yixin.nfyh.cloud.sos;

import com.baidu.mapapi.map.BaiduMap;

import android.content.Context;
import android.os.Bundle;

/**
 * 流程指挥者，处理每一个的流程具体的流向
 * 
 * @author MrChenrui
 * 
 */
public class FloweDirector implements IFlowerResult {

	/*
	 * 这里定义好所有的流程
	 */
	SOSFlower.SOSLocationFlower			location;
	SOSFlower.SOSSelectEventFlower		selectEvent;
	SOSFlower.SOSSendSOSToServerFlower	sendToServer;
	SOSFlower.SOSCallPhoneFlower		callPhone;
	private boolean						isStarted	= false;
	private OnekeySosCallback			callback;
	private SOSFlower					flower;

	public FloweDirector(Context context, OnekeySosCallback l, BaiduMap map) {
		flower = new SOSFlower(context, this);
		this.callback = l;

		location = flower.new SOSLocationFlower(map);
		selectEvent = flower.new SOSSelectEventFlower();
		sendToServer = flower.new SOSSendSOSToServerFlower();
		callPhone = flower.new SOSCallPhoneFlower();

		// 开始定位
		location.start(null);
	}

	public void start() {
		callback.sosStarted();
		flower.stopTTS();
		this.isStarted = true;
		String jwd = location.getData().getString("jwd");
		if (jwd == null)
			location.start(null);
		else
			selectEvent.start(location.getData());
	}

	/**
	 * 重新定位
	 */
	public void reLocation() {
		location.getBaiduLocation().start();
		location.getBaiduLocation().request();
	}

	public void start(String name) {
		selectEvent.setEventName(name);
		start();
	}

	public void stop() {
		flower.stop();
	}

	@Override
	public void onLocation(int status, Bundle data) {
		switch (status) {
			case IFlowerResult.FLOW_STATUS_ERROR:
				callback.sosError(status, data.getString(EXTRA_DATA));
				break;
			case IFlowerResult.FLOW_STATUS_SUCCESS:
				callback.sosLocated(data.getString("jwd"), data.getString("address"));
				break;
			default:
				break;
		}
		if (isStarted) this.selectEvent.start(data); // 定位结束，开始选择事件
	}

	@Override
	public void onSelectEvent(int status, Bundle data) {
		switch (status) {
			case IFlowerResult.FLOW_STATUS_ERROR:
				callback.sosError(status, "您没有选择发生的事件");
				break;
			case IFlowerResult.FLOW_STATUS_SUCCESS:
				sendToServer.start(data); // 选择事件成功，发送到服务器
				callPhone.start(data); // 开始拨打电话
				break;

			default:
				break;
		}
	}

	@Override
	public void onSendToserver(int status, Bundle data) {
		switch (status) {
			case IFlowerResult.FLOW_STATUS_ERROR:
				callback.sosError(status, "位置上传失败");
				break;
			case IFlowerResult.FLOW_STATUS_SUCCESS:
				break;

			default:
				break;
		}
	}

	@Override
	public void onCallingPhone(int status, Bundle data) {
		try {
			callback.sosCalled(data.getString("number"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onException(int status, Bundle data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFlowError(int status, Bundle data) {
		callback.sosError(status, data.getString(IFlowerResult.EXTRA_DATA));
	}

	@Override
	public void onFinsh(int status, Bundle data) {
		if (status == IFlowerResult.FLOW_STATUS_ERROR) callback.sosError(status, data.getString(IFlowerResult.EXTRA_DATA));
		callback.sosFinsh("点击继续发出呼救");
	}

}
