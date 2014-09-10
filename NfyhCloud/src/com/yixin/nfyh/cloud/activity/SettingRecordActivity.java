package com.yixin.nfyh.cloud.activity;

import com.yixin.nfyh.cloud.R;
import android.os.Bundle;

import com.yixin.nfyh.cloud.BaseActivity;

/**
 * 设置 - 语音呼救
 * 获取已有录音
 * 录音，并保存。
 * 删除已有的录音
 * 
 * @author Chenrui
 * 
 */
public class SettingRecordActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_record);
	}

	@Override
	protected String getActivityName()
	{
		return getString(R.string.activity_setting_desktop);
	}
}
