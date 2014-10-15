//package com.yixin.nfyh.cloud.activity;
//
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import cn.rui.framework.utils.DateUtil;
//
//import com.yixin.nfyh.cloud.BaseActivity;
//import com.yixin.nfyh.cloud.R;
//import com.yixin.nfyh.cloud.alarm.AlarmControl;
//import com.yixin.nfyh.cloud.model.Clocks;
//
//public class AlarmListActivity extends BaseActivity
//{
//	private ListView		lvAlarm;
//	AlarmListAdapter		adapter;
//	private List<Clocks>	alarmList;
//	private AlarmControl	alarmControll;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_alarm_list);
//		
//		this.adapter = new AlarmListAdapter();
//		this.alarmControll = new AlarmControl(this);
//		this.lvAlarm = (ListView) findViewById(android.R.id.list);
//		initData();
//		lvAlarm.setOnItemClickListener(adapter);
//	}
//	
//	@Override
//	protected String getActivityName()
//	{
//		return getString(R.string.activity_alarm);
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		initData();
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//	
//	private void initData()
//	{
//		this.alarmList = this.alarmControll.getAlarms();
//		lvAlarm.setAdapter(adapter);
//	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		getMenuInflater().inflate(R.menu.menu_alarm, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch (item.getItemId())
//		{
//			case R.id.menu_alarm_add:
//				Intent intent = new Intent(this, AlarmNormalAddActivity.class);
//				startActivityForResult(intent, 0);
//				break;
//			
//			default:
//				break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//	
//	private class AlarmListAdapter extends BaseAdapter implements
//			OnItemClickListener
//	{
//		
//		@Override
//		public int getCount()
//		{
//			return alarmList.size();
//		}
//		
//		@Override
//		public Object getItem(int position)
//		{
//			return alarmList.get(position);
//		}
//		
//		@Override
//		public long getItemId(int position)
//		{
//			return position;
//		}
//		
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent)
//		{
//			if (convertView == null)
//			{
//				convertView = getLayoutInflater().inflate(
//						R.layout.view_alarm_list_item, null);
//				
//			}
//			
//			Clocks model = alarmList.get(position);
//			
//			TextView tvTitle = (TextView) convertView
//					.findViewById(R.id.tv_alarm_list_item_title);
//			TextView tvSpan = (TextView) convertView
//					.findViewById(R.id.tv_alarm_list_item_span);
//			
//			tvTitle.setText(model.getTitle());
//			tvSpan.setText(DateUtil.getDateSpanString(model.getStartDate()));
//			
//			return convertView;
//		}
//		
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id)
//		{
//			Intent intent = new Intent(AlarmListActivity.this,
//					AlarmNormalAddActivity.class);
//			intent.putExtra(Intent.EXTRA_TEXT, alarmList.get(position));
//			startActivityForResult(intent, 0);
//		}
//		
//	}
//	
//	@Override
//	public void onClick(View v)
//	{
//		
//	}
//	
//}
