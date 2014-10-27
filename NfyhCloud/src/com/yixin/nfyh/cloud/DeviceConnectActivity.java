package com.yixin.nfyh.cloud;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.yixin.nfyh.cloud.ui.DeviceMsgView;

public class DeviceConnectActivity extends Activity {
	
	public static final int				EXTRA_SHOW				= 0;
	
	public static final int				EXTRA_SHOW_SUCCESS		= 1;
	
	public static final int				EXTRA_SHOW_ERROR		= 2;
	public static final int				EXTRA_DISMISS			= -1;
	
	public static final String			EXTRA_NAME				= "name";
	
	public static final String			INTENT_EXTRA_TIPS		= "TIPS";
	
	public static final String			INTENT_EXTRA_MESSAGE	= "MESSAGE";
	
	// private NfyhApplication mApplication;
	
	private DeviceMsgView				mDeviceMsgView;
	private DismissBoradcastReceiver	mReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDeviceMsgView = new DeviceMsgView(this);
		setContentView(mDeviceMsgView);
		mReceiver = new DismissBoradcastReceiver();
		registerReceiver(mReceiver, new IntentFilter("com.yixin.nfyh.cloud.device.dismiss"));
		init(getIntent());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
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
	
	class DismissBoradcastReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
		
	}
	
}
