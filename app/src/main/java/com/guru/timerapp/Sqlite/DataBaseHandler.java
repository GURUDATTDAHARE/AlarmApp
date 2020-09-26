package com.guru.timerapp.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.guru.timerapp.Modal.Data;
import com.guru.timerapp.ParametarForSQlite.guru;
import com.guru.timerapp.R;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context,guru.DATABASE_NAME, null,guru.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String s="create table "+guru.TABLE_NAME+"("+guru.KEY_ID+" integer primary key,"+guru.TIME_LIST+" text "+")";
     db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.valueOf(R.string.droptable),new String[]{guru.DATABASE_NAME});
        onCreate(db);
    }

    //CRUD

   public void addItem(Data data)
   {
       SQLiteDatabase db=this.getWritableDatabase();
       ContentValues values=new ContentValues();
       values.put(guru.TIME_LIST,data.getTime());
       db.insert(guru.TABLE_NAME,null,values);
       db.close();
   }
   public Data getAlarm_id()
   {
       SQLiteDatabase db=  this.getReadableDatabase();
       Cursor cursor=db.rawQuery("select *from "+guru.TABLE_NAME,null);
       cursor.moveToLast();
      // cursor.moveToPrevious();
        //cursor.getString(0);
        Data data=new Data();
        data.setAlarmId(Integer.parseInt(cursor.getString(0)));
       return data;
   }
    public List<Data> getAllContact(){
        List<Data>contacts=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectAll="SELECT * FROM "+guru.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do {
                Data contact=new Data();
                contact.setAlarmId(Integer.parseInt(cursor.getString(0)));
                contact.setTime(cursor.getString(1));
               // contact.setPhoneNumber(cursor.getString(2));
                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        return  contacts;
    }
    public void deleteItem(int position)
    {
         SQLiteDatabase db=this.getWritableDatabase();
         db.delete(guru.TABLE_NAME,guru.KEY_ID+" =? ",new String[]{String.valueOf(position)});
         db.close();
    }

}
