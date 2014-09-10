package com.yixin.nfyh.cloud.dialog;

/**
 * 滑动选择框发生改变的时候调用接口
 * 
 * @author admin
 * 
 */
public interface WheelViewValueChangeListener
{
	void onValueChange(int which, String oldValues, String newValues);

	void onValueFinsh(int which, String oldValues, String newValues);

	void onCancleFinsh();
}
