package com.yixin.nfyh.cloud.ui.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 体征仪表盘和图表切换
 * 
 * @author MrChenrui
 * 
 */
public class ChatViewPagerAdapter extends PagerAdapter
{

	private ArrayList<View>		views;

	private ArrayList<String>	titles;

	public ChatViewPagerAdapter(ArrayList<View> views, ArrayList<String> titles)
	{
		this.views = views;
		this.titles = titles;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return titles.get(position);
	}

	@Override
	public int getCount()
	{
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position)
	{
		// 初始化页面
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView(views.get(position));// 删除页卡
	}

}
