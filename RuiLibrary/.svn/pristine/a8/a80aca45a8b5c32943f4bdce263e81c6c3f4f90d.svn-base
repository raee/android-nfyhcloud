package cn.rui.framework.ui;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import cn.rui.framework.widget.TabView;

import com.rui.framework.R;

/**
 * 
 * 标签页Activity
 * 
 * @author MrChenrui
 * 
 */
@SuppressWarnings("deprecation")
public abstract class TabHostActivity extends ActivityGroup implements
		OnTabChangeListener
{
	public static final int	TAB_LOCATION_TOP	= 0;
	public static final int	TAB_LOCATION_BOTTOM	= 1;

	private TabHost			tabhost;
	private TabWidget		tabwidget;
	private int				curIndex			= 0;
	private int				sIndex				= 0;
	private float			startX;
	private boolean			enableGesture		= false;

	public void setTabHostView(TabHost tab)
	{
		this.tabhost = tab;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (tabhost != null)
			return;

		setContentView(R.layout.rui_tabhost);

		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(this.getLocalActivityManager()); // 如果Tab有Intent的，必须该类继承ActivityGroup
		tabwidget = tabhost.getTabWidget();

		tabhost.setOnTabChangedListener(this);
		int bgId = getBackgournd();
		if (bgId != 0)
			tabwidget.setBackgroundResource(bgId);

	}

	/**
	 * 是否启动手势支持切换页面
	 * 
	 * @param value
	 */
	public void enableGesture(boolean value)
	{
		this.enableGesture = value;
	}

	public void addTabViews(TabView... tabViews)
	{
		for (TabView tabView : tabViews)
		{
			addTab(tabView);
		}
	}

	private void addTab(TabView tabView)
	{
		try
		{
			if (tabView == null)
				return;
			TabSpec tab = tabhost.newTabSpec(String.valueOf(tabwidget
					.getTabCount()));
			tab.setIndicator(tabView);
			Intent intent = tabView.getIntent();
			int contentId = tabView.getContentViewId();
			if (intent != null)
				tab.setContent(intent);
			else
				tab.setContent(contentId);

			tabhost.addTab(tab);
		}
		catch (Exception e)
		{
			Log.e("TabHostActivity", "添加选项卡失败：" + e.getMessage());
			e.printStackTrace();
		}

	}

	public abstract int getBackgournd();

	/**
	 * 获取当前的TabHost
	 * 
	 * @return
	 */
	public TabHost getTabHost()
	{
		return tabhost;
	}

	@Override
	public void onTabChanged(String tabId)
	{

		int index = Integer.valueOf(tabId); // 点击的索引

		// // 切换当前
		TabView tab = (TabView) tabwidget.getChildAt(index); // 点击选项卡的View
		tab.getIconView().setImageResource(tab.getHoverIconId());
		tab.setBackgroundResource(tab.getHoverBackgroundId());

		// 保证不超过个数
		if (curIndex != index && curIndex < tabwidget.getTabCount()
				&& curIndex > -1)
		{

			// 恢复上一个
			TabView lastView = (TabView) tabwidget.getChildAt(curIndex);
			lastView.getIconView().setImageResource(lastView.getIconId());
			lastView.setBackgroundResource(lastView.getBackgoundId());
		}

		curIndex = index; // 保存当前点击的索引号
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (enableGesture)
			onTouch(event);
		return super.onTouchEvent(event);
	}

	private void showPre()
	{

		if (sIndex <= 0)
		{
			sIndex = 0;
			return;
		}
		sIndex--;
		tabhost.setCurrentTab(sIndex);
	}

	private void showNext()
	{
		int count = tabwidget.getTabCount();
		if (sIndex >= count)
		{
			sIndex = count;
			return;
		}
		sIndex++;
		tabhost.setCurrentTab(sIndex);
	}

	public boolean onTouch(MotionEvent event)
	{

		int action = event.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				this.startX = event.getRawX();
				break;
			case MotionEvent.ACTION_UP:
				float x = event.getRawX();
				if ((this.startX - x) > 120)
					// 左移
					showNext();
				if ((x - this.startX) > 120)
					// 右移
					showPre();
				break;
			default:
				break;
		}
		return false;
	}

}
