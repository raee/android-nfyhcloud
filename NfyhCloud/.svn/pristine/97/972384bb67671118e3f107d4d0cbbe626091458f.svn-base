//package com.yixin.nfyh.cloud.ui;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.renderscript.Type;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewStub;
//import android.widget.LinearLayout;
//import cn.rui.framework.ui.ScrollInputView;
//import cn.rui.framework.ui.ScrollInputView.ScrollInputChangedListener;
//import cn.rui.framework.utils.JsonUtil;
//
//public class SelectValueActivity extends Activity implements
//		ScrollInputChangedListener, OnClickListener
//{
//	private List<String>		titleList;						//标题
//	private List<List<String>>	dataList;						//数据
//	private ViewGroup			rootView;
//	private String[]			values;
//	private String[]			dateStrings	= new String[5];	// 日期类型
//	private ViewGroup			selectView;
//	private int					type		= 0;
//	private List<String>		defaultList;
//	
//	public void setType(int type)
//	{
//		this.type = type;
//	}
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		
//		super.onCreate(savedInstanceState);
//		this.rootView = (ViewGroup) getLayoutInflater().inflate(
//				R.layout.activity_empt, null);
//		this.selectView = (ViewGroup) this.rootView.getChildAt(0);
//		setContentView(this.rootView);
//		
//		findViewById(R.id.back).setOnClickListener(this);
//		findViewById(R.id.save).setOnClickListener(this);
//		
//		initView();
//		
//	}
//	
//	/**
//	 * 转换数据
//	 * 
//	 * @param jsonData
//	 * @return
//	 * @throws JSONException
//	 */
//	private List<List<String>> parseData(String jsonData)
//	{
//		if (null == jsonData || "".equals(jsonData)) { return null; }
//		
//		List<List<String>> result = new ArrayList<List<String>>();
//		
//		try
//		{
//			JSONArray json = new JSONArray(jsonData);
//			for (int i = 0; i < json.length(); i++)
//			{
//				JSONArray arr = json.getJSONArray(i);
//				List<String> item = new ArrayList<String>();
//				
//				for (int index = 0; index < arr.length(); index++)
//				{
//					String ran = arr.getString(index);
//					item.add(ran);
//				}
//				
//				result.add(item);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	private void initView()
//	{
//		// 获取传递过来的数据类型
//		this.type = getIntent().getType() == null ? 0 : Integer
//				.valueOf(getIntent().getType());
//		
//		this.titleList = getIntent()
//				.getStringArrayListExtra(Intent.EXTRA_TITLE);
//		String jsonData = getIntent().getStringExtra(Intent.EXTRA_TEXT);
//		this.dataList = parseData(jsonData);
//		
//		this.defaultList = getIntent().getStringArrayListExtra("defaultList");
//		
//		/*
//		 * 单条数据类型定义
//		 * 0:文本
//		 * 1：整型
//		 * 2：浮点
//		 * 3：日期
//		 * */
//		
//		if (type == 2)
//		{
//			initDate();
//		}
//		else
//		{
//			initData();
//		}
//	}
//	
//	private void initData()
//	{
//		if (dataList == null || dataList.size() < 0) { return; }
//		values = new String[dataList.size()];
//		
//		for (int i = 0; i < dataList.size(); i++)
//		{
//			if (i > dataList.size())
//			{
//				continue;
//			}
//			String title = i < titleList.size() ? titleList.get(i) : "";
//			int cur = 1;
//			
//			if (defaultList != null) //默认值初始化
//			{
//				String defaultValue = i < defaultList.size() ? defaultList
//						.get(i) : "1";
//				cur = defaultList.indexOf(defaultValue);
//			}
//			List<String> datas = dataList.get(i);
//			ScrollInputView v = getWheelView();
//			v.setTag(i);
//			v.setTitle(title);
//			v.setData(getRanges(datas.get(0), datas.get(1)), cur);
//			v.addChangingListener(this);
//			
//			// 赋予初始化值
//			values[i] = dataList.get(i).toString();
//			
//			this.selectView.addView(v);
//		}
//		
//	}
//	
//	/**
//	 * 生成范围
//	 * 
//	 * @param string
//	 * @param string2
//	 * @return
//	 */
//	private List<String> getRanges(String minStr, String maxStr)
//	{
//		List<String> result = new ArrayList<String>();
//		
//		double min = Double.parseDouble(minStr);
//		double max = Double.parseDouble(maxStr);
//		
//		String m = String.valueOf(min).substring(
//				String.valueOf(min).lastIndexOf(".") + 1);
//		
//		// TODO：浮点数的
//		
//		if (Integer.valueOf(m) == 0)
//		{
//			// 整数
//			for (int i = (int) min; i < max; i++)
//			{
//				result.add(i + "");
//			}
//			
//		}
//		else
//		{
//			// 浮点数
//			double current = min;
//			do
//			{
//				min += 0.1;
//				result.add(min + "");
//			}
//			while (current < max);
//			
//		}
//		
//		return result;
//	}
//	
//	public ScrollInputView getWheelView()
//	{
//		ScrollInputView v = new ScrollInputView(this, null);
//		android.widget.LinearLayout.LayoutParams layoutParameter = new LinearLayout.LayoutParams(
//				ViewGroup.LayoutParams.WRAP_CONTENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//		layoutParameter.weight = 1;
//		v.setLayoutParams(layoutParameter);
//		return v;
//	}
//	
//	private void initDate()
//	{
//		
//		ViewStub vs = (ViewStub) findViewById(R.id.viewstub_wheel_datetime);
//		vs.setLayoutResource(R.layout.view_wheel_datetime);
//		View wheelDateView = vs.inflate();
//		
//		Calendar calendar = Calendar.getInstance();
//		
//		final ScrollInputView month = (ScrollInputView) wheelDateView
//				.findViewById(R.id.view_wheel_month);
//		final ScrollInputView year = (ScrollInputView) wheelDateView
//				.findViewById(R.id.view_wheel_year);
//		final ScrollInputView day = (ScrollInputView) wheelDateView
//				.findViewById(R.id.view_wheel_day);
//		
//		final ScrollInputView hour = (ScrollInputView) wheelDateView
//				.findViewById(R.id.view_wheel_hour);
//		
//		final ScrollInputView minute = (ScrollInputView) wheelDateView
//				.findViewById(R.id.view_wheel_minute);
//		
//		ScrollInputChangedListener changingListener = new ScrollInputChangedListener()
//		{
//			@Override
//			public void onChanged(View view, String selectValue)
//			{
//				//updateDays(year, month, day);
//				
//				int id = view.getId();
//				int index = getIndex(id);
//				dateStrings[index] = selectValue;
//				Log.i("tttt", selectValue);
//			}
//			
//			private int getIndex(int id)
//			{
//				if (id == R.id.view_wheel_year) return 0;
//				else if (id == R.id.view_wheel_month) return 1;
//				else if (id == R.id.view_wheel_day) return 2;
//				else if (id == R.id.view_wheel_hour) return 3;
//				else if (id == R.id.view_wheel_minute) return 4;
//				else return 0;
//			}
//			
//		};
//		
//		// 年
//		int curYear = calendar.get(Calendar.YEAR);
//		year.setType(ScrollInputView.TYPE_DATE);
//		year.setData(curYear, curYear + 10, getDateDefalue(0, curYear));
//		year.addChangingListener(changingListener);
//		year.setTitle("年份");
//		
//		// 月
//		int curMonth = calendar.get(Calendar.MONTH);
//		month.setType(ScrollInputView.TYPE_DATE);
//		month.setData(1, 12, getDateDefalue(1, curMonth));
//		month.addChangingListener(changingListener);
//		month.setTitle("月份");
//		
//		//日
//		updateDays(year, month, day);
//		int curDay = calendar.get(Calendar.DAY_OF_MONTH) - 1;
//		day.setCurrentItem(getDateDefalue(2, curDay));
//		day.setTitle("日期");
//		day.addChangingListener(changingListener);
//		
//		// 时
//		int curHour = calendar.get(Calendar.HOUR_OF_DAY);
//		hour.setData(0, 23, getDateDefalue(3, curHour));
//		hour.addChangingListener(changingListener);
//		hour.setTitle("时");
//		
//		// 分
//		int curMin = calendar.get(Calendar.MINUTE);
//		minute.setData(1, 60, getDateDefalue(4, curMin));
//		minute.setTitle("分");
//		minute.addChangingListener(changingListener);
//		
//		// 赋予初始值
//		dateStrings[0] = String.valueOf(curYear);
//		dateStrings[1] = String.valueOf(curMonth + 1);
//		dateStrings[2] = String.valueOf(curDay + 1);
//		dateStrings[3] = String.valueOf(curHour);
//		dateStrings[4] = String.valueOf(curMin - 1);
//		
//	}
//	
//	private int getDateDefalue(int location, int defalutvalue)
//	{
//		if (location < defaultList.size() && defaultList.get(location) != null)
//		{
//			return Integer.valueOf(defaultList.get(location));
//		}
//		else
//		{
//			return defalutvalue;
//		}
//	}
//	
//	private void updateDays(ScrollInputView year, ScrollInputView month,
//			ScrollInputView day)
//	{
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR,
//				calendar.get(Calendar.YEAR) + year.getCurrentItem());
//		calendar.set(Calendar.MONTH, month.getCurrentItem());
//		
//		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//		day.setData(1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
//		day.getWheelView().setCurrentItem(curDay - 1, true);
//		
//	}
//	
//	@Override
//	public void onChanged(View view, String selectValue)
//	{
//		
//		int index = Integer.valueOf(view.getTag().toString());
//		if (values != null && values.length > index)
//		{
//			values[index] = selectValue;
//		}
//	}
//	
//	@Override
//	public void onClick(View v)
//	{
//		int id = v.getId();
//		if (id == R.id.back)
//		{
//			this.finish();
//		}
//		else if (id == R.id.save)
//		{
//			saveData();
//		}
//	}
//	
//	private void saveData()
//	{
//		Intent intent = this.getIntent();
//		
//		if (this.type == 1)
//		{
//			
//			intent.putExtra(Intent.EXTRA_TEXT, this.dateStrings);
//		}
//		else
//		{
//			intent.putExtra(Intent.EXTRA_TEXT, this.values);
//		}
//		
//		this.setResult(RESULT_OK, intent);
//		this.finish();
//	}
//}
