package com.yixin.nfyh.cloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import cn.rui.framework.ui.WebViewerActivity;

import com.yixin.nfyh.cloud.activity.MessageActivity;
import com.yixin.nfyh.cloud.activity.PhotoActivity;
import com.yixin.nfyh.cloud.activity.SignGanyuActivity;
import com.yixin.nfyh.cloud.bll.GlobalSetting;

/**
 * 院后档案
 * 
 * @author 睿
 * 
 */
public class HospitalFileActivity extends BaseActivity
{
	
	private GlobalSetting	setting;
	
	@Override
	public GlobalSetting getSetting()
	{
		if (setting == null) setting = new GlobalSetting(this);
		return setting;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yhda);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.ll_menu_yhzp: //院后照片
				startActivity(new Intent(this, PhotoActivity.class));
				break;
			case R.id.ll_menu_xxts: // 消息提醒
				startActivity(new Intent(this, MessageActivity.class));
				break;
			case R.id.ll_menu_tzgy: // 体征干预
				startActivity(new Intent(this, SignGanyuActivity.class));
				break;
			case R.id.ll_menu_jkpg: // 健康评估
				
				Intent jkpgIntent = new Intent(this, WebViewerActivity.class);
				Uri uri = Uri.parse(getResources().getString(R.string.url_health_assess));
				jkpgIntent.setData(uri);
				jkpgIntent.putExtra(WebViewerActivity.EXTRA_COOKIE, getSetting().getUser().getCookie());
				
				startActivity(jkpgIntent);
				break;
			default:
				break;
		}
	}
	
}
