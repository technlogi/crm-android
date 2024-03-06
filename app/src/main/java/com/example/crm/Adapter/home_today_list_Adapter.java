package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.fragment_main_todo_detail_POJO;
import com.example.crm.POJO.home_today_list_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;
import com.example.crm.view_all_todo_list;

import java.util.ArrayList;
import java.util.List;

public class home_today_list_Adapter extends RecyclerView.Adapter<home_today_list_Adapter.viewholder>{

    List<home_today_list_POJO.Result> item;
    ArrayList<home_today_list_POJO.Result> FullList;
    Context context;


    public home_today_list_Adapter(List<home_today_list_POJO.Result> item, Context context) {
        this.item = item;
//        FullList = new ArrayList<>(item);
        this.context = context;
    }

    @NonNull
    @Override
    public home_today_list_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mai_todo_detail_holder,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_today_list_Adapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        holder.name.setText(item.get(position).getCustomerName());
        holder.time_duration.setText(item.get(position).getDateTime());
        holder.address.setText(item.get(position).getAddress());
        holder.datetime.setText(item.get(position).getDateTime());

//        editor.putString("",)



        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, customer_detail_Activity.class);
                i.putExtra("visit_id",item.get(position).getVisitId());
                context.startActivity(i);
            }
        });

        holder.call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " +item.get(position).getContact() );
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + item.get(position).getContact()));
                context.startActivity(callIntent);
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView time_duration,datetime;
        TextView address;
        LinearLayout fra_list_ll1,fullLL;
        ImageView call_now;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            time_duration = itemView.findViewById(R.id.time_duration);
            address = itemView.findViewById(R.id.address);
            fra_list_ll1 = itemView.findViewById(R.id.fra_list_ll1);
            fullLL = itemView.findViewById(R.id.fullLL);
            call_now = itemView.findViewById(R.id.call_now);
            datetime = itemView.findViewById(R.id.datetime);
        }
    }
}
