package com.yixin.nfyh.cloud.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import cn.rui.framework.utils.DateUtil;

import com.yixin.nfyh.cloud.model.Clocks;

public class AlarmHandler implements IAlarmHandler
{
	
	@Override
	public void handler(Context context, AlarmManager manager, Clocks model)
	{
		
		// 处理时间
		if (model.getStartDate() != null)
		{
			model.getStartDate().setSeconds(0);
		}
		if (model.getEndDate() != null)
		{
			model.getEndDate().setSeconds(0);
		}
		
		// 添加到队列中
		AlarmQueue.getInstance(context).insert(model);
		
		// 这里去维护规则
		if (model.getRepeatCount() > 0 || model.getRepeatSpan() > 0)
		{
			new AlarmRepeatHandler().handler(context, manager, model);
		}
		
		else
		{
			new AlarmOnceHandler().handler(context, manager, model);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(model.getStartDate());
		
		Log.i("IAlarmHandler",
				"添加了闹钟：" + model.getTitle() + ";"
						+ DateUtil.getDateString("yyyy-MM-dd HH:mm", calendar));
		
	}
	
}
