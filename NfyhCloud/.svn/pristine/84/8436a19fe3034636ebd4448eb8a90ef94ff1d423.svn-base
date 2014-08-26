//package com.yixin.nfyh.cloud.data;
//
//import java.util.List;
//
//import cn.rui.framework.data.IQuery;
//
//import com.yixin.nfyh.cloud.bll.GlobalSetting;
//import com.yixin.nfyh.cloud.entitys.Contacts;
//
//import android.content.Context;
//
//public class MyContacts
//{
//	private DataContext		db;
//	private IQuery<Contacts>	query;
//	private GlobalSetting setting;
//
//	public MyContacts(Context context)
//	{
//		this.db = new DataContext(context);
//		this.query = this.db.createQuery(Contacts.class);
//		this.setting = new GlobalSetting(context);
//	}
//	
//	public void add(String num)
//	{
//		Contacts m=new Contacts();
//		m.setPhoneNum(num);
//		m.setUid(setting.getUid());
//		this.query.add(m);
//	}
//	
//	public void del(String num)
//	{
//		Contacts m = this.query.where("PhoneNum='"+num+"'").toSingle();
//		if(m!=null)
//			this.query.del(m);
//	}
//
//	public List<Contacts> getList()
//	{
//		return this.query.where("uid='"+setting.getUid()+"'").toList();
//	}
//}
