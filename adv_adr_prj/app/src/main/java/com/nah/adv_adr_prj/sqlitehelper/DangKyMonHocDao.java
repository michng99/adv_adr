package com.nah.adv_adr_prj.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.model.ThongTin;

import java.util.ArrayList;

public class DangKyMonHocDao {

    private Dbhelper dbhelper;

    public DangKyMonHocDao(Context context) {
        dbhelper = new Dbhelper(context);
    }

    //Viet ham get Danh sach mon hoc trong lop Model truyen vao ArrayList
    public ArrayList<MonHoc> getDSMonHoc(int id, boolean isAll) {

        ArrayList<MonHoc> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor;
        if (isAll) {
           cursor =  sqLiteDatabase.rawQuery("select mh.code, mh.name, mh.teacher, dk.id from MONHOC mh " +
                   "left join DANGKY dk on mh.code = dk.code and dk.id = ?", new String[]{String.valueOf(id)});
        } else {
            cursor = sqLiteDatabase.rawQuery("select mh.code, mh.name, mh.teacher, dk.id from MONHOC mh " +
                    "inner join DANGKY dk on mh.code = dk.code where dk.id = ?", new String[]{String.valueOf(id)});
        }
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new MonHoc(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), getThongTin(cursor.getString(0))));
            } while (cursor.moveToNext());
        }
        return list;
    }

        //đăng ký môn học
        public boolean dangKyMonHoc(int id, String code) {
            SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("id", id);
            cv.put("code", code);
            long check = sqlDB.insert("DANGKY", null, cv);
           if (check == 1)
               return false;
           return true;
        }

        //huy dang ky mon hoc
        public boolean huyDangKyMonHoc(int id, String code)
        {
            SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
            long check = sqlDB.delete("DANGKY", "id = ? and code = ?", new String[]{String.valueOf(id), code});
            if (check == -1)
                return false;
            return true;
        }

        public ArrayList<ThongTin> getThongTin(String code)
        {
            ArrayList<ThongTin> list = new ArrayList<>();
            SQLiteDatabase sqlDB = dbhelper.getReadableDatabase();
            Cursor cursor = sqlDB.rawQuery("select date, address from THONGTIN where code = ?", new String[]{code});
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    list.add(new ThongTin(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            return list;
        }
}