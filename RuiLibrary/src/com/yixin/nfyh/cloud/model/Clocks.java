package com.yixin.nfyh.cloud.model;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Clocks
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "clocks")
public class Clocks implements Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2009592276119270363L;

	// ClockId
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "clockId")
	private long				ClockId;

	// Title
	@DatabaseField(canBeNull = false, columnName = "title")
	private String				Title;

	// Content
	@DatabaseField(canBeNull = false, columnName = "content")
	private String				Content;

	// StartDate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "start_date")
	private Date				StartDate;

	// EndDate
	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "end_date")
	private Date				EndDate;

	// RepeatCount
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "repeat_count")
	private long				RepeatCount;

	// RepeatSpan
	@DatabaseField(defaultValue = "0", columnName = "repeat_span")
	private long				RepeatSpan;

	// AlarmCount
	@DatabaseField(defaultValue = "0", columnName = "alarm_count")
	private long				AlarmCount;

	// 父表 Uid
	@DatabaseField(foreign = true, foreignColumnName = "uid")
	private Users				_Users;

	/*
	 * 构造函数
	 */
	public Clocks()
	{
	}

	/*
	 * 获取 ClockId
	 */
	public long getClockId()
	{
		return ClockId;
	}

	/*
	 * 设置 ClockId
	 */
	public void setClockId(long value)
	{
		this.ClockId = value;
	}

	/*
	 * 获取 Title
	 */
	public String getTitle()
	{
		return Title;
	}

	/*
	 * 设置 Title
	 */
	public void setTitle(String value)
	{
		this.Title = value;
	}

	/*
	 * 获取 Content
	 */
	public String getContent()
	{
		return Content;
	}

	/*
	 * 设置 Content
	 */
	public void setContent(String value)
	{
		this.Content = value;
	}

	/*
	 * 获取 StartDate
	 */
	public Date getStartDate()
	{
		return StartDate;
	}

	/*
	 * 设置 StartDate
	 */
	public void setStartDate(Date value)
	{
		this.StartDate = value;
	}

	/*
	 * 获取 EndDate
	 */
	public Date getEndDate()
	{
		return EndDate;
	}

	/*
	 * 设置 EndDate
	 */
	public void setEndDate(Date value)
	{
		this.EndDate = value;
	}

	/*
	 * 获取 RepeatCount
	 */
	public long getRepeatCount()
	{
		return RepeatCount;
	}

	/*
	 * 设置 RepeatCount
	 */
	public void setRepeatCount(long value)
	{
		this.RepeatCount = value;
	}

	/*
	 * 获取 RepeatSpan
	 */
	public long getRepeatSpan()
	{
		return RepeatSpan;
	}

	/*
	 * 设置 RepeatSpan
	 */
	public void setRepeatSpan(long value)
	{
		this.RepeatSpan = value;
	}

	/*
	 * 获取 AlarmCount
	 */
	public long getAlarmCount()
	{
		return AlarmCount;
	}

	/*
	 * 设置 AlarmCount
	 */
	public void setAlarmCount(long value)
	{
		this.AlarmCount = value;
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
