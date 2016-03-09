package com.yixin.nfyh.cloud.dialog;

import android.widget.PopupWindow;

/**
 * 底部弹出菜单接口
 * 
 * @author admin
 * 
 */
public interface DialogPopupWindowListener {
	/**
	 * 取消按钮
	 * 
	 * @param dialog
	 * @param values
	 */
	void onDialogCancle(PopupWindow dialog, String... values);

	/**
	 * 确定的时候进行数据校验
	 * 
	 * @param values
	 * @return
	 */
	boolean getdialogValidate(String... values);

	/**
	 * 值发生变化
	 * 
	 * @param dialog
	 * @param which
	 * @param values
	 */
	void onDialogChange(PopupWindow dialog, int which, String values);

	/**
	 * 对话框关闭
	 * 
	 * @param dialog
	 * @param which
	 * @param values
	 */
	void onDialogFinsh(PopupWindow dialog, int which, String values);
}