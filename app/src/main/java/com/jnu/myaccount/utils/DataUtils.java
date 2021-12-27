package com.jnu.myaccount.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jnu.myaccount.data.AccountItem;
import com.jnu.myaccount.data.HomeItem;
import com.jnu.myaccount.databace.DatabaceHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DataUtils {
    private Context context;
    private String databaceName;
    private Integer databaceVersion;

    public DataUtils(Context context){
        this.context = context;
        this.databaceName = "Account";
        databaceVersion= 1;
    }

    public void loadData(TreeMap<String, List<HomeItem>> listTreeMap) {
        listTreeMap.clear();
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaceHelper(context, databaceName, null, databaceVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("item", new String[]{"record", "num", "date", "type"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int record = cursor.getInt(cursor.getColumnIndexOrThrow("record"));
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("num"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
            try {
                AccountItem accountItem = new AccountItem(record, num, new CalendarUtils().StringToCalender(date));
                updateData(listTreeMap, accountItem);
            } catch (ParseException e) {
            }
        }
    }

    public void InsertData(int record,int num,String date){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaceHelper(context,databaceName,null,databaceVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        int type = 0;
        if ( record <= 10 ) type = 0;
        else type = 1;

        ContentValues values = new ContentValues();
        values.put("record", record);
        values.put("num",num);
        values.put("date",date);
        values.put("type",type);
        sqLiteDatabase.insert("item",null,values);
    }

    public void DeleteTable(String table){
        SQLiteOpenHelper sqLiteOpenHelper = new DatabaceHelper(context,databaceName,null,databaceVersion);
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        String sql = "delete from "+table;
        sqLiteDatabase.execSQL(sql);
        sql = "update sqlite_sequence SET seq = 0 where name = " + "'"+table+"'";
        sqLiteDatabase.execSQL(sql);
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
}
