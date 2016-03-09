package com.yixin.nfyh.cloud.device;

import android.view.View;

/**
 * 设备连接界面接口
 * 
 * @author ChenRui
 * 
 */
public interface DeviceConnectView {
	/**
	 * 显示提示
	 * 
	 * @param tips
	 * @param msg
	 */
	void show(String tips, String msg);

	/**
	 * 显示错误信息
	 * 
	 * @param tips
	 * @param msg
	 */
	void showError(String tips, String msg);

	/**
	 * 提示成功
	 * 
	 * @param tips
	 * @param msg
	 */
	void showSuccess(String tips, String msg);

	/**
	 * 关闭界面
	 */
	void dismiss();

	/**
	 * 设置内容
	 * 
	 * @param view
	 */
	void setContentViewGroup(View view);

	/**
	 * 设置设备名称
	 * 
	 * @param name
	 */
	void setName(String name);
}
