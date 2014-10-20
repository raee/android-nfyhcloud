package com.rae.alarm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AlarmNumbericWhellAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.rae.alarm.view.DateSelectWindow;
import com.rae.alarm.view.ListItemView;
import com.rae.alarm.view.SelectPopupWindow;
import com.rae.alarm.view.WeekSelectWindow;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.AlarmUtils;
import com.rae.core.alarm.provider.AlarmProviderFactory;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 闹钟添加界面
 * 
 * @author ChenRui
 * 
 */
public class AlarmAddNormalActivity extends Activity implements OnClickListener {
	private static HashMap<String, String>	zhouqiHashMap;
	
	static {
		zhouqiHashMap = new HashMap<String, String>();
		zhouqiHashMap.put("每天一次", AlarmEntity.TYPE_REPEAT_EVERY_DAY);
		zhouqiHashMap.put("每周一次", AlarmEntity.TYPE_REPEAT_EVERY_WEEK);
		zhouqiHashMap.put("每月一次", AlarmEntity.TYPE_REPEAT_EVERY_MONTH);
		zhouqiHashMap.put("自定义", AlarmEntity.TYPE_ONCE);
	}
	
	private String							mDate;					//日期
	private String							mDateTime;				//完整日期
	private String							mHour;					//时
	private String							mMinitue;				// 分
	private int[]							mWeeks;				//重复周期，如果选择了周期
																	
	private WheelView						mViewHour;
	private WheelView						mViewMinute;
	private ListItemView					mItemZhouqi;
	private ListItemView					mItemDate;
	private ListItemView					mItemSign;
	private ListItemView					mItemMoreSetting;
	private EditText						mItemTitle;			// 闹钟标题
	private EditText						mItemContent;			// 闹钟备注
	private Button							mBtnSave;				// 保存按钮
	private View							mRootView;
	private SelectPopupWindow				mZhouqiSelectWindow;	// 选择列表弹出框
	private SelectPopupWindow				mSignSelectWindow;		// 选择列表弹出框
	private DateSelectWindow				mSelectDateWindow;		// 日期选择弹出框
	private List<String>					mZhouqiArray;			// 默认周期选择列表。
	private List<String>					mSignArray;			// 体征列表
	private NfyhAlarmEntity					mAlarmEntity;			// 闹钟实体
	private WeekSelectWindow				mWeekSelectWindow;
	
