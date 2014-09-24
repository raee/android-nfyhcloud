package com.yixin.nfyh.cloud.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;

public class ActionBar extends LinearLayout implements
		android.view.View.OnClickListener
{

	public static final String	namespace	= "http://schemas.android.com/apk/res/android";
	private TextView			tvTitle;
	private Button				btnBack;
	private Context				context;
	private Button				btnRight;

	public ActionBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.view_actionbar, this);

		try
		{
			this.tvTitle = (TextView) findViewById(R.id.tv_actionbar_title);
			this.btnBack = (Button) findViewById(R.id.btn_aa_back);
			this.btnBack.setOnClickListener(this);

			this.btnRight = (Button) findViewById(R.id.btn_right_one);

			if (attrs == null)
				return;

			int resid = attrs.getAttributeResourceValue(namespace, "tag", 0);
			if (resid != 0)
				this.tvTitle.setText(context.getResources().getString(resid));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Button getRightButton1()
	{
		return this.btnRight;
	}

	/**
	 * 设置返回按钮的显示状态
	 * 
	 * @param visibility
	 *            参考View.GONE
	 */
	public void setGoBackVisibility(int visibility)
	{
		this.btnBack.setVisibility(visibility);
	}

	public ActionBar(Context context)
	{
		this(context, null);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btn_aa_back:
				try
				{
					Activity at = (Activity) this.context;
					at.finish();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;

			default:
				break;
		}
	}
}
