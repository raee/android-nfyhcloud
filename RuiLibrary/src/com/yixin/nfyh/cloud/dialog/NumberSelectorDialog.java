package com.yixin.nfyh.cloud.dialog;

import java.util.List;

import android.content.Context;
import cn.rui.framework.ui.ScrollInputView;

import com.yixin.nfyh.cloud.model.view.DialogViewModel;

/**
 * @author MrChenrui
 * 
 */
public class NumberSelectorDialog extends DialogPopupWindow implements
		WheelViewValueChangeListener
{

	private ScrollInputView	contentView;

	/**
	 * @param context
	 * @param listener
	 * @param model
	 */
	public NumberSelectorDialog(Context context,
			DialogPopupWindowListener listener, List<DialogViewModel> model)
	{
		super(context, listener, model);

		contentView = new ScrollInputView(context);

		contentView.setValueChangListener(this);

		onCreate(model);

		contentView.setup(model);
		setContentView(contentView);
	}

	protected void onCreate(List<DialogViewModel> model)
	{

	}

	@Override
	public void onValueChange(int which, String oldValues, String newValues)
	{
		super.listener.onDialogChange(this, which, newValues);
	}

	@Override
	public void onValueFinsh(int which, String oldValues, String newValues)
	{
		super.listener.onDialogFinsh(this, which, newValues);
	}

	@Override
	public void onCancleFinsh()
	{
		this.dismiss();
	}

}
