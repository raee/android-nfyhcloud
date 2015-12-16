package com.yixin.nfyh.cloud.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.yixin.nfyh.cloud.R;

/**
 * 聊天界面
 * 
 * @author ChenRui
 * 
 */
public class ChatActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowHomeEnabled(false); // 不显示LOGO
		setContentView(R.layout.raeim_conversation);
		
		Uri uri = getIntent().getData();
		if (uri != null && uri.getQueryParameter("title") != null)
		{
			setTitle("与" + uri.getQueryParameter("title") + "对话");
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
