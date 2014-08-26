package com.yixin.nfyh.cloud.model;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：MarksDetail
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "marks_detail")
public class MarksDetail
{

	// Id
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "id")
	private long		Id;

	// Havedate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "havedate")
	private Date		Havedate;

	// 父表 RoleId
	@DatabaseField(foreign = true, foreignColumnName = "roleId")
	private MarksRole	_MarksRole;

	// 父表 Uid
	@DatabaseField(foreign = true, foreignColumnName = "uid")
	private Users		_Users;

	/*
	 * 构造函数
	 */
	public MarksDetail()
	{
	}

	/*
	 * 获取 Id
	 */
	public long getId()
	{
		return Id;
	}

	/*
	 * 设置 Id
	 */
	public void setId(long value)
	{
		this.Id = value;
	}

	/*
	 * 获取 Havedate
	 */
	public Date getHavedate()
	{
		return Havedate;
	}

	/*
	 * 设置 Havedate
	 */
	public void setHavedate(Date value)
	{
		this.Havedate = value;
	}

	/*
	 * 获取 RoleId 的父表
	 */
	public MarksRole getMarksRole()
	{
		return _MarksRole;
	}

	/*
	 * 设置 RoleId 的父表
	 */
	public void setMarksRole(MarksRole value)
	{
		this._MarksRole = value;
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
