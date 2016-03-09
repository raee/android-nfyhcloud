package com.yixin.nfyh.cloud.device;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
import cn.rui.framework.utils.MediaUtil;

import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.monitors.sdk.api.BluetoothListener;
import com.yixin.monitors.sdk.model.PackageModel;
import com.yixin.monitors.sdk.model.SignDataModel;
import com.yixin.nfyh.cloud.DeviceConnectActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.activity.SignDetailActivity;

/**
 * 蓝牙数据体征回调监听实现。
 * 
 * @author ChenRui
 * 
 */
public class DeviceReceiverListener implements BluetoothListener {

	private static MediaPlayer Player;

	private Context context;
	private int connectTime;
	private int maxConnectTime = -1; // 重试一次
	private ApiMonitor mApi;

	public DeviceReceiverListener(Context context, ApiMonitor api) {
		this.context = context;
		this.mApi = api;
	}

	@SuppressWarnings("deprecation")
	private void startActivity(int type, String tips, String msg) {
		Intent intent = new Intent(context, DeviceConnectActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_TIPS, tips);
		intent.putExtra(DeviceConnectActivity.INTENT_EXTRA_MESSAGE, msg);
		intent.putExtra(DeviceConnectActivity.EXTRA_NAME, type);

		if (tips.contains("连接失败") && !msg.contains("断开")) {
			context.startActivity(intent);
			return;
		}

		PendingIntent penddingIntent = PendingIntent.getActivity(context, 0,
				intent, PendingIntent.FLAG_ONE_SHOT
						| PendingIntent.FLAG_UPDATE_CURRENT);

		// 推送到通知栏
		Notification n = new Notification(
				R.drawable.bluetooth_connected_normal, tips,
				System.currentTimeMillis());
		n.defaults = Notification.DEFAULT_LIGHTS;
		n.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
		n.setLatestEventInfo(context, tips, msg, penddingIntent);

		if (tips.contains("接收数据中")) {
			n.icon = R.drawable.bluetooth_conneting_anim_list;
			n.flags = Notification.FLAG_NO_CLEAR;
		}

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(7210, n);

		// 发送广播
		Intent broadcastintent = new Intent("com.yixin.nfyh.cloud.device.msg");
		broadcastintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		broadcastintent.putExtra(DeviceConnectActivity.INTENT_EXTRA_TIPS, tips);
		broadcastintent.putExtra(DeviceConnectActivity.INTENT_EXTRA_MESSAGE,
				msg);
		broadcastintent.putExtra(DeviceConnectActivity.EXTRA_NAME, type);
		context.sendBroadcast(broadcastintent);
	}

