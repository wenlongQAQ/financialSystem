package com.example.personalfinancialmanagementsystem.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;/***@author*@version**/

public class GetDateUtil{

    public static String DEFAULT_FORMAT = "yyyy-MM-dd";

    public static String formatDate(Date date){

        SimpleDateFormat f= new SimpleDateFormat(DEFAULT_FORMAT);

        String sDate=f.format(date);
        return sDate;

    }
    /*** 获取当年的第一天

     *@paramyear

     *@return

     */

    public static Date getCurrYearFirst(){

        Calendar currCal=Calendar.getInstance();int currentYear =currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);

    }
    /**
     * 获取当年的最后一天
     *
     * @param
     * @return
     */

    public static Date getCurrYearLast(){

        Calendar currCal=Calendar.getInstance();int currentYear =currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);

    }
    /** 获取某年第一天日期

     *@paramyear 年份

     *@returnDate
     *
     */

    public static Date getYearFirst(int year){

        Calendar calendar=Calendar.getInstance();

        calendar.clear();

        calendar.set(Calendar.YEAR, year);

        Date currYearFirst=calendar.getTime();
        return currYearFirst;

    }
    /**获取某年最后一天日期

     *@paramyear 年份

     *@returnDate
     */

    public static Date getYearLast(int year){

        Calendar calendar=Calendar.getInstance();

        calendar.clear();

        calendar.set(Calendar.YEAR, year);

        calendar.roll(Calendar.DAY_OF_YEAR,-1);

        Date currYearLast=calendar.getTime();
        return currYearLast;
    }

}
