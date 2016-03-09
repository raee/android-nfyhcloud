package com.yixin.nfyh.cloud.dialog;

/**
 * 滑动选择框发生改变的时候调用接口
 * 
 * @author admin
 * 
 */
public interface WheelViewValueChangeListener {
	/**
	 * 当前滑动的值发生改变
	 * 
	 * @param which
	 * @param oldValues
	 * @param newValues
	 */
	void onValueChange(int which, String oldValues, String newValues);

	/**
	 * 选择完成
	 * 
	 * @param which
	 * @param oldValues
	 * @param newValues
	 */
	void onValueFinsh(int which, String oldValues, String newValues);

	/**
	 * 取消选择
	 */
	void onCancleFinsh();
}
