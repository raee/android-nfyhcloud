package com.yixin.nfyh.cloud.alarm;

import java.util.List;

import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 闹钟控制器接口
 * 
 * @author MrChenrui
 * 
 */
public interface IAlramControl
{
	/**
	 * 设置一个闹钟
	 */
	void setAlarm(Clocks model);
	
	/**
	 * 执行了一个闹钟
	 * 
	 * @param model
	 * @return
	 */
	void excuteAlarm(Clocks model);
	
	/**
	 * 执行了一个闹钟
	 * 
	 * @param model
	 * @return
	 */
	void deleteAlarm(Clocks model);
	
	/**
	 * 取消闹钟执行，从系统闹钟中删除
	 * 
	 * @param model
	 */
	void cancleAlarm(Clocks model);
	
	List<Clocks> getAlarms();
}
