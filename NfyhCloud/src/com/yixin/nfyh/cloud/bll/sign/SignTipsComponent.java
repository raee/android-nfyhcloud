/**
 * 
 */
package com.yixin.nfyh.cloud.bll.sign;

import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.model.UserSigns;

/**
 * 体征提示装饰者
 * 
 * @author Chenrui
 * 
 */
public abstract class SignTipsComponent {
	protected Context context;

	public SignTipsComponent(Context context) {
		this.context = context;
	}

	/**
	 * 显示提示
	 * 
	 * @param datas
	 * @return
	 */
	public abstract String showTips(List<UserSigns> datas);
}
