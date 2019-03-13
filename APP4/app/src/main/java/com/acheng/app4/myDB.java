package com.acheng.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;


public class myDB extends SQLiteOpenHelper implements Serializable {
    private static final String DB_NAME = "db_name";
    private static final String TABLE_NAME = "table_name";
    private static final String TABLE = "table_comment";
    private static  final int DB_VERSION = 1;
    public myDB(Context c)
    {
        super(c, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatebase)
    {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                +" (_id INTEGER PRIMARY KEY, name TEXT, password TEXT)";

        String CREATE_TABLE2 = "CREATE TABLE if not exists "
                + TABLE
                + " (_id INTEGER PRIMARY KEY, name TEXT, time TEXT, comment TEXT, likeNum INTEGER)";

        sqLiteDatebase.execSQL(CREATE_TABLE);
        sqLiteDatebase.execSQL(CREATE_TABLE2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii)
    {

    }
    public void insert2DB(String name, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("password", password);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }
    public void insert2DB(String name, String time, String comment, Integer likeNum)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("time", time);
        cv.put("comment", comment);
        cv.put("likeNum", likeNum);
        db.insert(TABLE, null, cv);
        db.close();
    }
    public int delete(Integer id)
    {
        SQLiteDatabase db = getWritableDatabase();
        int row = db.delete(TABLE,"_id=?", new String[] {String.valueOf(id+1)});
        //db.execSQL("DELETE FROM table_name WHERE _id = '"+id+"'");
        db.close();
        return row;
    }
    public User queryDB(String name)
    {
        User temp = null;
        SQLiteDatabase db = getWritableDatabase();
        String selection = "name = ?";
        String[] selectionArgs = {name};
        Cursor c = db.query(TABLE_NAME, null ,selection, selectionArgs, null, null, null);
        if (c.moveToNext())
        {
            if (c.getColumnCount() == 0 || !c.moveToFirst())
            {
                return null;
            }
            temp = new User(c.getString(1), c.getString(2));
        }
        c.close();
        db.close();
        return temp;
    }


    public ArrayList<CommentInfo> get()
    {
        ArrayList<CommentInfo> mylist = new ArrayList<CommentInfo>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE, null ,null,null, null, null, null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            mylist.add(new CommentInfo(c.getString(1), c.getString(2), c.getString(3), c.getInt(4)));
            c.moveToNext();
        }
        return mylist;
    }

}
