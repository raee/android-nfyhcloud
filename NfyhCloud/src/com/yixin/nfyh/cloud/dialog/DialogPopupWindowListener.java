package com.yixin.nfyh.cloud.dialog;

import android.widget.PopupWindow;

/**
 * 底部弹出菜单接口
 * 
 * @author admin
 * 
 */
public interface DialogPopupWindowListener
{
	void onDialogCancle(PopupWindow dialog, String... values);

	boolean getdialogValidate(String... values);

	void onDialogChange(PopupWindow dialog, int which, String values);

	void onDialogFinsh(PopupWindow dialog, int which, String values);
}