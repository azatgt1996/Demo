package com.javamaster.demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.javamaster.demo.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
     private Context context;
     private DbHelper dbHelper;
     private SQLiteDatabase db;

     public  DbManager(Context context) {
         this.context = context;
         dbHelper = new DbHelper(context);
     }

     public void openDb() {
         db = dbHelper.getWritableDatabase();
     }

     public void closeDb() {
         dbHelper.close();
     }

     public void insertPhone(String phone) {
         ContentValues values = new ContentValues();
         values.put(Phones.PHONE_NUM, phone);
         db.insert(Phones.TABLE_NAME, null, values);
     }

     public void deletePhone(String phone) {
         db.delete(Phones.TABLE_NAME, Phones.PHONE_NUM + "=?", new String[]{ phone});
     }

    public void deleteAllPhones() {
        db.delete(Phones.TABLE_NAME, null, null);
    }

     public List<Phone> getAllPhones() {
         List<Phone> list = new ArrayList<>();
         Cursor cursor = db.query( Phones.TABLE_NAME, null, null,
                 null, null, null, null);
         while (cursor.moveToNext()) {
             int id = cursor.getInt(cursor.getColumnIndex(Phones.ID));
             String phoneNum = cursor.getString(cursor.getColumnIndex(Phones.PHONE_NUM));
             list.add(new Phone(id, phoneNum));
         }
         cursor.close();
         return list;
     }

    public void updatePhone(String oldPhone, String changedPhone) {
        ContentValues values = new ContentValues();
        values.put(Phones.PHONE_NUM, changedPhone);
        db.update(Phones.TABLE_NAME, values, Phones.PHONE_NUM + "=?", new String[]{ oldPhone});
    }

    public boolean isExisted(String phone) {
        Cursor cursor = db.query( Phones.TABLE_NAME, null, Phones.PHONE_NUM + "=? ",
                new String[]{ phone}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }
}
