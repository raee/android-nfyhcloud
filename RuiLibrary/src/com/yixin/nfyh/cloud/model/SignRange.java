package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：SignRange
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "sign_range")
public class SignRange
{

	// Rangeid
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "rangeid")
	private long		Rangeid;

	// LeftRange
	@DatabaseField(canBeNull = false, columnName = "left_range")
	private double		LeftRange;

	// RightRange
	@DatabaseField(canBeNull = false, columnName = "right_range")
	private double		RightRange;

	// RangeArr
	@DatabaseField(columnName = "range_arr")
	private String		RangeArr;

	// RangeType
	@DatabaseField(canBeNull = false, columnName = "range_type")
	private int			RangeType;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String		Name;

	// 父表 TypeId
	@DatabaseField(foreign = true, foreignColumnName = "typeId")
	private SignTypes	_SignTypes;

	/*
	 * 构造函数
	 */
	public SignRange()
	{
	}

	/*
	 * 获取 Rangeid
	 */
	public long getRangeid()
	{
		return Rangeid;
	}

	/*
	 * 设置 Rangeid
	 */
	public void setRangeid(long value)
	{
		this.Rangeid = value;
	}

	/*
	 * 获取 LeftRange
	 */
	public double getLeftRange()
	{
		return LeftRange;
	}

	/*
	 * 设置 LeftRange
	 */
	public void setLeftRange(double value)
	{
		this.LeftRange = value;
	}

	/*
	 * 获取 RightRange
	 */
	public double getRightRange()
	{
		return RightRange;
	}

	/*
	 * 设置 RightRange
	 */
	public void setRightRange(double value)
	{
		this.RightRange = value;
	}

	/*
	 * 获取 RangeArr
	 */
	public String getRangeArr()
	{
		return RangeArr;
	}

	/*
	 * 设置 RangeArr
	 */
	public void setRangeArr(String value)
	{
		this.RangeArr = value;
	}

	/*
	 * 获取 RangeType
	 */
	public int getRangeType()
	{
		return RangeType;
	}

	/*
	 * 设置 RangeType
	 */
	public void setRangeType(int value)
	{
		this.RangeType = value;
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

}
