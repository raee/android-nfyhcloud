package com.yixin.nfyh.cloud;

import java.util.List;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.ui.TabHostActivity;
import cn.rui.framework.utils.CommonUtil;

import com.yixin.nfyh.cloud.activity.SignDetailActivity;
import com.yixin.nfyh.cloud.activity.SignGroupActivity;
import com.yixin.nfyh.cloud.activity.UserSettingActivity;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.bll.VersionUpdate;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.device.DefaultDevice;
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
	private NfyhApplication	app;

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

		// 初始化Tab 页
		addTab();

		ConfigServer config = new ConfigServer(this);
		if (!app.isConnected() && config.getBooleanConfig(ConfigServer.KEY_AUTO_CONNECTED)) // 提示连接设备
		{
			new RuiDialog.Builder(this).buildTitle("设备连接提示").buildMessage("您没有连接设备，是否现在就连接设备？").buildLeftButton("不连接", null)
					.buildRight("连接", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							app.connect();
							dialog.dismiss();
						}
					}).show();
		}

		// 自動檢測新版本
		VersionUpdate version = new VersionUpdate(this);
		version.showDialog(false);
		version.check();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			View actionView = findViewById(R.id.menu_main_device);
			if (app.isConnected() && actionView != null) {
				CommonUtil.setActionViewItemIcon(actionView, R.drawable.ic_switch_device_connected);
			}
		}
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
				DefaultDevice.connect(this, app.getApiMonitor());
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
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

	private void addTab() {
		// 过滤模块
		List<String> modules = NfyhCloudDataFactory.getFactory(this).getUser().getUserModule(app.getCurrentUser().getUid());
		if (modules == null || modules.size() <= 0) {
			addTabAll();
			return;
		}

		try {
			for (String val : modules) {
				int index = Integer.valueOf(val);
				switch (index) {
					case 1:
						addSignTab();
						break;
					case 2:
						addManagerTab();
						break;
					case 3:
						addFileTab();
						break;
					case 4:
						addSOSTab();
						break;
					default:
						break;
				}
			}

			// 只有一个Tab页，隐藏底部Tab。
			if (modules.size() <= 1) {
				getTabHost().getTabWidget().setVisibility(View.GONE);
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			addTabAll();
		}

	}

	// 添加所有的的Tab页
	private void addTabAll() {
		addSignTab();
		addManagerTab();
		addFileTab();
		addSOSTab();
	}

	// 体征测量
	private void addSignTab() {

		List<String> types = NfyhCloudDataFactory.getFactory(this).getUser().getUserSignType(app.getCurrentUser().getUid());
		Intent intent = null;
		// 只有一个，直接设置。
		if (types != null && types.size() == 1) {
			intent = new Intent(this, SignDetailActivity.class);
			intent.putExtra(Intent.EXTRA_TEXT, types.get(0));
		}
		else {
			intent = new Intent(this, SignGroupActivity.class);
		}

		add(R.string.tab_title_tzcl, R.drawable.icon_computer, R.drawable.icon_computer_2, R.drawable.tab_hover, intent);
	}

	// 院后管理
	private void addManagerTab() {
		add(R.string.tab_title_yhgl, R.drawable.icon_note, R.drawable.icon_note_2, R.drawable.tab_hover, HospitalManagerActivity.class);
	}

	// 院后档案
	private void addFileTab() {
		add(R.string.tab_title_yhda, R.drawable.icon_cloud, R.drawable.icon_cloud_2, R.drawable.tab_hover, HospitalFileActivity.class);

	}

	// 紧急呼救
	private void addSOSTab() {
		add(R.string.tab_title_jjhj, R.drawable.icon_hand, R.drawable.icon_hand_2, R.drawable.tab_hover, new Intent(this, OneKeySoSActivity.class));
	}
}
