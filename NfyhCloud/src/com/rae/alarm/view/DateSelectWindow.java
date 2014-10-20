package com.rae.alarm.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AlarmNumbericWhellAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rae.core.alarm.AlarmUtils;
import com.yixin.nfyh.cloud.R;

/**
 * 日期选择弹出框
 * 
 * @author ChenRui
 * 
 */
public class DateSelectWindow extends SelectPopupWindow {
	
	private WheelView			mDayWhellView;
	private WheelView			mMonthWhellView;
	private WheelView			mYearWheelView;
	private TextView			mZhouTextView;
	private long				mCurrentTimeMillis;
	private static final String	Numberformat	= "%02d";
	
	public DateSelectWindow(Context context) {
		super(context);
	}
	
	/**
	 * 初始化日期选择
	 */
	@Override
	protected View initView() {
		View contentView = mInflater.inflate(R.layout.view_wheelview_date, null);
		mCurrentTimeMillis = System.currentTimeMillis(); //设置当前时间
		
		// 找视图
		mYearWheelView = (WheelView) contentView.findViewById(R.id.wv_year);
		mMonthWhellView = (WheelView) contentView.findViewById(R.id.wv_month);
		mDayWhellView = (WheelView) contentView.findViewById(R.id.wv_day);
		mZhouTextView = (TextView) contentView.findViewById(R.id.tv_wv_zhou);
		
		// 确定按钮
		contentView.findViewById(R.id.btn_wv_date_ok).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String date = getDate() + " 23:59:59"; // 一天中的最大时间。
				// 过期检测
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(AlarmUtils.parseDate(date));
				
				if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
					Toast.makeText(mContext, "不能小于当前日期，请重新选择！", Toast.LENGTH_SHORT).show();
					// 滚动回来
					setCurrentTimeMillis(AlarmUtils.dateToString("yyyy-MM-dd", new Date()));
					refresh();
					setOutsideTouchable(false);
					return;
				}
				
				mSelectListener.onSelectChange(DateSelectWindow.this, getDate());
				setOutsideTouchable(true);
			}
		});
		
		// 设置循环滚动
		mDayWhellView.setCyclic(true);
		
		// 设置名称
		mYearWheelView.setName("year");
		mMonthWhellView.setName("month");
		mDayWhellView.setName("day");
		
		// 初始化值
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mCurrentTimeMillis);
		int year = calendar.get(Calendar.YEAR);
		
		// 初始化滚动，设置当前时间
		mYearWheelView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, year, year + 10, Numberformat));
		mMonthWhellView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, 1, 12, Numberformat));
		mDayWhellView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), Numberformat));
		
		OnWheelChangedListener listener = new WheelViewChangingListener();
		mYearWheelView.addChangingListener(listener);
		mMonthWhellView.addChangingListener(listener);
		mDayWhellView.addChangingListener(listener);
		
		refresh(); // 刷新
		
		return contentView;
	}
	
	@SuppressLint("SimpleDateFormat")
	public void setCurrentTimeMillis(String date) {
		if (TextUtils.isEmpty(date)) {
			mCurrentTimeMillis = System.currentTimeMillis();
			return;
		}
		SimpleDateFormat dateForamt = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = dateForamt.parse(date);
		}
		catch (ParseException e) {
			d = new Date();
			e.printStackTrace();
		}
		mCurrentTimeMillis = d.getTime();
	}
	
	public void refresh() {
		// 初始化值
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mCurrentTimeMillis);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH); // 系统获取的月份小于1，所以+1
		int day = calendar.get(Calendar.DATE);
		
		mYearWheelView.setTag(year);
		mMonthWhellView.setTag(month + 1);
		mDayWhellView.setTag(day);
		
		mYearWheelView.setCurrentItem(year);
		mMonthWhellView.setCurrentItem(month);
		mDayWhellView.setCurrentItem(day - 1);
	}
	
	/**
	 * 日期滑动选择适配器
	 * 
	 * @author ChenRui
	 * 
	 */
	class WheelViewChangingListener implements OnWheelChangedListener {
		
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int year = calendar.get(Calendar.YEAR);
			
			String name = wheel.getName();
			
			if (name.equals("year")) {
				mYearWheelView.setTag(year + newValue);
			}
			else if (name.equals("month")) {
				mMonthWhellView.setTag(newValue + 1);
				
			}
			else {
				mDayWhellView.setTag(newValue + 1);
			}
			
			calendar.set(Calendar.YEAR, Integer.valueOf(mYearWheelView.getTag().toString()));
			calendar.set(Calendar.MONTH, Integer.valueOf(mMonthWhellView.getTag().toString()));
			calendar.set(Calendar.DATE, Integer.valueOf(mDayWhellView.getTag().toString()));
			
			int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (name.equals("month")) {
				AlarmNumbericWhellAdapter adapter = (AlarmNumbericWhellAdapter) mDayWhellView.getViewAdapter();
				adapter.setMaxValue(maxDay);
				mDayWhellView.invalidateWheel(true);
			}
			
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			week = week < 0 ? 0 : week;
			String zhou = "周" + AlarmUtils.getWeekString(week);
			mZhouTextView.setText(zhou);
			
		}
	}
	
	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public String getDate() {
		String year = mYearWheelView.getTag().toString();
		String month = String.format(mMonthWhellView.getTag().toString(), Numberformat);
		String day = String.format(mDayWhellView.getTag().toString(), Numberformat);
		return year + "-" + month + "-" + day;
	}
}
