package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.R;

import com.example.crm.view_all_todo_list;

import java.util.ArrayList;
import java.util.List;

public class  customer_list_Adapter extends RecyclerView.Adapter<customer_list_Adapter.viewholder> implements Filterable {

    List<customer_list_POJO.Result> item;
    ArrayList<customer_list_POJO.Result> FullList;
    Context context;


    public customer_list_Adapter(List<customer_list_POJO.Result> item, Context context) {
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
            ArrayList<customer_list_POJO.Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (customer_list_POJO.Result item : FullList) {

                    if (item.getCustomerName() != null || item.getAddress() != null) {
                        if (item.getCustomerName().toLowerCase().contains(filterPattern) || item.getAddress().toLowerCase().contains(filterPattern))
                        {
                            filteredList.add(item);
                            if (item.getCustomerName().toLowerCase().contains(filterPattern) && item.getAddress().toLowerCase().contains(filterPattern))
                            {
                                filteredList.remove(item);
                            }
                        }
                        else {
                            filteredList.remove(item);
                        }


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
    public customer_list_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_holder, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customer_list_Adapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.c_name.setText(item.get(position).getCustomerName());
        holder.c_address.setText(item.get(position).getAddress());

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, view_all_todo_list.class);
                i.putExtra("customer_id", item.get(position).getCustomerId());
                context.startActivity(i);
            }
        });

        holder.cust_list_call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " + item.get(position).getContact());
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

        TextView c_name;
        TextView c_address;
        CardView cust_list;
        LinearLayout fullLL;
        ImageView cust_list_call_now;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            c_name = itemView.findViewById(R.id.c_name);
            c_address = itemView.findViewById(R.id.c_address);
            cust_list = itemView.findViewById(R.id.cust_list);
            cust_list_call_now = itemView.findViewById(R.id.cust_list_call_now);
            fullLL = itemView.findViewById(R.id.fullLL);
        }
    }
}
