package com.rae.alarm;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.rui.framework.ui.RuiDialog;

import com.rae.alarm.AlarmListActivity.AlarmListAdapter.Viewholder;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.AlarmUtils;
import com.rae.core.alarm.IDbAlarm;
import com.rae.core.alarm.provider.AlarmProviderFactory;
import com.yixin.nfyh.cloud.R;

public class AlarmListActivity extends Activity implements OnClickListener {
	
	private ListView			mAlarmListView;
	private AlarmListAdapter	mAlarmListAdapter;
	private Timer				mTimer;
	private Handler				mhHandler	= new Handler(new Handler.Callback() {
												
												@Override
												public boolean handleMessage(Message msg) {
													refreshListView();
													return false;
												}
											});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_list);
		//		setContentView(R.layout.window_alarm_type_list);
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		mAlarmListView = (ListView) findViewById(R.id.lv_alarm_list);
		findViewById(R.id.tv_alarm_normal).setOnClickListener(this);
		IDbAlarm db = AlarmProviderFactory.getDbAlarm(this);
		mAlarmListAdapter = new AlarmListAdapter(db.getAlarms());
		mAlarmListView.setAdapter(mAlarmListAdapter);
		mAlarmListView.setOnItemLongClickListener(mAlarmListAdapter);
		mAlarmListView.setOnItemClickListener(mAlarmListAdapter);
		
		// 倒计时计算
		if (mAlarmListAdapter.getCount() > 0) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Message.obtain(mhHandler).sendToTarget();
				}
			}, 1000, 1000);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTimer.cancel();
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
	
	class AlarmListAdapter extends BaseAdapter implements OnItemLongClickListener, OnItemClickListener {
		
		private List<AlarmEntity>	mAlarmList;
		
		public AlarmListAdapter(List<AlarmEntity> alarms) {
			
			mAlarmList = alarms;
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
		
		public List<AlarmEntity> getDataList() {
			return mAlarmList;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			Viewholder holder = null;
			AlarmEntity m = mAlarmList.get(position);
			if (view == null) {
				view = LayoutInflater.from(AlarmListActivity.this).inflate(R.layout.item_alarm_list, null);
				holder = new Viewholder();
				holder.tvTitle = (TextView) view.findViewById(R.id.tv_alarm_title);
				holder.tvTips = (TextView) view.findViewById(R.id.tv_alarm_tips);
				holder.tvTime = (TextView) view.findViewById(R.id.tv_alarm_time);
				view.setTag(holder);
			}
			else {
				holder = (Viewholder) view.getTag();
			}
			if (position == 0) {
				setTimeLine(holder, m);
			}
			else {
				holder.tvTips.setText(m.getNextTime());
			}
			
			holder.tvTitle.setText(m.getTitle());
			holder.tvTime.setText(AlarmUtils.dateToString("HH:mm", m.getTime()));
			return view;
		}
		
		public void setTimeLine(Viewholder holder, AlarmEntity m) {
			// 高亮数字时间，正在匹配数字。
			String tips = AlarmUtils.getNextTimeSpanString(m.getNextTime());
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(tips);
			while (matcher.find()) {
				String val = matcher.group();
				String html = "<big><font color='red'>" + val + "</font></big>";
				tips = tips.replace(val, html);
			}
			
			holder.tvTips.setText(Html.fromHtml(tips));
		}
		
		class Viewholder {
			public TextView	tvTitle, tvTips, tvTime;
		}
		
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
			final AlarmEntity entity = mAlarmList.get(position);
			
			// 删除闹钟对话框
			new RuiDialog.Builder(AlarmListActivity.this).buildTitle("删除闹钟").buildMessage("是否删除：" + entity.getTitle() + "?").buildLeftButton("返回", null).buildRight("删除", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					AlarmProviderFactory.getProvider(AlarmListActivity.this, entity).delete();
					mAlarmList.remove(position);
					notifyDataSetChanged();
					dialog.dismiss();
				}
			}).show();
			return false;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(AlarmListActivity.this, AlarmAddNormalActivity.class);
			intent.putExtra("data", mAlarmList.get(position));
			startActivity(intent);
			finish();
		}
		
	}
	
	private void refreshListView() {
		if (mAlarmListView.getChildCount() > 0) {
			AlarmEntity m = mAlarmListAdapter.getDataList().get(0);
			Viewholder holder = (Viewholder) mAlarmListView.getChildAt(0).getTag();
			mAlarmListAdapter.setTimeLine(holder, m);
		}
	}
	
}
