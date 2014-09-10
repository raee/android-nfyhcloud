package com.yixin.nfyh.cloud.model.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 体征底部显示的实体
 * 
 * @author 陈睿
 * 
 */
public class DialogViewModel
{
	/**
	 * 数组形
	 */
	public static final int	TYPE_ARRAY	= -1;

	/**
	 * 普通文本
	 */
	public static final int	TYPE_TEXT	= 0;

	/**
	 * 整型
	 */
	public static final int	TYPE_NUMBER	= 1;

	/**
	 * 日期
	 */
	public static final int	TYPE_DATE	= 3;

	/**
	 * 浮点型
	 */
	public static final int	TYPE_FLOAT	= 2;

	private String			title;				// 标题
	private String			subTitle;			// 二级标题
	private List<String>	datas;				// 数据
	private int				currentItem, DataType = TYPE_NUMBER,
			nextCurrentItem;					// 默认显示数据的几条
	private boolean			enableZero;		// 低于10的时候补零

	public void enableZero(boolean val)
	{
		this.enableZero = val;
	}

	private int	dataIndex	= 0;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSubTitle()
	{
		return subTitle;
	}

	public void setSubTitle(String subTitle)
	{
		this.subTitle = subTitle;
	}

	public List<String> getDatas()
	{
		return datas;
	}

	public void setDatas(List<String> datas)
	{
		this.datas = datas;
	}

	public void setDatas(int min, int max)
	{
		this.datas = new ArrayList<String>();
		for (int i = min; i <= max; i++)
		{
			if (enableZero && i < 10)
			{
				this.datas.add("0" + i);
			}
			else
			{
				this.datas.add(String.valueOf(i));
			}
		}
	}

	public void setDatas(String jsonString)
	{
		this.datas = new ArrayList<String>();
		JSONArray json;
		try
		{
			json = new JSONArray(jsonString);
			for (int i = 0; i < json.length(); i++)
			{
				Object item = json.get(i);
				datas.add(item.toString());
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	public DialogViewModel getInstance(int index, SignTypes m, double[] range,
			String arr)
	{
		if (m.getDataType() == -1) // 数组类型
		{
			this.setDatas(arr);
		}
		else
		{
			this.setDatas((int) range[0], (int) range[1]);
		}
		this.setDataIndex(index);
		this.setDataType(m.getDataType());
		this.setTitle(m.getName());
		this.setSubTitle(m.getTypeUnit());
		this.setCurrentItem(m.getDefaultValue());

		return this;
	}

	public int getCurrentItem()
	{
		return currentItem;
	}

	public void setCurrentItem(int currentItem)
	{
		this.currentItem = currentItem;
	}

	public void setCurrentItem(String value)
	{

		if (this.datas.isEmpty()) // 数据为空
		{
			return;
		}
		else if (this.getDataType() == TYPE_FLOAT) // 浮点类型
		{
			// 前面作为第一个
			float val = Float.valueOf(value);

			// 后面作为第二个
			int index = value.indexOf('.');
			int second = Integer.valueOf(value.substring(index + 1));
			this.setNextCurrentItem(second);
			value = (int) val + "";
		}

		if (this.datas.contains(value))
		{
			int index = this.datas.indexOf(value);
			this.setCurrentItem(index);
			return;
		}
		else
		{
			this.setCurrentItem(0);
		}

	}

	public int getDataType()
	{
		return DataType;
	}

	public void setDataType(int DataType)
	{
		this.DataType = DataType;
	}

	public int getNextCurrentItem()
	{
		return nextCurrentItem;
	}

	public void setNextCurrentItem(int nextCurrentItem)
	{
		this.nextCurrentItem = nextCurrentItem;
	}

	public int getDataIndex()
	{
		return dataIndex;
	}

	public void setDataIndex(int dataIndex)
	{
		this.dataIndex = dataIndex;
	}
}
