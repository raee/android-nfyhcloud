package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：SignTypes
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "sign_types")
public class SignTypes
{

	// TypeId
	@DatabaseField(id = true, canBeNull = false, columnName = "typeId")
	private String	TypeId;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;

	// TypeUnit
	@DatabaseField(canBeNull = false, columnName = "type_unit")
	private String	TypeUnit;

	// TypeIcon
	@DatabaseField(canBeNull = false, columnName = "type_icon")
	private String	TypeIcon;

	// DataType
	@DatabaseField(canBeNull = false, columnName = "data_type")
	private int		DataType;

	// PTypeid
	@DatabaseField(defaultValue = "0", columnName = "p_typeid")
	private int		PTypeid;

	// DefaultValue
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "default_value")
	private String	DefaultValue;

	// OrderId
	@DatabaseField(canBeNull = false, defaultValue = "1", columnName = "order_id")
	private int		OrderId;

	// IsSign
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "is_sign")
	private int		IsSign;

	/*
	 * 构造函数
	 */
	public SignTypes()
	{
	}

	/*
	 * 获取 TypeId
	 */
	public String getTypeId()
	{
		return TypeId;
	}

	/*
	 * 设置 TypeId
	 */
	public void setTypeId(String value)
	{
		this.TypeId = value;
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
	 * 获取 TypeUnit
	 */
	public String getTypeUnit()
	{
		return TypeUnit;
	}

	/*
	 * 设置 TypeUnit
	 */
	public void setTypeUnit(String value)
	{
		this.TypeUnit = value;
	}

	/*
	 * 获取 TypeIcon
	 */
	public String getTypeIcon()
	{
		return TypeIcon;
	}

	/*
	 * 设置 TypeIcon
	 */
	public void setTypeIcon(String value)
	{
		this.TypeIcon = value;
	}

	/*
	 * 获取 DataType
	 */
	public int getDataType()
	{
		return DataType;
	}

	/*
	 * 设置 DataType
	 */
	public void setDataType(int value)
	{
		this.DataType = value;
	}

	/*
	 * 获取 PTypeid
	 */
	public int getPTypeid()
	{
		return PTypeid;
	}

	/*
	 * 设置 PTypeid
	 */
	public void setPTypeid(int value)
	{
		this.PTypeid = value;
	}

	/*
	 * 获取 DefaultValue
	 */
	public String getDefaultValue()
	{
		return DefaultValue;
	}

	/*
	 * 设置 DefaultValue
	 */
	public void setDefaultValue(String value)
	{
		this.DefaultValue = value;
	}

	/*
	 * 获取 OrderId
	 */
	public int getOrderId()
	{
		return OrderId;
	}

	/*
	 * 设置 OrderId
	 */
	public void setOrderId(int value)
	{
		this.OrderId = value;
	}

	/*
	 * 获取 IsSign
	 */
	public int getIsSign()
	{
		return IsSign;
	}

	/*
	 * 设置 IsSign
	 */
	public void setIsSign(int value)
	{
		this.IsSign = value;
	}

}
