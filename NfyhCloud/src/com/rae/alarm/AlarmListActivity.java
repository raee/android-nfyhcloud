package com.rae.alarm;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.IDbAlarm;
import com.rae.core.alarm.provider.AlarmProviderFactory;
import com.yixin.nfyh.cloud.R;

public class AlarmListActivity extends Activity implements OnClickListener {
	
	private ListView	mAlarmListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_list);
		//		setContentView(R.layout.window_alarm_type_list);
		mAlarmListView = (ListView) findViewById(R.id.lv_alarm_list);
		findViewById(R.id.tv_alarm_normal).setOnClickListener(this);
		IDbAlarm db = AlarmProviderFactory.getDbAlarm(this);
		//		
		//		for (int i = 0; i < 5; i++) {
		//			AlarmEntity entity = new AlarmEntity(AlarmEntity.TYPE_ONCE, "闹钟" + i, "");
		//			db.addOrUpdate(entity);
		//		}
		//		
		mAlarmListView.setAdapter(new AlarmListAdapter(db.getAlarms()));

		startActivity(new Intent(this, AlarmAddNormalActivity.class));
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_alarm_normal:
				startActivity(new Intent(this, AlarmAddNormalActivity.class));
				break;
			
			default:
				break;
		}
	}
	
	class AlarmListAdapter extends BaseAdapter {
		
		private List<AlarmEntity>	mAlarmList;
		
		public AlarmListAdapter(List<AlarmEntity> alarms) {
			this.mAlarmList = alarms;
		}
		
		@Override
		public int getCount() {
			return mAlarmList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return mAlarmList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = LayoutInflater.from(AlarmListActivity.this).inflate(R.layout.item_alarm_list, null);
			}
			
			return view;
		}
		
	}
	
}
