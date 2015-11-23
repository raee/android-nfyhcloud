package com.yixin.nfyh.cloud.test;

import com.rae.alarm.AlarmListActivity;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.provider.AlarmProviderFactory;
import com.yixin.nfyh.cloud.AlarmRingActivity;
import com.yixin.nfyh.cloud.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestAlarmActivity extends Activity

{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intent = null;
		intent = new Intent(this, AlarmListActivity.class);
		//		intent = new Intent(this, AlarmRingActivity.class);
		//		AlarmEntity m = AlarmProviderFactory.getDbAlarm(this).getAlarm(2);
		//		m.putValue("url", getString(R.string.url_yjfk));
		//		m.putValue("sign", "url");		
		//		intent.putExtra("data", m);
		
		startActivity(intent);
	}
}
