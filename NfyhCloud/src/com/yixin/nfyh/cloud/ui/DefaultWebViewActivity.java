package com.yixin.nfyh.cloud.ui;

import com.yixin.nfyh.cloud.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

/**
 * 默认的浏览器
 * 
 * @author MrChenrui
 * 
 */
public class DefaultWebViewActivity extends Activity
{
	private WebView	webView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.webView = new WebView(this);
		this.webView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)); // 设置布局
		setContentView(webView);
		ActionbarUtil.setDefaultActionBar(this, getActionBar()); // 设置标题
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// 菜单Action

		MenuItem loadingItem = menu.add(0, 0, 0, "刷新");
		loadingItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		loadingItem.setIcon(R.drawable.app_panel_setting_icon);
		return super.onCreateOptionsMenu(menu);
	}
}
