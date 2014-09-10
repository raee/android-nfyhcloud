package com.yixin.nfyh.cloud.dialog;

import java.util.List;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.yixin.nfyh.cloud.model.view.DialogViewModel;

public abstract class DialogPopupWindow extends PopupWindow
{

	protected DialogPopupWindowListener	listener;
	protected String[]					value;

	public DialogPopupWindow(Context context,
			DialogPopupWindowListener listener, List<DialogViewModel> model)
	{
		super(context);
		this.listener = listener;

		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);

		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.FILL_PARENT);

		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);

		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.DialogAimation);

		this.setOutsideTouchable(true);

		this.setBackgroundDrawable(new ColorDrawable(0x7F3D3D3D)); // 背景
	}

	@Override
	public void dismiss()
	{
		// 通过校验
		if (listener.getdialogValidate(value))
		{
			super.dismiss();
			listener.onDialogCancle(this, value);
		}
	}

}
