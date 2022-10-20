package com.nah.adv_adr_prj.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    public Dbhelper(Context context) {
        super(context, "DANGKYMONHOC", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDb) {

        String dbNguoiDung = "create table NGUOIDUNG (\n" +
                "\tid INTEGER primary key autoincrement,\n" +
                "\tusername VARCHAR(50),\n" +
                "\tpassword VARCHAR(50)\n" +
                ")";
        sqlDb.execSQL(dbNguoiDung);

        String dbMonHoc = "create table MONHOC (\n" +
                "\tcode VARCHAR(50) primary key,\n" +
                "\tname VARCHAR(50),\n" +
                "\tteacher VARCHAR(50)\n" +
                ")";
        sqlDb.execSQL(dbMonHoc);

        String dbThongTin = "create table THONGTIN (\n" +
                "\tid INTEGER primary key autoincrement,\n" +
                "\tcode VARCHAR(50),\n" +
                "\tdate DATE,\n" +
                "\taddress VARCHAR(50)\n" +
                ")";
        sqlDb.execSQL(dbThongTin);

        String dbDangKy = "create table DANGKY (\n" +
                "\tid INTEGER,\n" +
                "\tcode VARCHAR(50)\n" +
                ")";
        sqlDb.execSQL(dbDangKy);

        //data mau
        String isNguoiDung = "INSERT INTO NGUOIDUNG VALUES(1, 'user01', 'user01abc'),(2, 'user02', 'user02abc'),(3, 'user03','user03abc')";
        sqlDb.execSQL(isNguoiDung);

        String isThongTin = "insert into THONGTIN (id, code, date, address) values (1, 'MOB201', '09/08/2022', 'T1008'),(2, 'MOB1013', '16/09/2022', 'Online - Google Meet'),(3, 'MOB306', '25/09/2022', 'T1011')";
        sqlDb.execSQL(isThongTin);

        String isMonHoc = "insert into MONHOC (code, name, teacher) values ('MOB201' ,'Android Nang Cao', 'Nguyen Tri Dinh'),('MOB1013' ,'Android Co Ban', 'Nguyen Ngoc Chan'),('MOB306' ,'React Native', 'Nguyen Do Anh Khoa')";
        sqlDb.execSQL(isMonHoc);

        String isDangKy = "insert into DANGKY (id, code) values (1, 'MOB201'),(2, 'MOB1013'),(3, 'MOB306')";
        sqlDb.execSQL(isDangKy);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDb, int i, int i1) {
        if (i != i1) {
            sqlDb.execSQL("Drop table if exists NGUOIDUNG");
            sqlDb.execSQL("Drop table if exists MONHOC");
            sqlDb.execSQL("Drop table if exists THONGTIN");
            sqlDb.execSQL("Drop table if exists DANGKY");
            onCreate(sqlDb);
        }
    }
}
