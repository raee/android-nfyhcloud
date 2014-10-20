package com.rae.alarm;

import android.app.Activity;
import android.os.Bundle;

import com.rae.core.alarm.AlarmEntity;

/**
 * 闹钟更多设置
 * 
 * @author ChenRui
 * 
 */
public class AlarmMoreSetingActivity extends Activity {
	private AlarmEntity	mAlarmEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAlarmEntity = getIntent().getParcelableExtra("data");
	}
	
}
