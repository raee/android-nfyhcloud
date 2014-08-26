package com.yixin.nfyh.cloud.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.model.view.DialogViewModel;

public class FloatSelectorDialog extends NumberSelectorDialog
{

	public FloatSelectorDialog(Context context,
			DialogPopupWindowListener listener, List<DialogViewModel> model)
	{
		super(context, listener, model);
	}

	@Override
	public void onCreate(List<DialogViewModel> model)
	{
		super.onCreate(model);

		DialogViewModel m = new DialogViewModel();
		m.setSubTitle("");
		m.setDatas(getDatas());
		m.setCurrentItem(0);
		model.add(m);
	}

	private List<String> getDatas()
	{
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			result.add(i + "");
		}
		return result;
	}

}
