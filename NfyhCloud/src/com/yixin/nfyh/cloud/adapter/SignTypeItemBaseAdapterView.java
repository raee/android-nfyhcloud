package com.yixin.nfyh.cloud.adapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;

public abstract class SignTypeItemBaseAdapterView extends BaseAdapter {
	
	protected List<SignTypes>	datas;
	protected Context			context;
	protected List<UserSigns>	defaultValueDatas;
	protected List<View>		viewList	= new ArrayList<View>();
	private Users				user		= null;
	private NfyhApplication		app;
	private ISignDevice			apiSign;
	
	public SignTypeItemBaseAdapterView(Activity context) {
		this.context = context;
		this.defaultValueDatas = new ArrayList<UserSigns>();
		app = (NfyhApplication) context.getApplication();
		user = app.getCurrentUser();
		this.apiSign = NfyhCloudDataFactory.getFactory(context).getSignDevice();
	}
	
	public void setDataList(List<SignTypes> datas) {
		this.datas = datas;
	}
	
	/**
	 * 加载上一次的测量的体征值
	 */
	public void loadLastValues() {
		try {
			// 初始化默认值
			for (SignTypes itemSignTypes : datas) {
				UserSigns usersign = apiSign.getLastUserSignsByType(user.getUid(), itemSignTypes); //获取最近一次的数据
				if (usersign != null) {
					itemSignTypes.setDefaultValue(usersign.getSignValue());
					Log.i("SignTypeItemBaseAdapterView", "赋上次体征值：" + itemSignTypes.getName() + "|" + usersign.getSignValue());
				}
				this.defaultValueDatas.add(usersign);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void setValue(int postion, String value);
	
	public abstract void loadDefault();
	
	@Override
	public int getCount() {
		return datas.size();
	}
	
	@Override
	public Object getItem(int location) {
		return datas.get(location);
	}
	
	@Override
	public long getItemId(int location) {
		return location;
	}
}
