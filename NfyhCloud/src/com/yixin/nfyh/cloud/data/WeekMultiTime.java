//package com.yixin.nfyh.cloud.data;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class WeekMultiTime implements MultiTime {
//	private List<String> litTime = null;
//
//	public WeekMultiTime(int freq, String performSchedule) {
//		litTime = createMultiTiem(freq, performSchedule);
//	}
//
//	@Override
//	public List<String> createMultiTiem(int freq, String performSchedule) {
//		if (performSchedule == null) {
//			return null;
//		}
//		String perform = "";
//		List<String> litTmpTime = new ArrayList<String>();
//		if (freq == 1) {
//			perform = performSchedule.replace("周", "");
//			perform = perform.replace("、", "");
//			for (int i = 0; i < perform.length(); i++) {
//				char c = perform.charAt(i);
//				litTmpTime.add(transformDate(c));
//			}
//		}
//		return litTmpTime;
//	}
//
//	@Override
//	public String toString() {
//		return super.toString();
//	}
//
//	/**
//	 * 转换成date
//	 * 
//	 * @param c
//	 * @return
//	 */
//	private String transformDate(char ch) {
//		String dateStr = "";
//		if (Week.SUN.dayOfWeek == ch) {
//			dateStr = getDayOfWeek(Week.SUN.weekIndex);
//		}else if(Week.ONE.dayOfWeek == ch){
//			dateStr = getDayOfWeek(Week.ONE.weekIndex);
//		}else if(Week.TWO.dayOfWeek == ch){
//			dateStr = getDayOfWeek(Week.TWO.weekIndex);
//		}else if(Week.THREE.dayOfWeek == ch){
//			dateStr = getDayOfWeek(Week.THREE.weekIndex);
//		}else if(Week.FOUR.dayOfWeek == ch){
//			dateStr = getDayOfWeek(Week.FOUR.weekIndex);
//		}else if(Week.SIX.dayOfWeek == ch){
//			dateStr = getDayOfWeek(Week.SIX.weekIndex);
//		}
//		return dateStr;
//	}
//
//	/**
//	 * 得到指定的星期几
//	 * 
//	 * @return
//	 */
//	private String getDayOfWeek(int toWeed) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		int currentWeek = calendar.DAY_OF_WEEK;
//		int count = toWeed - currentWeek;
//		Calendar cc = Calendar.getInstance();
//		cc.add(cc.DAY_OF_WEEK, count);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		return dateFormat.format(cc.getTime()) + " 00:00:00";
//	}
//
//	/**
//	 * 一个星期的识别
//	 * 
//	 * @author zhulin
//	 * 
//	 */
//	enum Week {
//		SUN(0, '日'), ONE(1, '一'), TWO(2, '二'), THREE(3, '三'), FOUR(4, '四'), FIVE(
//				5, '五'), SIX(6, '六');
//		private char dayOfWeek;
//		private int weekIndex;
//
//		Week(int weekIndex, char dayOfWeek) {
//			this.dayOfWeek = dayOfWeek;
//			this.weekIndex = weekIndex;
//		}
//
//	}
//
//}
