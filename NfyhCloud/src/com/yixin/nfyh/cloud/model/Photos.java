package com.yixin.nfyh.cloud.model;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Photos
 * 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "photos")
public class Photos
{
	// Categoryid
	@DatabaseField(canBeNull = false, columnName = "photoid")
	private String	PhotoId;

	public String getPhotoId()
	{
		return PhotoId;
	}

	public void setPhotoId(String photoId)
	{
		PhotoId = photoId;
	}

	// Categoryid
	@DatabaseField(canBeNull = false, columnName = "categoryid")
	private String			Categoryid;

	// Imagesrc
	@DatabaseField(canBeNull = false, columnName = "imagesrc")
	private String			Imagesrc;

	// CreateDate
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "create_date")
	private Date			CreateDate;

	// 父表 Pid
	@DatabaseField(foreign = true, foreignColumnName = "pid")
	private Photocategory	_Photocategory;

	/*
	 * 构造函数
	 */
	public Photos()
	{
	}

	/*
	 * 获取 Categoryid
	 */
	public String getCategoryid()
	{
		return Categoryid;
	}

	/*
	 * 设置 Categoryid
	 */
	public void setCategoryid(String value)
	{
		this.Categoryid = value;
	}

	/*
	 * 获取 Imagesrc
	 */
	public String getImagesrc()
	{
		return Imagesrc;
	}

	/*
	 * 设置 Imagesrc
	 */
	public void setImagesrc(String value)
	{
		this.Imagesrc = value;
	}

	/*
	 * 获取 CreateDate
	 */
	public Date getCreateDate()
	{
		return CreateDate;
	}

	/*
	 * 设置 CreateDate
	 */
	public void setCreateDate(Date value)
	{
		this.CreateDate = value;
	}

	/*
	 * 获取 Pid 的父表
	 */
	public Photocategory getPhotocategory()
	{
		return _Photocategory;
	}

	/*
	 * 设置 Pid 的父表
	 */
	public void setPhotocategory(Photocategory value)
	{
		this._Photocategory = value;
	}

}
