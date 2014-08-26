package cn.rui.framework.ui;

/**
 * 统一消息通知接口
 * 
 * @author 睿
 * 
 */
public interface IToast
{
	void show(String msg);

	void show(String msg, int code);
}
