package com.yixin.nfyh.cloud.w;

import android.content.Context;

import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 通用WebServices 链接
 * 
 * @author ChenRui
 * 
 */
public abstract class WebserverConnection {

	protected Context context;

	protected ILog log = LogUtil.getLog();

	protected String tag = "";

	protected String mCookie, mUserId;

	public WebserverConnection(Context context) {
		this.context = context;
		tag = getClass().getName();
	}

	public void setCookie(String cookie) {
		this.mCookie = cookie;
	}

	public void setUserId(String uid) {
		this.mUserId = uid;
	}

	public String getCookie() {
		return mCookie;
	}

	public String getUserId() {
		return this.mUserId;
	}
}
