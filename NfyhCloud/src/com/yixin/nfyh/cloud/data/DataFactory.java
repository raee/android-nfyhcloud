/**
 * 
 */
package com.yixin.nfyh.cloud.data;

/**
 * 数据库查询的工厂
 * 
 * @author MrChenrui
 * 
 */
public abstract class DataFactory
{
	/**
	 * 用户接口
	 * 
	 * @return
	 */
	public abstract IUser getUser();

	/**
	 * 字典表接口
	 * 
	 * @return
	 */
	public abstract IDict getDict();

	/**
	 * 体征测量和设备接口
	 * 
	 * @return
	 */
	public abstract ISignDevice getSignDevice();
}
