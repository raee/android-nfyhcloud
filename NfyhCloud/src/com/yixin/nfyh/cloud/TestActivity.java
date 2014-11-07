package com.yixin.nfyh.cloud;

import com.rae.alarm.AlarmAddNormalActivity;
import com.yixin.nfyh.cloud.bll.VersionUpdate;
import android.app.Activity;
import android.content.Intent;
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
		
//		setContentView(R.layout.activity_test);
//		update = new VersionUpdate(this);
		
		Intent intent = new Intent(this,AlarmAddNormalActivity.class);
		intent.putExtra("type", "add");
		intent.putExtra("entity", "{\"cycle\":\"TYPE_REPEAT_EVERY_DAY\",\"title\":\"服务器闹钟\",\"time\":\"12:00\",\"content\":\"客服温馨提醒，中午测血压！！\",\"weeks\":\"\",\"otherParam\":\"{\\\"sign\\\":\\\"2000\\\"}\"}");
		
		//intent.putExtras(extra);
		
		startActivity(intent);
		
	}
	
	public void onClick(View view) {
		update.check();
	}
}
