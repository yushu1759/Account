package com.jnu.myaccount.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountItem extends HomeItem {
    private int icon;
    private int record;
    private int num;
    private boolean type; //0为支出 1为收入
    private Calendar date;


    public AccountItem(int icon,int record, int num, boolean type, int year,int month,int date) {
        this.icon = icon;
        this.record = record;
        this.num = num;
        this.type = type;
        this.date = Calendar.getInstance();
        this.date.set(year, month, date);
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public int getRecord(){
        return record;
    }

    public void setRecord(int record){
        this.record = record;
    }

    public String getTagDate(){
        String date;
        Calendar calendar = this.date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public Calendar getDate(){
        return this.date;
    }
}