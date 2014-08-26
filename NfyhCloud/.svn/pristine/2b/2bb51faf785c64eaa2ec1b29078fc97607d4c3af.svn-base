package com.yixin.nfyh.cloud.adapter;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.ISignCompareable;
import com.yixin.nfyh.cloud.data.RangeCompareable;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;

/**
 * 具体的体征类型适配器
 * 
 * @author Chenrui
 * @param <T>
 * 
 */
public class SignTypeItemGridViewAdapter extends SignTypeItemBaseAdapterView
{
	private ISignCompareable	compare;
	
	public SignTypeItemGridViewAdapter(Activity context, List<SignTypes> datas)
			throws SQLException
	{
		super(context, datas);
		compare = new RangeCompareable(this.context);
	}
	
	@Override
	public View getView(int location, View v, ViewGroup vg)
	{
		
		SignTypes m = datas.get(location);
		if (v == null)
		{
			View signView = LayoutInflater.from(this.context).inflate(
					R.layout.ui_sign_detail_item, null);
			TextView tvValue = (TextView) signView
					.findViewById(R.id.tv_ui_sign_detail_subtitle);
			
			TextView tvTitle = (TextView) signView
					.findViewById(R.id.tv_ui_sign_detail_title);
			TextView tvUnit = (TextView) signView
					.findViewById(R.id.tv_ui_sign_detail_unit);
			ImageView imgRight = (ImageView) signView
					.findViewById(R.id.img_ui_sign_detail_right);
			if (m.getIsSign() == 1)
			{
				imgRight.setVisibility(View.GONE);
				SignTipsViewModel model = compare.compare(m);
				String color = model.getLevelColor();
				tvValue.setTextColor(Color.parseColor(color));
			}
			
			tvTitle.setText(m.getName());
			tvUnit.setText(m.getTypeUnit());
			tvValue.setText(m.getDefaultValue());
			v = signView;
			viewList.add(v);
		}
		return v;
	}
	
	@Override
	public void setValue(int postion, String value)
	{
		
		try
		{
			TextView tvValue = (TextView) this.viewList.get(postion)
					.findViewById(R.id.tv_ui_sign_detail_subtitle);
			tvValue.setText(value);
		}
		catch (Exception e)
		{
			Log.e("SignTypeItemBaseAdapterView", "设置体征值失败");
			e.printStackTrace();
		}
	}
	
	public void setColor(int postion, String color)
	{
		
		TextView tvValue = (TextView) this.viewList.get(postion).findViewById(
				R.id.tv_ui_sign_detail_subtitle);
		if (tvValue == null) { return; }
		
		tvValue.setTextColor(Color.parseColor(color));
	}
	
	@Override
	public void loadDefault()
	{
		for (View view : this.viewList)
		{
			view.setTag(null);
			TextView tv = (TextView) view
					.findViewById(R.id.tv_ui_sign_edit_item_value);
			
			if (tv != null) tv.setText("0");
			
		}
	}
}