	private void show(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW, tips, msg);
	}

	private void showError(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW_ERROR, tips, msg);
		stopMusic();

	}

	private void showSuccess(String tips, String msg) {
		startActivity(DeviceConnectActivity.EXTRA_SHOW_SUCCESS, tips, msg);
		stopMusic();
	}

	@Override
	public void onStartDiscovery() {
		play(R.raw.tips, 0);
		show("扫描设备中", "");
	}

	@Override
	public void onStopDiscovery() {
		if (mApi.isConnected()) {
			showSuccess("连接成功", "");
		} else {
			showSuccess("扫描完成", "没发现设备！");
		}

	}

	@Override
	public void onOpenBluetooth() {
		show("打开蓝牙", "");
		play(R.raw.tips, 0);
	}

	@Override
	public void onCloseBluetooth() {
		// showError("蓝牙已经关闭", "");
		Toast.makeText(context, "蓝牙已经关闭，设备连接将断开！", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBluetoothStateChange(int state, BluetoothDevice device) {
		// show("尝试连接", "");
	}

	@Override
	public boolean onFindBluetooth(BluetoothDevice device, boolean isBonded) {
		// show("发现设备", "");
		play(R.raw.tips, R.raw.open_bluetooth);
		return false;
	}

	@Override
	public void onBluetoothBonding(BluetoothDevice device) {
		play(R.raw.tips, R.raw.wait);
		show("设备配对中", "如果出现配对框3秒后不消失，请手动输入配对码。");
	}

	@Override
	public void onBluetoothSetPin(BluetoothDevice device) {
		show("设置配对码", "");
	}

	@Override
	public void onBluetoothBonded(BluetoothDevice device) {
		// show("设备配对成功", "");
	}

	@Override
	public void onBluetoothBondNone(BluetoothDevice device) {
		onError(0, "蓝牙配对失败，部分手机由于系统限制导致自动配对失败，如果设备上蓝牙状态显示已经连接，可以不必再重新连接。");
		// showError("配对失败", "");
	}

	@Override
	public void onStartReceive() {
		show("接收数据中...", "正在接收数据，请稍候...");
		playMusic();
	}

	@Override
	public void onReceiving(byte[] data) {
	}

	@Override
	public void onReceived(byte[] data) {
		showSuccess("接收完成", "请查看您的体征。");
		clearNotification();
	}

	@Override
	public void onReceived(PackageModel model) {
		stopMusic();
		Log.i("CoreServerBinder", "---- 接收到数据  -----");
		for (SignDataModel m : model.getSignDatas()) {
			Log.i("CoreServerBinder", m.getDataName() + "|" + m.getValue());
		}

		Intent intent = new Intent(context, SignDetailActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		intent.putExtra("data", model);
		intent.putExtra(Intent.EXTRA_TEXT, "-1");
		intent.putExtra("from", "device"); // 数据来自设备
		context.startActivity(intent);
		dismiss();
	}

	private void dismiss() {
		context.sendBroadcast(new Intent("com.yixin.nfyh.cloud.device.dismiss"));
	}

	@Override
	public void onConnected(BluetoothDevice device) {
		showSuccess("设备连接成功", "");
		play(R.raw.tips, R.raw.bluetooth_success);
		clearNotification();
	}

	@Override
	public void onError(int errorCode, String msg) {
		play(R.raw.tips_error, R.raw.bluetooth_error);
		// 关闭蓝牙
		BluetoothAdapter.getDefaultAdapter().disable();

		stopMusic();
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

		} else if (msg.contains("断开连接")) {
			showError("谢谢使用！", "设备已经断开连接了。谢谢您的使用！");
		} else {
			showError("连接失败", msg);
		}
	}

	@Override
	public void onBluetoothCancle() {
		stopMusic();
	}

	private void stopMusic() {
		MediaUtil.stopPlayMusic(Player);
		Player = null;
	}

	private void clearNotification() {

		// 移除通知栏
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(7210);
	}

	private void playMusic() {
		try {
			if (Player == null) {
				Player = MediaUtil.playMusic(context, R.raw.ring);
			}

			if (!Player.isPlaying()) {
				Player.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			stopMusic();
		}
	}

	// 播放提示音
	private void play(int tips, int wmv) {

		try {

			stopMusic(); // 停止之前播放的

			if (wmv == 0)
				wmv = tips;
			Player = MediaPlayer.create(context, wmv);

			Player.setVolume(1, 1);
			// player.prepare();
			// Player.setOnPreparedListener(new BluetoothPreparedListener());
			// Player.setOnCompletionListener(new
			// BluetoothCompletionListener(wmv));

			// Player.setLooping(true);
			Player.start();

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "播放提示异常！", Toast.LENGTH_SHORT).show();
			stopMusic();
		}
	}

	class BluetoothPreparedListener implements OnPreparedListener {

		@Override
		public void onPrepared(MediaPlayer mp) {
			mp.start();
		}
	}

	// class BluetoothCompletionListener implements OnCompletionListener {
	// private int resid;
	//
	// BluetoothCompletionListener(int resid) {
	// this.resid = resid;
	// }
	//
	// @Override
	// public void onCompletion(MediaPlayer mp) {
	// if (resid != 0) {
	// Player = MediaPlayer.create(context, resid);
	// try {
	// Player.setVolume(1, 1);
	// Player.setOnPreparedListener(new BluetoothPreparedListener());
	// } catch (Exception e) {
	// Toast.makeText(context, "onCompletion Error",
	// Toast.LENGTH_SHORT).show();
	// e.printStackTrace();
	// MediaUtil.stopPlayMusic(Player);
	// }
	// }
	// MediaUtil.stopPlayMusic(mp);
	// }
	// }

	@Override
	public void onBluetoothSendData(byte[] arg0) {
		showSuccess("设备通讯成功", "您可以开始测量了！");
		stopMusic();
		clearNotification();
	}

	@Override
	public void onStartConnection(BluetoothDevice arg0) {
		show("正在连接...", "正在连接，请稍后...");
	}

}
