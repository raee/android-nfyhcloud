package com.yixin.nfyh.cloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.rui.framework.widget.RuiSwitch;

import com.yixin.nfyh.cloud.R;

public class SubMenu extends LinearLayout {
	public static final String	namespace	= "http://schemas.android.com/apk/res/android";
	private TextView			tvTitle;
	private TextView			tvRightTitle;
	private ImageView			imgIcon, imgRight;
	private RuiSwitch			btnSwitch;

	public SubMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_submenu, this);
		try {
			this.tvTitle = (TextView) this.findViewById(R.id.tv_menu_title);
			this.tvRightTitle = (TextView) this.findViewById(R.id.tv_menu_right_title);

			this.imgIcon = (ImageView) this.findViewById(R.id.img_submenu_icon);
			this.imgRight = (ImageView) this.findViewById(R.id.img_bg_menu_right);
			this.btnSwitch = (RuiSwitch) this.findViewById(R.id.rui_switch);

			int gravity = attrs.getAttributeIntValue(namespace, "layout_gravity", 0);

			int resid = attrs.getAttributeResourceValue(namespace, "tag", 0);

			int iconId = attrs.getAttributeResourceValue(namespace, "background", 0);

			this.setBackgroundResource(android.R.color.transparent);

			if (resid != 0) setTitle(context.getResources().getString(resid));

			if (iconId != 0)
				setIcon(iconId);
			else
				this.imgIcon.setVisibility(View.GONE);

			if (gravity == 0) gravity = Gravity.LEFT;
			setGravity(gravity);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setTitle(String title) {
		this.tvTitle.setText(title);
	}

	public void setRightTitle(String title) {
		this.tvRightTitle.setText(title);
	}

	public void showSwitch() {
		this.btnSwitch.setVisibility(View.VISIBLE);
		this.imgRight.setVisibility(View.INVISIBLE);
	}

	public RuiSwitch getSwitch() {
		return this.btnSwitch;
	}

	public void showRightImage() {
		this.imgRight.setVisibility(View.VISIBLE);
	}

	public String getRightTitle() {
		return this.tvRightTitle.getText().toString();
	}

	public void setIcon(int resId) {
		this.imgIcon.setImageResource(resId);
		this.imgIcon.setVisibility(View.VISIBLE);
	}

	@Override
	public void setGravity(int gravity) {
		switch (gravity) {
			case Gravity.BOTTOM:
				this.getChildAt(0).setBackgroundResource(R.drawable.common_setting_bottom);

				break;
			case Gravity.CENTER:
				this.getChildAt(0).setBackgroundResource(R.drawable.common_setting_middle);

				break;
			case Gravity.START:
				this.getChildAt(0).setBackgroundResource(R.drawable.common_setting_sigle);

				break;
			case Gravity.TOP:
			default:
				this.getChildAt(0).setBackgroundResource(R.drawable.common_setting_top);
				break;
		}
	}

}
