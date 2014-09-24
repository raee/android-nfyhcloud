package com.yixin.nfyh.cloud.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

/**
 * 体征类型Tab视图
 * 
 * @author MrChenrui
 * 
 */
public class SignTypesTabHostView extends TabHost
{
	
	private LayoutInflater	inflat;
	
	private Context			context;
	
	public SignTypesTabHostView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		this.inflat = LayoutInflater.from(context);
		
	}
	
	public void addTab(String title, int iconId, int selectIconId, final View view)
	{
		View signItemView = inflat.inflate(R.layout.ui_sign_type_item, null);
		TextView tvTitleTextView = (TextView) signItemView
				.findViewById(R.id.tv_ui_sign_type_name);
		tvTitleTextView.setText(title);
		tvTitleTextView
				.setCompoundDrawablesWithIntrinsicBounds(0, iconId, 0, 0);
		tvTitleTextView.setTag(selectIconId); //设置选择状态的时候的资源Id
		
		// 添加到选项卡中
		TabSpec tabSpec = this.newTabSpec(String.valueOf(title));
		tabSpec.setIndicator(signItemView);
		TabContentFactory contentFactory = new TabContentFactory()
		{
			@Override
			public View createTabContent(String tag)
			{
				return view;
			}
		};
		tabSpec.setContent(contentFactory );
		super.addTab(tabSpec);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.TabHost#setCurrentTab(int)
	 */
	@Override
	public void setCurrentTab(int index)
	{
		int lastIndex = getCurrentTab();
		super.setCurrentTab(index);
		
		// 设置之前的Tab
		View tabView = getTabWidget().getChildAt(lastIndex);
		initTabItemView(tabView, R.color.white, View.GONE);
		
		// 设置当前点击的Tab
		View lastView = getTabWidget().getChildAt(index);
		initTabItemView(lastView, R.color.Widget_Light_IOS_Tab_Item_TextColor,
				View.VISIBLE);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		if (this.getTabWidget() != null)
		{
			this.getTabWidget().setDividerDrawable(R.drawable.ic_menu_split);
		}
	}
	
	public void initTabItemView(View signItemView, int colorId, int visible)
	{
		try
		{
			TextView tvTitleTextView = (TextView) signItemView
					.findViewById(R.id.tv_ui_sign_type_name);
			tvTitleTextView.setTextColor(context.getResources().getColor(
					colorId));
			ImageView imgSelectImageView = (ImageView) signItemView
					.findViewById(R.id.img_ui_sign_type_selected);
			imgSelectImageView.setVisibility(visible);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
