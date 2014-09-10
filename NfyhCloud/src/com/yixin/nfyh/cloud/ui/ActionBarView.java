package com.yixin.nfyh.cloud.ui;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义的ActionbarView
 * 
 * @author MrChenrui
 * 
 */
public class ActionBarView extends LinearLayout
{
	private TextView	tvTitle;
	
	public ActionBarView(Context context)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.ui_default_actionbar,
				this);
		tvTitle = (TextView) findViewById(R.id.tv_ui_actionbar_title);
	}
	
	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.tvTitle.setText(title);
		this.setDisplayTitleEnable(true); //显示标题
	}
	
	/**
	 * 是否显示自定义标题
	 * 
	 * @param enable
	 */
	public void setDisplayTitleEnable(boolean enable)
	{
		int visibility = enable ? View.VISIBLE : View.GONE;
		
		this.tvTitle.setVisibility(visibility);
	}
	
}
