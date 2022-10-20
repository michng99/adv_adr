package com.nah.adv_adr_prj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MonHocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_hoc);

        MaterialButton bt_DangKyMonHoc = findViewById(R.id.bt_DangKyMonHoc);
        MaterialButton bt_MonDaDangKy = findViewById(R.id.bt_MonHocDaDangKy);

        bt_DangKyMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MonHocActivity.this, DangKyMonHocAct.class);
                Bundle b = new Bundle();
                b.putBoolean("isAll", true);
                i.putExtras(b);
                startActivity(i);
            }
        });

        bt_MonDaDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MonHocActivity.this, DangKyMonHocAct.class);
                Bundle b = new Bundle();
                b.putBoolean("isAll", false);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }
}