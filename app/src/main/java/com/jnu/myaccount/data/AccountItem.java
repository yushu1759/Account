package com.jnu.myaccount.data;

import android.content.Context;

import com.jnu.myaccount.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountItem extends HomeItem {
    private int icon;
    private int record;
    private double num;
    private int type; //0为支出 1为收入
    private Calendar date;

    public AccountItem(int record, double num, int year,int month,int date) {
        this.icon = getIcon(record);
        this.record = record;
        this.num = num;
        this.type = getType();
        this.date = Calendar.getInstance();
        this.date.set(year, month, date);
    }

    public AccountItem(int record,double num,Calendar calendar){
        this.icon = getIcon(record);
        this.record = record;
        this.num = num;
        this.type = getType();
        this.date = calendar;
    }

    public String getTitle(Context context, int record){
        switch (record){
            case 1:
                return context.getString(R.string.foods);
            case 2:
                return context.getString(R.string.debt);
            case 3:
                return context.getString(R.string.supplies);
            case 4:
                return context.getString(R.string.shopping);
            case 5:
                return context.getString(R.string.sports);
            case 6:
                return context.getString(R.string.call_credit);
            case 7:
                return context.getString(R.string.traffic);
            case 8:
                return context.getString(R.string.party);
            case 9:
                return context.getString(R.string.housing);
            case 10:
                return context.getString(R.string.medical);
            case 11:
                return context.getString(R.string.gift);
            case 12:
                return context.getString(R.string.salary);
            case 13:
                return context.getString(R.string.red_packet);
            case 14:
                return context.getString(R.string.fund);
            default:
                return context.getString(R.string.others);
        }
    }

    public int getIcon(int record) {
        switch (record){
            case 1:
                return R.drawable.foods;
            case 2:
                return R.drawable.debt;
            case 3:
                return R.drawable.supplies;
            case 4:
                return R.drawable.shopping;
            case 5:
                return R.drawable.sports;
            case 6:
                return R.drawable.call_credit;
            case 7:
                return R.drawable.traffic;
            case 8:
                return R.drawable.party;
            case 9:
                return R.drawable.housing;
            case 10:
                return R.drawable.medical;
            case 11:
                return R.drawable.gift;
            case 12:
                return R.drawable.salary;
            case 13:
                return R.drawable.red_packet;
            case 14:
                return R.drawable.fund;
            default:
                return R.drawable.others;
        }
    }

    public int getType(){
        if(this.record<=10) return 0;
        else return 1;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
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