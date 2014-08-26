package com.yixin.nfyh.cloud.bll.sign;

import java.sql.SQLException;

import com.yixin.nfyh.cloud.model.SignTypes;

/**
 * 核心的体征接口
 * 
 * @author admin
 * 
 */
public interface SignCoreInterface
{
	/**
	 * 设置回调监听者
	 * 
	 * @param listener
	 */
	void setSignCoreListener(SignCoreListener listener);

	/**
	 * 保存记录
	 * 
	 * @param m
	 * @throws SQLException
	 */
	void saveUserSign(SignTypes m) throws SQLException;

	/**
	 * 上传到服务器中
	 * 
	 */
	void upload();
}
