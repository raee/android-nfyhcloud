package com.yixin.nfyh.cloud.bll;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.bll.sign.SignCoreInterface;
import com.yixin.nfyh.cloud.bll.sign.SignCoreListener;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.ISignServer;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

public class SignCore implements SignCoreInterface,
		SoapConnectionCallback<List<UserSigns>>
{
	
	private ISignDevice			mDbSignApi;
	private Users				user;		//登陆用户
	private SignCoreListener	listener;
	private String				msg	= "";
	private ISignServer			mSignApi;
	
	public SignCore(Context context, Users user) throws SQLException
	{
		this.mDbSignApi = NfyhCloudDataFactory.getFactory(context)
				.getSignDevice();
		this.mSignApi = NfyhWebserviceFactory.getFactory(context)
				.getSignServer();
		this.user = user;
		this.mSignApi.setOnConnectonCallback(this);
		this.mSignApi.setCookie(user.getCookie());
	}
	
	@Override
	public void saveUserSign(SignTypes m) throws SQLException
	{
		// 不是体征的，并且有数组的，转化为索引
//		if (m.getIsSign() != 1 && m.getDataType() == -1)
//		{
//			DialogViewModel entity = new DialogViewModel();
//			String arr = mDbSignApi.getUserSignRangeArray(m.getTypeId());
//			entity.setDatas(arr);
//			entity.setCurrentItem(m.getDefaultValue());
//			m.setDefaultValue(entity.getCurrentItem() + "");
//		}
		
		UserSigns usersign = new UserSigns();
		usersign.setGroupid(m.getPTypeid() + "");
		usersign.setIsSync(0);
		usersign.setRecDate(new Date());
		usersign.setSignMark(m.getOrderId());
		usersign.setSignTypes(m);
		usersign.setUsers(user);
		usersign.setSignValue(m.getDefaultValue());
		
		mDbSignApi.addOrUpdateUserSign(usersign);
//		msg += String.format("正在保存：%s-%s%n", m.getName(), m.getDefaultValue());
		this.listener.onSignCoreSuccess(1, msg);
	}
	
	@Override
	public void upload()
	{
		// 获取没有同步的数据
		List<UserSigns> datas = this.mDbSignApi.getUserSignsNotSysnc();
		if (datas != null)
		{
			this.mSignApi.upload(datas);
		}
		else
		{
			this.listener.onSignCoreSuccess(1, "不需要同步。");
		}
	}
	
	@Override
	public void setSignCoreListener(SignCoreListener listener)
	{
		this.listener = listener;
	}
	
	@Override
	public void onSoapConnectSuccess(List<UserSigns> data)
	{
		// 更新状态
		this.mDbSignApi.uploadUserSign(data);
		this.listener.onSignCoreSuccess(1, "上传成功！");
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e)
	{
		this.listener.onSignCoreError(-1, e.getMessage());
		
	}
	
}
