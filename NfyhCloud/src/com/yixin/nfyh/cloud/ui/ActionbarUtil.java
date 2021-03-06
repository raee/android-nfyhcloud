package com.yixin.nfyh.cloud.ui;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.yixin.nfyh.cloud.R;

/**
 * 标题栏帮助类
 * 
 * @author MrChenrui
 * 
 */
public class ActionbarUtil {
	private ActionbarUtil() {
	}
	
	/**
	 * 设置默认的标题栏
	 * 
	 * @param bar
	 */
	public static ActionBar setActionbar(Context context, ActionBar bar) {
		if (bar == null) return bar;
		ActionBarView actionbarView = new ActionBarView(context); // 自定义的ActionbarView
		
		bar.setDisplayShowTitleEnabled(false); // 不显示标题
		bar.setDisplayShowHomeEnabled(false); // 不显示Icon
		bar.setDisplayShowCustomEnabled(true); // 启动自定义显示视图
		bar.setCustomView(actionbarView); // 设置自定义视图
		//setDefaultActionBar(context, bar);
		
		return bar;
	}
	
	/**
	 * 设置默认的Actionbar
	 */
	public static ActionBar setDefaultActionBar(Context context, ActionBar bar) {
		Drawable bg = context.getResources().getDrawable(R.drawable.actionbar_bg);
		bar.setBackgroundDrawable(bg);
		return bar;
	}
	
	public static ActionBar setTitleAsUpHome(Context context, ActionBar bar, String title) {
		if (bar == null) return bar;
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowCustomEnabled(false);
		bar.setDisplayShowTitleEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayUseLogoEnabled(false);
		bar.setTitle(title);
		return bar;
	}
	
	/**
	 * 设置背景颜色
	 * 
	 * @param context
	 * @param bar
	 * @param resid
	 * @return
	 */
	public static ActionBar setBackgroud(Context context, ActionBar bar, int resid) {
		if (bar == null) return bar;
		bar.setBackgroundDrawable(context.getResources().getDrawable(resid));
		return bar;
	}
	
}
