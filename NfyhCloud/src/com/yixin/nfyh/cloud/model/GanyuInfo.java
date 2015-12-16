package com.yixin.nfyh.cloud.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.rui.framework.utils.CommonUtil;

/**
 * 干预
 * 
 * @author ChenRui
 * 
 */
public class GanyuInfo implements Parcelable
{
	
	public static Creator<GanyuInfo>	CREATOR	= new Creator<GanyuInfo>()
												{
													
													@Override
													public GanyuInfo[] newArray(int size)
													{
														return new GanyuInfo[size];
													}
													
													@Override
													public GanyuInfo createFromParcel(Parcel source)
													{
														return new GanyuInfo(source);
													}
												};
	
	private String						id;
	private String						doctorName;	// 医生名称
	private String						doctorId;		// 医生Id
	private String						pid;			// 病人Id
	private String						content;		// 内容
														
	private String						title;			// 标题
	private String						signType;		// 干预体征
	private String						signTypeName;	// 干预体征名称
	private String						signDate;		// 干预日期
	private String						date;
	
	public GanyuInfo(JSONObject obj) throws JSONException
	{
		id = obj.getString("Id");
		date = CommonUtil.getDateStringFromJson(obj.getString("Createdate"));
		doctorId = obj.getString("Doctorid");
		signDate = obj.getString("Ivtime");
		content = obj.getString("Message");
		pid = obj.getString("Sendid");
		signType = obj.getString("Typeno");
		doctorName = doctorId;
	}
	
	public GanyuInfo()
	{
	}
	
	public GanyuInfo(String signType, String signTypeName)
	{
		this.signType = signType;
		this.signTypeName = signTypeName;
	}
	
	public GanyuInfo(Parcel source)
	{
		id = source.readString();
		doctorName = source.readString();
		doctorId = source.readString();
		content = source.readString();
		title = source.readString();
		signType = source.readString();
		signTypeName = source.readString();
		signDate = source.readString();
		date = source.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(id);
		dest.writeString(doctorName);
		dest.writeString(doctorId);
		dest.writeString(content);
		dest.writeString(title);
		dest.writeString(signType);
		dest.writeString(signTypeName);
		dest.writeString(signDate);
		dest.writeString(date);
		
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getTitle()
	{
		if (TextUtils.isEmpty(title))
		{
			// 返回格式：陈睿医生2015年12月11日的干预。
			SimpleDateFormat df = new SimpleDateFormat("yyyy年mm月dd日");
			String d = df.format(new Date());
			
			title = doctorName + "医生" + d + "的干预报告";
		}
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getSignType()
	{
		if (signType == null)
		{
			signType = new SignTypeProvider().getAll().get(0).getSignType();
		}
		return signType;
	}
	
	public void setSignType(String signType)
	{
		this.signType = signType;
	}
	
	public String getSignDate()
	{
		return signDate;
	}
	
	public void setSignDate(String signDate)
	{
		this.signDate = signDate;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getDoctorName()
	{
		return doctorName;
	}
	
	public void setDoctorName(String doctorName)
	{
		this.doctorName = doctorName;
	}
	
	public String getDoctorId()
	{
		return doctorId;
	}
	
	public void setDoctorId(String doctorId)
	{
		this.doctorId = doctorId;
	}
	
	public String getPid()
	{
		return pid;
	}
	
	public void setPid(String pid)
	{
		this.pid = pid;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	
	public String getSignTypeName()
	{
		if (signTypeName == null && signType != null)
		{
			GanyuInfo m = new SignTypeProvider().getSignById(signType);
			if (m != null)
			{
				signTypeName = m.signTypeName;
			}
		}
		
		if (signTypeName == null)
		{
			signTypeName = "血压";
		}
		return signTypeName;
	}
	
	public void setSignTypeName(String signTypeName)
	{
		this.signTypeName = signTypeName;
	}
	
}
