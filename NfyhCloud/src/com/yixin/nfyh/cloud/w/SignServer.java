package com.yixin.nfyh.cloud.w;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.i.ISignServer;
import com.yixin.nfyh.cloud.model.UserSigns;

public class SignServer extends WebserverConnection implements ISignServer, IWebserverParser<List<UserSigns>> {
	
	private String									mCookie;
	private NfyhSoapConnection<List<UserSigns>>		mConnection;
	private SoapConnectionCallback<List<UserSigns>>	mListener;
	private List<UserSigns>							mList;
	
	public SignServer(Context context) {
		super(context);
		mConnection = new NfyhSoapConnection<List<UserSigns>>(context);
		mConnection.setParser(this);
	}
	
	@Override
	public void setCookie(String cookie) {
		this.mCookie = cookie;
	}
	
	@Override
	public void setUserId(String uid) {
	}
	
	@Override
	public void upload(List<UserSigns> model) {
		JSONArray array = new JSONArray();
		for (UserSigns m : model) {
			JSONObject object = new JSONObject();
			try {
				object.put("Recdate", formatDate(m.getRecDate()));
				object.put("Signvalue", m.getSignValue());
				object.put("Signmark", "50");
				object.put("Groupid", m.getGroupid());
				object.put("Createuserid", m.getUsers().getUid());
				object.put("Createdate", formatDate(m.getRecDate()));
				object.put("Createusername", m.getUsers().getUsername());
				object.put("BaseSignTypes", m.getSignTypes().getTypeId());
				object.put("BaseUserinfo", m.getUsers().getUid());
				array.put(object);
			}
			catch (JSONException e) {
				log.setExcetion(tag, e); // 格式有误
				mListener.onSoapConnectedFalid(new WebServerException("数据解析错误"));
			}
			Log.i("tt", "上传值：" + m.getSignTypes().getName() + m.getSignValue());
		}
		this.mList = model;
		mConnection.setParams("cookie", this.mCookie);
		mConnection.setParams("json", array.toString());
		mConnection.request(context.getString(R.string.soap_method_upload_sign));
		Log.i("tt", array.toString());
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private String formatDate(Date date) {
		String inFormat = "yyyy-MM-dd HH:mm:ss";
		
		try {
			return new SimpleDateFormat(inFormat).format(date).toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return DateFormat.format(inFormat, new Date()).toString();
	}
	
	@Override
	public List<UserSigns> parser(String json) {
		for (UserSigns m : mList) {
			m.setIsSync(1); // 设置已经同步
		}
		return mList;
	}
	
	@Override
	public void setOnConnectonCallback(SoapConnectionCallback<List<UserSigns>> l) {
		this.mListener = l;
		this.mConnection.setonSoapConnectionCallback(l);
	}
	
}
