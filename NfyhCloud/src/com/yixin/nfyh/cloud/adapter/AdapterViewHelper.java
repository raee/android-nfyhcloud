package com.yixin.nfyh.cloud.adapter;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

/**
 * 列表类型视图帮助器
 * 
 * @author ChenRui
 * 
 */
public class AdapterViewHelper
{
	private View		mEmptyView;
	private TextView	mEmptyMessage;
	private View		mLoaddingView;
	private TextView	mLoaddingMsg;
	private ImageView	mLoaddingImage;
	
	public AdapterViewHelper(View view)
	{
		mEmptyView = view.findViewById(R.id.empty_view);
		if (mEmptyView != null)
		{
			mEmptyMessage = (TextView) mEmptyView.findViewById(R.id.empty_message);
		}
		
		mLoaddingView = view.findViewById(R.id.loadding_view);
		if (mLoaddingView != null)
		{
			mLoaddingMsg = (TextView) mLoaddingView.findViewById(R.id.loadding_message);
			
			mLoaddingImage = (ImageView) mLoaddingView.findViewById(R.id.loadding_image);
		}
		
	}
	
	public AdapterViewHelper(Activity at)
	{
		
		mEmptyView = at.findViewById(R.id.empty_view);
		if (mEmptyView != null)
		{
			mEmptyMessage = (TextView) mEmptyView.findViewById(R.id.empty_message);
		}
		
		mLoaddingView = at.findViewById(R.id.loadding_view);
	}
	
	/**
	 * 显示没有数据视图
	 * 
	 * @param msg
	 */
	public void showEmptyView(String msg)
	{
		if (mEmptyView == null) return;
		dismissLoaddingView();
		mEmptyView.setVisibility(View.VISIBLE);
		mEmptyMessage.setText(msg);
	}
	
	/**
	 * 隐藏没有数据视图
	 */
	public void dismissEmptyView()
	{
		if (mEmptyView == null) return;
		mEmptyView.setVisibility(View.GONE);
	}
	
	/**
	 * 显示加载中视图
	 * 
	 * @param msg
	 */
	public void showLoaddingView()
	{
		showLoaddingView(null);
	}
	
	/**
	 * 显示加载中视图
	 * 
	 * @param msg
	 */
	public void showLoaddingView(String msg)
	{
		if (mLoaddingView == null) return;
		dismissEmptyView();
		mLoaddingView.setVisibility(View.VISIBLE);
		
		if (mLoaddingMsg != null && msg != null)
		{
			mLoaddingMsg.setText(msg);
		}
		if (mLoaddingImage != null)
		{
			AnimationDrawable bg = (AnimationDrawable) mLoaddingImage.getDrawable();
			bg.start();
		}
	}
	
	/**
	 * 取消加载中视图
	 */
	public void dismissLoaddingView()
	{
		if (mLoaddingView == null) return;
		mLoaddingView.setVisibility(View.GONE);
	}
}
