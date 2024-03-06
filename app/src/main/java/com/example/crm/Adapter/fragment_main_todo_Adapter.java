package com.example.crm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.fragment_main_todo_POJO;
import com.example.crm.POJO.fragment_main_todo_detail_POJO;
import com.example.crm.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_main_todo_Adapter extends RecyclerView.Adapter<fragment_main_todo_Adapter.viewholder>{

        List<fragment_main_todo_POJO> itemList;
        Context context;

    public fragment_main_todo_Adapter(List<fragment_main_todo_POJO> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }



    @NonNull
    @Override
    public fragment_main_todo_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_todo_holder_horizontal,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fragment_main_todo_Adapter.viewholder holder, int position) {

        holder.day.setText(itemList.get(position).getDay());
        holder.date.setText(itemList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView day;
        TextView date;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
        }
    }
}
