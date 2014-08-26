package com.yixin.nfyh.cloud.model;

import java.util.Date;

import cn.rui.framework.utils.CommonUtil;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 表：Users 备注：
 * 
 * @author 陈睿
 * 
 */
@DatabaseTable(tableName = "users")
public class Users
{

	// Age
	@DatabaseField(columnName = "age")
	private int		Age;

	// Cookie
	@DatabaseField(columnName = "cookie")
	private String	Cookie;

	// HeadImage
	@DatabaseField(columnName = "head_image")
	private String	HeadImage;

	// Hospital
	@DatabaseField(columnName = "hospital")
	private String	Hospital;

	// Location
	@DatabaseField(columnName = "location")
	private String	Location;

	// LoginDate
	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", columnName = "login_date")
	private Date	LoginDate;

	// LoginTime
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "login_time")
	private int		LoginTime;

	// Marks
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "marks")
	private double	Marks;

	// Name
	@DatabaseField(canBeNull = false, columnName = "name")
	private String	Name;

	// Phonenumber
	@DatabaseField(columnName = "phonenumber")
	private String	Phonenumber;

	// PUid
	@DatabaseField(columnName = "p_uid")
	private String	PUid;

	// Pwd
	@DatabaseField(canBeNull = false, columnName = "pwd")
	private String	Pwd;

	// RecTime
	@DatabaseField(canBeNull = false, defaultValue = "0", columnName = "rec_time")
	private int		RecTime;

	// Rid
	@DatabaseField(columnName = "rid")
	private String	Rid;

	// Sex
	@DatabaseField(canBeNull = false, columnName = "sex")
	private String	Sex;

	// Uid
	@DatabaseField(id = true, canBeNull = false, columnName = "uid")
	private String	Uid;

	// Username
	@DatabaseField(canBeNull = false, columnName = "username")
	private String	Username;

	// Zhiye
	@DatabaseField(columnName = "zhiye")
	private String	Zhiye;

	/*
	 * 构造函数
	 */
	public Users()
	{
	}

	/*
	 * 获取 Age
	 */
	public int getAge()
	{
		return Age;
	}

	/*
	 * 获取 Cookie
	 */
	public String getCookie()
	{
		return Cookie;
	}

	/*
	 * 获取 HeadImage
	 */
	public String getHeadImage()
	{
		return HeadImage;
	}

	/*
	 * 获取 Hospital
	 */
	public String getHospital()
	{
		return Hospital;
	}

	/*
	 * 获取 Location
	 */
	public String getLocation()
	{
		return Location;
	}

	/*
	 * 获取 LoginDate
	 */
	public Date getLoginDate()
	{
		return LoginDate;
	}

	/*
	 * 获取 LoginTime
	 */
	public int getLoginTime()
	{
		return LoginTime;
	}

	/*
	 * 获取 Marks
	 */
	public double getMarks()
	{
		return Marks;
	}

	/*
	 * 获取 Name
	 */
	public String getName()
	{
		return Name;
	}

	/*
	 * 获取 Phonenumber
	 */
	public String getPhonenumber()
	{
		return Phonenumber;
	}

	/*
	 * 获取 PUid
	 */
	public String getPUid()
	{
		return PUid;
	}

	/*
	 * 获取 Pwd
	 */
	public String getPwd()
	{
		return CommonUtil.decryptBASE64(this.Pwd);
	}

	/*
	 * 获取 RecTime
	 */
	public int getRecTime()
	{
		return RecTime;
	}

	/*
	 * 获取 Rid
	 */
	public String getRid()
	{
		return Rid;
	}

	/*
	 * 获取 Sex
	 */
	public String getSex()
	{
		return Sex;
	}

	/*
	 * 获取 Uid
	 */
	public String getUid()
	{
		return Uid;
	}

	/*
	 * 获取 Username
	 */
	public String getUsername()
	{
		return Username;
	}

	/*
	 * 获取 Zhiye
	 */
	public String getZhiye()
	{
		return Zhiye;
	}

	/*
	 * 设置 Age
	 */
	public void setAge(int value)
	{
		this.Age = value;
	}

	/*
	 * 设置 Cookie
	 */
	public void setCookie(String value)
	{
		this.Cookie = value;
	}

	/*
	 * 设置 HeadImage
	 */
	public void setHeadImage(String value)
	{
		this.HeadImage = value;
	}

	/*
	 * 设置 Hospital
	 */
	public void setHospital(String value)
	{
		this.Hospital = value;
	}

	/*
	 * 设置 Location
	 */
	public void setLocation(String value)
	{
		this.Location = value;
	}

	/*
	 * 设置 LoginDate
	 */
	public void setLoginDate(Date value)
	{
		this.LoginDate = value;
	}

	/*
	 * 设置 LoginTime
	 */
	public void setLoginTime(int value)
	{
		this.LoginTime = value;
	}

	/*
	 * 设置 Marks
	 */
	public void setMarks(double value)
	{
		this.Marks = value;
	}

	/*
	 * 设置 Name
	 */
	public void setName(String value)
	{
		this.Name = value;
	}

	/*
	 * 设置 Phonenumber
	 */
	public void setPhonenumber(String value)
	{
		this.Phonenumber = value;
	}

	/*
	 * 设置 PUid
	 */
	public void setPUid(String value)
	{
		this.PUid = value;
	}

	/*
	 * 设置 Pwd
	 */
	public void setPwd(String value)
	{
		this.Pwd = CommonUtil.encryptBASE64(value); // 加密
	}

	/*
	 * 设置 RecTime
	 */
	public void setRecTime(int value)
	{
		this.RecTime = value;
	}

	/*
	 * 设置 Rid
	 */
	public void setRid(String value)
	{
		this.Rid = value;
	}

	/*
	 * 设置 Sex
	 */
	public void setSex(String value)
	{
		this.Sex = value;
	}

	/*
	 * 设置 Uid
	 */
	public void setUid(String value)
	{
		this.Uid = value;
	}

	/*
	 * 设置 Username
	 */
	public void setUsername(String value)
	{
		this.Username = value;
	}

	/*
	 * 设置 Zhiye
	 */
	public void setZhiye(String value)
	{
		this.Zhiye = value;
	}

}
