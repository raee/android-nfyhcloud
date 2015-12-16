package com.yixin.nfyh.cloud.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 体征类型数据提供
 * 
 * @author ChenRui
 * 
 */
public class SignTypeProvider
{
	
	public List<GanyuInfo> getAll()
	{
		List<GanyuInfo> datas = new ArrayList<GanyuInfo>();
		datas.add(new GanyuInfo("1000", "血压"));
		datas.add(new GanyuInfo("2000", "血糖"));
		datas.add(new GanyuInfo("3000", "血氧"));
		datas.add(new GanyuInfo("4000", "体温"));
		datas.add(new GanyuInfo("5000", "呼吸峰速"));
		datas.add(new GanyuInfo("6000", "心电"));
		return datas;
	}
	
	public GanyuInfo getSignById(String typeId)
	{
		List<GanyuInfo> datas = getAll();
		
		for (GanyuInfo ganyuInfo : datas)
		{
			if (ganyuInfo.getSignType().equals(typeId)) { return ganyuInfo; }
		}
		
		return null;
	}
	
	public GanyuInfo getSignByName(String name)
	{
		List<GanyuInfo> datas = getAll();
		
		for (GanyuInfo ganyuInfo : datas)
		{
			if (ganyuInfo.getSignTypeName().equals(name)) { return ganyuInfo; }
		}
		
		return null;
	}
	
}
