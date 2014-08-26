package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：MarksRole
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "marks_role")
public class MarksRole
{

	// RoleId
	@DatabaseField(id = true, canBeNull = false, columnName = "roleId")
	private int		RoleId;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;

	// RoleCode
	@DatabaseField(canBeNull = false, columnName = "role_code")
	private String	RoleCode;

	// Marks
	@DatabaseField(canBeNull = false, columnName = "marks")
	private int		Marks;

	/*
	 * 构造函数
	 */
	public MarksRole()
	{
	}

	/*
	 * 获取 RoleId
	 */
	public int getRoleId()
	{
		return RoleId;
	}

	/*
	 * 设置 RoleId
	 */
	public void setRoleId(int value)
	{
		this.RoleId = value;
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
	 * 获取 RoleCode
	 */
	public String getRoleCode()
	{
		return RoleCode;
	}

	/*
	 * 设置 RoleCode
	 */
	public void setRoleCode(String value)
	{
		this.RoleCode = value;
	}

	/*
	 * 获取 Marks
	 */
	public int getMarks()
	{
		return Marks;
	}

	/*
	 * 设置 Marks
	 */
	public void setMarks(int value)
	{
		this.Marks = value;
	}

}
