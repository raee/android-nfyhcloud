package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.yixin.nfyh.cloud.ui.DeviceMsgView;

/**
 * 设备连接展示界面
 * 
 * @author ChenRui
 * 
 */
public class DeviceConnectActivity extends Activity {

	public static final int EXTRA_SHOW = 0; // 正常状态

	public static final int EXTRA_SHOW_SUCCESS = 1; // 显示成功

	public static final int EXTRA_SHOW_ERROR = 2; // 显示失败
	public static final int EXTRA_DISMISS = -1; // 取消

	public static final String EXTRA_NAME = "name";

	public static final String INTENT_EXTRA_TIPS = "TIPS";

	public static final String INTENT_EXTRA_MESSAGE = "MESSAGE";

	// private NfyhApplication mApplication;

	private DeviceMsgView mDeviceMsgView;
	private DismissBoradcastReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDeviceMsgView = new DeviceMsgView(this);
		setContentView(mDeviceMsgView);
		mReceiver = new DismissBoradcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				"com.yixin.nfyh.cloud.device.dismiss");
		intentFilter.addAction("com.yixin.nfyh.cloud.device.msg");
		registerReceiver(mReceiver, intentFilter);
		init(getIntent());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	/**
	 * 初始化
	 * 
	 * @param intent
	 */
	private void init(Intent intent) {
		int type = intent.getIntExtra(EXTRA_NAME, EXTRA_SHOW);
		String tips = intent.getStringExtra(INTENT_EXTRA_TIPS);
		String msg = intent.getStringExtra(INTENT_EXTRA_MESSAGE);
		switch (type) {
		case EXTRA_DISMISS:
			finish();
			break;
		case EXTRA_SHOW_ERROR:
			mDeviceMsgView.showError(tips, msg);
			break;
		case EXTRA_SHOW_SUCCESS:
			mDeviceMsgView.showSuccess(tips, msg);
			break;
		case EXTRA_SHOW:
		default:
			mDeviceMsgView.show(tips, msg);
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		init(intent);
	}

	/**
	 * 取消连接广播接收
	 * 
	 * @author ChenRui
	 * 
	 */
	class DismissBoradcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.yixin.nfyh.cloud.device.msg")) {
				init(intent);
				Log.i("rae", "收到广播com.yixin.nfyh.cloud.device.msg");
			} else {
				finish();
			}
		}

	}

}
