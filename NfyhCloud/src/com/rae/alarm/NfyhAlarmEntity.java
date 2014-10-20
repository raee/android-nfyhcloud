package com.rae.alarm;

import android.os.Parcel;

import com.rae.core.alarm.AlarmEntity;

public class NfyhAlarmEntity extends AlarmEntity {
	private String	signName;
	
	public String getSignName() {
		if (signName == null) {
			signName = getValue("sign");
		}
		
		return signName;
	}
	
	public void setSignName(String signName) {
		putValue("sign", signName);
		this.signName = signName;
	}
	
	public NfyhAlarmEntity(Parcel source) {
		super(source);
		signName = source.readString();
	}
	
	public NfyhAlarmEntity(String cycle, String title, String time) {
		super(cycle, title, time);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(signName);
	}
}
