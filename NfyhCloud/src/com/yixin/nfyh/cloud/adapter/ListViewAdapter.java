package com.yixin.nfyh.cloud.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.ui.EmpteyView;

public abstract class ListViewAdapter<T> extends BaseAdapter
{
	public List<T>		dataList;
	protected Context	context;
	private EmpteyView	emptView;
	private String		mEmptyMessage;
	private ViewGroup	contentView;
	
	public ListViewAdapter(Context context, List<T> dataList)
	{
		this.context = context;
		this.dataList = dataList;
	}
	
	public T getDataItem(int position)
	{
		return this.dataList.get(position);
	}
	
	@Override
	public int getCount()
	{
		int count = dataList.size();
		if (count <= 0)
		{
			Log.i("ListViewAdapter", "ListView 没有数据~");
			this.showEmptyView();
		}
		return count;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (emptView != null)
		{
			emptView.setVisibility(View.GONE);
			this.contentView = parent;
		}
		if(parent.getVisibility() == View.GONE)
		{
			parent.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}
	
	@Override
	public Object getItem(int position)
	{
		return dataList.get(position);
	}
	
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	/**
	 * 没有内容的视图，使用该方法，需要在主布局文件下创建一个Viewgroud并且Id为：android.R.id.empty的视图
	 */
	public void showEmptyView()
	{
		if (contentView != null)
		{
			contentView.setVisibility(View.GONE);
		}
		if (emptView == null)
		{
			ViewGroup view = (ViewGroup) ((Activity) this.context)
					.findViewById(android.R.id.empty);
			view.setVisibility(View.VISIBLE);
			emptView = new EmpteyView(this.context, view);
		}
		
		mEmptyMessage = mEmptyMessage == null ? "没有找到数据" : mEmptyMessage;
		emptView.setText(mEmptyMessage);
		emptView.setVisibility(View.VISIBLE);
		emptView.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.fade_in));
	}
	
	public void hideEmptyView()
	{
		if (emptView != null) emptView.setVisibility(View.GONE);
	}
	
	public void setEmptyMessage(String msg)
	{
		this.mEmptyMessage = msg;
	}
	
}
