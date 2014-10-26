package com.yixin.nfyh.cloud.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class BaseViewPageAdapter extends PagerAdapter {
	
	private ArrayList<View>	views;
	
	public BaseViewPageAdapter(ArrayList<View> views) {
		this.views = views;
	}
	
	@Override
	public int getCount() {
		return this.views.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position));
		return views.get(position);
		
	}
}
