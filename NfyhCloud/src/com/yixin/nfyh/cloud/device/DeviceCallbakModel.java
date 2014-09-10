package com.yixin.nfyh.cloud.device;

import java.io.Serializable;

/**
 * 设备接收数据回调实体模型
 * 
 * @author MrChenrui
 * 
 */
public class DeviceCallbakModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3981448226950291143L;

	// 数据类型
	private int dataType;

	/**
	 * 数据类型名称
	 */
	private String dataName;

	// 数据值
	private Object value;

	/**
	 * 接收数据日期
	 */
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取dataType
	 * 
	 * @return the dataType
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * 设置 dataType
	 * 
	 * @param dataType
	 *            设置 dataType 的值
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * 获取value
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置 value
	 * 
	 * @param value
	 *            设置 value 的值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 获取dataName
	 * 
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * 设置 dataName
	 * 
	 * @param dataName
	 *            设置 dataName 的值
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

}
