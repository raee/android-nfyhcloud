package com.yixin.nfyh.cloud.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.activity.AddGanyuActivity;
import com.yixin.nfyh.cloud.model.GanyuInfo;

/**
 * 干预适配器
 * 
 * @author ChenRui
 * 
 */
public class GanyuListViewAdapter extends BaseListAdapter<GanyuInfo>
{
	
	public GanyuListViewAdapter(Context context, List<GanyuInfo> datas)
	{
		super(context, datas);
	}
	
	@Override
	public void fillViewData(AdapterViewHolder absHolder, GanyuInfo m)
	{
		ViewHolder holder = (ViewHolder) absHolder;
		holder.tvName.setText(m.getTitle());
	}
	
	@Override
	public AdapterViewHolder getViewHolder(View view)
	{
		ViewHolder holder = new ViewHolder();
		holder.tvName = (TextView) view.findViewById(R.id.tv_item_ptinfo_ganyu_name);
		return holder;
	}
	
	@Override
	public View getItemView(LayoutInflater inflater)
	{
		return super.initView(R.layout.item_ptinfo_ganyu);
	}
	
	protected class ViewHolder extends AdapterViewHolder
	{
		public TextView	tvName;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		GanyuInfo m = getDataItem(position);
		
		Intent intent = new Intent(getContext(), AddGanyuActivity.class);
		intent.putExtra("data", m);
		getContext().startActivity(intent);
		
	}
	
}
