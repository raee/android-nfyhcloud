package com.yixin.nfyh.cloud.bll;

/**
 * API 控制器，用于切换不同平台的接口
 * 
 * @author ChenRui
 * 
 */
public interface IApiController
{
	/**
	 * 网站域名
	 * 
	 * @return
	 */
	String getDomain();
	
	/**
	 * API 地址
	 * 
	 * @return
	 */
	String getApiUrl();
	
	/**
	 * 公司名称
	 * 
	 * @return
	 */
	String getCompany();
	
	/**
	 * 欢迎界面资源Id
	 * 
	 * @return
	 */
	int getWelcomeBackgroundResId();
	
	/**
	 * API 命名空间
	 * 
	 * @return
	 */
	String getNameSpace();
	
	/**
	 * 健康评估
	 * 
	 * @return
	 */
	String getHealthAssessUrl();
	
	/**
	 * 满意度调查
	 * 
	 * @return
	 */
	String getSmsUrl();
	
	/**
	 * 意见反馈
	 * 
	 * @return
	 */
	String getYiJianFanKuiUrl();
	
	/**
	 * 病情评估
	 * 
	 * @return
	 */
	String getBingQingPingGuUrl();
	
	/**
	 * 图片路径
	 * 
	 * @return
	 */
	String getPhotoUrl();
	
	/**
	 * 图片上传地址
	 * 
	 * @return
	 */
	String getPhotoUploadUrl();
}
