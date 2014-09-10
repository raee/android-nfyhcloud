package com.yixin.nfyh.cloud.model.view;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 体征提示视图模型
 * 
 * @author MrChenrui
 * 
 */
public class SignTipsViewModel
{
	private static SparseArray<String>	Colors			= null;						// 等级显示的颜色
	private static String[]				LevelArray;									// 等级集合
	static
	{
		Colors = new SparseArray<String>();

		/*
		 * b200d8 - 10 紫色 d8005c - 9 红色 f68b00 - 8 黄 ff8c53 - 7 橙色 8ad100 - 6 绿黄
		 * 17a200 - 5 绿色 13b845 - 4 浅绿 0db4eb - 3 浅蓝色 01e9db - 2 aeaeae - 1
		 */
		Colors.put(0, "#cfd0d0"); // 偏低
		Colors.put(1, "#aeaeae"); // 偏低
		Colors.put(2, "#aeaeae"); // 偏低
		Colors.put(3, "#01e9db"); // 偏低
		Colors.put(4, "#0db4eb"); // 偏低
		Colors.put(5, "#13b845"); // 偏低
		Colors.put(6, "#17a200"); // 偏低
		Colors.put(7, "#8ad100"); // 偏低
		Colors.put(8, "#f68b00"); // 偏低
		Colors.put(9, "#d8005c"); // 偏低
		Colors.put(10, "#b200d8"); // 偏低

		/*
		 * LevelArray 等级集合
		 */

		LevelArray = new String[] { "三级低", "二级低", "一级低", "理想", "正常", "临界",
			"偏高", "一级高", "二级高", "三级高" };
	}
	private boolean						isInRange		= false;						// 是否在体征范围内
	private String						levelName		= "正常";						// 总的平均等级名称
	private int							marks			= 0;							// 总平均等级，根据所有体征范围提示计算平均值
	private List<SignTips>				signTipsList	= new ArrayList<SignTips>();
	private SignTypes					signTypes		= null;

	private double						span			= 0;							// 上升或者下降的点数

	public SignTipsViewModel()
	{

	}

	/**
	 * 获取平均等级
	 * 
	 * @return
	 */
	public int getLevel()
	{
		int size = this.signTipsList.size();
		// 被除数不能为0
		if (size <= 0)
		{
			return 0;
		}
		int result = this.marks / size;
		return result;
	}

	/**
	 * 获取等级对应的颜色显示
	 * 
	 * @return RGB颜色值
	 */
	public String getLevelColor()
	{
		int level = getLevel();
		if (Colors.size() > level)
		{
			return Colors.get(level);
		}
		else
			return Colors.get(5);
	}

	public String getLevelName()
	{

		int avgLevel = getLevel() - 1;// 获取平均等级
		if (avgLevel < 0)
			avgLevel = 0;

		if (LevelArray.length > avgLevel)
		{
			levelName = LevelArray[avgLevel];// 根据等级返回等级名称
		}

		return levelName;
	}

	public int getMarks()
	{
		return marks;
	}

	public List<SignTips> getSignTipsList()
	{
		return signTipsList;
	}

	public SignTypes getSignTypes()
	{
		return signTypes;
	}

	public double getSpan()
	{
		return span;
	}

	public boolean isInRange()
	{
		return isInRange;
	}

	public void setInRange(boolean isInRange)
	{
		this.isInRange = isInRange;
	}

	public void setMarks(int marks)
	{
		this.marks += marks;
	}

	public void setSignTipsList(List<SignTips> signTipsList)
	{
		this.signTipsList.addAll(signTipsList);
	}

	public void setSignTipsList(SignTips signTips)
	{
		this.signTipsList.add(signTips);
	}

	public void setSignTypes(SignTypes signTypes)
	{
		this.signTypes = signTypes;
	}

	public void setSpan(double span)
	{
		this.span = span;
	}

}
