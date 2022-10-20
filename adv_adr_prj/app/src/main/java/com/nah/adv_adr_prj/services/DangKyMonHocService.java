package com.nah.adv_adr_prj.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.sqlitehelper.DangKyMonHocDao;

import java.util.ArrayList;

public class DangKyMonHocService extends Service {



    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        Bundle b = i.getExtras();
        int id = b.getInt("id");
        boolean isAll = b.getBoolean("isAll");

        DangKyMonHocDao dao = new DangKyMonHocDao(this);
        ArrayList<MonHoc> list = dao.getDSMonHoc(id, isAll);

        //send data
        Intent iBR = new Intent();
        Bundle bBR = new Bundle();
        bBR.putSerializable("list",list);
        iBR.putExtras(bBR);
        iBR.setAction("DSMonHoc");
        sendBroadcast(iBR);

        return super.onStartCommand(i, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent i) {
        return null;
    }
}
