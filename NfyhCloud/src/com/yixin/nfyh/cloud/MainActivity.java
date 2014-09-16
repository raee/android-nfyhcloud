package com.yixin.nfyh.cloud;

import com.yixin.nfyh.cloud.R;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.ui.TabHostActivity;
import cn.rui.framework.utils.CommonUtil;

import com.yixin.nfyh.cloud.activity.SignGroupActivity;
import com.yixin.nfyh.cloud.activity.UserSettingActivity;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.broadcastreceiver.DeviceReceviceBroadcasetreceiver;
import com.yixin.nfyh.cloud.service.CoreServerBinder;
import com.yixin.nfyh.cloud.ui.ActionBarView;
import com.yixin.nfyh.cloud.ui.ActionbarUtil;
import com.yixin.nfyh.cloud.widget.TabHostView;

/**
 * 主界面
 * 
 * @author MrChenrui
 * 
 */
public class MainActivity extends TabHostActivity implements OnClickListener {
	private NfyhApplication						app;
	private boolean								isCreated;
	private DeviceReceviceBroadcasetreceiver	receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		app = (NfyhApplication) getApplication();
		app.addActivity(this);
		
		ActionBar bar = ActionbarUtil.setActionbar(this, this.getActionBar());
		ActionBarView actionView = (ActionBarView) bar.getCustomView();
		actionView.setTitle(app.getCurrentUser() == null ? "未登录" : app.getCurrentUser().getName());
		actionView.setOnClickListener(this);
		actionView.setId(android.R.id.home);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayUseLogoEnabled(false);
		
		// 启动手势切换
		// enableGesture(true);
		
		Intent sosIntent = new Intent(this, OneKeySoSActivity.class);
		
		// 体征测量
		add(R.string.tab_title_tzcl, R.drawable.icon_computer, R.drawable.icon_computer_2, R.drawable.tab_hover, SignGroupActivity.class);
		
		// 院后管理
		add(R.string.tab_title_yhgl, R.drawable.icon_note, R.drawable.icon_note_2, R.drawable.tab_hover, HospitalManagerActivity.class);
		
		// 院后档案
		add(R.string.tab_title_yhda, R.drawable.icon_cloud, R.drawable.icon_cloud_2, R.drawable.tab_hover, HospitalFileActivity.class);
		
		// 紧急呼救
		add(R.string.tab_title_jjhj, R.drawable.icon_hand, R.drawable.icon_hand_2, R.drawable.tab_hover, sosIntent);
		
		ConfigServer config = new ConfigServer(this);
		if (!app.isConnected() && config.getBooleanConfig(ConfigServer.KEY_AUTO_CONNECTED)) // 提示连接设备
		{
			new RuiDialog.Builder(this).buildTitle("设备连接提示").buildMessage("您没有连接设备，是否现在就连接设备？").buildLeftButton("不连接", null).buildRight("连接", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					app.connect();
					dialog.dismiss();
				}
			}).show();
		}
		
		// startActivity(new Intent(this, PhotoActivity.class));// TODO:删除该句
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (isCreated || !hasFocus) return;
		View actionView = findViewById(R.id.menu_main_device);
		
		if (app.isConnected() && actionView != null) {
			CommonUtil.setActionViewItemIcon(actionView, R.drawable.ic_switch_device_connected);
		}
		registerReceiver(this, findViewById(R.id.contentview), actionView);
		isCreated = true;
	}
	
	/**
	 * 注册
	 * 
	 * @param context
	 * @param contentView
	 */
	public void registerReceiver(Context context, View contentView, View actionView) {
		Log.i("tt", "--> 注册广播！");
		receiver = new DeviceReceviceBroadcasetreceiver(context, contentView, actionView);
		IntentFilter filter = new IntentFilter();
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTING);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_DISCONNECTED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_FAILD);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICED);
		filter.addAction(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICEING);
		context.registerReceiver(receiver, filter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_main_device:
				connectDevice();
				break;
			
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void connectDevice() {
		
		if (app.isConnected()) {
			new RuiDialog.Builder(this).buildTitle("断开连接").buildMessage("设备已经连接，是否要断开监测设备？").buildLeftButton("否", null).buildRight("断开", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					app.disconnect();
					dialog.dismiss();
					
				}
			}).show();
		}
		else {
			Toast.makeText(this, "正在连接设备...", Toast.LENGTH_LONG).show();
			app.connect();
		}
	}
	
	@Override
	public int getBackgournd() {
		return R.drawable.bg_tab;
	}
	
	private void add(int title, int icon, int hicon, int bg, Class<?> cls) {
		
		add(title, icon, hicon, bg, new Intent(this, cls));
	}
	
	private void add(int title, int icon, int hicon, int bg, Intent intent) {
		TabHostView tabView = new TabHostView(this);
		tabView.setTitle(getResources().getString(title));
		tabView.setIconId(icon);
		tabView.setHoverIconId(hicon);
		tabView.setHoverBackgroundId(bg);
		tabView.setIntent(intent);
		addTabViews(tabView);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, UserSettingActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
}