	public AlarmAddNormalActivity() {
		mZhouqiArray = new ArrayList<String>();
		for (Entry<String, String> item : zhouqiHashMap.entrySet()) {
			mZhouqiArray.add(item.getKey());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mRootView = getLayoutInflater().inflate(R.layout.activity_add_normal_alarm, null);
		setContentView(mRootView);
		
		mAlarmEntity = getIntent().getParcelableExtra("data");
		if (mAlarmEntity == null) {
			// 闹钟实体
			mAlarmEntity = new NfyhAlarmEntity(AlarmEntity.TYPE_REPEAT_EVERY_DAY, "每天重复闹钟", AlarmUtils.getDateByTimeInMillis(System.currentTimeMillis()));
		}
		else {
			initValue();
		}
		
		initView();
		setUpDateSelectView();
		
		// 获取所有的体征
		mSignArray = new ArrayList<String>();
		try {
			List<SignTypes> signTypes = NfyhCloudDataFactory.getFactory(this).getSignDevice().getSignTypes();
			for (SignTypes signTtype : signTypes) {
				mSignArray.add(signTtype.getName());
			}
			mSignArray.add("不测量");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		initZhouqiWindow();
		
		initSignWindow();
		
		initDateWindow();
		
		initWeekWindow();
		
	}
	
	// 初始化周选择框。
	private void initWeekWindow() {
		mWeekSelectWindow = new WeekSelectWindow(this);
		mWeekSelectWindow.setReturnWindow(mZhouqiSelectWindow);
		mWeekSelectWindow.setOnSelectListener(new SelectPopupWindow.onSelectListener() {
			
			@Override
			public void onSelectChange(SelectPopupWindow window, String value) {
				mWeeks = mWeekSelectWindow.getSelectItems();
				
				if (mWeeks.length <= 0) {
					mItemZhouqi.setSubTitle(mItemZhouqi.getTag().toString());
					return;
				}
				
				// 设置周期
				mAlarmEntity.setWeeks(mWeeks);
				showWeeks();
				window.dismiss();
			}
			
		});
	}
	
	// 初始化日期选择框
	private void initDateWindow() {
		mSelectDateWindow = new DateSelectWindow(this);
		mSelectDateWindow.setOnSelectListener(new SelectPopupWindow.onSelectListener() {
			
			@Override
			public void onSelectChange(SelectPopupWindow window, String value) {
				mDate = mSelectDateWindow.getDate();
				
				if (AlarmEntity.TYPE_ONCE.equals(mAlarmEntity.getCycle())) { //一次性周期
					mDateTime = mDate + " " + mHour + ":" + mMinitue + ":00";
					mAlarmEntity.setTime(mDateTime); //设置响铃时间
				}
				
				mItemDate.setSubTitle(mDate);
				window.dismiss();
			}
		});
	}
	
	//初始化体征选择框
	private void initSignWindow() {
		mSignSelectWindow = new SelectPopupWindow(this);
		mSignSelectWindow.setOnSelectListener(new SelectPopupWindow.onSelectListener() {
			
			@Override
			public void onSelectChange(SelectPopupWindow window, String value) {
				String val = mSignSelectWindow.getCurrentItem();
				mItemSign.setSubTitle(val);
				mAlarmEntity.setSignName(val);
				window.dismiss();
			}
		});
	}
	
	// 初始化周期列表选择框
	private void initZhouqiWindow() {
		this.mZhouqiSelectWindow = new SelectPopupWindow(this);
		mZhouqiSelectWindow.setOnSelectListener(new SelectPopupWindow.onSelectListener() {
			
			@Override
			public void onSelectChange(SelectPopupWindow window, String value) {
				String val = mZhouqiSelectWindow.getCurrentItem();
				
				// 周期处理
				mItemZhouqi.setSubTitle(val);
				mItemZhouqi.setTag(val);
				
				// 这里处理不同的周期分类
				String cycle = zhouqiHashMap.get(val);
				mAlarmEntity.setCycle(cycle);
				if (AlarmEntity.TYPE_REPEAT_EVERY_WEEK.equals(cycle)) {
					createWeek();
					mItemDate.setVisibility(View.GONE);
				}
				else if (AlarmEntity.TYPE_ONCE.equals(cycle)) {
					mItemDate.setVisibility(View.VISIBLE);
					createDate();
				}
				else {
					mItemDate.setVisibility(View.GONE);
				}
				window.dismiss();
				
			}
			
		});
	}
	
	// 显示星期
	private void showWeeks() {
		String title = "周 ";
		for (int item : mWeeks) {
			title += AlarmUtils.getWeekString(item) + "、";
		}
		title = title.substring(0, title.length() - 1);
		mItemZhouqi.setSubTitle(title);
	}
	
	/**
	 * 初始化默认值
	 */
	private void initValue() {
		
		// 初始化时间
		Date date = AlarmUtils.parseDate(mAlarmEntity.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		// 时间赋值
		mHour = String.valueOf(calendar.get(Calendar.HOUR));
		mMinitue = String.valueOf(calendar.get(Calendar.MINUTE));
		mDate = AlarmUtils.dateToString("yyyy-MM-dd", date);
		mDateTime = AlarmUtils.dateToString(date);
		if (mAlarmEntity.getWeeks() != null && mAlarmEntity.getWeeks().length > 0) {
			mWeeks = mAlarmEntity.getWeeks();
		}
		
		for (Entry<String, String> item : zhouqiHashMap.entrySet()) {
			if (item.getValue().equals(mAlarmEntity.getCycle())) {
				mItemZhouqi.setSubTitle(item.getKey());
				mItemZhouqi.setTag(item.getKey());
				
				if (AlarmEntity.TYPE_REPEAT_EVERY_WEEK.equals(item.getValue())) {
					showWeeks();
				}
				
				break;
			}
		}
		
		mItemDate.setSubTitle(mDate);		// 日期
		mItemSign.setSubTitle(mAlarmEntity.getSignName()); // 体征
		mItemTitle.setText(mAlarmEntity.getTitle()); //标题
		mItemContent.setText(mAlarmEntity.getContent()); //备注
		
	}
	
	private void initView() {
		// 找视图
		mViewHour = (WheelView) findViewById(R.id.wv_hour);
		mViewMinute = (WheelView) findViewById(R.id.wv_mins);
		mItemZhouqi = (ListItemView) findViewById(R.id.liv_normal_alarm_zhouqi); //周期
		mItemDate = (ListItemView) findViewById(R.id.liv_normal_alarm_date);
		mItemSign = (ListItemView) findViewById(R.id.liv_normal_alarm_sign);
		mItemMoreSetting = (ListItemView) findViewById(R.id.liv_normal_alarm_more_setting);
		mItemTitle = (EditText) findViewById(R.id.et_normal_alarm_title);
		mItemContent = (EditText) findViewById(R.id.et_normal_alarm_content);
		mBtnSave = (Button) findViewById(R.id.btn_normal_alarm_save);
		
		// 设置监听
		mViewHour.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				mHour = String.format("%02d", newValue);
			}
		});
		mViewMinute.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
				mMinitue = String.format("%02d", newValue);
			}
		});
		
		mItemZhouqi.setOnClickListener(this);
		mItemDate.setOnClickListener(this);
		mItemTitle.setOnClickListener(this);
		mItemSign.setOnClickListener(this);
		mItemMoreSetting.setOnClickListener(this);
		mBtnSave.setOnClickListener(this);
		
		// 初始化当前日期
		if (TextUtils.isEmpty(mDate)) {
			mDate = AlarmUtils.dateToString("yyyy-MM-dd", new Date());
		}
		
		mItemDate.setSubTitle(mDate);
		
		// 周期默认设置为第一个。
		for (Entry<String, String> item : zhouqiHashMap.entrySet()) {
			if (item.getValue().equals(mAlarmEntity.getCycle())) {
				mItemZhouqi.setSubTitle(item.getKey());
				break;
			}
		}
		
		mItemSign.setSubTitle("不测量");
	}
	
	// 初始化时间滑动选择
	private void setUpDateSelectView() {
		if (TextUtils.isEmpty(mMinitue)) {
			mMinitue = "00";
		}
		if (TextUtils.isEmpty(mHour)) {
			mHour = "08";
		}
		
		mViewHour.setViewAdapter(new AlarmNumbericWhellAdapter(this, 0, 23, "%02d"));
		mViewHour.setCyclic(true);
		mViewHour.setCurrentItem(Integer.valueOf(mHour));
		mViewHour.setVisibleItems(3);
		mViewHour.setWheelBackground(android.R.color.transparent);
		
		mViewMinute.setViewAdapter(new AlarmNumbericWhellAdapter(this, 0, 59, "%02d"));
		mViewMinute.setCyclic(true);
		mViewMinute.setCurrentItem(Integer.valueOf(mMinitue));
		mViewMinute.setVisibleItems(3);
		mViewMinute.setWheelBackground(android.R.color.transparent);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_normal_alarm_save: //保存
				save();
				break;
			case R.id.liv_normal_alarm_zhouqi: // 周期
				createZhouqi();
				break;
			case R.id.liv_normal_alarm_date: // 日期
				createDate();
				break;
			case R.id.liv_normal_alarm_sign: // 体征
				createSign();
				break;
			case R.id.liv_normal_alarm_more_setting:// 更多设置
				createMoreSetting();
				break;
			
			default:
				break;
		}
	}
	
	// 跳转更多
	private void createMoreSetting() {
		Intent intent = null; //TODO:跳转到更多设置，获得回调结果。
		startActivityForResult(intent, 0);
	}
	
	// 创建体征
	private void createSign() {
		mSignSelectWindow.setAdapter(mSignArray);
		mSignSelectWindow.show();
	}
	
	// 创建日期
	private void createDate() {
		mSelectDateWindow.setCurrentTimeMillis(mDate);
		mSelectDateWindow.show();
	}
	
	// 创建星期
	public void createWeek() {
		if (mWeeks != null && mWeeks.length > 0) {
			mWeekSelectWindow.setSelectItems(mWeeks);
		}
		mWeekSelectWindow.show();
	}
	
	// 创建周期
	private void createZhouqi() {
		if (AlarmEntity.TYPE_REPEAT_EVERY_WEEK.equals(mAlarmEntity.getCycle())) { //直接弹出星期选择框
			createWeek();
			return;
		}
		mZhouqiSelectWindow.setAdapter(mZhouqiArray);
		mZhouqiSelectWindow.show();
	}
	
	private void save() {
		String title = mItemTitle.getText().toString();
		title = TextUtils.isEmpty(title) ? "自定义闹钟" : title;
		mAlarmEntity.setTitle(title);
		mAlarmEntity.setContent(mItemContent.getText().toString());
		
		if (AlarmEntity.TYPE_ONCE.equals(mAlarmEntity.getCycle())) {
			mAlarmEntity.setTime(mDateTime); //具体时间
		}
		else {
			mAlarmEntity.setTime(mHour + ":" + mMinitue); //重复类型时间
		}
		
		AlarmProviderFactory.create(this, mAlarmEntity); //创建闹钟
		
		startActivity(new Intent(this, AlarmListActivity.class));
		
	}
}
