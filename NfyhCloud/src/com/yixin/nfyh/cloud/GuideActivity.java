package com.yixin.nfyh.cloud;

import java.util.ArrayList;

import com.yixin.nfyh.cloud.adapter.BaseViewPageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 引导页
 * 
 * @author ChenRui
 * 
 */
public class GuideActivity extends Activity {
	// 翻页控件
	private ViewPager mViewPager;
	private int mViewCount;
	private int mCurrentIndex;

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
		BaseViewPageAdapter mPagerAdapter = new BaseViewPageAdapter(views);
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

	/**
	 * 跳转到登录界面
	 */
	private void gotoLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
