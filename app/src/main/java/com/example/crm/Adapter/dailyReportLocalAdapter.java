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

import com.example.crm.POJO.AllReportDataBean;
import com.example.crm.R;
import com.example.crm.edit_daily_report;

import java.util.ArrayList;


public class dailyReportLocalAdapter extends RecyclerView.Adapter<dailyReportLocalAdapter.viewholder> {

    ArrayList<AllReportDataBean> item;
    ArrayList<AllReportDataBean> FullList;
    Context context;

    public dailyReportLocalAdapter(ArrayList<AllReportDataBean> item, Context context) {
        this.item = item;
        FullList = new ArrayList<>(item);
        this.context = context;

    }


    @NonNull
    @Override
    public dailyReportLocalAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_report_adapter_layout, parent, false);
        return new dailyReportLocalAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dailyReportLocalAdapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_date1.setText(item.get(position).getREPORT_DATE_COL().substring(0,2));
        holder.tv_date2.setText(item.get(position).getREPORT_DATE_COL().substring(3,5));
        holder.tv_date3.setText(item.get(position).getREPORT_DATE_COL().substring(8));//here
        holder.tv_okm.setText(item.get(position).getREPORT_OPENKM_COL());
        holder.tv_ckm.setText(item.get(position).getREPORT_CLOSEKM_COL());
        holder.tv_rkm.setText("no data"/*item.get(position).getRunningKm()*/);
        holder.tv_ndp.setText(item.get(position).getREPORT_RREMARK_COL());

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Log.d("TAG", "onClick111: "+item.get(position).getREPORT_CLOSEKM_PHOTO_COL());
//                Intent i = new Intent(context, edit_daily_report.class);
//                i.putExtra("opening km",item.get(position).getREPORT_OPENKM_COL());
//                i.putExtra("closing km",item.get(position).getREPORT_CLOSEKM_COL());
//                i.putExtra("next_plan",item.get(position).getREPORT_RREMARK_COL());
//                i.putExtra("report_id",item.get(position).getREPORT_TABLE_ITEM_ID());
//                i.putExtra("opening_photo",item.get(position).getREPORT_OPENKM_PHOTO_COL());
//                i.putExtra("closing_photo",item.get(position).getREPORT_CLOSEKM_PHOTO_COL());
//                i.putExtra("coming_from","Local");
//                context.startActivity(i);
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
