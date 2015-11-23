package com.rae.alarm;

import android.os.Parcel;
import android.text.TextUtils;

import com.rae.core.alarm.AlarmEntity;

public class NfyhAlarmEntity extends AlarmEntity
{
	private String	signName;
	private String	url;
	
	public String getSignName()
	{
		if (signName == null)
		{
			signName = getValue("sign");
		}
		
		return signName;
	}
	
	public void setSignName(String signName)
	{
		signName = TextUtils.isEmpty(signName) ? "" : signName;
		putValue("sign", signName);
		this.signName = signName;
	}
	
	public NfyhAlarmEntity(Parcel source)
	{
		super(source);
		signName = source.readString();
	}
	
	public NfyhAlarmEntity(AlarmEntity entity)
	{
		super(entity.getCycle(), entity.getTitle(), entity.getTime());
		convert(entity);
	}
	
	/**
	 * 转化实体
	 * 
	 * @param entity
	 */
	public void convert(AlarmEntity entity)
	{
		setContent(entity.getContent());
		setCycle(entity.getCycle());
		setId(entity.getId());
		setImages(entity.getImages());
		setNextTime(entity.getNextTime());
		setOtherParam(entity.getOtherParam());
		setRing(entity.getRing());
		setSound(entity.getSound());
		setState(entity.getState());
		setTime(entity.getTime());
		setTimeSpan(entity.getTimeSpan());
		setTitle(entity.getTitle());
		setWeeks(entity.getWeeks());
	}
	
	public NfyhAlarmEntity(String cycle, String title, String time)
	{
		super(cycle, title, time);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		super.writeToParcel(dest, flags);
		dest.writeString(signName);
	}
	
	public String getUrl()
	{
		if (url == null)
		{
			url = getValue("url");
		}
		
		return url;
	}
	
	public void setUrl(String url)
	{
		url = TextUtils.isEmpty(this.url) ? "" : this.url;
		putValue("url", url);
		this.url = url;
	}
}
