package com.yixin.nfyh.cloud.utils;

import android.content.Context;

/**
 * Log 接口
 * 
 * @author ChenRui
 * 
 */
public interface ILog {

	void info(String tag, Object msg);

	void verbose(String tag, Object msg);

	void error(String tag, Object msg);

	void debug(String tag, Object msg);

	void warn(String tag, Object msg);

	void setExcetion(String tag, Exception e);

	/**
	 * 提交到服务器
	 */
	void commit(Context context);
}
