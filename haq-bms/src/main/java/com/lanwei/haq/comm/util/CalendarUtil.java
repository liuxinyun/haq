package com.lanwei.haq.comm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @作者: liuxinyun
 * @日期: 2017/3/21 14:31
 * @描述:
 */
public class CalendarUtil {

    /**
     * 节假日
     */
    private static List<Calendar> holidayList = new ArrayList<>();
    /**
     * 补班
     */
    private static List<Calendar> extraList = new ArrayList<>();

    /**
     * 获取指定日期所在月的第一个工作日
     * @param date
     * @return
     */
    public static Date getFirstWorkDay(Date date){
        List<Date> list = getWorkDay(date);
        return list.get(0);
    }

    /**
     * 获取指定日期所在月的最后一个工作日
     * @param date
     * @return
     */
    public static Date getLastWorkDay(Date date){
        List<Date> list = getWorkDay(date);
        return list.get(list.size()-1);
    }

    /**
     * 获取指定日期所在年份每个月的第一个工作日
     * @param date
     * @return
     */
    public static List<Date> getFirstWorkDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);//指定日期所在年份
        //从每个月1号开始
        List<String> li = new ArrayList<>();
        for (int i=1; i<=12; i++){
            String s = year+"-";
            if (i<10){
                s += "0"+i+"-01";
            }else {
                s += i+"-01";
            }
            li.add(s);
        }
        List<Date> list = new ArrayList<>();
        for (String s: li){
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(s);
                Date dd = getFirstWorkDay(d);
                list.add(dd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取指定日期所在月的工作日
     * @param date
     * @return
     */
    public static List<Date> getWorkDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        //首先将日期设置为上个月最后一天
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        List<Date> list = new ArrayList<Date>();
        try {
            while (true){
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                if (calendar.get(Calendar.MONTH) != month){
                    break;
                }
                if (checkWorkday(calendar.getTime())) {
                    list.add(calendar.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param date 当前的日期
     * @param day 相加天数
     * @return 返回相加day天，并且排除节假日和周末后的日期
     * throws
     */
    public static Date addDateByWorkDay(Date date, int day) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        try {
            for (int i = 0; i < day; i++) {
                ca.add(ca.DAY_OF_MONTH, 1);
                if (!checkWorkday(ca.getTime())) {
                    i--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ca.getTime();
    }

    /**
     * @param date 传入需要验证的日期
     * @return true是工作日，false是节假日
     * throws
     */
    public static boolean checkWorkday(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //判断日期是否是补班
        for (Calendar ca : extraList) {
            if (ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                    ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                return true;
            }
        }

        //判断日期是否是周六周日
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return false;
        }
        //判断日期是否是节假日
        for (Calendar ca : holidayList) {
            if (ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                    ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断两个日期是否为同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2){
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(date1);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(date2);

        boolean isSameYear = ca1.get(Calendar.YEAR) == ca2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && ca1.get(Calendar.MONTH) == ca2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && ca1.get(Calendar.DAY_OF_MONTH) == ca2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断两个日期大小，date1大返回真
     * @param date1
     * @param date2
     * @return
     */
    public static boolean oneBigger(Date date1, Date date2){
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(date1);
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(date2);

        boolean year = ca1.get(Calendar.YEAR) >= ca2
                .get(Calendar.YEAR);
        boolean month = year
                && ca1.get(Calendar.MONTH) >= ca2.get(Calendar.MONTH);
        boolean day = month
                && ca1.get(Calendar.DAY_OF_MONTH) > ca2
                .get(Calendar.DAY_OF_MONTH);

        return day;

    }

    /**
     * 构建时初始化
     */
    public CalendarUtil(List<Date> holiday, List<Date> extra) {
        if (ListUtil.isEmpty(holiday) && ListUtil.isEmpty(extra)){
            return;
        }
        holidayList.clear();
        extraList.clear();
        for (Date date : holiday) {
            String s = DateUtil.formatDate(EnumDateCode.YEAR_MM_DD,date);
            String[] da = s.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
            calendar.set(Calendar.MONTH, Integer.valueOf(da[1]) - 1);// 月份比正常小1,0代表一月
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
            holidayList.add(calendar);
        }
        for (Date date : extra) {
            String s = DateUtil.formatDate(EnumDateCode.YEAR_MM_DD,date);
            String[] da = s.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
            calendar.set(Calendar.MONTH, Integer.valueOf(da[1]) - 1);// 月份比正常小1,0代表一月
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
            extraList.add(calendar);
        }
    }


    public static void main(String[] args) throws ParseException {
        List<String> holiday = new ArrayList<>();
        holiday.add("2017-01-02");
        holiday.add("2017-01-27");
        holiday.add("2017-01-30");
        holiday.add("2017-01-31");
        holiday.add("2017-02-01");
        holiday.add("2017-02-02");
        holiday.add("2017-04-03");
        holiday.add("2017-04-04");
        holiday.add("2017-05-01");
        holiday.add("2017-05-29");
        holiday.add("2017-05-30");
        holiday.add("2017-10-02");
        holiday.add("2017-10-03");
        holiday.add("2017-10-04");
        holiday.add("2017-10-05");
        holiday.add("2017-10-06");
        holiday.add("2018-01-01");
        holiday.add("2018-02-15");
        holiday.add("2018-02-16");
        holiday.add("2018-02-19");
        holiday.add("2018-02-20");
        holiday.add("2018-02-21");
        holiday.add("2018-04-05");
        holiday.add("2018-04-06");
        holiday.add("2018-04-30");
        holiday.add("2018-05-01");
        holiday.add("2018-06-18");
        holiday.add("2018-09-24");
        holiday.add("2018-10-01");
        holiday.add("2018-10-02");
        holiday.add("2018-10-03");
        holiday.add("2018-10-04");
        holiday.add("2018-10-05");
        List<Date> holiday1 = new ArrayList<>();
        for (String s: holiday){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(s);
            holiday1.add(d);
        }
        List<String> extra = new ArrayList<>();
        extra.add("2017-01-22");
        extra.add("2017-02-04");
        extra.add("2017-04-01");
        extra.add("2017-05-27");
        extra.add("2017-09-30");
        extra.add("2018-02-11");
        extra.add("2018-02-24");
        extra.add("2018-04-01");
        extra.add("2018-09-30");
        extra.add("2018-10-13");
        List<Date> extra1 = new ArrayList<>();
        for (String s: extra){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(s);
            extra1.add(d);
        }

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse("2017-09-30");
            CalendarUtil cu = new CalendarUtil(holiday1,extra1);
            //判断传入的日期是否是工作日
            if (cu.checkWorkday(d)){
                System.out.println(df.format(d)+"是工作日");
            }else {
                System.out.println(df.format(d)+"是节假日");
            }
            //输出传入的日期在指定天数后的工作日日期
            Date dd = cu.addDateByWorkDay(d,5);
            System.out.println("接下来第5个工作日"+df.format(dd));

            //输出传入日期当月的工作日
            List<Date> list1 = cu.getWorkDay(d);
            Calendar ca = Calendar.getInstance();
            ca.setTime(d);
            System.out.println(ca.get(Calendar.YEAR)+"年"+(ca.get(Calendar.MONTH)+1)+"月所有工作日:");
            for (Date date:list1){
                System.out.println(df.format(date));
            }
            //输出传入日期当年的每个月的第一个工作日
            List<Date> list2 = cu.getFirstWorkDayOfMonth(d);
            System.out.println(ca.get(Calendar.YEAR)+"年每个月第一个工作日:");
            for (Date date:list2){
                System.out.println(df.format(date));
            }

        } catch ( Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }



}
