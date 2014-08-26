//package com.yixin.nfyh.cloud.data;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class ADayMultiTime implements MultiTime{
//	private List<String> litTime = null;
//	public ADayMultiTime(int freq, String performSchedule){
//		litTime = createMultiTiem(freq,performSchedule);
//	}
//	@Override
//	public List<String> createMultiTiem(int freq,String performSchedule) {
//		if(performSchedule == null){
//			return null;
//		}
//		List<String> litTmpTime = new ArrayList<String>();
//		if(freq > 1 && performSchedule.contains("-")){
//			String[] multiStr = performSchedule.split("-");
//			for (String string : multiStr) {
//				litTmpTime.add(stringTransform(string));
//				return litTmpTime;
//			}
//		}else if(performSchedule.contains(":")){
//			litTmpTime.add((getToDayStr() + " " + performSchedule));
//		}
//		return litTmpTime;
//	}
//	
//	@Override
//	public String toString() {
//		
//		return super.toString();
//	}
//	/**
//	 * 把string转换成固定格式的时间
//	 */
//	private String stringTransform(String res){
//		if(Integer.parseInt(res) < 10){
//			res = " 0" + res;
//		}
//		res = res + ":00";
//		return getToDayStr() + res;
//	}
//	/**
//	 * 获得今天的时间
//	 */
//	private String getToDayStr(){
//		Date mDate = new Date(System.currentTimeMillis());
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		return format.format(mDate);
//	}
//	
//}
