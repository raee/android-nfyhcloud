package com.yixin.nfyh.cloud.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 单次闹钟处理
 * 
 * @author MrChenrui
 * 
 */
public class AlarmOnceHandler implements IAlarmHandler
{
	
	@Override
	public void handler(Context context, AlarmManager manager, Clocks model)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(model.getStartDate());
		
		long triggerAtMillis = calendar.getTimeInMillis();
		PendingIntent operation = new AlarmWakeupOpetation(context)
				.getIntent(model);
		
		if (model.getRepeatSpan() > 0) //重复提醒
		{
			long intervalMillis = model.getRepeatSpan() * 1000;// 毫秒
			manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis,
					intervalMillis, operation);
		}
		else
		{
			manager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
		}
	}

	
}
