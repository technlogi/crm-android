package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.crm.POJO.daily_report_list_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;
import com.example.crm.edit_daily_report;

import java.util.ArrayList;
import java.util.List;

public class daily_report_adapter extends RecyclerView.Adapter<daily_report_adapter.viewholder> {

    List<daily_report_list_POJO.Result> item;
    ArrayList<daily_report_list_POJO.Result> FullList;
    Context context;

    public daily_report_adapter(List<daily_report_list_POJO.Result> item, Context context) {
        this.item = item;
        FullList = new ArrayList<>(item);
        this.context = context;
    }


    @NonNull
    @Override
    public daily_report_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_report_adapter_layout, parent, false);
        return new daily_report_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull daily_report_adapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_date1.setText(item.get(position).getDate().substring(0,2));
        holder.tv_date2.setText(item.get(position).getDate().substring(3,5));
        holder.tv_date3.setText(item.get(position).getDate().substring(8));//here
        holder.tv_okm.setText(item.get(position).getOpKm());
        holder.tv_ckm.setText(item.get(position).getClKm());
        holder.tv_rkm.setText(item.get(position).getRunningKm());
        holder.tv_ndp.setText(item.get(position).getNextDayPlan());

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick111: "+item.get(position).getClosingPhoto());
                Intent i = new Intent(context, edit_daily_report.class);
                i.putExtra("opening km",item.get(position).getOpKm());
                i.putExtra("closing km",item.get(position).getClKm());
                i.putExtra("next_plan",item.get(position).getNextDayPlan());
                i.putExtra("report_id",item.get(position).getId());
                i.putExtra("opening_photo",item.get(position).getOpeningPhoto());
                i.putExtra("closing_photo",item.get(position).getClosingPhoto());
                i.putExtra("coming_from","API");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tv_date1,tv_date2,tv_date3,tv_okm,tv_ckm,tv_rkm,tv_ndp;
        LinearLayout fullLL;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_date1 = itemView.findViewById(R.id.tv_date1);
            tv_okm = itemView.findViewById(R.id.tv_okm);
            tv_ckm = itemView.findViewById(R.id.tv_ckm);
            tv_rkm = itemView.findViewById(R.id.tv_rkm);
            tv_ndp = itemView.findViewById(R.id.tv_ndp);
            tv_date2 = itemView.findViewById(R.id.tv_date2);
            tv_date3 = itemView.findViewById(R.id.tv_date3);


            fullLL = itemView.findViewById(R.id.fullLL);
        }
    }
}
