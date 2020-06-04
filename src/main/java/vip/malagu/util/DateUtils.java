package vip.malagu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 日期工具类
 * @author Lynn -- 2020年5月21日 下午5:11:28
 */
public final class DateUtils {
	
	private DateUtils() {}

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
	 * @throws ParseException 
	 */
	public static Date stringToDate(String source, String patten) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten, Locale.CHINA);
		return dateFormat.parse(source);
	}
	
	/**
	 * 判断 firstDate 是否 大于等于 lastDate, 大于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean ge(Date firstDate, Date lastDate) {
		return !firstDate.before(lastDate);
	}
	
	/**
	 * 判断 firstDate 是否 大于 lastDate, 大于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean gt(Date firstDate, Date lastDate) {
		return firstDate.after(lastDate);
	}
	
	/**
	 * 判断 firstDate 是否 小于等于 lastDate, 小于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean le(Date firstDate, Date lastDate) {
		return !firstDate.after(lastDate);
	}
	
	/**
	 * 判断 firstDate 是否 小于 lastDate, 小于等于 返回 true, 反之 false
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean lt(Date firstDate, Date lastDate) {
		return firstDate.before(lastDate);
	}
	
	/**
	 * 判断 firstDate 是否等于 lastDate
	 * @param firstDate
	 * @param lastDate
	 * @return
	 */
	public static boolean eq(Date firstDate, Date lastDate) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(firstDate, lastDate);
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
	
	/**  
     * 获取year的 第一天日期  
     * @param year 年份  
     * @return Date  
     */  
    public static Date getYearFirstDate(int year) {   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        return calendar.getTime();
    }   
       
    /**  
     * 获取year的 最后一天日期  
     * @param year 年份  
     * @return Date  
     */  
    public static Date getYearLastDate(int year) {   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        calendar.roll(Calendar.DAY_OF_YEAR, -1);   
        return calendar.getTime();    
    } 
    
    /**  
     * 获取当前年份  指定 month 第一天日期  
     * @param year 月份  
     * @return Date  
     */  
    public static Date getCurrentYearOfMonthFirstDate(int month) {   
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }   
       
    /**  
     * 获取当前年份  指定 month 最后一天日期  
     * @param year 月份  
     * @return Date  
     */  
    public static Date getCurrentYearOfMonthLastDate(int month) {   
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();    
    }
    
    /**  
     * 获取year 指定 month 第一天日期  
     * @param year 年份  
     * @param year 月份  
     * @return Date  
     */  
    public static Date getMonthFirstDate(int year, int month) {   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getTime();
    }   
       
    /**  
     * 获取year 指定 month 最后一天日期  
     * @param year 年份  
     * @param year 月份  
     * @return Date  
     */  
    public static Date getMonthLastDate(int year, int month) {   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);
        return calendar.getTime();    
    }
    
    /**
     * 获取 startDate 和 endDate 之间间隔的 所有日期 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static List<Date> getBetweenDates(Date startDate, Date endDate) {
    	return buildBetweenDates(startDate, endDate, "asc");
    }
    
    /**
     * 获取 startDate 和 endDate 之间间隔的 所有日期 （倒序）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static List<Date> getBetweenDatesOfDesc(Date startDate, Date endDate) {
    	return buildBetweenDates(startDate, endDate, "desc");
    }
 
    private static List<Date> buildBetweenDates(Date startDate, Date endDate, String sort) {
		List<Date> result = new ArrayList<>();
		Calendar tempDate = Calendar.getInstance();
		tempDate.setTime(startDate);
		while (startDate.getTime() <= endDate.getTime()) {
			result.add(tempDate.getTime());
			tempDate.add(Calendar.DAY_OF_YEAR, 1);
			startDate = tempDate.getTime();
		}
		if (sort.equals("desc")) {
			Collections.sort(result, new Comparator<Date>() {
				@Override
				public int compare(Date d1, Date d2) {
					return d2.compareTo(d1);
				}
			});
		}
		return result;
	}
    
    public static void main(String[] args) throws ParseException {
    	Date startDate = stringToDate("2020-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
    	Date endDate = stringToDate("2020-06-30 00:00:00", "yyyy-MM-dd HH:mm:ss");
    	List<Date> dates = getBetweenDatesOfDesc(startDate, endDate);
    	for (Date date : dates) {
    		System.out.println(dateToString(date, "yyyy-MM-dd HH:mm:ss"));
		}
    }
    
}
