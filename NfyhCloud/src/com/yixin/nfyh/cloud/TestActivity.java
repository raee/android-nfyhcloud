package com.yixin.nfyh.cloud;

import com.rae.alarm.NfyhAlarmEntity;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.AlarmUtils;
import com.rae.core.alarm.provider.AlarmProviderFactory;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NfyhAlarmEntity entity = new NfyhAlarmEntity(AlarmEntity.TYPE_ONCE, "测试闹钟", AlarmUtils.getDateByTimeInMillis((System.currentTimeMillis() + 2000)));
		entity.setSignName("1000");
		AlarmProviderFactory.getProvider(this, entity).create();
		
	}
}
