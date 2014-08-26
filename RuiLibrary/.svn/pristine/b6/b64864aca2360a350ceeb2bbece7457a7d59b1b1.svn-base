package cn.rui.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 * 输入辅助类
 * 
 * @author MrChenrui
 * 
 */
public class InputUtils
{
	private Button			btn;
	private EditText[]		editTexts;
	private IInputValidate	lValidate;

	/**
	 * 手机和电话号码匹配
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isPhoneNumber(String number)
	{
		Pattern pat = Pattern
				.compile("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");

		Matcher match = pat.matcher(number);
		return match.matches();
	}

	/**
	 * 中文匹配
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isChinese(String val)
	{
		Pattern reg = Pattern.compile("[\u4e00-\u9fa5]+");
		return reg.matcher(val).matches();
	}

	/**
	 * 设置输入框校验监听者
	 * 
	 * @param l
	 */
	public void setInputValidateLinsener(IInputValidate l)
	{
		this.lValidate = l;
	}

	/**
	 * 设置监听文本框内容，只要校验通过就设置按钮可以使用
	 * 
	 * @param btn
	 *            触发的按钮
	 * @param editTexts
	 *            需要监听的输入框
	 */
	public void setButtonEnableOnEditTextChange(Button btn,
			EditText... editTexts)
	{
		this.editTexts = editTexts;
		this.btn = btn;
		this.btn.setEnabled(false);

		for (EditText editText : editTexts)
		{
			editText.addTextChangedListener(new EditTextChangeWatcher());
		}
	}

	/**
	 * 输入框文本监听类
	 * 
	 * @author MrChenrui
	 * 
	 */
	private class EditTextChangeWatcher implements TextWatcher
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{

		}

		@Override
		public void afterTextChanged(Editable s)
		{
			btn.setEnabled(false);
			if (lValidate != null)
				lValidate.OnFaild(null);
			boolean isValidate = true;
			for (EditText et : editTexts)
			{
				String str = et.getText().toString().trim();
				if (str.length() > 0)
				{
					// 通过继续找
					continue;
				}
				else
				{
					// 不通过
					isValidate = false;
					if (lValidate != null)
						lValidate.OnFaild(et);
					break;
				}
			}
			if (isValidate && lValidate != null)
			{
				btn.setEnabled(true);
				// 调用接口
				lValidate.OnSuccees();
			}
		}

	}

}
