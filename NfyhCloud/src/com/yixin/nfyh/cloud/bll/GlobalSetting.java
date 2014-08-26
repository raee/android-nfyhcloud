package com.yixin.nfyh.cloud.bll;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yixin.nfyh.cloud.model.Users;

/**
 * 全局配置文件
 * 
 * @author MrChenrui
 * 
 */
public class GlobalSetting
{

	private static final String	GLOBAL_NAME	= "GLOBAL_NAME";

	private SharedPreferences	share;

	private Editor				editor;

	private Users				user;

	public GlobalSetting(Context context)
	{
		this.share = context.getSharedPreferences(GLOBAL_NAME, Context.MODE_PRIVATE);
		this.editor = this.share.edit();
	}

	public void setApplicationInstalled()
	{
		editor.putBoolean("ApplicationInstalled", true);
		commit();
	}

	public boolean isApplicationInstalled()
	{
		return share.getBoolean("ApplicationInstalled", false);
	}

	public void commit()
	{
		this.editor.commit();
	}

	public void setUser(Users user)
	{
		this.user = user;
		setValue("uid", user.getUid());
		setValue("username", user.getUsername());
		setValue("pwd", user.getPwd());
		setValue("cookie", user.getCookie());
		commit();
	}

	public Users getUser()
	{
		String userName = share.getString("username", "");
		String uid = share.getString("uid", "");
		String pwd = share.getString("pwd", "");
		String cookie = share.getString("cookie", "");
		user = new Users();
		user.setUsername(userName);
		user.setPwd(pwd);
		user.setUid(uid);
		user.setCookie(cookie);
		return user;
	}

	public void setValue(String key, String value)
	{
		if (share.contains(key)) editor.remove(key);
		editor.putString(key, value);
	}

	public String getValue(String key, String defaultValue)
	{
		return share.getString(key, defaultValue);
	}
}
