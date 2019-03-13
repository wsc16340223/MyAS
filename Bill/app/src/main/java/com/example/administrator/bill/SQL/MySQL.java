package com.example.administrator.bill.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MySQL extends SQLiteOpenHelper {
    private static final String DB_NAME = "myDB.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "bills";
    private static final String SQL_CREAT_TABLE = "create table " + TABLE_NAME + " (id integer primary key not null, type text not null, money text not null, time text not null, tip text not null, style integer not null, pic blbo);";

    public MySQL(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public int id() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if(c.getCount() == 0 || !c.moveToFirst()) {
            return 0;
        }
        c.moveToPosition(c.getCount() - 1);
        return c.getInt(c.getColumnIndex("id")) + 1;
    }

    public long add(Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", bill.getId());
        values.put("type", bill.getType());
        values.put("money", String.valueOf(bill.getMoney()));
        values.put("time", bill.getTime());
        values.put("tip", bill.getTip());
        values.put("style", bill.getStyle());
        values.put("pic", bill.getPic());
        long rid = db.insert(TABLE_NAME, null, values);
        db.close();
        return rid;
    }

    public int delete(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {id.toString()};
        int rows = db.delete(TABLE_NAME, selection, selectionArgs);

        String selection2 = "id > ?";
        Cursor c = db.query(TABLE_NAME, null, selection2, selectionArgs, null, null, null);
        while (c.moveToNext()) {
            int newid = c.getInt(c.getColumnIndex("id")) - 1;
            String[] selectionArgs2 = {String.valueOf(c.getInt(c.getColumnIndex("id")))};
            ContentValues values = new ContentValues();
            values.put("id", newid);
            int rows2 = db.update(TABLE_NAME, values, selection, selectionArgs2);
        }
        db.close();
        return rows;
    }

    public Cursor get() {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id > ?";
        String[] selectionArgs = {String.valueOf(id() - 60)};
        Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(c.getCount() == 0 || !c.moveToFirst()) {
            return null;
        }
        return c;
    }

    public String getTip(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {id.toString()};
        Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("tip"));
    }
    //月账单查询使用
    public ArrayList<Bill> queryDate(String date){
        ArrayList<Bill> myList = new ArrayList<Bill>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from bills where time like ?", new String[]{date});
        if (c.getCount() == 0){
            return null;
        }
        c.moveToFirst();
        while (!c.isAfterLast()){
            Bill item = new Bill(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("type")), Float.parseFloat(c.getString(c.getColumnIndex("money"))), c.getString(c.getColumnIndex("time")), c.getString(c.getColumnIndex("tip")), c.getInt(c.getColumnIndex("style")), c.getBlob(c.getColumnIndex("pic")));
            myList.add(item);
            c.moveToNext();
        }
        c.close();
        db.close();
        return myList;
    }

}
