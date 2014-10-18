package com.rae.alarm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yixin.nfyh.cloud.R;

public class ListItemView extends LinearLayout {
	
	private TextView	mTitle;
	private ImageView	mImgAllowRight;
	private TextView	mSubTitle;
	
	public ListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewItem);
		LayoutInflater.from(context).inflate(R.layout.view_item, this);
		this.mTitle = (TextView) findViewById(R.id.tv_view_item_title);
		this.mSubTitle = (TextView) findViewById(R.id.tv_view_item_subtitle);
		this.mImgAllowRight = (ImageView) findViewById(R.id.img_view_item_allow_right);
		
		for (int index = 0; index < array.getIndexCount(); index++) {
			switch (array.getIndex(index)) {
				case R.styleable.ViewItem_title:
					setTitle(array.getText(index));
					break;
				case R.styleable.ViewItem_subtitle:
					setSubTitle(array.getText(index));
					break;
				case R.styleable.ViewItem_allow_right:
					allowRight(array.getBoolean(index, false));
					break;
				default:
					break;
			}
		}
		
		array.recycle();
	}
	
	public void setTitle(CharSequence title) {
		if (mTitle != null) {
			mTitle.setText(title);
		}
	}
	
	public void setSubTitle(CharSequence title) {
		if (mSubTitle != null) {
			mSubTitle.setText(title);
		}
	}
	
	/**
	 * 允许右侧
	 * 
	 * @param val
	 */
	public void allowRight(boolean val) {
		if (mImgAllowRight != null) {
			mImgAllowRight.setVisibility(val ? View.VISIBLE : View.GONE);
		}
	}
	
	public CharSequence getSubTitle(){
		return mSubTitle.getText();
	}
	
}
