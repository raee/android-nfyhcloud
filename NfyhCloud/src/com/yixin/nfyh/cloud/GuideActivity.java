package com.yixin.nfyh.cloud;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;

public class GuideActivity extends Activity {
	// 翻页控件
	private ViewPager	mViewPager;
	private int			mViewCount;
	private int			mCurrentIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.whats_news_gallery_one, null);
		View view2 = mLi.inflate(R.layout.whats_news_gallery_two, null);
		
		ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		
		// 填充ViewPager的数据适配器
		MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views);
		mViewPager.setAdapter(mPagerAdapter);
		
		mViewCount = views.size();
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageSelected(int page) {
			mCurrentIndex = page;
		}
		
		@Override
		public void onPageScrolled(int index, float arg1, int arg2) {
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
			int count = mViewCount - 1;
			int page = mViewPager.getCurrentItem();
			if (page == count && mCurrentIndex == mViewCount + 1) {
				gotoLoginActivity();
			}
			mCurrentIndex++;
		}
	}
	
	private void gotoLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	private class MyPagerAdapter extends PagerAdapter {
		
		private ArrayList<View>	views;
		
		public MyPagerAdapter(ArrayList<View> views) {
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
}
