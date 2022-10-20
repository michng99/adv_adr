package com.nah.adv_adr_prj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.nah.adv_adr_prj.adapter.DangKyMonHocAdapter;
import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.services.DangKyMonHocService;

import java.io.Serializable;
import java.util.ArrayList;

public class DangKyMonHocAct extends AppCompatActivity {

    RecyclerView ryc_DangKyMonHoc;
    IntentFilter intentFilter;
    int id;
    boolean isAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_mon_hoc);

        ryc_DangKyMonHoc = findViewById(R.id.ryc_DangKyMonHoc);
        MaterialTextView tvTitle = findViewById(R.id.tvTitle);

        intentFilter = new IntentFilter();
        intentFilter.addAction("DSMonHoc");
        intentFilter.addAction("DKMonHoc");


        // id của người dùng đăng nhập
        SharedPreferences sP = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        id = sP.getInt("id",-1);


        // lau gia tri isAll
        Intent iIsAll = getIntent();
        Bundle bIsAll = iIsAll.getExtras();
        isAll = bIsAll.getBoolean("isAll");
        if (isAll)
        {
            tvTitle.setText("Đăng ký môn học");
        } else {
            tvTitle.setText("Môn học đã đăng ký");
        }

        Intent i = new Intent(DangKyMonHocAct.this, DangKyMonHocService.class);
        Bundle b =  new Bundle();
        b.putInt("id",id);
        b.putBoolean("isAll", isAll);
        i.putExtras(b);
        startService(i);

    }


    //ham chuc nang
    private void loadData(ArrayList<MonHoc> list)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ryc_DangKyMonHoc.setLayoutManager(linearLayoutManager);
        DangKyMonHocAdapter adapter = new DangKyMonHocAdapter(this, list, id, isAll);
        ryc_DangKyMonHoc.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBR, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBR);
    }

    private BroadcastReceiver myBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            switch (i.getAction()){
                case "DSMonHoc":
                case "DKMonHoc":
                    Bundle b = i.getExtras();
                    boolean check = b.getBoolean("check",true);

                    if (check == true) {
                        ArrayList<MonHoc> list = (ArrayList<MonHoc>) b.getSerializable("list");
                        loadData(list);
                    } else {
                        Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

}