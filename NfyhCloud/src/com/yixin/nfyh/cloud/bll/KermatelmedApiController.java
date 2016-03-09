package com.yixin.nfyh.cloud.bll;

import android.content.Context;

import com.yixin.nfyh.cloud.R;

/**
 * 柯尔迈平台 实现
 * 
 * @author ChenRui
 * 
 */
class KermatelmedApiController extends FimmuApiController {
	private final Context context;

	KermatelmedApiController(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public String getDomain() {
		return context.getString(R.string.kermatelmed_domain);
	}

	@Override
	public String getCompany() {
		return context.getString(R.string.company_kermatelmed);
	}

	@Override
	public int getWelcomeBackgroundResId() {
		return R.drawable.welcome_background_kermatelmed;
	}

}