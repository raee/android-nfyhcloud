package cn.rui.framework.utils;

import android.widget.EditText;

/**
 * 输入框校验接口
 * 
 * @author MrChenrui
 * 
 */
public interface IInputValidate
{
	/**
	 * 校验通过
	 */
	void OnSuccees();

	/**
	 * 校验失败
	 * 
	 * @param et
	 *            失败的文本框
	 */
	void OnFaild(EditText et);
}
