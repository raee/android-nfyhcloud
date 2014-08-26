package com.yixin.nfyh.cloud.alarm;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;
import cn.rui.framework.utils.DateUtil;

import com.yixin.nfyh.cloud.data.IClock;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 闹钟队列
 * 
 * @author MrChenrui
 * 
 */
public class AlarmQueue
{
	private static AlarmQueue	alarmqueue	= null;
	private IClock				db;
	private Context				context;
	private AlarmManager		manager;
	private List<Clocks>		clockLists;

	private AlarmQueue(Context context)
	{
		this.context = context;
		this.manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		db = NfyhCloudDataFactory.getFactory(context).getClock();
		this.clockLists = order();

	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static AlarmQueue getInstance(Context context)
	{
		if (alarmqueue == null)
		{
			alarmqueue = new AlarmQueue(context);
		}
		return alarmqueue;
	}

	/**
	 * 插入到队列中
	 * 
	 * @param model
	 */
	public void insert(Clocks model)
	{
		try
		{
			Clocks inserted = db.addClock(model);
			model = null;
			model = inserted;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 队列排序，根据时间的先后顺序
	 */
	public List<Clocks> order()
	{
		try
		{
			this.clockLists = db.getAll();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return this.clockLists;
	}

	/**
	 * 从队列中删除
	 * 
	 * @param model
	 */
	public boolean delete(Clocks model)
	{
		try
		{
			cancle(model);
			return db.deleteClock(model); // 从数据库中删除
		}
		catch (SQLException e)
		{
			Log.e("AlarmControl", "删除闹钟失败！" + model.getTitle());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 取消闹钟
	 * 
	 * @param model
	 */
	public void cancle(Clocks model)
	{
		PendingIntent pendingIntent = new AlarmWakeupOpetation(context)
				.getIntent(model);
		manager.cancel(pendingIntent);
		log("系统--【删除】闹钟：" + model.getTitle());
	}

	public void update(Clocks model)
	{
		try
		{
			db.update(model); // 数据库更新
			cancle(model); // 先删除的闹钟
			log("系统--【更新】闹钟：" + model.getTitle());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void log(Object msg)
	{
		Log.i("AlarmControl", msg.toString());
	}

	public void excute(Clocks model)
	{
		// 删除单次闹钟
		if (model.getRepeatCount() < 1 && model.getRepeatSpan() < 0)
		{
			delete(model);
			log("删除单次闹钟:" + model.getTitle());
		}

		// 删除过期闹钟
		if (model.getEndDate() != null
				&& DateUtil.isOutdate(model.getEndDate()))
		{
			delete(model);
			log("删除过期闹钟:" + model.getTitle());
		}

		// 单次，连续提醒的
		if (model.getRepeatCount() < 1 && model.getRepeatSpan() > 0)
		{
			// 次数满足，删除闹钟
			if (model.getAlarmCount() >= model.getRepeatSpan())
			{
				delete(model);
				log("删除单次，连续提醒的闹钟:" + model.getTitle());
			}
			else
			{
				// 更新次数
				long value = model.getAlarmCount() + 1;
				model.setAlarmCount(value);
				this.update(model);
			}

		}

		// 每天的重复闹钟，数据库更新开始时间
		if (model.getRepeatCount() > 1)
		{
			Date date = model.getStartDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + 1); // 设置成明天

			model.setStartDate(calendar.getTime());
			// 更新次数,和开始时间
			long value = model.getAlarmCount() + 1;
			model.setAlarmCount(value);
			this.update(model); // 后添加
		}
	}

	public List<Clocks> getAlarms()
	{
		this.order();
		return clockLists;
	}
}
