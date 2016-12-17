package com.trip.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeUtil {

	public static long MINUTE_MILLISECONDS = 1000 * 60;
	public static long SCAN_LOG_INTERVAL = 1000 * 60;
	public static long HOUR_MILLISECONDS = MINUTE_MILLISECONDS * 60;
	public static long DAY_MILLISECONDS = HOUR_MILLISECONDS * 24;
	private static DateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date nextInterval(long interval) {
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		while (c.getTimeInMillis() <= new Date().getTime()) {
			c.setTimeInMillis(c.getTimeInMillis() + interval);
		}
		return c.getTime();
	}

	public static Date beginOfTheWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return beginOfTheDay(c.getTime());
	}

	public static String getYearWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		return c.get(Calendar.YEAR) + "-" + c.get(Calendar.WEEK_OF_YEAR);
	}

	public static String getYearMonth(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1);
	}

	public static boolean isSafe(Date end) {
		return (new Date().getTime() - end.getTime()) >= 2 * 60 * 1000;
	}

	public static int getHour24() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static Date parseMysql(String s) {
		try {
			DateFormat fmtTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return fmtTemp.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 获取时间的时间数值型
	 * @param s
	 * @return
	 */
	public static long getTime(String s){
		return parseMysql(s).getTime();
	}
	
	public static boolean isWeekend(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			return true;
		else
			return false;
	}

	public static String sqlDateTime(Date d) {
		return fmt1.format(d);
	}

	public static Date nextDay(Date date) {
		return new Date(beginOfTheDay(date).getTime() + DAY_MILLISECONDS);
	}
	
	public static Date nextHour(Date date) {
		return new Date(date.getTime() + HOUR_MILLISECONDS);
	}

	public static Date nextDay(Date date, int n) {
		return new Date(beginOfTheDay(date).getTime() + DAY_MILLISECONDS * n);
	}

	public static Date plusDay(Date date, int n) {
		return new Date(date.getTime() + DAY_MILLISECONDS * n);
	}

	public static Date lastDay(Date date, int days) {
		return new Date(date.getTime() - days * DAY_MILLISECONDS);
	}

	public static Date trimToHour(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date nextTime(Date date, int minute) {
		return new Date(date.getTime() + minute * MINUTE_MILLISECONDS);
	}

	public static Date addMinutes(Date date, int m) {
		return new Date(date.getTime() + m * MINUTE_MILLISECONDS);
	}

	public static Date addDays(Date date, int m) {
		return new Date(date.getTime() + m * DAY_MILLISECONDS);
	}
	
	public static Date timeStampToDate(long ts){
		return new Date(ts * 1000);
	}

	public static Date addMonth(Date date, int m) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.MONTH, m);
		return c.getTime();
	}

	public static Date addSeconds(Date date, int m) {
		return new Date(date.getTime() + m * 1000);
	}

	public static long getMilliInMonth(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH) * DAY_MILLISECONDS;
	}

	public static Date beginOfTheMonth() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static Date beginOfTheDay(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date beginOfTheHour(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date beginOfThisHour() {
		return beginOfTheHour(new Date());
	}

	public static Date beginOfLastHour() {
		return beginOfTheHour(new Date(new Date().getTime() - HOUR_MILLISECONDS));
	}
	
	public static Date beginOfLastHour(Date time) {
		return beginOfTheHour(new Date(time.getTime() + HOUR_MILLISECONDS));
	}

	public static Date endOfTheDay(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date standardTime(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int minute = c.get(Calendar.MINUTE);
		int gewei = minute % 15;
		int shiwei = minute / 15 * 15;
		if (gewei >= 7) {
			gewei = 15;
		} else {
			gewei = 0;
		}
		minute = gewei + shiwei;
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}
	
	public static Date alignTime(Date date, int min){
		if(min <= 0){
			return date;
		}
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int minute = c.get(Calendar.MINUTE);
		int gewei = minute % min;
		int shiwei = minute / min * min;
		if (gewei >= min) {
			gewei = min;
		} else {
			gewei = 0;
		}
		minute = gewei + shiwei;
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static Date standardTime(Date date, int min) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int minute = c.get(Calendar.MINUTE);
		int gewei = minute % min;
		int shiwei = minute / min * min;
		if (gewei >= min / 2) {
			gewei = min;
		} else {
			gewei = 0;
		}
		minute = gewei + shiwei;
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static boolean isStandardTime(Date date) {

		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int minute = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);
		if (sec == 0 && minute % 15 == 0) {
			return true;
		} else {
			return false;
		}

	}

	public static Date standardTime() {
		return standardTime(new Date());

	}

	public static Date standardTime(long interval) {
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		while (Math.abs(c.getTimeInMillis() - new Date().getTime()) > interval / 2d) {
			c.setTimeInMillis(c.getTimeInMillis() + interval);
		}
		return c.getTime();
	}

	public static String getTimestamp() {
		return getTimestamp(new Date());
	}

	public static String getTimestamp(Date date) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		return fmt.format(date);
	}

	public static Date toDate(String str) {
		if(null == str || str.trim().isEmpty()){
			return null;
		}
		try {
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return fmt.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}
	public static String formatDateString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date toDate(String dateString, String format) {
		try {
			DateFormat fmt = new SimpleDateFormat(format);
			return fmt.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * @param date
	 * @return
	 */
	public static String toDateString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

	public static String getYear(Date date) {
		return new SimpleDateFormat("yyyy").format(date);
	}

	public static String getMonth(Date date) {
		return new SimpleDateFormat("MM").format(date);
	}

	public static String getDay(Date date) {
		return new SimpleDateFormat("dd").format(date);
	}

	public static int getMonthCha(Date start, Date end) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);
		int m1 = c1.get(Calendar.MONTH);
		int m2 = c2.get(Calendar.MONTH);
		int monthCha = (y2 - y1) * 12 + m2 - m1;
		return monthCha;
	}

	public static Date nextMonth(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}

	public static String toTimeStamp(Date date) {
		if(null == date){
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	/**
	 * 获取友好显示的日期
	 * @return
	 */
	public static String curView() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss").format(date);
	}	
	public static String getCurDateTime(){
		return toTimeStamp(new Date());
	}
	public static String getHourMinuteSecondStamp(Date date) {
		DateFormat fmt = new SimpleDateFormat("HHmmss");
		return fmt.format(date);
	}
	public static String getMillisecondStamp(Date date) {
		DateFormat fmt = new SimpleDateFormat("HHmmssSSS");
		return fmt.format(date);
	}

	public static String getHourMinuteStamp(Date date) {
		DateFormat fmt = new SimpleDateFormat("HH:mm");
		return fmt.format(date);
	}

	public static Date getDate(Date date2, Date time2) {
		Calendar date = new GregorianCalendar();
		date.setTime(date2);
		Calendar time = new GregorianCalendar();
		time.setTime(time2);
		time.set(Calendar.YEAR, date.get(Calendar.YEAR));
		time.set(Calendar.MONTH, date.get(Calendar.MONTH));
		time.set(Calendar.DATE, date.get(Calendar.DATE));
		return time.getTime();
	}

	public static String formatNianYueRi(Date date) {
		DateFormat fmt = new SimpleDateFormat("yy年M月d日");
		return fmt.format(date);
	}
	
	 public static String getDayOfWeek(Date date) {  
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return "周日";
		case 2:
			return "周一";
		case 3:
			return "周二";
		case 4:
			return "周三";
		case 5:
			return "周四";
		case 6:
			return "周五";
		case 7:
			return "周六";
		default:
			return "未知日期";
		}
	}  

	public static String formatMysqlDate(Date date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		return fmt.format(date);
	}

	public static Date yesterday() {
		return new Date(new Date().getTime() - DAY_MILLISECONDS);
	}

	public static Date pastday(int n) {
		return new Date(new Date().getTime() - n * DAY_MILLISECONDS);
	}

	public static Date pastday(Date d, int n) {
		return new Date(d.getTime() - n * DAY_MILLISECONDS);
	}

	public static Date last5WeekStart() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(TimeUtil.beginOfTheDay(pastday(30)));
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}

	/**
	 * @param date
	 * @return 统计log库日期
	 */
	public static String toStatisticDate(Date date) {
		DateFormat fmt = new SimpleDateFormat("MMdd");
		return fmt.format(date);
	}

	/**
	 * @param prefix
	 *            with out _
	 * @param date
	 * @return
	 */
	public static String toStatisticTable(String prefix, Date date) {
		DateFormat fmt = new SimpleDateFormat("MMdd");
		return prefix + "_" + fmt.format(date);
	}

	public static String getStatisticDateFull(Date date) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date);
	}

	public static Date fromStatisticDateFull(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date fromMysqlDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date fromMysqlDateTime(String date) {		
		try {
			return fmt1.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


	public static long getDaysCa(Date d1, Date d2) {
		long quot = Math.abs(TimeUtil.beginOfTheDay(d1).getTime()
				- TimeUtil.beginOfTheDay(d2).getTime());
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}

	public static long getMinuteCa(Date d1, Date d2) {
		long quot = Math.abs(d1.getTime() - d2.getTime());
		quot = quot / 1000 / 60;
		return quot;
	}
	
	public static long getSecondCa(Date d1, Date d2) {
		long quot = Math.abs(d1.getTime() - d2.getTime());
		quot = quot / 1000;
		return quot;
	}
	/**
	 * 获取两个时间的秒间隔差
	 * @param d1
	 * @param d2
	 * @param format
	 * @return
	 */
	public static long getSecondCa(String d1,String d2,String format) {
		return getSecondCa(toDate(d1,format),toDate(d2,format));
	}
	/**
	 * 增加相应的秒间隔
	 * @param d1
	 * @param timeSpan
	 * @param format
	 * @return
	 */
	public static String addSecondCa(String d1,int timeSpan,String format) {
		Date d1Date = toDate(d1,format);
		long timeMili = d1Date.getTime()+timeSpan*1000;
		d1Date.setTime(timeMili);
		return formatDateString(d1Date,format);
	}
	/**
	 * 以当前时间为准，前推指定的天数
	 * @param day
	 * @return
	 */
	public static String nextDayFromCur(int day){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());   
		//cd.add(Calendar.DATE, -7);//前7天       
		calendar.add(Calendar.DATE, day);//加一天    
		return toTimeStamp(calendar.getTime());
	}
	/**
	 * 返回当天的数据格式,格式为2013-08-09 00:00:00
	 * @return
	 */
	public static String getCurDayZero(){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());   
		return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime());		
	}
	/**
	 * 获取前一天的时候，格式为2013-08-09 00:00:00
	 * @return
	 */
	public static String getPreDay(){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());   
		calendar.add(Calendar.DATE, -1);//加一天    	
		return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime());
	}
	/**
	 * 获取前一天的时候，格式为2013-08-09 00:00:00
	 * @return
	 */
	public static String getPreDay(int day){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());   
		calendar.add(Calendar.DATE, -1*day);//加一天    	
		return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendar.getTime());
	}	
	public static boolean sameDay(Date date1, Date date2) {
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.setTime(date1);
		c2.setTime(date2);
		return c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
	}

	public static boolean sameWeek(Date date1, Date date2) {
		return getYearWeek(date1).equals(getYearWeek(date2));
	}

	public static boolean sameTimeInOneMinute(Date date1, Date date2) {
		return Math.abs(getMinuteCa(date1, date2)) <= 1;
	}

	public static Date mid(Date date1, Date date2) {
		return new Date((date1.getTime() + date2.getTime()) / 2);
	}

	public static Date getPrevHour(Date date) {
		return new Date(date.getTime() - 60 * MINUTE_MILLISECONDS);
	}

	public static Date nextTime(int hour) {
		Calendar c = new GregorianCalendar();
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if (hour <= nowHour) {
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		c.set(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public static Date getFirstDayOfPastWek(int n) {
		return TimeUtil.beginOfTheDay(getFirstDayOfWeek(pastday(n * 7)));
	}

	public static Date getLastDayOfPastWek(int n) {
		return TimeUtil.endOfTheDay(getLastDayOfWeek(pastday(n * 7)));
	}

	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Monday
		return c.getTime();
	}

	public static boolean futureTime(Date date) {
		Date curTime = new Date();
		return date.after(curTime);
	}

	public static boolean inRange(Date date, Date first, Date second) {
		return date.after(first) && date.before(second);
	}

	public static Date tomorrow() {
		return new Date(new Date().getTime() + TimeUtil.DAY_MILLISECONDS);
	}
	/**
	 * 判断两个日期是否相等
	 * @param d1
	 * @param d2
	 * @param format
	 * @return
	 */
	public static boolean isEqual(String d1,String d2,String format) {
		Date d1Date = toDate(d1,format);
		Date d2Date = toDate(d2,format);
		if(d1Date.getTime()==d2Date.getTime())
			return true;
		return false;
	}
	/**
	 * 判断D1是否大于D2
	 * @param d1
	 * @param d2
	 * @param format
	 * @return
	 */
	public static boolean isThan(String d1,String d2,String format){
		Date d1Date = toDate(d1,format);
		Date d2Date = toDate(d2,format);
		if(d1Date.getTime()>d2Date.getTime())
			return true;
		return false;
	}
	/**
	 * d1是否小于d2
	 * @param d1
	 * @param d2
	 * @param format
	 * @return
	 */
	public static boolean isLessThan(String d1,String d2,String format){
		Date d1Date = toDate(d1,format);
		Date d2Date = toDate(d2,format);
		if(d1Date.getTime()<d2Date.getTime())
			return true;
		return false;
	}
	
	/**
	 * 获取两个字符串日期的间隔时间的形式化表达：
	 * <p>xx天xx小时xx分钟xx秒
	 * @param start_time
	 * @param end_time
	 * @return
	 */
	public static String getLastTimestamp(String start_time, String end_time) {
		Date start = toDate(start_time, "yyyy-MM-dd HH:mm");
		Date end = toDate(end_time, "yyyy-MM-dd HH:mm");
		return getLastTimestamp(end.getTime() - start.getTime());
	}
	
	/**
	 * 获取时间戳所代表的间隔时间的形式化表达：
	 * <p>xx天xx小时xx分钟xx秒
	 * @param stamp
	 * @return
	 */
	public static String getLastTimestamp(long stamp) {
		int day = (int)(stamp / (86400 * 1000L));
		int hour = (int)(stamp / (3600 * 1000L) % 24);
		int minute = (int)(stamp / (60 * 1000L) % 60);
		int second = (int)(stamp / 1000L % 60);
		String s = "";
		if(day > 0) s += day + "天";
		if(hour > 0) s += hour + "小时";
		if(minute > 0) s += minute + "分钟";
		if(second > 0) s += second + "秒";
		return s.length() == 0 ? "0" : s;
	}
	public static void main(String[] args) {
		String a = TimeUtil.getPreDay();
		System.out.println(a);
	}
	
	public static boolean isTimeout(Date now,Date dbTime,int timeOut) {
		StringBuilder buff = new StringBuilder(100);
		buff.append("[tools][得到的最后时间]");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(now);
		c2.setTime(dbTime);
//		buff.append("数据库原时间=<"+c2.getTime().toLocaleString()).append(">,");
//		//相差时间，20分钟 ，如果数据库时间加了15分钟与当前时间比较 ，大于或等于当前时间，表示收集数据正常(c2>=c1)
//		c2.add(GregorianCalendar.SECOND, timeOut);
//		buff.append("当前时间 <").append(c1.getTime().toLocaleString()).append(">,数据库时间 =<").append(c2.getTime().toLocaleString()).append(">");
//		
//		System.out.println(buff.toString());
		int result = c1.compareTo(c2);
		if (result == 0){
			return false;
		}else if (result < 0){
			return false;
		}else{
			return true;
		}
	}
}
