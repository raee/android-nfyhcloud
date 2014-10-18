package com.rae.alarm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AlarmNumbericWhellAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.rae.alarm.view.ListItemView;
import com.rae.alarm.view.SelectPopupWindow;
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
	
	private String				mDate;				//日期
	private String				mHour;				//时
	private String				mMinitue;			// 分
	private WheelView			mViewHour;
	private WheelView			mViewMinute;
	private ListItemView		mItemZhouqi;
	private ListItemView		mItemDate;
	private ListItemView		mItemSign;
	private ListItemView		mItemMoreSetting;
	private EditText			mItemTitle;
	private EditText			mItemContent;
	private Button				mBtnSave;
	private View				mRootView;
	private SelectPopupWindow	mSelectWindow;
	private SelectPopupWindow	mSelectDateWindow;
	private List<String>		mZhouqiArray;
	private List<String>		mSignArray;
	private AlarmEntity			mAlarmEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mRootView = getLayoutInflater().inflate(R.layout.activity_add_normal_alarm, null);
		setContentView(mRootView);
		
		// 闹钟实体
		mAlarmEntity = new AlarmEntity(AlarmEntity.TYPE_ONCE, "自定义闹钟", AlarmUtils.getDateByTimeInMillis(System.currentTimeMillis()));
		
		mZhouqiArray = new ArrayList<String>();
		mZhouqiArray.add("每天一次");
		mZhouqiArray.add("每周一次");
		mZhouqiArray.add("每月一次");
		mZhouqiArray.add("自定义");
		
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
		
		this.mSelectWindow = new SelectPopupWindow(this);
		mSelectWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			
			@Override
			public void onDismiss() {
				String name = mSelectWindow.getName();
				String val = mSelectWindow.getCurrentItem();
				if ("zhouqi".equals(name)) {
					// 周期处理
					mItemZhouqi.setSubTitle(val);
				}
				else if ("sign".equals(name)) {
					mItemSign.setSubTitle(val);
				}
			}
		});
		
		mSelectDateWindow = new SelectPopupWindow(this, SelectPopupWindow.TYPE_DATE);
		mSelectDateWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			
			@Override
			public void onDismiss() {
				mDate = mSelectDateWindow.getDate();
				mItemDate.setSubTitle(mDate);
			}
		});
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
		mDate = AlarmUtils.dateToString("yyyy-MM-dd", new Date());
	
		mItemDate.setSubTitle(mDate);
		
		// 周期默认设置为第一个。
		mItemZhouqi.setSubTitle(mZhouqiArray.get(0));
		mItemSign.setSubTitle("不测量");
	}
	
	private void setUpDateSelectView() {
		mMinitue = "00";
		mHour = "08";
		
		mViewHour.setViewAdapter(new AlarmNumbericWhellAdapter(this, 0, 23, "%02d"));
		mViewHour.setCyclic(true);
		mViewHour.setCurrentItem(8);
		mViewHour.setVisibleItems(3);
		mViewHour.setWheelBackground(android.R.color.transparent);
		
		mViewMinute.setViewAdapter(new AlarmNumbericWhellAdapter(this, 0, 59, "%02d"));
		mViewMinute.setCyclic(true);
		mViewMinute.setCurrentItem(0);
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
	
	private void createMoreSetting() {
		Intent intent = null; //TODO:跳转到更多设置，获得回调结果。
		startActivityForResult(intent, 0);
	}
	
	private void createSign() {
		mSelectWindow.setName("sign");
		mSelectWindow.setAdapter(mSignArray);
		mSelectWindow.show(mRootView);
	}
	
	private void createDate() {
		mSelectDateWindow.setCurrentTimeMillis(mDate);
		mSelectDateWindow.show(mRootView);
	}
	
	private void createZhouqi() {
		mSelectWindow.setName("zhouqi");
		mSelectWindow.setAdapter(mZhouqiArray);
		mSelectWindow.show(mRootView);
	}
	
	private void save() {
		String time = mHour + ":" + mMinitue;
		// 处理周期转换
		String subTitle = mItemZhouqi.getSubTitle().toString();
		
		for (int i = 0; i < mZhouqiArray.size(); i++) {
			if (!subTitle.equals(mZhouqiArray.get(i))) {
				continue;
			}
			
			switch (i) {
				case 0: // 每天重复
					mAlarmEntity.setCycle(AlarmEntity.TYPE_REPEAT_EVERY_DAY);
					break;
				case 1: //每周重复
					mAlarmEntity.setCycle(AlarmEntity.TYPE_REPEAT_EVERY_WEEK);
					
					break;
				case 2: //每月
					mAlarmEntity.setCycle(AlarmEntity.TYPE_REPEAT_EVERY_MONTH);
					break;
				default: //仅一次,具体时间。
					time = mDate + " " + time;
					mAlarmEntity.setCycle(AlarmEntity.TYPE_ONCE);
					break;
			}
		}
		
		// 处理体征转换
		mAlarmEntity.setTime(time);
		AlarmProviderFactory.create(this, mAlarmEntity); //创建闹钟
		
	}
}
