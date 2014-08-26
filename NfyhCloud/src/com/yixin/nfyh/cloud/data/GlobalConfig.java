//package com.yixin.nfyh.cloud.data;
//
//import java.util.List;
//
//import android.content.Context;
//import cn.rui.framework.data.IQuery;
//
//import com.yixin.nfyh.cloud.entitys.Config;
//
//public class GlobalConfig
//{
//	private DataContext		db;
//	private IQuery<Config>	query;
//
//	public GlobalConfig(Context context)
//	{
//		this.db = new DataContext(context);
//		this.query = this.db.createQuery(Config.class);
//	}
//
//	/**
//	 * 添加一键呼救事件
//	 * 
//	 * @param evenName
//	 */
//	public void addSOSEvent(String evenName)
//	{
//		Config entity = new Config();
//		entity.setConfigKey("event");
//		entity.setConfigValue(evenName);
//		this.query.add(entity);
//	}
//
//	/**
//	 * 获取所有的一键呼救事件
//	 * @return
//	 */
//	public List<Config> getSoSEventList()
//	{
//		return this.query.where("configKey='event'").toList();
//	}
//
//}
