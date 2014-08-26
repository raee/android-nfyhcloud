//package com.yixin.nfyh.cloud.ui;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yixin.nfyh.cloud.R;
//
//public class CustomerHealthType extends LinearLayout
//{
//	RelativeLayout			rlChtPrompt;			// 提示的布局
//	/**
//	 * 显示值布局
//	 */
//	RelativeLayout			layout;
//	ImageView				imgType;
//	//	ImageView				imgChtPrompt;			// 提示的图片
//	TextView				tvName;
//	TextView				tvValue;
//	TextView				tvChtPrompt;			// 提示的text
//	EditText				etValue;
//	private TextView		tvUnit;
//	private boolean			isSave		= false;
//	
//	private int				mOldImageId	= 0;
//	
//	private int				showType	= -2;		// 记录显示状态
//													
//	/**
//	 * 警告
//	 */
//	public static final int	WARING		= 0;
//	/**
//	 * 正常
//	 */
//	public static final int	NORMAL		= 1;
//	/**
//	 * 危险
//	 */
//	public static final int	DANGER		= -1;
//	
//	public CustomerHealthType(Context context, AttributeSet attrs)
//	{
//		super(context, attrs);
//		try
//		{
//			LayoutInflater inflater = LayoutInflater.from(context);
//			inflater.inflate(R.layout.customer_health_type, this);
//			this.setClickable(true);
//			rlChtPrompt = (RelativeLayout) findViewById(R.id.rl_cht_prompt);
//			imgType = (ImageView) findViewById(R.id.img_health_type);
//			tvName = (TextView) findViewById(R.id.tv_health_name);
//			tvValue = (TextView) findViewById(R.id.tv_health_value);
//			tvChtPrompt = (TextView) findViewById(R.id.tv_cht_prompt);
//			etValue = (EditText) findViewById(R.id.et_health_value);
//			tvUnit = (TextView) findViewById(R.id.tv_health_unit);
//			layout = (RelativeLayout) findViewById(R.id.layout_health_type);
//			
//			if (attrs == null) return;
//			// 设置属性
//			TypedArray atr = context.obtainStyledAttributes(attrs,
//					R.styleable.CustomerHealthType);
//			
//			// 显示名称
//			int id = atr.getResourceId(R.styleable.CustomerHealthType_name, 0);
//			setName(context.getString(id));
//			
//			// 显示图像
//			id = atr.getResourceId(R.styleable.CustomerHealthType_background, 0);
//			mOldImageId = id;
//			setSignImage(id);
//			
//			// 回收
//			atr.recycle();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public void setName(String name)
//	{
//		this.tvName.setText(name);
//	}
//	
//	public void setSignImage(int redId)
//	{
//		
//		this.imgType.setBackgroundResource(redId);
//	}
//	
//	/**
//	 * 设置单位
//	 */
//	public void setUnit(String unit)
//	{
//		tvUnit.setText(unit);
//	}
//	
//	/**
//	 * 设置显示的监测值
//	 * 
//	 * @param val
//	 *            值
//	 */
//	public void setValue(String val)
//	{
//		this.tvValue.setText(val);
//		this.etValue.setText(val);
//		this.showText();
//	}
//	
//	/**
//	 * 获取正在显示的值
//	 * 
//	 * @return
//	 */
//	public String getValue()
//	{
//		String result = this.tvValue.getText().toString();
//		if (result.equals("") || result.length() < 1)
//		{
//			result = this.etValue.getText().toString();
//		}
//		return result;
//	}
//	
//	/**
//	 * 得到edit的值
//	 * 
//	 * @return
//	 */
//	public String getEditValue()
//	{
//		return this.etValue.getText().toString();
//	}
//	
//	/**
//	 * 显示图片
//	 */
//	public void showImage()
//	{
//		this.imgType.setVisibility(View.VISIBLE);
//		this.tvValue.setVisibility(View.GONE);
//		this.etValue.setVisibility(View.GONE);
//		// tvUnit.setVisibility(View.GONE);
//	}
//	
//	/**
//	 * 显示文本
//	 */
//	public void showText()
//	{
//		this.tvValue.setVisibility(View.VISIBLE);
//		// this.imgType.setVisibility(View.GONE);
//		this.etValue.setVisibility(View.GONE);
//		tvUnit.setVisibility(View.VISIBLE);
//	}
//	
//	/**
//	 * 显示编辑框
//	 */
//	public void showEdit()
//	{
//		this.etValue.setVisibility(View.VISIBLE);
//		this.tvValue.setVisibility(View.GONE);
//		// this.imgType.setVisibility(View.GONE);
//		// tvUnit.setVisibility(View.GONE);
//	}
//	
//	/**
//	 * 清除显示的值
//	 */
//	public void cleanValue()
//	{
//		this.etValue.setText("");
//		this.tvValue.setText("");
//	}
//	
//	/**
//	 * 数值是否保存过
//	 */
//	public boolean getIsSave()
//	{
//		return isSave;
//	}
//	
//	/**
//	 * 设置圈圈的颜色类型
//	 * 
//	 * @param typeID
//	 *            类型Id，设置参考：0警告；1正常；-1异常；
//	 * 
//	 */
//	public void setHealthType(int typeID)
//	{
//		this.setShowType(typeID);// 保存显示类型
//		
//		switch (typeID)
//		{
//			case WARING:
//				setAlertType(R.drawable.all_img_cirle_yellow,
//						R.drawable.icon_alert, R.color.coral);
//				break;
//			case NORMAL:
//				setAlertType(R.drawable.all_img_cirle_green,
//						R.drawable.icon_true, R.color.green);
//				break;
//			case DANGER:
//				setAlertType(R.drawable.all_img_cirle_red,
//						R.drawable.icon_false, R.color.red);
//				break;
//			
//			default:
//				break;
//		}
//	}
//	
//	public void setHealthType(int typeID, String Condition)
//	{
//		
//		setPrompt(typeID, Condition);// 设置提示
//		setHealthType(typeID);
//	}
//	
//	/**
//	 * 测量后的提示
//	 * 
//	 * @param type
//	 */
//	private void setPrompt(int type, String Condition)
//	{
//		rlChtPrompt.setVisibility(View.GONE);
//		if (type != NORMAL)
//		{
//			layout.setBackgroundResource(R.drawable.common_setting_top);
//			rlChtPrompt.setVisibility(View.VISIBLE);
//			tvChtPrompt.setText(Condition);
//			if (type == DANGER)
//			{
//				setImgChtPrompt(R.drawable.all_img_cirle_red,
//						R.drawable.all_img_prompt);
//			}
//			else
//			{
//				setImgChtPrompt(R.drawable.all_img_cirle_yellow,
//						R.drawable.all_img_prompt);
//			}
//		}
//	}
//	
//	/**
//	 * 设置提示的图片
//	 */
//	private void setImgChtPrompt(int resbgImgId, int resImgId)
//	{
//		//		this.imgChtPrompt.setImageResource(resImgId);
//	}
//	
//	/**
//	 * 设置警告类型
//	 * 
//	 * @param resbgImgId
//	 *            图片背景
//	 * @param resImgId
//	 *            图片源
//	 * @param resColorId
//	 */
//	private void setAlertType(int resbgImgId, int resImgId, int resColorId)
//	{
//		this.imgType.setBackgroundResource(resbgImgId);
//		this.imgType.setImageResource(resImgId);
//		this.tvValue.setTextColor(getResources().getColor(resColorId));
//	}
//	
//	/**
//	 * 还原默认布局
//	 */
//	public void resetLayout()
//	{
//		// 还原图标
//		this.imgType.setBackgroundResource(this.mOldImageId);
//		
//		// 还原值
//		this.tvValue.setText("");
//		
//	}
//	
//	/**
//	 * 设置值
//	 */
//	public void setIsSave(boolean isSave)
//	{
//		this.isSave = isSave;
//	}
//	
//	/**
//	 * 显示提示信息
//	 */
//	public void showLog(String logMsg)
//	{
//		
//	}
//	
//	public int getShowType()
//	{
//		return showType;
//	}
//	
//	public void setShowType(int showType)
//	{
//		this.showType = showType;
//	}
//	
//}
