package com.yixin.nfyh.cloud.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

public class EmpteyView extends LinearLayout
{
	
	private TextView	tvTips;
	
	public EmpteyView(Context context)
	{
		super(context, null);
	}
	
	/**
	 * @param context
	 * @param contentView
	 *            活动视图
	 */
	public EmpteyView(Context context, ViewGroup contentView)
	{
		super(context, null);
		LayoutInflater.from(context).inflate(R.layout.view_empty, this);
		this.tvTips = (TextView) findViewById(R.id.tv_empty_title);
		contentView.addView(this);
		this.setVisibility(View.GONE);
	}
	
	public void setText(String text)
	{
		tvTips.setText(text);
	}
}
