package com.nah.adv_adr_prj.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.sqlitehelper.DangKyMonHocDao;

import java.util.ArrayList;

public class DkMonHocSerVice extends Service{

    @Override
    public int onStartCommand(Intent i, int flags, int startId)
    {
        Bundle b = i.getExtras();
        int id = b.getInt("id",-1);
        String code = b.getString("code","");
        int isRegister = b.getInt("isRegister",-1);
        boolean isAll = b.getBoolean("isAll");

        boolean check;
        DangKyMonHocDao dao = new DangKyMonHocDao(this);
        if (isRegister == id){
            check = dao.huyDangKyMonHoc(id, code);
        } else {
            check = dao.dangKyMonHoc(id, code);
        }

        //Neu check = true thi lay lai ds
        ArrayList<MonHoc> list = new ArrayList<>();
        if (check == true) {
            list = dao.getDSMonHoc(id, isAll);
        }

        //send data broadcast
        Intent iBR = new Intent();
        Bundle bBR = new Bundle();
        bBR.putBoolean("check",check);
        bBR.putSerializable("list", list);
        iBR.putExtras(bBR);
        iBR.setAction("DKMonHoc");
        sendBroadcast(iBR);


        return super.onStartCommand(i, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
