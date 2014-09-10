package com.yixin.nfyh.cloud.adapter;

import java.util.List;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yixin.nfyh.cloud.model.Photocategory;

/**
 * 照片分类适配器
 * 
 * @author MrChenrui
 * 
 */
public class PhotocategoryAdapter extends ListViewAdapter<Photocategory>

{
	
	public PhotocategoryAdapter(Context context, List<Photocategory> dataList)
	{
		super(context, dataList);
		super.setEmptyMessage("这里还没有分类，点击下面分类添加一个吧！");
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		super.getView(position, convertView, parent);
		ViewHolder holder = null;
		Photocategory model = getDataItem(position);
		
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(
					R.layout.view_photo_category_item, null);
			holder = new ViewHolder();
			holder.titleView = (TextView) convertView
					.findViewById(R.id.tv_photocategory_title);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titleView.setText(model.getName());
		return convertView;
	}
	
	private class ViewHolder
	{
		public TextView	titleView;
	}
	
}
