package com.yixin.nfyh.cloud.alarm;

import java.util.List;

import android.app.AlarmManager;
import android.content.Context;

import com.yixin.nfyh.cloud.model.Clocks;

public class AlarmControl implements IAlramControl
{
	private AlarmManager	manager;
	private Context			context;
	private AlarmQueue		queue;
	
	public AlarmControl(Context context)
	{
		this.context = context;
		manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		queue = AlarmQueue.getInstance(context);
	}
	
	@Override
	public void setAlarm(Clocks model)
	{
		IAlarmHandler handler = new AlarmHandler();
		handler.handler(context, manager, model);
	}
	
	@Override
	public void excuteAlarm(Clocks model)
	{
		queue.excute(model); // 执行提醒
	}

	@Override
	public void deleteAlarm(Clocks model)
	{
		queue.delete(model);
	}
	
	@Override
	public void cancleAlarm(Clocks model)
	{
		
	}

	@Override
	public List<Clocks> getAlarms()
	{
		return queue.getAlarms();
	}
}
