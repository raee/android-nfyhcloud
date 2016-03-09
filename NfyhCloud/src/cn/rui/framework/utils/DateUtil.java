package cn.rui.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 通用日期处理
 * 
 * @author ChenRui
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public static final int YEAR = 0;

	public static final int MONTH = 1;

	public static final int DAY = 2;

	public static final int HOUR = 3;

	public static final int MINUTE = 4;

	public static final int SECOND = 5;

	/**
	 * 获取当前时间
	 * 
	 * @param format
	 *            格式化：yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDateString(String format) {
		Calendar calendar = getCalendar();
		return getDateString(format, calendar);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param format
	 *            格式化：yyyy-MM-dd
	 * @return
	 */
	public static String getDateString(String format, Calendar calendar) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(calendar.getTime());
	}

	/**
	 * 获取当前日历对象
	 * 
	 * @return 当前时间的日历对象
	 */
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar;
	}

	/**
	 * 获取当前时间，以数组格式保存[y,M,d,h,m,s]
	 * 
	 * @return
	 */
	public static int[] getCurrentDate() {
		Calendar calendar = getCalendar();
		return getCurrentDate(calendar);
	}

	/**
	 * 获取当前时间，以数组格式保存[y,M,d,h,m,s]
	 * 
	 * @param calendar
	 *            日历
	 * @return
	 */
	public static int[] getCurrentDate(Calendar calendar) {
		int[] result = new int[] { calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), };
		return result;
	}

	/**
	 * 日期距离现在还有多少时间
	 * 
	 * @return
	 */
	public static int[] getDateSpan(Date date) {
		try {
			Calendar calendar = getCalendar(); // 目标时间
			calendar.setTime(date);
			long start = getCalendar().getTimeInMillis();
			long end = calendar.getTimeInMillis();
			long between = (end - start) / 1000; // 换算成秒
			int day = (int) between / (24 * 3600); // 天
			int hour = (int) between % (24 * 3600) / 3600; // 时
			int minute = (int) between % 3600 / 60; // 分
			int second = (int) between % 60 / 60; // 秒
			return new int[] { day, hour, minute, second };
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期距离现在还有多少时间
	 * 
	 * @param date
	 * @return 2天30分20秒
	 */
	public static String getDateSpanString(Date date) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.roll(Calendar.DAY_OF_MONTH, -1);
		int lastMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int[] dates = getDateSpan(date);
		if (dates == null)
			return "";
		StringBuilder sb = new StringBuilder();
		if (dates[0] < 0)// 目标时间小于当前时间为过时时间
		{
			return "过时";
		} else if (dates[0] > lastMonth) // 大于30的变成月
		{
			sb.append((dates[0] / lastMonth) + "月");
		} else if (dates[0] != 0) {
			sb.append(dates[0] + "天");
		}
		if (dates[1] != 0) {
			sb.append(dates[1] + "时");
		}
		if (dates[2] != 0) {
			sb.append(dates[2] + "分");
		}
		if (dates[3] != 0) {
			sb.append(dates[3] + "秒");
		}
		return sb.toString();
	}

	/**
	 * 字符串转日期
	 * 
	 * @param str
	 *            字符串
	 * @return 不成功返回空
	 */
	public static Date parser(String str) {
		return parser("yyyy-MM-dd hh:mm:ss", str);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param str
	 *            字符串
	 * @return 不成功返回空
	 */
	public static Date parser(String format, String str) {
		try {
			if (str == null || str.length() <= 0) {
				return null;
			}
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			Date date = dateformat.parse(str);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 是否过期
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isOutdate(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		Calendar curCalendar = getCalendar();
		return calendar.getTime().after(curCalendar.getTime()); // 在当前日期之前则为过期
	}
}
