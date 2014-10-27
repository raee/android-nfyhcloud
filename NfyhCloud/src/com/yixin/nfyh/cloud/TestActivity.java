package com.yixin.nfyh.cloud;

import com.yixin.nfyh.cloud.bll.VersionUpdate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity {
	private VersionUpdate	update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		NfyhAlarmEntity entity = new NfyhAlarmEntity(AlarmEntity.TYPE_ONCE, "测试闹钟", AlarmUtils.getDateByTimeInMillis((System.currentTimeMillis() + 2000)));
		//		entity.setSignName("1000");
		//		AlarmProviderFactory.getProvider(this, entity).create();
		
		setContentView(R.layout.activity_test);
		update = new VersionUpdate(this);
		
	}
	
	public void onClick(View view) {
		update.check();
	}
}
