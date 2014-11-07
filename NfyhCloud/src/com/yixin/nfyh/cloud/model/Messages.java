package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Messages
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "messages")
public class Messages
{

	// MsgId
	@DatabaseField(id = true, canBeNull = false, columnName = "msgId")
	private String	MsgId;

	// TypeCode
	@DatabaseField(canBeNull = false, columnName = "type_code")
	private String	TypeCode;

	// Title
	@DatabaseField(canBeNull = false, columnName = "title")
	private String	Title;

	// Content
	@DatabaseField(canBeNull = false, columnName = "content")
	private String	Content;

	// Summary
	@DatabaseField(columnName = "summary")
	private String	Summary;

	// IntentName
	@DatabaseField(canBeNull = false, columnName = "intent_name")
	private String	IntentName;

	// IntentCategroy
	@DatabaseField(columnName = "intent_categroy")
	private String	IntentCategroy;

	// IntentFlag
	@DatabaseField(columnName = "intent_flag")
	private String	IntentFlag;

	// IntentAction
	@DatabaseField(columnName = "intent_action")
	private String	IntentAction;

	// IntentDate
	@DatabaseField(columnName = "intent_date")
	private String	IntentDate;

	// IntentExtra
	@DatabaseField(columnName = "intent_extra")
	private String	IntentExtra;

	// SendDate
	@DatabaseField(columnName = "send_date")
	private String	SendDate;

	// Status
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "status")
	private Integer	Status;

	/*
	 * 构造函数
	 */
	public Messages()
	{
	}

	/*
	 * 获取 MsgId
	 */
	public String getMsgId()
	{
		return MsgId;
	}

	/*
	 * 设置 MsgId
	 */
	public void setMsgId(String value)
	{
		this.MsgId = value;
	}

	/*
	 * 获取 TypeCode
	 */
	public String getTypeCode()
	{
		return TypeCode;
	}

	/*
	 * 设置 TypeCode
	 */
	public void setTypeCode(String value)
	{
		this.TypeCode = value;
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
	 * 获取 Summary
	 */
	public String getSummary()
	{
		return Summary;
	}

	/*
	 * 设置 Summary
	 */
	public void setSummary(String value)
	{
		this.Summary = value;
	}

	/*
	 * 获取 IntentName
	 */
	public String getIntentName()
	{
		return IntentName;
	}

	/*
	 * 设置 IntentName
	 */
	public void setIntentName(String value)
	{
		this.IntentName = value;
	}

	/*
	 * 获取 IntentCategroy
	 */
	public String getIntentCategroy()
	{
		return IntentCategroy;
	}

	/*
	 * 设置 IntentCategroy
	 */
	public void setIntentCategroy(String value)
	{
		this.IntentCategroy = value;
	}

	/*
	 * 获取 IntentFlag
	 */
	public String getIntentFlag()
	{
		return IntentFlag;
	}

	/*
	 * 设置 IntentFlag
	 */
	public void setIntentFlag(String value)
	{
		this.IntentFlag = value;
	}

	/*
	 * 获取 IntentAction
	 */
	public String getIntentAction()
	{
		return IntentAction;
	}

	/*
	 * 设置 IntentAction
	 */
	public void setIntentAction(String value)
	{
		this.IntentAction = value;
	}

	/*
	 * 获取 IntentDate
	 */
	public String getIntentDate()
	{
		return IntentDate;
	}

	/*
	 * 设置 IntentDate
	 */
	public void setIntentDate(String value)
	{
		this.IntentDate = value;
	}

	/*
	 * 获取 IntentExtra
	 */
	public String getIntentExtra()
	{
		return IntentExtra;
	}

	/*
	 * 设置 IntentExtra
	 */
	public void setIntentExtra(String value)
	{
		this.IntentExtra = value;
	}

	/*
	 * 获取 SendDate
	 */
	public String getSendDate()
	{
		return SendDate;
	}

	/*
	 * 设置 SendDate
	 */
	public void setSendDate(String value)
	{
		this.SendDate = value;
	}

	/*
	 * 获取 Status
	 */
	public Integer getStatus()
	{
		return Status;
	}

	/*
	 * 设置 Status
	 */
	public void setStatus(Integer value)
	{
		this.Status = value;
	}

}
