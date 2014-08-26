package com.yixin.nfyh.cloud.model;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Photocategory
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "photocategory")
public class Photocategory
{

	// Pid
	@DatabaseField(id = true, canBeNull = false, columnName = "pid")
	private String	Pid;

	// Parentid
	@DatabaseField(columnName = "parentid")
	private String	Parentid;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;

	// UpdateTime
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "update_time")
	private Integer	UpdateTime;

	// CreateDate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "create_date")
	private Date	CreateDate;

	// UpdateDate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "update_date")
	private Date	UpdateDate;

	// 父表 Uid
	@DatabaseField(foreign = true, foreignColumnName = "uid")
	private Users	_Users;

	/*
	 * 构造函数
	 */
	public Photocategory()
	{
	}

	/*
	 * 获取 Pid
	 */
	public String getPid()
	{
		return Pid;
	}

	/*
	 * 设置 Pid
	 */
	public void setPid(String value)
	{
		this.Pid = value;
	}

	/*
	 * 获取 Parentid
	 */
	public String getParentid()
	{
		return Parentid;
	}

	/*
	 * 设置 Parentid
	 */
	public void setParentid(String value)
	{
		this.Parentid = value;
	}

	/*
	 * 获取 Name
	 */
	public String getName()
	{
		return Name;
	}

	/*
	 * 设置 Name
	 */
	public void setName(String value)
	{
		this.Name = value;
	}

	/*
	 * 获取 UpdateTime
	 */
	public Integer getUpdateTime()
	{
		return UpdateTime;
	}

	/*
	 * 设置 UpdateTime
	 */
	public void setUpdateTime(Integer value)
	{
		this.UpdateTime = value;
	}

	/*
	 * 获取 CreateDate
	 */
	public Date getCreateDate()
	{
		return CreateDate;
	}

	/*
	 * 设置 CreateDate
	 */
	public void setCreateDate(Date value)
	{
		this.CreateDate = value;
	}

	/*
	 * 获取 UpdateDate
	 */
	public Date getUpdateDate()
	{
		return UpdateDate;
	}

	/*
	 * 设置 UpdateDate
	 */
	public void setUpdateDate(Date value)
	{
		this.UpdateDate = value;
	}

	/*
	 * 获取 Uid 的父表
	 */
	public Users getUsers()
	{
		return _Users;
	}

	/*
	 * 设置 Uid 的父表
	 */
	public void setUsers(Users value)
	{
		this._Users = value;
	}

}
