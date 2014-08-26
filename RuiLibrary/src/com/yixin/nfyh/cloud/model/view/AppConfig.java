package com.yixin.nfyh.cloud.model.view;

import java.util.List;

/**
 * 软件设置实体
 * 
 * @author MrChenrui
 * 
 */
public class AppConfig
{
	private String			deviceName;		// 当前监测设备名称
	private boolean			enableFall;		// 是否启用跌倒监测
	private boolean			enableDesktop;		// 是否启动桌面悬浮框
	private boolean			enableClock;		// 是否开启提醒
	private boolean			enableMsg;			// 是否开启消息推送
	private String			appVersion;		// 软版本
	private String			fallPhoneNumber;	// 跌倒设备绑定号码
	private List<String>	fallContacts;		// 跌倒联系人
	private List<String>	fallEvents;		// 跌倒事件

	public String getDeviceName()
	{
		return deviceName;
	}

	public void setDeviceName(String deviceName)
	{
		this.deviceName = deviceName;
	}

	public boolean isEnableFall()
	{
		return enableFall;
	}

	public void setEnableFall(boolean enableFall)
	{
		this.enableFall = enableFall;
	}

	public boolean isEnableDesktop()
	{
		return enableDesktop;
	}

	public void setEnableDesktop(boolean enableDesktop)
	{
		this.enableDesktop = enableDesktop;
	}

	public boolean isEnableClock()
	{
		return enableClock;
	}

	public void setEnableClock(boolean enableClock)
	{
		this.enableClock = enableClock;
	}

	public boolean isEnableMsg()
	{
		return enableMsg;
	}

	public void setEnableMsg(boolean enableMsg)
	{
		this.enableMsg = enableMsg;
	}

	public String getAppVersion()
	{
		return appVersion;
	}

	public void setAppVersion(String appVersion)
	{
		this.appVersion = appVersion;
	}

	public String getFallPhoneNumber()
	{
		return fallPhoneNumber;
	}

	public void setFallPhoneNumber(String fallPhoneNumber)
	{
		this.fallPhoneNumber = fallPhoneNumber;
	}

	public List<String> getFallContacts()
	{
		return fallContacts;
	}

	public void setFallContacts(List<String> fallContacts)
	{
		this.fallContacts = fallContacts;
	}

	public List<String> getFallEvents()
	{
		return fallEvents;
	}

	public void setFallEvents(List<String> fallEvents)
	{
		this.fallEvents = fallEvents;
	}
}
