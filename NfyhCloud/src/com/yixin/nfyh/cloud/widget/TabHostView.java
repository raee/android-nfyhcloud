package com.yixin.nfyh.cloud.widget;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rui.framework.widget.TabView;

public class TabHostView extends TabView
{

	public TabHostView(Context context)
	{
		super(context, null);
	}

	private View		view;
	private ImageView	imgIcon;
	private TextView	tvTitleView;

	@Override
	public void loadLayout(Context context)
	{
		view = LayoutInflater.from(context)
				.inflate(R.layout.view_tabview, this);
		imgIcon = (ImageView) view.findViewById(R.id.img_tab_icon);
		tvTitleView = (TextView) view.findViewById(R.id.tv_tab_title);
	}

	@Override
	public ImageView getIconView()
	{
		return imgIcon;
	}

	@Override
	public TextView getTitleView()
	{
		return tvTitleView;
	}

}
