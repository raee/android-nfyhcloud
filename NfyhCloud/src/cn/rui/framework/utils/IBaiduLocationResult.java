package cn.rui.framework.utils;

import com.baidu.location.BDLocation;

/**
 * 定位成功后返回的接口
 */
public interface IBaiduLocationResult
{

	/**
	 * 当定位成功时调用的的方法
	 * 
	 * @param location
	 *            成功后的位置
	 */
	void onReceiveLocationSuccess(BDLocation location);

	/**
	 * 当定位失败时候调用
	 * 
	 * @param code错误码
	 *            ，参考百度定位错误码
	 * @param msg
	 *            错误信息
	 */
	void onReceiveLocationFaild(int code, String msg);
}
