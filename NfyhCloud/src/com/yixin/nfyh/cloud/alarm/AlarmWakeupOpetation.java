package com.yixin.nfyh.cloud.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 唤醒操作
 * 
 * @author MrChenrui
 * 
 */
public class AlarmWakeupOpetation
{
	private Context				context;
	public static final String	ACTION	= "com.yixin.nfyh.cloud.alarm.action";
	
	public AlarmWakeupOpetation(Context context)
	{
		this.context = context;
	}
	
	public PendingIntent getIntent(Clocks model)
	{
		Intent intent = new Intent(ACTION);
		intent.putExtra(Intent.EXTRA_TEXT, model);
		intent.putExtra("id", model.getClockId());
		int requestCode = (int) model.getClockId(); //产生一个随机数，保证闹钟是一个新的闹钟
		return PendingIntent.getBroadcast(context, requestCode, intent, 0);
	}
}
