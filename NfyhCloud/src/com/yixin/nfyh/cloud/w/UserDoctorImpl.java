package com.yixin.nfyh.cloud.w;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.IUser;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.GanyuInfo;
import com.yixin.nfyh.cloud.model.Users;

public class UserDoctorImpl extends WebserverConnection implements IUserDoctor, SoapConnectionCallback<List<Users>>
{
	private IUserDoctorListener	mListener;
	private IUser				mUserApi;
	
	public UserDoctorImpl(Context context)
	{
		super(context);
		mUserApi = NfyhCloudDataFactory.getFactory(context).getUser();
	}
	
	@Override
	public void getUserGroupDoctor(String pid, IUserDoctorListener l)
	{
		mListener = l;
		
		NfyhSoapConnection<List<Users>> conn = new NfyhSoapConnection<List<Users>>(context);
		conn.setParams("token", context.getString(R.string.API_TOKEN));
		conn.setParams("pid", pid);
		
		conn.setonSoapConnectionCallback(this);
		conn.setParser(new IWebserverParser<List<Users>>()
		{
			
			@Override
			public List<Users> parser(String json)
			{
				try
				{
					List<Users> result = new ArrayList<Users>();
					JSONArray arr = new JSONArray(json);
					int len = arr.length();
					if (len == 0)
					{
						mListener.onNotDoctor();
						return null;
					}
					
					for (int i = 0; i < len; i++)
					{
						JSONObject om = arr.getJSONObject(i);
						
						Users m = new Users();
						m.setUid(om.getString("Id"));
						m.setName(om.getString("Username")); // 医生昵称
						m.setPwd("null");
						m.setUsername(om.getString("Useraccount"));
						m.setSex(om.getString("Usersex").equals("1") ? "男" : "女");
						m.setHeadImage(om.getString("Headimg")); // 头像地址
						
						result.add(m);
						
						// 插入到数据库中
						mUserApi.createUser(m);
					}
					
					return result;
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		conn.request("GetDoctorsByPid");
	}
	
	@Override
	public void onSoapConnectSuccess(List<Users> data)
	{
		mListener.onGetUserDoctorSuccess(data);
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e)
	{
		mListener.onGetUserDoctorError(e.getCode(), e.getMessage());
		
	}
	
	@Override
	public void getIntervenes(String uid, final IGetIntervenesLinstener l)
	{
		NfyhSoapConnection<List<GanyuInfo>> conn = new NfyhSoapConnection<List<GanyuInfo>>(context);
		conn.setParams("token", context.getString(R.string.API_TOKEN));
		conn.setParams("uid", uid);
		
		conn.setParser(new IWebserverParser<List<GanyuInfo>>()
		{
			
			@Override
			public List<GanyuInfo> parser(String json)
			{
				try
				{
					// 解析数据
					JSONArray arr = new JSONArray(json);
					
					// 没有数据
					if (arr.length() <= 0)
					{
						l.onNotIntervenes();
						return null;
					}
					
					List<GanyuInfo> result = new ArrayList<GanyuInfo>();
					for (int i = 0; i < arr.length(); i++)
					{
						JSONObject obj = arr.getJSONObject(i);
						
						// 解析
						GanyuInfo info = new GanyuInfo(obj);
						result.add(info);
					}
					
					return result;
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		conn.setonSoapConnectionCallback(new SoapConnectionCallback<List<GanyuInfo>>()
		{
			
			@Override
			public void onSoapConnectedFalid(WebServerException e)
			{
				l.onGetIntervenesError(e.getCode(), e.getMessage());
			}
			
			@Override
			public void onSoapConnectSuccess(List<GanyuInfo> data)
			{
				l.onGetIntervenesSuccess(data);
			}
		});
		
		conn.request("GetIntervenes");
	}
	
}
