package com.yixin.nfyh.cloud.w;

/**
 * 解析webserver 返回的数据
 * 
 * @author MrChenrui
 * 
 */
public interface IWebserverParser<T> {
	/**
	 * 解析
	 * 
	 * @param json
	 * @return
	 */
	T parser(String json);
}
