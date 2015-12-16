package com.rae.im.sdk.rongcloud;

/**
 * µÇÂ¼»Øµ÷
 * 
 * @author ChenRui
 * 
 */
public interface IMLoginListener extends IMConnectionListener {
	void onIMLoginSuccess(String userName, String password, String extra);

	void onIMLoginError(String userName, String password, int errorCode,
			String msg);
}
