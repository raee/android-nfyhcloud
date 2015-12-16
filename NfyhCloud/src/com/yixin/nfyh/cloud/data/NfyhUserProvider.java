package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;

import com.yixin.nfyh.cloud.model.Users;

import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imlib.model.UserInfo;
import android.content.Context;
import android.net.Uri;

/**
 * 用户提供者
 * 
 * @author ChenRui
 * 
 */
public class NfyhUserProvider implements UserInfoProvider
{
	private IUser	mUserApi;
	
	public NfyhUserProvider(Context context)
	{
		mUserApi = NfyhCloudDataFactory.getFactory(context).getUser();
	}
	
	@Override
	public UserInfo getUserInfo(String uid)
	{
		Users m;
		try
		{
			m = mUserApi.getUser(uid);
			if (m != null) { return new UserInfo(m.getUid(), m.getName(), Uri.parse(m.getHeadImage())); }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return new UserInfo(uid, uid, Uri.parse("http://nfyh.smu.edu.cn/Images/head/user.jpg"));
	}
	
}
