package com.jnu.myaccount.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jnu.myaccount.data.AccountItem;
import com.jnu.myaccount.data.HomeItem;
import com.jnu.myaccount.database.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DataUtils {
    private Context context;
    private String databaseName;
    private Integer databaseVersion;

    public DataUtils(Context context){
        this.context = context;
        this.databaseName = "Account";
        databaseVersion = 1;
    }

    public void loadData(TreeMap<String,List<HomeItem>> listTreeMap){
        listTreeMap.clear();
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(context, databaseName,null,databaseVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("item",new String[]{"createTime","record","num","type","date","year","month","day"}, null,null,null,null,null);
        while(cursor.moveToNext()){
            int record = cursor.getInt(cursor.getColumnIndexOrThrow("record"));
            double num = cursor.getDouble(cursor.getColumnIndexOrThrow("num"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String createTime = cursor.getString(cursor.getColumnIndexOrThrow("createTime"));
            try {
                AccountItem accountItem = new AccountItem(record,num,new CalendarUtils().StringToCalender(date),createTime);
                updateData(listTreeMap, accountItem);
            } catch (ParseException e) {
            }
        }
    }

    private void updateData(TreeMap<String,List<HomeItem>> listTreeMap, AccountItem accountItem){
        List<HomeItem> homeItemList;
        if(listTreeMap.containsKey(accountItem.getTagDate())){
            homeItemList = listTreeMap.get(accountItem.getTagDate());
            homeItemList.add(accountItem);
        }
        else{
            homeItemList = new ArrayList<>();
            homeItemList.add(accountItem);
            listTreeMap.put(accountItem.getTagDate(),homeItemList);
        }
    }

    public void InsertData(int record,double num,String date){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(context, databaseName,null, databaseVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        int type = 0;
        if ( record <= 10 ) type = 0;
        else type = 1;
        int[] tmpTime = new int[5];
        new CalendarUtils().TimeStringToInt(date, tmpTime);

        ContentValues values = new ContentValues();
        values.put("createTime",new CalendarUtils().getNowTimeString());
        values.put("record", record);
        values.put("num",num);
        values.put("type",type);
        values.put("date",date);
        values.put("year",tmpTime[0]);
        values.put("month",tmpTime[1]);
        values.put("day",tmpTime[2]);
        sqLiteDatabase.insert("item",null,values);
    }

    public void EditData(int record, double num, String date, String createTime){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(context, databaseName,null, databaseVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        int type = 0;
        if ( record <= 10 ) type = 0;
        else type = 1;

        int[] tmpTime = new int[5];
        new CalendarUtils().TimeStringToInt(date, tmpTime);

        ContentValues values = new ContentValues();
        values.put("record", record);
        values.put("num",num);
        values.put("type",type);
        values.put("date",date);
        values.put("year",tmpTime[0]);
        values.put("month",tmpTime[1]);
        values.put("day",tmpTime[2]);

        sqLiteDatabase.update("item", values, "createTime=?", new String[]{createTime});
    }

    public void DeleteItem(String createTime){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(context, databaseName,null, databaseVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        sqLiteDatabase.delete("item","createTime=?",new String[]{createTime});
    }

    public void DeleteTable(String table){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(context, databaseName,null, databaseVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        String sql = "delete from "+table;
        sqLiteDatabase.execSQL(sql);
        sql = "update sqlite_sequence SET seq = 0 where name = " + "'"+table+"'";
        sqLiteDatabase.execSQL(sql);
    }

}
