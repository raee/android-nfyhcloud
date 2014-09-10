package com.yixin.nfyh.cloud.widget;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 体征测量项视图
 * 
 * @author MrChenrui
 * 
 */
public class SignsItemView extends LinearLayout
{
	private static final String	NAMESPACE		= "http://schemas.android.com/apk/res/android";
	/**
	 * 默认状态
	 */
	public static final int		TYPE_DEFAULT	= R.drawable.all_img_cirle_default;
	/**
	 * 高危险
	 */
	public static final int		TYPE_WAING		= R.drawable.all_img_cirle_red;
	/**
	 * 警告
	 */
	public static final int		TYPE_ALERT		= R.drawable.all_img_cirle_yellow;
	
	/**
	 * 正常
	 */
	public static final int		TYPE_NORMAL		= R.drawable.all_img_cirle_green;
	
	private TextView			tvName;
	private ImageView			imgIcon;
	private TextView			tvData;
	private TextView			tvUntil;
	private int					iconId;
	
	/**
	 * 体征值是否改变
	 */
	private boolean				isDataChange	= false;
	
	public SignsItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_item_recdata, this);
		
		this.tvName = (TextView) findViewById(R.id.tv_recdata_name);
		this.tvData = (TextView) findViewById(R.id.tv_recdata_data);
		this.tvUntil = (TextView) findViewById(R.id.tv_recdata_until);
		this.imgIcon = (ImageView) findViewById(R.id.img_recdata_icon);
		
		if(attrs == null) return;
		int nameId = attrs.getAttributeResourceValue(NAMESPACE, "tag", 0);
		int untilId = attrs.getAttributeResourceValue(NAMESPACE,
				"contentDescription", 0);
		iconId = attrs.getAttributeResourceValue(NAMESPACE, "background", 0);
		
		if (nameId != 0 && this.tvName != null) setName(context
				.getString(nameId));
		if (untilId != 0 && this.tvUntil != null) setUntil(context
				.getString(untilId));
		
		if (iconId != 0 && this.imgIcon != null) setIcon(iconId);
		
	}
	
	public void setName(String name)
	{
		this.tvName.setText(name);
	}
	
	public void setUntil(String value)
	{
		this.tvUntil.setText(value);
	}
	
	public void setIcon(int iconId)
	{
		this.imgIcon.setImageResource(iconId);
	}
	
	/**
	 * 设置数据
	 * 
	 * @param data
	 *            数据
	 * @param type
	 *            数据级别，请参考SignsItemView的静态变量
	 */
	public void setData(String data, int type)
	{
		this.imgIcon.setBackgroundResource(type);
		this.imgIcon.setImageResource(android.R.color.transparent); // 图标隐藏
		this.tvData.setText(data);
		this.tvData.setVisibility(View.VISIBLE);
		this.tvUntil.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 获取值
	 */
	public String getData()
	{
		return tvData.getText() == null ? "" : tvData.getText().toString();
	}
	
	/**
	 * 获取单位
	 * 
	 * @return
	 */
	public String getUnit()
	{
		return tvUntil.getText() == null ? "" : tvUntil.getText().toString();
	}
	
	/**
	 * 值是否改变了
	 * 
	 * @return
	 */
	public boolean isValueChange()
	{
		return isDataChange;
	}
	
	public void setValueChange(boolean bchange)
	{
		isDataChange = bchange;
	}
	
	/**
	 * 获取显示不同类型的告警显示的图片
	 * 
	 * @return
	 */
	public ImageView getTypeImage()
	{
		return this.imgIcon;
	}
	
	/**
	 * 恢复一开始的状态
	 */
	public void loadDefault()
	{
		this.imgIcon.setBackgroundResource(TYPE_DEFAULT);
		this.imgIcon.setImageResource(iconId); // 图标隐藏
		this.tvData.setVisibility(View.GONE);
		this.tvUntil.setVisibility(View.GONE);
	}
	
}
