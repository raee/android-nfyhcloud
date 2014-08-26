package com.yixin.nfyh.cloud.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：SignReport
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "sign_report")
public class SignReport
{

	// 父表 TipsId
	@DatabaseField(foreign = true, foreignColumnName = "tipsId")
	private SignTips	_SignTips;

	// 父表 RecordId
	@DatabaseField(foreign = true, foreignColumnName = "recordId")
	private UserSigns	_UserSigns;

	/*
	 * 构造函数
	 */
	public SignReport()
	{
	}

	/*
	 * 获取 TipsId 的父表
	 */
	public SignTips getSignTips()
	{
		return _SignTips;
	}

	/*
	 * 设置 TipsId 的父表
	 */
	public void setSignTips(SignTips value)
	{
		this._SignTips = value;
	}

	/*
	 * 获取 RecordId 的父表
	 */
	public UserSigns getUserSigns()
	{
		return _UserSigns;
	}

	/*
	 * 设置 RecordId 的父表
	 */
	public void setUserSigns(UserSigns value)
	{
		this._UserSigns = value;
	}

}
