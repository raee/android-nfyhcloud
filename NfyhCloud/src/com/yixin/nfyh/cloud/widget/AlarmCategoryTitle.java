package com.yixin.nfyh.cloud.widget;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlarmCategoryTitle extends RelativeLayout {
	private TextView tvOne;
	private TextView tvTwo;
	private TextView tvThree;

	public AlarmCategoryTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_category_remind,
				this);
		tvOne = (TextView) findViewById(R.id.tv_one);
		tvTwo = (TextView) findViewById(R.id.tv_two);
		tvThree = (TextView) findViewById(R.id.tv_three);
	}

	// 设置第一个textView的值
	public void setTvOne(String str) {
		tvOne.setText(str);
	}

	// 设置第二个textView的值
	public void setTvTwo(String str) {
		tvTwo.setText(str);
	}

	/**
	 * 设置textView值
	 * 
	 * @param strOne
	 * @param strTwo
	 */
	public void setTv(String strOne, String strTwo) {
		setTvOne(strOne);
		setTvTwo(strTwo);
	}

	public void setTv(String strOne, String strTwo, String strThree) {
		this.setTv(strOne, strTwo);
		tvThree.setText(strThree);
	}

}
