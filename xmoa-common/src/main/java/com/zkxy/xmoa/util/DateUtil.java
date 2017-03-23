package com.zkxy.xmoa.util;


import com.zkxy.xmoa.common.constant.SearchTimeUnitEnum;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Description: 日期工具类</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author maojia
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class DateUtil {
	/**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     * 
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据格式 返回当前日期
     * 
     * @param format
     * @return
     */
    public static String getNowFormat(String format) {
        return format(new Date(), format);
    }


    /**
     * 使用预设格式格式化日期 【yyyy-MM-dd HH:mm:ss】
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用字符串格式 日期 获取Date
     * 
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用字符串日期格式 转换相应格式的 Date
     * 
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     * 
     * @param date
     *            日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式【yyyy-MM-dd HH:mm:ss】的字符串距离今天的天数
     * 
     * @param date
     *            日期字符串【yyyy-MM-dd HH:mm:ss】
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     * 
     * @param date
     *            日期字符串
     * @param format
     *            日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 判断日期【年月日】是不是今天
     *
     * @param date
     *            日期
     * @return
     */
    public static boolean isToday(Date date) { 
        Calendar c1 = Calendar.getInstance();              
        c1.setTime(date);                                 
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);   
        
        Calendar c2 = Calendar.getInstance();    
        c2.setTime(new Date());      
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);   
        
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;                               
    }         
    
    /**
     * 返回【加】或【减】后的日期
     * 
     * @param date
     *            日期
     * @param field 日历字段
     *       <li>Calendar.YEAR——年份<li/>
     *       <li>Calendar.MONTH——月份<li/>
     *       <li>Calendar.DATE——日期<li/>
     *       <li>Calendar.DAY_OF_MONTH——日期，和上面的字段完全相同<li/>
     *       <li>Calendar.HOUR——12小时制的小时数<li/>
     *       <li>Calendar.HOUR_OF_DAY——24小时制的小时数<li/>
     *       <li>Calendar.MINUTE——分钟<li/>
     *       <li>Calendar.SECOND——秒<li/>
     *       <li>Calendar.DAY_OF_WEEK——星期几<li/>
     * @param amount
     *            要添加到该字段的日期或时间的量
     *            <li>-1(当前日期减一天)<li/>
     *            <li>2<li/>
     * @return
     */
    public static final Date add(Date date,int field,int amount) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 格式化加减日期
     * 例子DateUtil.add("2012-12-12", "yyyy-MM-dd", Calendar.DATE, 12); 返回"2012-12-24"
     */
    public static String add(String dateStr, String format, int field, int amount) {
        Date date = parse(dateStr,format);
        Calendar calendar = GregorianCalendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        calendar.add(field, amount);
        Date retDate = calendar.getTime();
        return format(retDate,format);
    }

    /***
     *  传入指定日期 前一个小时的日期
     * <li>创建人：maojia</li>
     * <li>创建时间：2016年4月21日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param
     *     paramDate 日期
     * @return
     */
    public static Date anHourBefore(Date paramDate){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        return calendar.getTime();
    }

    /***
     *  传入指定日期  上一周的日期
     * <li>创建人：maojia</li>
     * <li>创建时间：2016年4月21日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param
     *     paramDate 日期
     * @return
     */
    public static Date anWeekBefore(Date paramDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        return calendar.getTime();
    }

    /***
     * 比对当前时间
     * @param date1 与 date2 相比: 【相等是 0 】 【大于date2是 > 0 】 【小于date2 < 0 】
     * @return
     */
    public static int compareDate(Date date1,Date date2){

    	 Calendar c1= Calendar.getInstance();
    	 Calendar c2 = Calendar.getInstance();
    	 c1.setTime(date1);
         c2.setTime(date2);
    	 int result = c1.compareTo(c2);

        return result;
    }

    /**
     * 判断时间是否在时间段内
     * @param date
     *            当前时间 yyyy-MM-dd HH:mm:ss
     * @param start
     *            开始时间 00:00:00
     * @param end
     *            结束时间 00:05:00
     * @return
     */
    public static boolean compareDate(Date date,Date start, Date end) {
        if ( date.getTime() >= start.getTime() && date.getTime() <= end.getTime() ) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否在时间段内
     * @param date
     *            当前时间 yyyy-MM-dd HH:mm:00
     * @param averageNumber
     *            【分钟】分割倍数 必须是整除数 例如：5

     * @return
     *    map:
     *     <li>startTime:  2016-04-10 02:30:00</li>
     *     <li>  endTime:  2016-04-10 02:30:00</li>
     */
    public static Map<String,String> timeAverage(Date date,int averageNumber){

        SimpleDateFormat nowDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        try {
            date = nowDateFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sf = new SimpleDateFormat("mm");
        int minute = Integer.parseInt(sf.format(date))%5;
        Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.MINUTE, -minute);

        Date beforeDate = calendar.getTime();
        calendar.setTime(beforeDate);
        calendar.add(Calendar.MINUTE, 5);

        Date afterDate = calendar.getTime();

        Map<String,String> returnMap = new HashMap<String,String>();
         returnMap.put("startTime", DateUtil.format(beforeDate,DateUtil.FORMAT_LONG) );
         returnMap.put("endTime", DateUtil.format(afterDate,DateUtil.FORMAT_LONG));

      return returnMap;
    }

    /***
     * 判断时间 时间区间内的范围
     * <li>创建人：maojia</li>
     * <li>创建时间：2016年4月21日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param
     *     date 传入的日期
     * @return
     *      没有返回null
     *      map:
     *     <li>startTime:  2016-04-12 00:00(年月时分)</li>
     *     <li>  endTime:  2016-04-12 00:05(年月时分)</li>
     */
    public static Map<String,String> timeInterval(Date date) throws ParseException {

        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        Date formDate=sf.parse(sf.format(date));

        for( int i = 0; i < timeIntervalArray.length; i++ ){
            String[] time = timeIntervalArray[i].split("-");
            Date start = sf.parse(time[0]);
            Date end = sf.parse(time[1]);
            if( start.getTime() <= formDate.getTime() && end.getTime() >= formDate.getTime() ){
                SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
                String startTime = temp.format(date) + time[0];
                String endTime;
                //结尾是0:00 需要加一天
                if( time[1].split(":")[1].equals("00") ){
                   endTime = temp.format( DateUtil.add(date,Calendar.DATE,1) ) + time[1];
                }else{
                   endTime = temp.format(date) + time[1];
                }
                Map<String,String> returnMap = new HashMap<String,String>();
                returnMap.put("startTime",startTime);
                returnMap.put("endTime",endTime);
               return returnMap;
            }
        }
        return null;
    }



    /***
     *  传入日期  获取指定多少之前的日期
     * <li>创建人：maojia</li>
     * <li>创建时间：2016年4月21日</li>
     * <li>创建目的：【】</li>
     * <li>修改目的：【修改人：，修改时间：】</li>
     * @param
     *     paramDate  指定的日期 <br/>
     *     lengthDate  指定前几天的长度<br/>
     *     beforOrAfter  &nbsp;1:表示后面几天的日期  &nbsp;&nbsp; -1: 表示之前几天的日期<br/>
     * @return
     */
    public static List<Date> beforeOrAfterDATE(Date paramDate,int lengthDate,int beforOrAfter){
        Calendar calendar = Calendar.getInstance();
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(paramDate);
        for(int dayNumber = 1 ;dayNumber <= lengthDate ; dayNumber++ ) {
            calendar.setTime(paramDate);
            calendar.add(Calendar.DAY_OF_MONTH,dayNumber*beforOrAfter);
            dateList.add(calendar.getTime());
        }
       return dateList;
    }


    /**
     * 获取某年第一天日期
     * @param date 日期
     * @return Date
     */
    public static Date getYearFirst(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(DateUtil.format(date,"yyyy")) );
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * @Description:
     *       获取某年最后一天日期
     * @param
     *      date 日期
     * @return
     *     Date 例如 2016-12-31 00:00:00
     */
    public static Date getYearLast(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(DateUtil.format(date,"yyyy")) );
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     *       获取某月的最后一天
     * @param
     *        year
     *
     * @param month
     *
     * @return:   yyyy-MM-dd
     * @throws
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);

        return format(cal.getTime(),FORMAT_SHORT);
    }

    /**
     * 将年补成当年的第1个月：即YYYY补成YYYY-01：如传参数2016则补成2016-01
     *                         YYYY-MM则原样返回
     * @param myDate YYYY或YYYY-MM格式
     * @return
     */
    public static String fillMonth2YearBegin(String myDate){
        return StringUtils.rightPad(myDate,7,"-01");
    }
    /**
     * 将年补成当年的最后一个月：即YYYY补成YYYY-12：如传参数2016则补成2016-12
     *                             YYYY-MM则原样返回
     * @param myDate  YYYY或YYYY-MM格式
     * @return
     */
    public static String fillMonth2YearEnd(String myDate){
       return StringUtils.rightPad(myDate,7,"-12");
    }
    /**
     * 将年月补成当月的第一天：即YYYY-MM补成YYYY-MM-01：如传参数2016-02则补成2016-02-01
     *                           YYYY-MM-DD则原样返回
     * @param myDate YYYY-MM或YYYY-MM-DD格式
     * @return
     */
    public static String fillDay2MonthBegin(String myDate){
        String result = fillMonth2YearBegin(myDate);
        return StringUtils.rightPad(result,10,"-01");
    }
    /**
     * 将年月补成当月的最后一天：即YYYY-MM补成YYYY-MM-28(29,30,31)：如传参数2016-02则补成2016-02-28
     *                           YYYY-MM-DD则原样返回
     * @param myDate YYYY-MM或YYYY-MM-DD格式
     * @return
     */
    public static String fillDay2MonthEnd(String myDate){
        if(myDate.length() == 10){
            return myDate;
        }
        String result = fillMonth2YearEnd(myDate);
        String year = result.substring(0,4);
        String month = result.substring(5,7);
        result = DateUtil.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        return result;
    }
    /**
     * 将年月日补成当天的开始时间：即YYYY-MM-DD补成YYYY-MM-DD 00:00:00，如传参数2016-02-02则补成2016-02-02 00:00:00
     *                           YYYY-MM-DD HH:mm:ss则原样返回
     * @param myDate YYYY-MM-DD或YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String fillTime2DayBegin(String myDate){
        String result = fillDay2MonthBegin(myDate);
        return StringUtils.rightPad(result,19," 00:00:00");
    }
    /**
     * 将年月日补成当天的结束时间：即YYYY-MM-DD补成YYYY-MM-DD 23:59:59，如传参数2016-02-02则补成2016-02-02 23:59:59
     *                           YYYY-MM-DD HH:mm:ss则原样返回
     * @param myDate YYYY-MM-DD或YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String fillTime2DayEnd(String myDate){
        String result = fillDay2MonthEnd(myDate);
        return StringUtils.rightPad(result,19," 23:59:59");
    }

    /**
     * @创建人 贺兴宇
     * @创建时间 2016年11月08日
     * @创建目的 计算年数间隔
     */
    public static Integer differYear(String begin,String end){
        String beginYear = begin.split("-")[0];
        String endYear = end.split("-")[0];
        return Integer.parseInt(endYear) - Integer.parseInt(beginYear);
    }

    /**
     * @创建人 贺兴宇
     * @创建时间 2016年11月08日
     * @创建目的 计算月数间隔
     */
    public static Integer differMonth(String begin,String end){
        String[] begins = begin.split("-");
        String[] ends = end.split("-");
        return 12*(Integer.parseInt(ends[0]) - Integer.parseInt(begins[0])) + Integer.parseInt(ends[1]) - Integer.parseInt(begins[1]);
    }

    /**
     * @创建人 贺兴宇
     * @创建时间 2016年11月08日
     * @创建目的 计算周数数间隔
     */
    public static Integer differWeek(Date begin,Date end){
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        cal.add(Calendar.DATE,-1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        begin = cal.getTime();
        cal.setTime(end);
        cal.add(Calendar.DATE,-1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        end = cal.getTime();
        return new Long((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24 * 7)).intValue();
    }

    /**
     * @创建人 贺兴宇
     * @创建时间 2016年11月08日
     * @创建目的 计算天数间隔
     */
    public static Integer differDay(Date begin,Date end){
        return new Long((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24)).intValue();
    }

    public static Integer getWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //每周的第一天是周一
        cal.add(Calendar.DATE,-1); //每周的第一天是周一
        int month = cal.get(Calendar.MONTH);
        int week = cal.get(Calendar.WEEK_OF_YEAR); //多少周
        //如果是 12月的第一周，说明是下一年
        if (week == 1 && month == 11) {
            cal.add(Calendar.YEAR,1);
        }
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        /*判断1月1号是周几*/
        int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;    //每年1月1号
        if (weekDay == -1) {
            weekDay = 7;
        }
        //如果1月1号是周五以上，说明这一年少1周，也就是1号归类于上一年(注意，java里面都是以1号为1年的第一周开始)
        if (weekDay >= 5) {
            week --;
        }
        //如果小于零，则取上一周的周数再+1
        if (week<=0) {
            cal.add(Calendar.DATE, -7);
            //取上一年的倒数第二周
            //因为用日历控件取上一周还是不准的，所以这里调用自己取上一周，上一周肯定大于0，所以不会死循环
            week = getWeek(cal.getTime())+1;
        }
        return week;
    }

    /**
     * @创建人 贺兴宇
     * @创建时间 2016年11月09日
     * @创建目的 年和周格式化
     * @param format yyyy vv
     */
    public static String getYearAndWeek(Date date,String format){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekOfYear = DateUtil.getWeek(date);
        int year = cal.get(Calendar.YEAR);
        String weekOfYearStr = "";
        if (weekOfYear < 10) {
            weekOfYearStr = "0" + weekOfYear;
        } else {
            weekOfYearStr = "" + weekOfYear;
        }
        if (weekOfYear == 1 && cal.get(Calendar.MONTH) == 11) {
            ++year;
        } else if (weekOfYear>40 && cal.get(Calendar.MONTH) == 0) {
            --year;
        }
        return format.replaceAll("yyyy", year + "").replaceAll("vv", weekOfYearStr).replaceAll("v", "" + weekOfYear);
    }

    public static String getTimeByType(String timeType,String time) {
        if ("year".equals(timeType)) {
            return time.substring(0, 4) + "年";
        } else if ("month".equals(timeType)) {
            return time.substring(0, 4) + "年" + time.substring(5, 7) + "月";
        } else if ("day".equals(timeType)){
            return time.substring(0, 4) + "年" + time.substring(5, 7) + "月" + time.substring(8, 10) + "日";
        } else if ("week".equals(timeType)) {
            return getYearAndWeek(parse(time, "yyyy-MM-dd"), "yyyy年第vv周");
        }
        return "";
    }

    public static List<String> getTimeArray(String timeType,String startTime,String endTime){
        List<String> result = new ArrayList<String>();
        int interval;
        if(SearchTimeUnitEnum.YEAR.getCode().equals(timeType)){
            interval = DateUtil.differYear(startTime, endTime) + 1;
            for(int i=0;i<interval;i++){
                result.add(DateUtil.format(DateUtil.add(DateUtil.parse(startTime,"yyyy"), Calendar.YEAR , i),"yyyy"));
            }
            //按月
        }else if(SearchTimeUnitEnum.MONTH.getCode().equals(timeType)){
            interval = DateUtil.differMonth(startTime, endTime) + 1;
            for(int i=0;i<interval;i++){
                result.add(DateUtil.format(DateUtil.add(DateUtil.parse(startTime,"yyyy-MM"), Calendar.MONTH , i),"yyyy年MM月"));
            }
            //按周
        }else if(SearchTimeUnitEnum.WEEK.getCode().equals(timeType)){
            Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd");
            Date endDate = DateUtil.parse(endTime, "yyyy-MM-dd");
            interval = DateUtil.differWeek(startDate, endDate) + 1;
            Calendar cal = Calendar.getInstance();
            assert startDate != null;
            cal.setTime(startDate);
            for(int i=0;i<interval;i++){
                result.add(DateUtil.getYearAndWeek(cal.getTime(), "yyyy年第vv周"));
                cal.add(Calendar.WEEK_OF_YEAR,1);
            }
            //按日
        }else if(SearchTimeUnitEnum.DAY.getCode().equals(timeType)){
            Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd");
            Date endDate = DateUtil.parse(endTime, "yyyy-MM-dd");
            interval = DateUtil.differDay(startDate, endDate) + 1;
            for(int i=0;i<interval;i++){
                result.add(DateUtil.format(DateUtil.add(DateUtil.parse(startTime,"yyyy-MM-dd"), Calendar.DATE , i),DateUtil.FORMAT_SHORT_CN));
            }
        }
        return result;
    }
// 同比查询日期
    public static String getTbDate(String time){
        String temp;
        temp=time.substring(0,4);
        int year =Integer.parseInt(temp)-1;
        time=year+time.substring(4);
        return time;
    }

    public static Map<String ,Object> getQueryTime(String timeType,String dateStart,String dateEnd) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        // 当时间类型是周的时候  保证 dateStart是周一 dateEnd是周日
        if ("week".equals(timeType)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setFirstDayOfWeek(Calendar.MONDAY);
                cal.setTime(sdf.parse(dateStart));
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                dateStart = sdf.format(cal.getTime());
                if (StringUtil.isNotEmpty(dateEnd)) {
                    cal.setTime(sdf.parse(dateEnd));
                }
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                dateEnd = sdf.format(cal.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //如果dateEnd为空 是单选时间框（此时时间类型不用考虑周了）
        if (StringUtil.isEmpty(dateEnd)) {
            dateEnd = dateStart;
        }
        if ("month".equals(timeType)) {
            if (dateStart.length() > 7)
                dateStart = dateStart.substring(0, 7);
            if (dateEnd.length() > 7)
                dateEnd = dateEnd.substring(0, 7);
            paramMap.put("beginDate", dateStart);
            String[] dateArray = dateEnd.split("-");
            paramMap.put("endDate", DateUtil.getLastDayOfMonth(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1])) + " 23:59:59");
        } else if ("day".equals(timeType)) {
            paramMap.put("beginDate", dateStart);
            paramMap.put("endDate", dateEnd + " 23:59:59");
        } else if ("week".equals(timeType)) {
            paramMap.put("beginDate", dateStart);
            paramMap.put("endDate", dateEnd + " 23:59:59");
        } else if ("year".equals(timeType)) {
            if (dateStart.length() > 4)
                dateStart = dateStart.substring(0, 4);
            if (dateEnd.length() > 4)
                dateEnd = dateEnd.substring(0, 4);
            paramMap.put("beginDate", dateStart);
            paramMap.put("endDate", dateEnd + "-12-31 23:59:59");
        } else {
            paramMap.put("beginDate", dateStart);
            paramMap.put("endDate", dateEnd);
        }
        return paramMap;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(fillMonth2YearBegin("2015"));
        System.out.println(fillMonth2YearBegin("2015-02"));
        System.out.println(fillMonth2YearEnd("2015"));
        System.out.println(fillMonth2YearEnd("2015-02"));
        System.out.println(fillDay2MonthBegin("2015-03"));
        System.out.println(fillDay2MonthBegin("2015-02-02"));
        System.out.println(fillDay2MonthEnd("2015-02"));
        System.out.println(fillDay2MonthEnd("2016-02"));
        System.out.println(fillDay2MonthEnd("2016-03"));
        System.out.println(fillDay2MonthEnd("2016-04"));
        System.out.println(fillDay2MonthEnd("2016-04-09"));

        System.out.println(fillTime2DayBegin("2016-04"));
        System.out.println(fillTime2DayBegin("2016-04-02"));
        System.out.println(fillDay2MonthEnd("2015-02"));
        System.out.println(fillTime2DayEnd("2016-02"));
        System.out.println(fillTime2DayEnd("2016-03"));
        System.out.println(fillTime2DayEnd("2016-04"));
        System.out.println(fillTime2DayEnd("2016-04-09"));
    }
    //设定的时间范围
    private static
    String[] timeIntervalArray={
            "00:00-00:05",
            "00:05-00:10",
            "00:10-00:15",
            "00:15-00:20",
            "00:20-00:25",
            "00:25-00:30",
            "00:35-00:40",
            "00:40-00:45",
            "00:45-00:50",
            "00:55-01:00",

            "01:00-01:05",
            "01:05-01:10",
            "01:10-01:15",
            "01:15-01:20",
            "01:20-01:25",
            "01:25-01:30",
            "01:35-01:40",
            "01:40-01:45",
            "01:45-01:50",
            "01:55-02:00",

            "02:00-02:05",
            "02:05-02:10",
            "02:10-02:15",
            "02:15-02:20",
            "02:20-02:25",
            "02:25-02:30",
            "02:35-02:40",
            "02:40-02:45",
            "02:45-02:50",
            "02:55-03:00",

            "03:00-03:05",
            "03:05-03:10",
            "03:10-03:15",
            "03:15-03:20",
            "03:20-03:25",
            "03:25-03:30",
            "03:35-03:40",
            "03:40-03:45",
            "03:45-03:50",
            "03:55-04:00",

            "04:00-04:05",
            "04:05-04:10",
            "04:10-04:15",
            "04:15-04:20",
            "04:20-04:25",
            "04:25-04:30",
            "04:35-04:40",
            "04:40-04:45",
            "04:45-04:50",
            "04:55-05:00",

            "05:00-05:05",
            "05:05-05:10",
            "05:10-05:15",
            "05:15-05:20",
            "05:20-05:25",
            "05:25-05:30",
            "05:35-05:40",
            "05:40-05:45",
            "05:45-05:50",
            "05:55-06:00",

            "06:00-06:05",
            "06:05-06:10",
            "06:10-06:15",
            "06:15-06:20",
            "06:20-06:25",
            "06:25-06:30",
            "06:35-06:40",
            "06:40-06:45",
            "06:45-06:50",
            "06:55-07:00",

            "07:00-07:05",
            "07:05-07:10",
            "07:10-07:15",
            "07:15-07:20",
            "07:20-07:25",
            "07:25-07:30",
            "07:35-07:40",
            "07:40-07:45",
            "07:45-07:50",
            "07:55-08:00",

            "08:00-08:05",
            "08:05-08:10",
            "08:10-08:15",
            "08:15-08:20",
            "08:20-08:25",
            "08:25-08:30",
            "08:35-08:40",
            "08:40-08:45",
            "08:45-08:50",
            "08:55-09:00",

            "09:00-09:05",
            "09:05-09:10",
            "09:10-09:15",
            "09:15-09:20",
            "09:20-09:25",
            "09:25-09:30",
            "09:35-09:40",
            "09:40-09:45",
            "09:45-09:50",
            "09:55-10:00",

            "10:00-10:05",
            "10:05-10:10",
            "10:10-10:15",
            "10:15-10:20",
            "10:20-10:25",
            "10:25-10:30",
            "10:35-10:40",
            "10:40-10:45",
            "10:45-10:50",
            "10:55-11:00",

            "11:00-11:05",
            "11:05-11:10",
            "11:10-11:15",
            "11:15-11:20",
            "11:20-11:25",
            "11:25-11:30",
            "11:35-11:40",
            "11:40-11:45",
            "11:45-11:50",
            "11:55-12:00",

            "12:00-12:05",
            "12:05-12:10",
            "12:10-12:15",
            "12:15-12:20",
            "12:20-12:25",
            "12:25-12:30",
            "12:35-12:40",
            "12:40-12:45",
            "12:45-12:50",
            "12:55-13:00",

            "13:00-13:05",
            "13:05-13:10",
            "13:10-13:15",
            "13:15-13:20",
            "13:20-13:25",
            "13:25-13:30",
            "13:35-13:40",
            "13:40-13:45",
            "13:45-13:50",
            "13:55-14:00",

            "14:00-14:05",
            "14:05-14:10",
            "14:10-14:15",
            "14:15-14:20",
            "14:20-14:25",
            "14:25-14:30",
            "14:35-14:40",
            "14:40-14:45",
            "14:45-14:50",
            "14:55-15:00",

            "15:00-15:05",
            "15:05-15:10",
            "15:10-15:15",
            "15:15-15:20",
            "15:20-15:25",
            "15:25-15:30",
            "15:35-15:40",
            "15:40-15:45",
            "15:45-15:50",
            "15:55-16:00",

            "16:00-16:05",
            "16:05-16:10",
            "16:10-16:15",
            "16:15-16:20",
            "16:20-16:25",
            "16:25-16:30",
            "16:35-16:40",
            "16:40-16:45",
            "16:45-16:50",
            "16:55-17:00",

            "17:00-17:05",
            "17:05-17:10",
            "17:10-17:15",
            "17:15-17:20",
            "17:20-17:25",
            "17:25-17:30",
            "17:35-17:40",
            "17:40-17:45",
            "17:45-17:50",
            "17:55-18:00",

            "18:00-18:05",
            "18:05-18:10",
            "18:10-18:15",
            "18:15-18:20",
            "18:20-18:25",
            "18:25-18:30",
            "18:35-18:40",
            "18:40-18:45",
            "18:45-18:50",
            "18:55-19:00",

            "19:00-19:05",
            "19:05-19:10",
            "19:10-19:15",
            "19:15-19:20",
            "19:20-19:25",
            "19:25-19:30",
            "19:35-19:40",
            "19:40-19:45",
            "19:45-19:50",
            "19:55-20:00",

            "20:00-20:05",
            "20:05-20:10",
            "20:10-20:15",
            "20:15-20:20",
            "20:20-20:25",
            "20:25-20:30",
            "20:35-20:40",
            "20:40-20:45",
            "20:45-20:50",
            "20:55-21:00",

            "21:00-21:05",
            "21:05-21:10",
            "21:10-21:15",
            "21:15-21:20",
            "21:20-21:25",
            "21:25-21:30",
            "21:35-21:40",
            "21:40-21:45",
            "21:45-21:50",
            "21:55-21:00",

            "22:00-22:05",
            "22:05-22:10",
            "22:10-22:15",
            "22:15-22:20",
            "22:20-22:25",
            "22:25-22:30",
            "22:35-22:40",
            "22:40-22:45",
            "22:45-22:50",
            "22:55-23:00",

            "23:00-23:05",
            "23:05-23:10",
            "23:10-23:15",
            "23:15-23:20",
            "23:20-23:25",
            "23:25-23:30",
            "23:35-23:40",
            "23:40-23:45",
            "23:45-23:50",
            "23:55-00:00",
    };
}
