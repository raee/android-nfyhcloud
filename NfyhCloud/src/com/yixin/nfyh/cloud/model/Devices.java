package com.yixin.nfyh.cloud.model;

import android.text.Html;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Devices 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "devices")
public class Devices {
	
	// DevId
	@DatabaseField(id = true, canBeNull = false, columnName = "devId")
	private String	DevId;
	
	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;
	
	// IsUsed
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "isUsed")
	private int		IsUsed;
	
	// Comment
	@DatabaseField(columnName = "comment")
	private String	Comment;
	
	// Logo
	@DatabaseField(columnName = "logo")
	private String	Logo;
	
	/*
	 * 构造函数
	 */
	public Devices() {
	}
	
	/*
	 * 获取 DevId
	 */
	public String getDevId() {
		return DevId;
	}
	
	/*
	 * 设置 DevId
	 */
	public void setDevId(String value) {
		this.DevId = value;
	}
	
	/*
	 * 获取 Name
	 */
	public String getName() {
		return Name;
	}
	
	/*
	 * 设置 Name
	 */
	public void setName(String value) {
		this.Name = value;
	}
	
	/*
	 * 获取 IsUsed
	 */
	public int getIsUsed() {
		return IsUsed;
	}
	
	/*
	 * 设置 IsUsed
	 */
	public void setIsUsed(int value) {
		this.IsUsed = value;
	}
	
	/*
	 * 获取 Comment
	 */
	public String getComment() {
		return Html.fromHtml(Comment).toString();
	}
	
	/*
	 * 设置 Comment
	 */
	public void setComment(String value) {
		this.Comment = value;
	}
	
	/*
	 * 获取 Logo
	 */
	public String getLogo() {
		return Logo;
	}
	
	/*
	 * 设置 Logo
	 */
	public void setLogo(String value) {
		this.Logo = value;
	}
	
}
