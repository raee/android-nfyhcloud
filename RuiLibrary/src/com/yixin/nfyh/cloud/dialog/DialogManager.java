package com.yixin.nfyh.cloud.dialog;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.yixin.nfyh.cloud.model.view.DialogViewModel;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * @author MrChenrui
 * 
 */
public class DialogManager
{

	// private int which = -1;

	private PopupWindow	dialog;

	public DialogManager(Context context, int which,
			DialogPopupWindowListener listener, List<DialogViewModel> model)
	{
		switch (which)
		{
			case DialogViewModel.TYPE_DATE:
			case DialogViewModel.TYPE_FLOAT:
				dialog = new FloatSelectorDialog(context, listener, model);
				break;
			case DialogViewModel.TYPE_ARRAY:
			default:
				dialog = new NumberSelectorDialog(context, listener, model);
				break;
		}
	}

	public void show(View parent)
	{
		if (dialog == null)
		{
			LogUtil.getLog().error(this.getClass().getName(), "体征滑动选择为空");
		}
		dialog.showAtLocation(parent, Gravity.TOP, 0, 0);
	}

}
