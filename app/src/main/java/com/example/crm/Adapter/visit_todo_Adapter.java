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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.visit_todo_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;
import com.example.crm.view_all_todo_list;

import java.util.ArrayList;
import java.util.List;

public class visit_todo_Adapter extends RecyclerView.Adapter<visit_todo_Adapter.ViewHolder> implements Filterable {

    List<visit_todo_POJO.Result> item;
    ArrayList<visit_todo_POJO.Result> FullList;
    Context context;
    String visit_id;

    public visit_todo_Adapter(List<visit_todo_POJO.Result> item, Context context) {
        this.item = item;
        FullList = new ArrayList<>(item);
        this.context = context;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private final Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<visit_todo_POJO.Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (visit_todo_POJO.Result item : FullList) {
                    if (item.getCustomerName().toLowerCase().contains(filterPattern) || item.getAddress().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                        if (item.getCustomerName().toLowerCase().contains(filterPattern) && item.getAddress().toLowerCase().contains(filterPattern) ) {
                            filteredList.remove(item);
                        }
//                        if (item.getParentItemTitle().toLowerCase().contains(filterPattern) ){
//                            filteredList.remove(item);
//                        }

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            item.clear();
            item.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_to_do_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        holder.work.setText(item.get(position).getCustomerName());
        holder.time_duration.setText(item.get(position).getDateTime());
        holder.address.setText(item.get(position).getAddress());

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, customer_detail_Activity.class);
                i.putExtra("visit_id",item.get(position).getVisitId());
                context.startActivity(i);
            }
        });
        holder.idividual_call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " +item.get(position).getContact() );
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + item.get(position).getCustomerName()));
                context.startActivity(callIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView work;
        TextView time_duration;
        TextView address;
        LinearLayout visiter_list,fullLL;
        ImageView idividual_call_now;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            work = itemView.findViewById(R.id.work);
            time_duration = itemView.findViewById(R.id.time_duration);
            visiter_list = itemView.findViewById(R.id.visiter_list);
            address = itemView.findViewById(R.id.address);
            idividual_call_now = itemView.findViewById(R.id.idividual_call_now);
            fullLL = itemView.findViewById(R.id.fullLL);
        }
    }
}
