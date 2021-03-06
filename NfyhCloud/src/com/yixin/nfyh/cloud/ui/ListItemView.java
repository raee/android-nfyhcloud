package com.yixin.nfyh.cloud.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

/**
 * 列表项视图
 * 
 * @author Chenrui
 * 
 */
public class ListItemView extends LinearLayout
{
	
	private TextView		tvTitle;
	private TextView		tvSubTitle;
	private ImageView		imgNext;
//	private OnClickListener	listener;
	private Context			context;
//	private View			viewBorder;
	private LinearLayout	customerViews;
//	private Intent			nextIntent;
	
	public ListItemView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	
	public ListItemView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.view_list_item, this);
		this.tvTitle = (TextView) this
				.findViewById(R.id.tv_view_liset_item_title);
		this.tvSubTitle = (TextView) this
				.findViewById(R.id.tv_view_liset_item_sub_title);
		this.imgNext = (ImageView) this
				.findViewById(R.id.img_view_liset_item_right);
		
//		this.viewBorder = findViewById(R.id.view_list_item_border);
		customerViews = (LinearLayout) this
				.findViewById(R.id.viewStub_view_list_item);
		
		initView(attrs, defStyle);
	}
	
	private void initView(AttributeSet attrs, int defStyle)
	{
		if (attrs == null) return; //赋值
		
		TypedArray typeArray = null;
		try
		{
			typeArray = context.obtainStyledAttributes(attrs, R.styleable.nfyh,
					defStyle, 0);
			
			int count = typeArray.getIndexCount();
			
			for (int i = 0; i < count; i++)
			{
				int index = typeArray.getIndex(i);
				switch (index)
				{
					case R.styleable.nfyh_title:
						this.setText(typeArray.getString(index));
						break;
					case R.styleable.nfyh_enable_right:
						this.setNextEnable(typeArray.getBoolean(index, false));
						break;
					case R.styleable.nfyh_selected:
						if (typeArray.getBoolean(index, false)) this
								.setSelected();
						break;
					case R.styleable.nfyh_icon:
						this.setIcon(typeArray.getResourceId(index, 0));
						break;
					case R.styleable.nfyh_border_width:
						this.setBorder(typeArray.getDimensionPixelOffset(index,
								1));
						break;
					default:
						break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			typeArray.recycle();
		}
	}
	
	@Override
	public void setOnClickListener(View.OnClickListener l)
	{
		super.setOnClickListener(l);
//		this.listener = l;
	}
	
	public void setIcon(int id)
	{
		this.tvTitle.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
		
	}
	
	public void setText(String text)
	{
		this.tvTitle.setText(text);
	}
	
	public String getTitle()
	{
		return this.tvTitle.getText().toString();
	}
	
	public void setSubText(String text)
	{
		this.tvSubTitle.setText(text);
	}
	
	public void setSelected()
	{
		this.tvTitle.setTextColor(context.getResources()
				.getColor(R.color.green));
	}
	
	public void setBorder(int height)
	{
//		this.viewBorder.setLayoutParams(new LayoutParams(
//				LayoutParams.MATCH_PARENT, height));
		//		this.viewBorder.setBackgroundColor(colorID);
	}
	
	public void setNextEnable(boolean value)
	{
		int visibility = value ? View.VISIBLE : View.GONE;
		this.imgNext.setVisibility(visibility);
	}
	
	public void setCustomerView(View v)
	{
		customerViews.addView(v);
	}
	
	public void removeCustomerViews()
	{
		customerViews.removeAllViews();
	}
	
//	public void setNextIntent(Intent intent)
//	{
////		this.nextIntent = intent;
//	}
	
}
