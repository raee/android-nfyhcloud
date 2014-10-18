package com.rae.alarm.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AlarmNumbericWhellAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rae.core.alarm.AlarmUtils;
import com.yixin.nfyh.cloud.R;

public class SelectPopupWindow extends PopupWindow {
	public static final int		TYPE_STRING_ARRAY	= 0;
	public static final int		TYPE_DATE			= 1;
	private Context				mContext;
	private ListView			mListView;
	private OnItemClickListener	mItemClickListener;
	private WheelView			mDayWhellView;
	private WheelView			mMonthWhellView;
	private WheelView			mYearWheelView;
	private TextView			mZhouTextView;
	private long				mCurrentTimeMillis;
	private BaseAdapter			mStringAdaper;
	private List<String>		mStringDataList		= new ArrayList<String>();
	private String				name;
	private String				mCurrentItem;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public SelectPopupWindow(Context context) {
		this(context, TYPE_STRING_ARRAY);
	}
	
	public SelectPopupWindow(Context context, int type) {
		super(context);
		mContext = context;
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.DialogAimation);
		
		this.setOutsideTouchable(true);
		
		this.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent)); // 透明背景
		
		// 背景模糊
		//		Activity activity = (Activity) mContext;
		//		Window window = activity.getWindow();
		//		if (android.os.Build.VERSION.SDK_INT > 14) {
		//			android.view.WindowManager.LayoutParams layoutParam = window.getAttributes();
		//			layoutParam.dimAmount = 0.55f;
		//			window.setAttributes(layoutParam);
		//			window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		//		}
		//		else {
		//			
		//			window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		//		}
		//		
		
		switch (type) {
			case TYPE_STRING_ARRAY:
				initString();
				break;
			case TYPE_DATE:
				mCurrentTimeMillis = System.currentTimeMillis();
				initDate();
				break;
			default:
				break;
		}
	}
	
	/**
	 * 作为字符串的形式
	 */
	private void initString() {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_window_select, null);
		setContentView(contentView);
		this.mListView = (ListView) contentView.findViewById(android.R.id.list);
		contentView.findViewById(R.id.btn_select_window_cancle).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		mStringAdaper = new DefaultAdapter();
		mListView.setAdapter(mStringAdaper);
		setOnItemClickListener(null);
		update();
	}
	
	private void initDate() {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_wheelview_date, null);
		mYearWheelView = (WheelView) contentView.findViewById(R.id.wv_year);
		mMonthWhellView = (WheelView) contentView.findViewById(R.id.wv_month);
		mDayWhellView = (WheelView) contentView.findViewById(R.id.wv_day);
		mZhouTextView = (TextView) contentView.findViewById(R.id.tv_wv_zhou);
		contentView.findViewById(R.id.btn_wv_date_ok).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		mDayWhellView.setCyclic(true);
		
		mYearWheelView.setName("year");
		mMonthWhellView.setName("month");
		mDayWhellView.setName("day");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mCurrentTimeMillis);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		
		mYearWheelView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, year, year + 10, "%02d"));
		mMonthWhellView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, 1, 12, "%02d"));
		mDayWhellView.setViewAdapter(new AlarmNumbericWhellAdapter(mContext, 1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
		
		OnWheelChangedListener listener = new WheelViewChangingListener();
		mYearWheelView.addChangingListener(listener);
		mMonthWhellView.addChangingListener(listener);
		mDayWhellView.addChangingListener(listener);
		
		mYearWheelView.setTag(year);
		mMonthWhellView.setTag(month);
		mDayWhellView.setTag(day);
		
		mYearWheelView.setCurrentItem(year);
		mMonthWhellView.setCurrentItem(month);
		mDayWhellView.setCurrentItem(day - 1);
		
		setContentView(contentView);
	}
	
	public void setAdapter(ListAdapter adapter) {
		this.mListView.setAdapter(adapter);
	}
	
	public String getDate() {
		String year = mYearWheelView.getTag().toString();
		String month = mMonthWhellView.getTag().toString();
		String day = mDayWhellView.getTag().toString();
		return year + "-" + month + "-" + day;
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mItemClickListener = listener;
		mListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCurrentItem = mStringDataList.get(position).toString();
				if (mItemClickListener != null) {
					mItemClickListener.onItemClick(parent, view, position, id);
				}
				dismiss();
			}
		});
	}
	
	public void setAdapter(List<String> dataList) {
		setStringDataList(dataList);
		setAdapter(mStringAdaper);
		mListView.invalidate();
		mStringAdaper.notifyDataSetChanged();
	}
	
	public void show(View parent) {
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}
	
	class DefaultAdapter extends BaseAdapter implements OnItemClickListener {
		
		@Override
		public int getCount() {
			return mStringDataList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return mStringDataList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view;
			if (convertView == null) {
				view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, null);
			}
			else {
				view = (TextView) convertView;
			}
			view.setText(getItem(position).toString());
			
			return view;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mCurrentItem = getItem(position).toString();
			dismiss();
		}
		
	}
	
	public void setStringDataList(List<String> dataList) {
		this.mStringDataList = dataList;
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
			String zhou;
			switch (week) {
				case 1:
					zhou = "周一";
					break;
				case 2:
					zhou = "周二";
					break;
				case 3:
					zhou = "周三";
					break;
				case 4:
					zhou = "周四";
					break;
				case 5:
					zhou = "周五";
					break;
				case 6:
					zhou = "周六";
					break;
				case 0:
					zhou = "周日";
					break;
				default:
					zhou = "error!";
					break;
			}
			mZhouTextView.setText(zhou);
		}
	}
	
	/**
	 * 获取当前选择的项
	 */
	public String getCurrentItem() {
		return mCurrentItem;
	}
}
