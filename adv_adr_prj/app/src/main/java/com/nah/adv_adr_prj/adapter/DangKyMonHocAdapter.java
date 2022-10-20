package com.nah.adv_adr_prj.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.nah.adv_adr_prj.R;
import com.nah.adv_adr_prj.model.MonHoc;
import com.nah.adv_adr_prj.model.ThongTin;
import com.nah.adv_adr_prj.services.DangKyMonHocService;
import com.nah.adv_adr_prj.services.DkMonHocSerVice;

import java.util.ArrayList;
import java.util.HashMap;

public class DangKyMonHocAdapter extends RecyclerView.Adapter<DangKyMonHocAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<MonHoc> list;
    private int id;
    private boolean isAll;

    public DangKyMonHocAdapter(Context context, ArrayList<MonHoc> list, int id, boolean isAll) {
        this.context = context;
        this.list = list;
        this.id = id;
        this.isAll = isAll;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dangkymonhoc, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCode.setText(String.valueOf(list.get(position).getCode()));
        holder.tvName.setText(String.valueOf(list.get(position).getName()));
        holder.tvTeacher.setText(String.valueOf(list.get(position).getTeacher()));

        if (list.get(position).getIsRegister() == id) {
            holder.btDangKy.setText("Huỷ đăng ký môn học");
            holder.btDangKy.setBackgroundColor(Color.TRANSPARENT);
            holder.btDangKy.setTextColor(Color.WHITE);
        } else {
            holder.btDangKy.setText("Đăng ký môn học");
            holder.btDangKy.setBackgroundColor(Color.CYAN);
            holder.btDangKy.setTextColor(Color.BLACK);
        }

        holder.btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trong Adapter ko goi dc Activity nen phai goi Context
                //trong Fragment phai goi getContext
                Intent i = new Intent(context, DkMonHocSerVice.class);
                Bundle b = new Bundle();
                b.putInt("id",id);
                b.putString("code", list.get(holder.getAdapterPosition()).getCode());
                b.putInt("isRegister", list.get(holder.getAdapterPosition()).getIsRegister());
                b.putBoolean("isAll", isAll);
                i.putExtras(b);
                context.startService(i);
            }
        });

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()).getListTT());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvCode, tvName, tvTeacher;
        MaterialButton btDangKy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCode = itemView.findViewById(R.id.tvCode);
            tvName = itemView.findViewById(R.id.tvName);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            btDangKy = itemView.findViewById(R.id.btDangKy);
        }
    }

    private void showDialog(ArrayList<ThongTin> list) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_thongtin,null);
        builder.setView(v);

        ListView lvTT = v.findViewById(R.id.lvTT);

        ArrayList<HashMap<String, Object>> listTT = new ArrayList<>();
        for (ThongTin tt: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("date","Ngày học: "+ tt.getDate());
            hs.put("address","Địa điểm: "+ tt.getAddress());
            listTT.add(hs);
        }

        SimpleAdapter spA = new SimpleAdapter(context,listTT,
                android.R.layout.simple_list_item_2,
                new String[]{"date", "address"},
                new int[]{android.R.id.text1, android.R.id.text2});

        lvTT.setAdapter(spA);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
