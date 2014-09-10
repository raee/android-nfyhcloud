package com.yixin.nfyh.cloud;

import java.util.ArrayList;
import com.yixin.nfyh.cloud.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rui.framework.utils.AppInfo;

public class GuideActivity extends Activity
{
	// 翻页控件
	private ViewPager	mViewPager;

	// 这5个是底部显示当前状态点imageView
	private ImageView	mPage0;
	private ImageView	mPage1;
	private ImageView	mPage2;
	private Button		btmStart;
	private TextView	tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);

		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);

		/*
		 * 这里是每一页要显示的布局，根据应用需要和特点自由设计显示的内容 以及需要显示多少页等
		 */
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.whats_news_gallery_one, null);
		View view2 = mLi.inflate(R.layout.whats_news_gallery_two, null);
		View view3 = mLi.inflate(R.layout.whats_news_gallery_three, null);

		btmStart = (Button) view3.findViewById(R.id.start_btn);
		tvVersion = (TextView) view3.findViewById(R.id.tv_version);
		btmStart.setOnClickListener(new btmStartOnClickListener());
		/*
		 * 这里将每一页显示的view存放到ArrayList集合中 可以在ViewPager适配器中顺序调用展示
		 */
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		/*
		 * 每个页面的Title数据存放到ArrayList集合中 可以在ViewPager适配器中调用展示
		 */
		final ArrayList<String> titles = new ArrayList<String>();
		titles.add("tab1");
		titles.add("tab2");
		titles.add("tab3");

		// 填充ViewPager的数据适配器
		MyPagerAdapter mPagerAdapter = new MyPagerAdapter(views, titles);
		mViewPager.setAdapter(mPagerAdapter);
		// 设置版本号
		AppInfo ai = new AppInfo(getApplicationContext());
		tvVersion.setText("V" + ai.getVersion());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int page)
		{

			// 翻页时当前page,改变当前状态园点图片
			switch (page)
			{
				case 0:
					mPage0.setImageDrawable(getResources().getDrawable(
							R.drawable.page_now));
					mPage1.setImageDrawable(getResources().getDrawable(
							R.drawable.page));
					break;
				case 1:
					mPage1.setImageDrawable(getResources().getDrawable(
							R.drawable.page_now));
					mPage0.setImageDrawable(getResources().getDrawable(
							R.drawable.page));
					mPage2.setImageDrawable(getResources().getDrawable(
							R.drawable.page));
					break;
				case 2:
					mPage2.setImageDrawable(getResources().getDrawable(
							R.drawable.page_now));
					mPage1.setImageDrawable(getResources().getDrawable(
							R.drawable.page));
					break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	private class btmStartOnClickListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			finish();
		}

	}

	private class MyPagerAdapter extends PagerAdapter
	{

		private ArrayList<View>	views;

		public MyPagerAdapter(ArrayList<View> views, ArrayList<String> titles)
		{

			this.views = views;
		}

		@Override
		public int getCount()
		{
			return this.views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object)
		{
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position)
		{
			((ViewPager) container).addView(views.get(position));
			return views.get(position);

		}
	}
}
