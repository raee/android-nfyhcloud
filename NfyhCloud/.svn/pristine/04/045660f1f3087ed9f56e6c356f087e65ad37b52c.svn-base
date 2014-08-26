package com.yixin.nfyh.cloud.sos;

import android.os.Bundle;

/**
 * 定义流程执行的步骤，并获得返回的结果
 * 
 * @author MrChenrui
 * 
 */
public interface IFlowerResult
{
	public static final int		FLOW_STATUS_SUCCESS	= 1;
	public static final int		FLOW_STATUS_ERROR	= 0;

	/**
	 * 默认的数据
	 */
	public static final String	EXTRA_DATA			= "EXTRA_DATA";

	// 定位
	void onLocation(int status, Bundle data);

	// 选择事件
	void onSelectEvent(int status, Bundle data);

	// 上传
	void onSendToserver(int status, Bundle data);

	// 打电话
	void onCallingPhone(int status, Bundle data);

	// 出错
	void onException(int status, Bundle data);

	// 流程错误
	void onFlowError(int status, Bundle data);

	void onFinsh(int status, Bundle data);
}
