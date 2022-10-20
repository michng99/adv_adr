package com.nah.adv_adr_prj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.sqlitehelper.DangKyMonHocDao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btMonHoc = findViewById(R.id.btMonHoc);
        MaterialButton btBanDo = findViewById(R.id.btBanDo);
        MaterialButton btTinTuc = findViewById(R.id.btTinTuc);
        MaterialButton btMXH = findViewById(R.id.btMXH);

        btMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MonHocActivity.class));

            }
        });

        btTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TinTucActivity.class));
            }
        });

        btBanDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        btMXH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MXHActivity.class));
            }
        });


    }
}