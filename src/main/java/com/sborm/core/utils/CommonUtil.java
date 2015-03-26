package com.sborm.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 通用工具类
 * 
 * @author fengli
 * @date 2014-7-16 下午11:28:03 
 */
public class CommonUtil {

	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getCurrentYear() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(new java.util.Date()));
	}

	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getCurrentMonth() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M");
		return Integer.parseInt(sdf.format(new java.util.Date()));
	}

	/**
	 * 获取当前天
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取当前时间，格式：2011-11-05 00:00:00
	 * @return
	 */
	public static String getCurrentDateTime() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取时间的格式化字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateTimeString(Date date, String format) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 通过字符串转换为时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parseDateTime(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算2个日期天数差，d1-d2
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int diffDayOfDate(Date d1, Date d2) {
		if ((d1 == null) || (d2 == null))
			return 0;

		Calendar cal = Calendar.getInstance();

		int zoneoffset = cal.get(Calendar.ZONE_OFFSET);
		int dstoffset = cal.get(Calendar.DST_OFFSET);

		long dl1 = d1.getTime() + zoneoffset + dstoffset;
		long dl2 = d2.getTime() + zoneoffset + dstoffset;

		int intDaysFirst = (int) (dl1 / (60 * 60 * 1000 * 24));
		int intDaysSecond = (int) (dl2 / (60 * 60 * 1000 * 24));

		return intDaysFirst - intDaysSecond;
	}

	/**
	 * 根据指定的格式判断字符串是否是日期格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static boolean isDate(String date, String format) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(date);
			return true;
		} catch (ParseException ex) {
			return false;
		}

	}

	/**
	 * 判断字符串是否是数字，包括小数
	 * @param strNumber
	 * @return
	 */
	public static boolean isNumber(String strNumber) {
		boolean bolResult = false;
		try {
			Double.parseDouble(strNumber);
			bolResult = true;
		} catch (NumberFormatException ex) {
			bolResult = false;
		}
		return bolResult;
	}

	/**
	 * 获取N天后的日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTimeInMillis(date.getTime());
		gdate.add(GregorianCalendar.DAY_OF_MONTH, n);
		return gdate.getTime();
	}

	/**
	 * 获取N个月后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTime(date);
		gdate.add(GregorianCalendar.MONTH, n);
		return gdate.getTime();
	}

	/**
	 * 获取N年后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static java.util.Date addYear(Date date, int n) {
		GregorianCalendar gdate = new GregorianCalendar();
		gdate.setTime(date);
		gdate.add(GregorianCalendar.YEAR, n);
		return gdate.getTime();
	}

	/**
	 * 获取一个月份的天数
	 * @param month
	 * @return
	 */
	public static int getDayCountOfMonth(int month) {
		int a = 0;
		switch (month) {
		case 1:
			a = 31;
			break;
		case 2:
			a = 28;
			break;
		case 3:
			a = 31;
			break;
		case 4:
			a = 30;
			break;
		case 5:
			a = 31;
			break;
		case 6:
			a = 30;
			break;
		case 7:
			a = 31;
			break;
		case 8:
			a = 31;
			break;
		case 9:
			a = 30;
			break;
		case 10:
			a = 31;
			break;
		case 11:
			a = 30;
			break;
		case 12:
			a = 31;
			break;
		}
		return a;
	}
}
