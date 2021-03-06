package com.yixin.nfyh.cloud.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 体征测量主Adapt
 * 
 * @author MrChenrui
 * 
 */
public class SignGroupTypePagerAdapter extends PagerAdapter
{
	
	private List<View>	list;
	
	public SignGroupTypePagerAdapter(List<View> list)
	{
		this.list = list;
	}
	
	@Override
	public void destroyItem(ViewGroup view, int position, Object arg2)
	{
		ViewPager pViewPager = ((ViewPager) view);
		pViewPager.removeView(list.get(position));
	}
	
	@Override
	public int getCount()
	{
		return list.size();
	}
	
	@Override
	public Object instantiateItem(View view, int position)
	{
		ViewPager pViewPager = ((ViewPager) view);
		pViewPager.addView(list.get(position));
		return list.get(position);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}
	
}
