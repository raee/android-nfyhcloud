/**
 * 
 */
package com.yixin.nfyh.cloud.bll.sign;

import android.content.Context;

/**
 * 体征提示装饰者
 * 
 * @author Chenrui
 * 
 */
public abstract class SignTipsDecorator extends SignTipsComponent {
	/**
	 * @param context
	 */
	public SignTipsDecorator(Context context, SignTipsComponent signTips) {
		super(context);
		this.signTips = signTips;
	}

	protected SignTipsComponent signTips;

}
