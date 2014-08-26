package com.yixin.nfyh.cloud.model;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：UserSigns
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "user_signs")
public class UserSigns
{

	// RecordId
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "recordId")
	private long		RecordId;

	// SignValue
	@DatabaseField(canBeNull = false, columnName = "sign_value")
	private String		SignValue;

	// IsSync
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "isSync")
	private int			IsSync;

	// RecDate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "rec_date")
	private Date		RecDate;

	// SignMark
	@DatabaseField(canBeNull = false, defaultValue = "50", columnName = "sign_mark")
	private double		SignMark;

	// Groupid
	@DatabaseField(columnName = "groupid")
	private String		Groupid;

	// 父表 TypeId
	@DatabaseField(foreign = true, foreignColumnName = "typeId")
	private SignTypes	_SignTypes;

	// 父表 Uid
	@DatabaseField(foreign = true, foreignColumnName = "uid")
	private Users		_Users;

	/*
	 * 构造函数
	 */
	public UserSigns()
	{
	}

	/*
	 * 获取 RecordId
	 */
	public long getRecordId()
	{
		return RecordId;
	}

	/*
	 * 设置 RecordId
	 */
	public void setRecordId(long value)
	{
		this.RecordId = value;
	}

	/*
	 * 获取 SignValue
	 */
	public String getSignValue()
	{
		return SignValue;
	}

	/*
	 * 设置 SignValue
	 */
	public void setSignValue(String value)
	{
		this.SignValue = value;
	}

	/*
	 * 获取 IsSync
	 */
	public int getIsSync()
	{
		return IsSync;
	}

	/*
	 * 设置 IsSync
	 */
	public void setIsSync(int value)
	{
		this.IsSync = value;
	}

	/*
	 * 获取 RecDate
	 */
	public Date getRecDate()
	{
		return RecDate;
	}

	/*
	 * 设置 RecDate
	 */
	public void setRecDate(Date value)
	{
		this.RecDate = value;
	}

	/*
	 * 获取 SignMark
	 */
	public double getSignMark()
	{
		return SignMark;
	}

	/*
	 * 设置 SignMark
	 */
	public void setSignMark(double value)
	{
		this.SignMark = value;
	}

	/*
	 * 获取 Groupid
	 */
	public String getGroupid()
	{
		return Groupid;
	}

	/*
	 * 设置 Groupid
	 */
	public void setGroupid(String value)
	{
		this.Groupid = value;
	}

	/*
	 * 获取 TypeId 的父表
	 */
	public SignTypes getSignTypes()
	{
		return _SignTypes;
	}

	/*
	 * 设置 TypeId 的父表
	 */
	public void setSignTypes(SignTypes value)
	{
		this._SignTypes = value;
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
