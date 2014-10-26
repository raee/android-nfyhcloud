package com.yixin.nfyh.cloud.device;

import java.util.Timer;
import java.util.TimerTask;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.Html;
import android.util.Log;
import cn.rui.framework.utils.MediaUtil;

import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.monitors.sdk.api.BluetoothListener;
import com.yixin.monitors.sdk.model.PackageModel;
import com.yixin.monitors.sdk.model.SignDataModel;
import com.yixin.nfyh.cloud.DeviceConnectActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.activity.SignDetailActivity;

public class DeviceReceiverListener implements BluetoothListener {

	private static MediaPlayer	Player;

	private Context				context;
	private int					connectTime;
	private int					maxConnectTime	= 1;	// 重试一次
	private ApiMonitor			mApi;

	// private DeviceMsgView mView;

	public DeviceReceiverListener(Context context, ApiMonitor api) {
		this.context = context;
		this.mApi = api;
		// mView = new DeviceMsgView(context);
		// setName(mApi.getDeviceInfo().getDeviceName());
	}

	// public void setContentView(View view) {
	// setContentViewGroup(view);
	// }

	// public void pause() {
	// setShowable(false);
	// }
	//
	// public void resume() {
	// setShowable(true);
	//
	// }

	private void startActivity(int type, String tips, String msg) {
		Intent intent = new Intent(context, DeviceConnectActivity.class);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_TIPS, tips);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_MESSAGE, msg);
		intent.putExtra(DeviceConnectActivity.EXTRA_NAME, type);
		context.startActivity(intent);
	}

	private void show(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW, tips, msg);
	}

	private void showError(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW_ERROR, tips, msg);

	}

	private void showSuccess(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW_SUCCESS, tips, msg);
	}

	private void dismiss() {
		startActivity(DeviceConnectActivity.EXTRA_DISMISS, "", "");

	}

	@Override
	public void onStartDiscovery() {
		show("扫描设备中", "");
	}

	@Override
	public void onStopDiscovery() {
		if (mApi.isConnected()) {
			showSuccess("连接成功", "");
		}

	}

	@Override
	public void onOpenBluetooth() {
		show("打开蓝牙", "");
	}

	@Override
	public void onCloseBluetooth() {
		showError("蓝牙已经关闭", "");
	}

	@Override
	public void onBluetoothStateChange(int state, BluetoothDevice device) {
		// show("尝试连接", "");
	}

	@Override
	public boolean onFindBluetooth(BluetoothDevice device, boolean isBonded) {
		// show("发现设备", "");
		return false;
	}

	@Override
	public void onBluetoothBonding(BluetoothDevice device) {
		show("设备配对中", "如果出现配对框3秒后不消失，请手动输入配对码。");
	}

	@Override
	public void onBluetoothSetPin(BluetoothDevice device) {
		show("设置配对码", "");
	}

	@Override
	public void onBluetoothBonded(BluetoothDevice device) {
		show("设备配对成功", "");
	}

	@Override
	public void onBluetoothBondNone(BluetoothDevice device) {
		showError("配对失败", "蓝牙配对失败，请重试。");
	}

	@Override
	public void onStartReceive() {
		show("接收数据中", "");
		playMusic();
	}

	@Override
	public void onReceiving(byte[] data) {
	}

	@Override
	public void onReceived(byte[] data) {
		show("接收完成", "");
	}

	@Override
	public void onReceived(PackageModel model) {
		stopMusic();
		show("接收完成", "");
		Log.i("CoreServerBinder", "---- 接收到数据  -----");
		for (SignDataModel m : model.getSignDatas()) {
			Log.i("CoreServerBinder", m.getDataName() + "|" + m.getValue());
		}

		Intent intent = new Intent(context, SignDetailActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		intent.putExtra("data", model);
		intent.putExtra(Intent.EXTRA_TEXT, "-1");
		context.startActivity(intent);
		dismiss();

	}

	@Override
	public void onConnected(BluetoothDevice device) {
		showSuccess("连接成功", "");
	}

	@Override
	public void onError(int errorCode, String msg) {

		if (connectTime < maxConnectTime) {
			msg = "5秒后尝试连接（" + connectTime + "/" + maxConnectTime + "）<br>"
					+ msg;
			msg = Html.fromHtml(msg).toString();
			showError("连接失败", msg);
			connectTime++;
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					mApi.connect(); // 连接
				}
			}, 5000);

		} else {
			showError("连接失败", msg);
		}
	}

	@Override
	public void onBluetoothCancle() {
		showError("连接取消", "设备连接已经取消，如需连接请再点击连接。");
	}

	private void stopMusic() {
		MediaUtil.stopPlayMusic(Player);
	}

	private void playMusic() {
		try {
			if (Player == null) {
				Player = MediaUtil.playMusic(context, R.raw.ring);
			}

			if (!Player.isPlaying()) {
				Player.prepare();
				Player.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
