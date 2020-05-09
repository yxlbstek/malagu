package vip.malagu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

	/**
	 * 获取当天时间  Sat Apr 21 00:00:00 CST 2018
	 * @return
	 */
	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 根据参数获取当天间隔的天数的时间
	 * @return
	 */
	public static Date getToday(int increment) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, increment);
		return calendar.getTime();
	}
	
	/**
	 * 根据参数获取  参数date 间隔的月份 increment 的时间
	 * @return
	 */
	public static Date getDateIntervalMonth(Date date, int increment) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, increment);
		return calendar.getTime();
	}
	
	/**
	 * 根据参数获取  参数date 间隔的天数 increment 的时间
	 * @return
	 */
	public static Date getDate(Date date, int increment) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, increment);
		return calendar.getTime();
	}
	
	/**
	 * 根据参数获取  参数date 间隔的小时数 hour 的时间
	 * @return
	 */
	public static Date getDateIntervalHours(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long millis = calendar.getTimeInMillis() + hour * 60 * 60 * 1000;
		return new Date(millis);
	}
	
	/**
	 * 根据参数获取  参数date 间隔的分钟数 minute 的时间
	 * @return
	 */
	public static Date getDateIntervalMinutes(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long millis = calendar.getTimeInMillis() + minute * 60 * 1000;
		return new Date(millis);
	}
	
	/**
	 * 根据参数获取  参数date 间隔的秒数 mill 的时间
	 * @return
	 */
	public static Date getDateIntervalSeconds(Date date, int mill) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long millis = calendar.getTimeInMillis() + mill * 1000;
		return new Date(millis);
	}
	
	/**
	 * 返回清空时、分、秒的 date
	 * @param date
	 * @return
	 */
	public static Date clearTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取date开始时间   Sat Apr 21 00:00:00 CST 2018
	 * @param date
	 * @return
	 */
	public static Date startTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取date结束时间   Sat Apr 21 23:59:59 CST 2018
	 * @param date
	 * @return
	 */
	public static Date endTime(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	/**
	 * 获取date的年份 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取date月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取date天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取date小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取date分钟
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取date秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取date毫秒
	 * @param date
	 * @return
	 */
	public static int getMillisecond(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * 将 Date 按 patten 格式转换成 String  "yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String dateToString(Date date, String patten) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten, Locale.CHINA);
		return dateFormat.format(date);
	}
	
	/**
	 * 将 毫秒 millis 按 patten 格式转换成 String  "yyyy-MM-dd HH:mm:ss"
	 * @param millis
	 * @param patten
	 * @return
	 */
	public static String timeMillisToString(long millis, String patten) {
		Date date = new Date();
		date.setTime(millis);
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten, Locale.CHINA);
		return dateFormat.format(date);
	}

	/**
	 * 将 String 按 patten 格式转换成 Date  "yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @param patten
	 * @return
	 */
	public static Date stringToDate(String source, String patten) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten, Locale.CHINA);
		Date date = null;
		try {
			date = dateFormat.parse(source);
		} catch (ParseException e) {
			
		}
		return date;
	}
	
	/**
	 * 判断 firstDate 是否 大于等于 lastDate, 大于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean ge(Date firstDate, Date lastDate) {
		if (!firstDate.before(lastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断 firstDate 是否 大于 lastDate, 大于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean gt(Date firstDate, Date lastDate) {
		if (firstDate.after(lastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断 firstDate 是否 小于等于 lastDate, 小于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean le(Date firstDate, Date lastDate) {
		if (!firstDate.after(lastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断 firstDate 是否 小于 lastDate, 小于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean lt(Date firstDate, Date lastDate) {
		if (firstDate.before(lastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断 firstDate 是否等于 lastDate
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean eq(Date firstDate, Date lastDate) {
		if (org.apache.commons.lang.time.DateUtils.isSameDay(firstDate, lastDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取 firstDate 与 lastDate 间隔的天数
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static long intervalDay(Date firstDate, Date lastDate) {
		return (firstDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取 firstDate 与 lastDate 间隔分钟数
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static long intervalMinute(Date firstDate, Date lastDate) {
		return (firstDate.getTime() - lastDate.getTime()) / (1000 * 60);
	}
	
	/**
	 * 获取 年份year 中 月份month 的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DATE);
	}

}
