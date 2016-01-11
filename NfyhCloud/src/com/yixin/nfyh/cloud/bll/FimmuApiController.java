package com.yixin.nfyh.cloud.bll;

import android.content.Context;

import com.yixin.nfyh.cloud.R;

class FimmuApiController implements IApiController
{
	protected String getString(int resId)
	{
		return context.getString(resId);
	}
	
	private final Context	context;
	
	FimmuApiController(Context context)
	{
		this.context = context;
	}
	
	@Override
	public String getApiUrl()
	{
		return getDomain() + getString(R.string.soap_url);
	}
	
	@Override
	public String getCompany()
	{
		return getString(R.string.company);
	}
	
	@Override
	public int getWelcomeBackgroundResId()
	{
		return R.drawable.welcome_background;
	}
	
	@Override
	public String getNameSpace()
	{
		return getString(R.string.soap_namespace);
	}
	
	@Override
	public String getDomain()
	{
		return getString(R.string.nfyh_domain);
	}
	
	@Override
	public String getHealthAssessUrl()
	{
		return getDomain() + getString(R.string.url_health_assess);
	}
	
	@Override
	public String getSmsUrl()
	{
		return getDomain() + getString(R.string.url_myddc);
	}
	
	@Override
	public String getYiJianFanKuiUrl()
	{
		return getDomain() + getString(R.string.url_yjfk);
	}
	
	@Override
	public String getBingQingPingGuUrl()
	{
		return getDomain() + getString(R.string.url_bqpg);
	}
	
	@Override
	public String getPhotoUrl()
	{
		return getDomain() + getString(R.string.url_method_photo_list);
	}
	
	@Override
	public String getPhotoUploadUrl()
	{
		return getDomain() + getString(R.string.url_method_photo_upload);
	}
	
}