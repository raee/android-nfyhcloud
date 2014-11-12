package com.yixin.nfyh.cloud.model.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 体征提示视图模型
 * 
 * @author MrChenrui
 * 
 */
public class SignTipsViewModel {
	private String[] colors = null; // 等级显示的颜色
	private String[] leveNames; // 等级集合
	private boolean isInRange = false; // 是否在体征范围内
	private String levelName = "正常"; // 总的平均等级名称
	private int marks = 0; // 总平均等级，根据所有体征范围提示计算平均值
	private List<SignTips> signTipsList = new ArrayList<SignTips>();
	private SignTypes signTypes = null;

	private double span = 0; // 上升或者下降的点数
	private int mLevel;

	public SignTipsViewModel(Context context) {
		colors = context.getResources().getStringArray(R.array.signLevelColor);
		leveNames = new String[] { "三级低", "二级低", "一级低", "理想", "正常", "临界", "偏高",
				"一级高", "二级高", "三级高" };
	}

	/**
	 * 获取平均等级
	 * 
	 * @return
	 */
	public int getLevel() {

		int size = this.signTipsList.size();
		// 被除数不能为0
		if (size <= 0) {
			return 0;
		}
		mLevel = this.marks / size;
		return mLevel;
	}

	public void setLevel(int level) {
		this.mLevel = level;
	}

	/**
	 * 获取等级对应的颜色显示
	 * 
	 * @return RGB颜色值
	 */
	public int getLevelColor() {
		int level = getLevel();
		String color;
		if (colors.length > level) {
			color = colors[level];
		} else {
			color = colors[6];
		}

		return Color.parseColor(color);
	}

	/**
	 * 获取等级名称
	 * 
	 * @return
	 */
	public String getLevelName() {

		int avgLevel = getLevel() - 1;// 获取平均等级
		if (avgLevel < 0)
			avgLevel = 0;

		if (leveNames.length > avgLevel) {
			levelName = leveNames[avgLevel];// 根据等级返回等级名称
		}

		return levelName;
	}

	public int getMarks() {
		return marks;
	}

	public List<SignTips> getSignTipsList() {
		return signTipsList;
	}

	public SignTypes getSignTypes() {
		return signTypes;
	}

	public double getSpan() {
		return span;
	}

	public boolean isInRange() {
		return isInRange;
	}

	public void setInRange(boolean isInRange) {
		this.isInRange = isInRange;
	}

	public void setMarks(int marks) {
		this.marks += marks;
	}

	public void setSignTipsList(List<SignTips> signTipsList) {
		this.signTipsList.addAll(signTipsList);
	}

	public void setSignTipsList(SignTips signTips) {
		this.signTipsList.add(signTips);
	}

	public void setSignTypes(SignTypes signTypes) {
		this.signTypes = signTypes;
	}

	public void setSpan(double span) {
		this.span = span;
	}

	/**
	 * 获取星星数量
	 * 
	 * @param level
	 * @return
	 */
	public int getStarNumber() {
		int level = getLevel();
		if (level >= 8 || level <= 2)
			return 1;
		else if (level == 7 || level == 3)
			return 2;
		else if (level == 6 || level == 4)
			return 3;
		else if (level == 5)
			return 5;
		else
			return 3;
	}
}
