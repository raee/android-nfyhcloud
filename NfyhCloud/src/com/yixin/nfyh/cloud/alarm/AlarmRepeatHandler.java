package com.yixin.nfyh.cloud.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 重复闹钟处理
 * 
 * @author MrChenrui
 * 
 */
public class AlarmRepeatHandler implements IAlarmHandler
{
	
	@Override
	public void handler(Context context, AlarmManager manager, Clocks model)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(model.getStartDate());
		
		long triggerAtMillis = calendar.getTimeInMillis();
		long intervalMillis = 0;
		if (model.getRepeatSpan() > 0)
		{
			intervalMillis = model.getRepeatSpan() * 60 * 1000; // 用户自定义提醒的时间，分钟
		}
		else
		{
			intervalMillis = 24 * 60 * 60 * 1000; //一天的时间
		}
		PendingIntent operation = new AlarmWakeupOpetation(context)
				.getIntent(model);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis,
				intervalMillis, operation);
		
	}
	
}
