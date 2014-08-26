//package com.yixin.nfyh.cloud.data;
//
//
//public class MultiTimeCreateFactory {
//	private int freq;
//	private String freqUnit;
//	private String performSchedule;
//	/**
//	 * 
//	 * @param freq 执行频率的次数部分
//	 * @param freqUnit 频率间隔单位
//	 * @param performSchedule 预定执行时间 对3/日的时间表为8-12-18，对应的是8:00,12:00,18:00时间
//	 */
//	public MultiTimeCreateFactory(int freq,String freqUnit,String performSchedule){
//		this.freq = freq;
//		this.freqUnit = freqUnit;
//		this.performSchedule = performSchedule;
//	}
//	/**
//	 * 创建一个时间
//	 * @return
//	 */
//	public MultiTime createMultiTime(){
//		MultiTime multiTime = null; 
//		if(TimeFreq.DAY.equals(freqUnit)){
//			multiTime = new ADayMultiTime(freq,performSchedule);
//		}else if(TimeFreq.WEEK.equals(freqUnit)){
//			multiTime = new WeekMultiTime(freq,performSchedule);
//		}else if(TimeFreq.YEAR.equals(freqUnit)){
//			multiTime = new OneYearMultiTime(freq,performSchedule);
//		}
//		return multiTime;
//	}
//	
//}
//class TimeFreq{
//	public static String DAY = "日";
//	public static String WEEK = "周";
//	public static String YEAR = "年";
//}