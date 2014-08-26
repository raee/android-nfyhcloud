//package com.yixin.nfyh.cloud.ui;
//
//import java.util.ArrayList;
//
//import com.yixin.nfyh.cloud.ui.adapter.ChatViewPagerAdapter;
//
//import android.content.Context;
//import android.support.v4.view.PagerTabStrip;
//import android.support.v4.view.ViewPager;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * 体征详细记录ViewPager
// * 
// * @author MrChenrui
// * 
// */
//public class SignDetailViewGrop extends ViewPager
//{
//	public SignDetailViewGrop(Context context)
//	{
//		super(context);
//	}
//	
//	public SignDetailViewGrop(Context context, ArrayList<View> views,
//			ArrayList<String> titles)
//	{
//		super(context);
//		
//		PagerTabStrip tab = new PagerTabStrip(context);
//		LayoutParams params = new LayoutParams();
//		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//		params.gravity = Gravity.TOP;
//		
//		tab.setLayoutParams(params);
//		tab.setBackgroundResource(R.drawable.actionbar_blue_bg);
//		tab.setTabIndicatorColorResource(R.color.white);
//		tab.setTextColor(getResources().getColor(R.color.white));
//		
//		LayoutParams param = new LayoutParams();
//		param.width = LayoutParams.MATCH_PARENT;
//		param.height = LayoutParams.MATCH_PARENT;
//		param.gravity = Gravity.CENTER;
//		this.setLayoutParams(param);
//		
//		this.setAdapter(new ChatViewPagerAdapter(views, titles));
//		this.setCurrentItem(0);
//		
//		this.addView(tab);
//	}
//	
//	@Override
//	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4)
//	{
//		
//		super.onLayout(arg0, arg1, arg2, arg3, arg4);
//	}
//	
//}
