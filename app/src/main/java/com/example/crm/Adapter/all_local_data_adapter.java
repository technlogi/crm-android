package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.ActVisitDetailLocalData;
import com.example.crm.POJO.AllDataBean;

import com.example.crm.R;
import com.google.gson.Gson;


import java.util.ArrayList;


public class all_local_data_adapter extends RecyclerView.Adapter<all_local_data_adapter.viewholder> {

    ArrayList<AllDataBean> item;
//    ArrayList<AllDataBean> FullList;
    Context context;

    public all_local_data_adapter(ArrayList<AllDataBean> item, Context context) {
        this.item = item;
//        FullList = new ArrayList<>(item);
        this.context = context;

    }


    @NonNull
    @Override
    public all_local_data_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_adapter_layout, parent, false);
        return new all_local_data_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull all_local_data_adapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvCustomerNm.setText(": "+item.get(position).getCUSTOMER_NAME_COL());
        holder.tvDate.setText(item.get(position).getDATE_COL());

        holder.tvCustomerType.setText(item.get(position).getCATEGORY());
        holder.tvMobileo.setText(item.get(position).getCUSTOMER_MOBILE1_COL());


        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TAG", "onClick111: "+item.get(position).getClosingPhoto());
                Intent i = new Intent(context, ActVisitDetailLocalData.class);
                Gson gson = new Gson();
                String json = gson.toJson(item.get(position));
                i.putExtra("bean", json);
//                i.putExtra("bean", item.get(position).toString());
                context.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tvDate,tvCustomerType,tvMobileo,tvCustomerNm/*,tv_ckm*//*,tv_rkm,tv_ndp*/;
        LinearLayout fullLL;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvCustomerNm = itemView.findViewById(R.id.tv_customer_nm);
            tvCustomerType = itemView.findViewById(R.id.tv_customer_type);
            tvMobileo = itemView.findViewById(R.id.tv_mobile_no);
            fullLL = itemView.findViewById(R.id.full_detail);
        }
    }
}
