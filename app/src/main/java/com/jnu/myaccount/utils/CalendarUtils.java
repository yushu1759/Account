package com.jnu.myaccount.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    public Calendar StringToCalender(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public String getNowDateString(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public String getNowTimeString(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
        return format.format(date);
    }

    public String IntToTimeString(int year, int month, int dayOfMonth){
        String date,strYear,strMonth,strDay;
        strYear = Integer.toString(year);
        if(month<10){
            strMonth = "0"+month;
        }
        else strMonth = Integer.toString(month);
        if(dayOfMonth<10){
            strDay = "0"+dayOfMonth;
        }
        else strDay = Integer.toString(dayOfMonth);
        date = strYear+"-"+strMonth+"-"+strDay;
        return date;
    }

    public int[] getNowDate(){
        int[] date = new int[5];
        Calendar calendar = Calendar.getInstance();
        date[0] = calendar.get(Calendar.YEAR);
        date[1] = calendar.get(Calendar.MONTH)+1;
        date[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return date;
    }

    public int[] TimeStringToInt(String str, int[] intDate){
        String[] strDate = str.split("-");
        if(strDate[1].charAt(0)=='0'){
            strDate[1] = strDate[1].charAt(1)+"";
        }
        if(strDate[2].charAt(0)=='0'){
            strDate[2] = strDate[2].charAt(1)+"";
        }
        for(int i=0;i<3;i++){
            intDate[i] = Integer.parseInt(strDate[i]);
        }
        return intDate;
    }
}
