package com.nah.adv_adr_prj.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.nah.adv_adr_prj.sqlitehelper.UserDao;

import java.security.Provider;

public class CheckLoginServices extends Service {

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {

        Bundle bd = i.getExtras();
        String username = bd.getString("username");
        String password = bd.getString("password");

        UserDao dao = new UserDao(this);
        boolean check = dao.checkLogin(username,password);

        // send data
        Intent iBR = new Intent();
        Bundle bBR = new Bundle();
        bBR.putBoolean("check", check);
        iBR.putExtras(bBR);
        iBR.setAction("checkLogin");
        sendBroadcast(iBR);

        return super.onStartCommand(i, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
