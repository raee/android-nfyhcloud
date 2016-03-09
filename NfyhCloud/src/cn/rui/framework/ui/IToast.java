package cn.rui.framework.ui;

/**
 * 统一消息通知接口
 * 
 * @author 睿
 * 
 */
public interface IToast {
	/**
	 * 显示Toast
	 * 
	 * @param msg
	 */
	void show(String msg);

	/**
	 * 显示Toast
	 * 
	 * @param msg
	 * @param code
	 *            资源图片ID
	 */
	void show(String msg, int code);
}
