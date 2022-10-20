package com.nah.adv_adr_prj.sqlitehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao
{
    SharedPreferences sP;
    Dbhelper dbhelper;
    public UserDao(Context context){
        dbhelper = new Dbhelper(context);
        sP = context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }


    public boolean checkLogin(String username, String password)
    {
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username = ? and password = ?",
                new String[]{username,password});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sP.edit();
            editor.putInt("id", cursor.getInt(0));
            editor.apply();
            return true;
        } else {
            return false;
        }
    }
}
