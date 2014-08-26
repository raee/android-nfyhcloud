package com.yixin.nfyh.cloud.alarm;

import android.app.Service;
import android.os.Binder;
import android.util.Log;

import com.yixin.nfyh.cloud.model.Clocks;

public class AlarmBinder extends Binder
{
	private Service			context;
	private AlarmControl	control;
	
	public AlarmBinder(AlarmServer alarmServer)
	{
		this.context = alarmServer;
		control = new AlarmControl(context.getApplicationContext());
	}
	
	/**
	 * 开始闹铃
	 * 
	 * @param model
	 */
	public void startAlarm(Clocks model)
	{
		Log.i("ttt", "闹钟响铃：" + model.getTitle());
		
	}
	
	public void excuteAlarm(Clocks model)
	{
		control.excuteAlarm(model);
	}
}
