package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.model.SignRange;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;

/**
 * 默认的比较，正常范围的比较
 * 
 * @author MrChenrui
 * 
 */
public class DefaultCompareable implements ISignCompareable
{

	private ISignDevice			api;
	private ISignCompareable	compareable;

	public DefaultCompareable(Context context) throws SQLException
	{
		api = NfyhCloudDataFactory.getFactory(context).getSignDevice();
	}

	@Override
	public SignTipsViewModel compare(SignTypes m)
	{

		SignTipsViewModel result = new SignTipsViewModel();

		// 装饰比较对象
		if (compareable != null)
		{
			result = compareable.compare(m);
		}

		try
		{
			// 1、查找【80】所在的范围是属于哪一种类型
			String value = m.getDefaultValue(); // 体征值
			String typeid = m.getTypeId();// 体征类型
			int countMrak = 0;
			List<SignRange> results = api.getUserSignRange(typeid, value); // 获取范围

			List<SignTips> signTipsList = new ArrayList<SignTips>(); // 【主要】范围提示

			// 2、找到范围的提示
			for (SignRange range : results)
			{
				List<SignTips> tips = api.getSignTipsBySignRangeId(range
						.getRangeid() + ""); // 获取到正常范围的提示
				if (tips != null && tips.size() > 0)
				{
					for (SignTips signTips : tips)
					{
						countMrak += signTips.getTipsLevel(); // 等级累加
					}
					signTipsList.addAll(tips);
				}
			}

			result.setMarks(countMrak);
			result.setSignTypes(m); // 设置当前体征
			result.setSignTipsList(signTipsList); // 添加到结果集中
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void decorate(ISignCompareable compareable)
	{
		this.compareable = compareable;
	}

}
