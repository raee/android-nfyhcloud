package com.yixin.nfyh.cloud.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.yixin.nfyh.cloud.AlarmActivity;
import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 闹钟核心服务
 * 
 * @author MrChenrui
 * 
 */
public class AlarmServer extends Service {
	
	private AlarmBinder	binder	= null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		init();
		
		if (intent != null && intent.getExtras() != null) {
			Clocks model = (Clocks) intent.getExtras().getSerializable(Intent.EXTRA_TEXT);
			// 开始闹铃
			binder.startAlarm(model);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClass(this, AlarmActivity.class); //跳转
		
			
			this.startActivity(intent);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void init() {
		if (binder == null) {
			binder = new AlarmBinder(this);
		}
	}
	
}
