package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.model.SignRange;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;

public class RangeCompareable implements ISignCompareable { // 操作符号定义
	public static final int		SYMBOL_GT	= 0;
	public static final int		SYMBOL_LT	= 1;
	public static final int		SYMBOL_EQ	= 2;
	public static final int		SYMBOL_GE	= 3;
	public static final int		SYMBOL_LE	= 4;
	
	private ISignCompareable	compareable;
	private ISignDevice			api;
	
	public RangeCompareable(Context context) {
		api = NfyhCloudDataFactory.getFactory(context).getSignDevice();
	}
	
	@Override
	public SignTipsViewModel compare(SignTypes m) {
		SignTipsViewModel result = new SignTipsViewModel();
		result.setSignTypes(m);
		if (compareable != null) {
			result = compareable.compare(m);// 装饰比较对象
		}
		
		try {
			
			double value = Double.valueOf(m.getDefaultValue());
			
			List<SignRange> ranges = api.getSignRange(m.getTypeId());// 1、找出所有的范围一条条对比
			List<SignTips> signTipsList = new ArrayList<SignTips>(); // 主要的结果
			int countmarks = 0; // 总等级
			for (SignRange range : ranges) {
				double left = range.getLeftRange(); // 左边范围
				double right = range.getRightRange();// 右边范围
				
				// 2、找到一条范围，则找到该范围的提示
				List<SignTips> tips = api.getSignTipsBySignRangeId(range.getRangeid() + "");
				
				for (SignTips tip : tips) {
					// 3、对比该数据和提示的数据值是否匹配
					double taget = tip.getTipsValue();
					int symbol = tip.getTipsSymbol();
					boolean compareResult = compareWithSymbol(symbol, left, right, value, taget, result); // 比较范围的结果是否满足条件，如果是则把结果添加到集合中
					if (compareResult) // 如果匹配则返回匹配结果
					{
						signTipsList.add(tip);// 添加到提示列表中
						countmarks += tip.getTipsLevel();// 等级累加
					}
				}
			}
			
			result.setMarks(countmarks); // 设置等级总数
			result.setSignTipsList(signTipsList);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 二个数字比较结果，返回比较结果。如：a>b,30>5
	 * 
	 * @param symbol
	 *            比较符号
	 * @param a
	 *            要比较的数字
	 * @param b
	 *            要比较的目标
	 * @return
	 */
	private boolean compareWithSymbol(int symbol, double left, double right, double src, double taget, SignTipsViewModel m) {
		double span = 0; // 间隔相差多少？
		boolean result = false;
		
		// 在不在范围内？
		if (src >= left && src <= right) {
			// 在范围内容
			m.setInRange(true);
			return true;
		}
		else if (taget >= 0)// 有没有定义范围？!=-1有定义范围
		{
			switch (symbol) {
				case SYMBOL_GT:// 0大于
					span = (src - right);
					result = span > taget;
					break;
				case SYMBOL_LT:// 小于
					span = (left - src);
					result = span > taget;
					break;
				case SYMBOL_EQ:
					result = src == taget;
					break;
				case SYMBOL_GE:
					span = (src - right);
					result = span >= taget;
					break;
				case SYMBOL_LE:
					span = (left - src);
					result = span <= taget;
					break;
				default:
					break;
			}
			
		}
		span = Double.valueOf(new DecimalFormat(".00").format(span));
		m.setSpan(span);
		return result;
	}
	
	@Override
	public void decorate(ISignCompareable compareable) {
		this.compareable = compareable;
	}
	
}
