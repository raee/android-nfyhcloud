package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Dicts
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "dicts")
public class Dicts
{

	// DictId
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "dictId")
	private long	DictId;

	// CodeName
	@DatabaseField(columnName = "code_name")
	private String	CodeName;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;

	// DicValue
	@DatabaseField(canBeNull = false, columnName = "dic_value")
	private String	DicValue;

	// Comment
	@DatabaseField(columnName = "comment")
	private String	Comment;

	/*
	 * 构造函数
	 */
	public Dicts()
	{
	}

	/*
	 * 获取 DictId
	 */
	public long getDictId()
	{
		return DictId;
	}

	/*
	 * 设置 DictId
	 */
	public void setDictId(long value)
	{
		this.DictId = value;
	}

	/*
	 * 获取 CodeName
	 */
	public String getCodeName()
	{
		return CodeName;
	}

	/*
	 * 设置 CodeName
	 */
	public void setCodeName(String value)
	{
		this.CodeName = value;
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
	 * 获取 DicValue
	 */
	public String getDicValue()
	{
		return DicValue;
	}

	/*
	 * 设置 DicValue
	 */
	public void setDicValue(String value)
	{
		this.DicValue = value;
	}

	/*
	 * 获取 Comment
	 */
	public String getComment()
	{
		return Comment;
	}

	/*
	 * 设置 Comment
	 */
	public void setComment(String value)
	{
		this.Comment = value;
	}

}
