package com.yixin.nfyh.cloud.data;

import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.view.SignTipsViewModel;

/**
 * 体征比较接口
 * 
 * @author MrChenrui
 * 
 */
public interface ISignCompareable
{
	/**
	 * 比较数据
	 * 
	 * @param m
	 *            比较结果视图模型
	 */
	SignTipsViewModel compare(SignTypes m);

	/**
	 * 装饰方法
	 * 
	 * @param compareable
	 */
	void decorate(ISignCompareable compareable);

	// void setParseCompareSignResultListener(ParseCompareSignResultListener l);
}
