//package com.yixin.nfyh.cloud.data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import cn.rui.framework.data.IQuery;
//
//import com.yixin.nfyh.cloud.bll.GlobalSetting;
//
///**
// * 用户事件类型
// * @author MrChenrui
// *
// */
//public class UserEventType
//{
//	private DataContext											db;
//	private IQuery<com.yixin.nfyh.cloud.entitys.UserEventType>	query;
//	private GlobalSetting										setting;
//
//	public UserEventType(Context context)
//	{
//		this.db = new DataContext(context);
//		this.query = this.db
//				.createQuery(com.yixin.nfyh.cloud.entitys.UserEventType.class);
//		this.setting = new GlobalSetting(context);
//	}
//
//	public List<String> getEventList()
//	{
////		List<String> result = new ArrayList<String>();
////
////		List<com.yixin.nfyh.cloud.entitys.UserEventType> datas = query.where(
////				"uid='" + setting.getUid() + "'").toList();
////
////		for (com.yixin.nfyh.cloud.entitys.UserEventType userEventType : datas)
////		{
////			result.add(userEventType.getEventName());
////		}
////
////		if (result.size() <= 0)
////			result.add("用户没有定义事件");
//
//		return result;
//	}
//
////	public void add(String name)
////	{
////		com.yixin.nfyh.cloud.entitys.UserEventType m = new com.yixin.nfyh.cloud.entitys.UserEventType();
////		m.setEventName(name);
////		m.setUid(setting.getUid());
////		this.query.add(m);
////	}
////
////	public void del(String name)
////	{
////		com.yixin.nfyh.cloud.entitys.UserEventType m = this.query.where(
////				"eventname='" + name + "'").toSingle();
////		if (m != null)
////			this.query.del(m);
////	}
//
//}
