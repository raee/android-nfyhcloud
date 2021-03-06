package com.yixin.nfyh.cloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cn.rui.framework.widget.RuiSwitch;
import cn.rui.framework.widget.RuiSwitch.OnCheckedChangeListener;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;

/**
 * 设置-桌面悬浮框设置界面
 * 
 * @author MrChenrui
 * 
 */
public class SettingDesktopActivity extends BaseActivity implements OnCheckedChangeListener
{

	private ConfigServer	config;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_desktop);
		config = new ConfigServer(this);
		findViewById(R.id.ll_setting_desktop_phone).setOnClickListener(this);
		findViewById(R.id.ll_setting_desktop_events).setOnClickListener(this);
		findViewById(R.id.ll_setting_desktop_record).setOnClickListener(this);
		RuiSwitch swDesktop = (RuiSwitch) findViewById(R.id.sw_setting_desktop);
		swDesktop.setOnCheckedChangeListener(this);
		swDesktop.setChecked(config.getBooleanConfig(ConfigServer.KEY_ENABLE_DESKTOP));
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this, SettingPhoneEventActivity.class);
		switch (v.getId())
		{
			case R.id.ll_setting_desktop_phone:
				intent.putExtra(Intent.EXTRA_TEXT, ConfigServer.KEY_DESKTOP_PHONE_LIST);
				break;
			case R.id.ll_setting_desktop_events:
				intent.putExtra(Intent.EXTRA_TEXT, ConfigServer.KEY_DESKTOP_EVENT_LIST);
				break;
			case R.id.ll_setting_desktop_record:
				intent.setClass(this, SettingRecordActivity.class);
				break;
			default:
				break;
		}
		startActivity(intent);
	}

	@Override
	protected String getActivityName()
	{
		return getString(R.string.activity_setting_desktop);
	}

	@Override
	public void onCheckedChanged(RuiSwitch switchView, boolean isChecked)
	{
		config.enableDesktop(isChecked);
	}
}
