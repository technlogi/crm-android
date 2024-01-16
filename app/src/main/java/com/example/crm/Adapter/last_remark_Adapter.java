package com.example.crm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.last_remark_POJO;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;

import java.util.List;

public class last_remark_Adapter extends RecyclerView.Adapter<last_remark_Adapter.viewholder> {

    List<last_remark_POJO> last_remark_list;
    Context context;

    public last_remark_Adapter(List<last_remark_POJO> last_remark_list, Context context) {
        this.last_remark_list = last_remark_list;
        this.context = context;
    }

//    public last_remark_Adapter(List<last_remark_POJO> last_remark_list, customer_detail_Activity context) {
//        this.last_remark_list = last_remark_list;
//        this.context = context;
//    }


    @NonNull
    @Override
    public last_remark_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_remark_holder, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull last_remark_Adapter.viewholder holder, int position) {

        holder.next_date.setText(last_remark_list.get(position).getNext_date());
        holder.next_time.setText(last_remark_list.get(position).getNext_time());
        holder.remark.setText(last_remark_list.get(position).getRemark());
        //holder.dilog_agent_code.setText(last_remark_list.get(position).getAgent_code());

    }

    @Override
    public int getItemCount() {
        return last_remark_list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView next_date;
        TextView next_time;
        TextView remark;
        //TextView dilog_agent_code;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            next_date = itemView.findViewById(R.id.next_date);
            next_time = itemView.findViewById(R.id.next_time);
            remark = itemView.findViewById(R.id.remark);
            //dilog_agent_code = itemView.findViewById(R.id.dilog_agent_code);

        }
    }
}
