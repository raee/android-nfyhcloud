package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：SignTips
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "sign_tips")
public class SignTips
{

	// TipsId
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "tipsId")
	private long		TipsId;

	// TipsSymbol
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "tips_symbol")
	private int			TipsSymbol;

	// TipsValue
	@DatabaseField(canBeNull = false, defaultValue = "1", columnName = "tips_value")
	private double		TipsValue;

	// Uid
	@DatabaseField(columnName = "uid")
	private String		Uid;

	// Remark
	@DatabaseField(columnName = "remark")
	private String		Remark;

	// TipsComment
	@DatabaseField(canBeNull = false, columnName = "tips_comment")
	private String		TipsComment;

	// TipsLevel
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "tips_level")
	private int			TipsLevel;

	// 父表 Rangeid
	@DatabaseField(foreign = true, foreignColumnName = "rangeid")
	private SignRange	_SignRange;

	/*
	 * 构造函数
	 */
	public SignTips()
	{
	}

	/*
	 * 获取 TipsId
	 */
	public long getTipsId()
	{
		return TipsId;
	}

	/*
	 * 设置 TipsId
	 */
	public void setTipsId(long value)
	{
		this.TipsId = value;
	}

	/*
	 * 获取 TipsSymbol
	 */
	public int getTipsSymbol()
	{
		return TipsSymbol;
	}

	/*
	 * 设置 TipsSymbol
	 */
	public void setTipsSymbol(int value)
	{
		this.TipsSymbol = value;
	}

	/*
	 * 获取 TipsValue
	 */
	public double getTipsValue()
	{
		return TipsValue;
	}

	/*
	 * 设置 TipsValue
	 */
	public void setTipsValue(double value)
	{
		this.TipsValue = value;
	}

	/*
	 * 获取 Uid
	 */
	public String getUid()
	{
		return Uid;
	}

	/*
	 * 设置 Uid
	 */
	public void setUid(String value)
	{
		this.Uid = value;
	}

	/*
	 * 获取 Remark
	 */
	public String getRemark()
	{
		return Remark;
	}

	/*
	 * 设置 Remark
	 */
	public void setRemark(String value)
	{
		this.Remark = value;
	}

	/*
	 * 获取 TipsComment
	 */
	public String getTipsComment()
	{
		return TipsComment;
	}

	/*
	 * 设置 TipsComment
	 */
	public void setTipsComment(String value)
	{
		this.TipsComment = value;
	}

	/*
	 * 获取 TipsLevel
	 */
	public int getTipsLevel()
	{
		return TipsLevel;
	}

	/*
	 * 设置 TipsLevel
	 */
	public void setTipsLevel(int value)
	{
		this.TipsLevel = value;
	}

	/*
	 * 获取 Rangeid 的父表
	 */
	public SignRange getSignRange()
	{
		return _SignRange;
	}

	/*
	 * 设置 Rangeid 的父表
	 */
	public void setSignRange(SignRange value)
	{
		this._SignRange = value;
	}

}
