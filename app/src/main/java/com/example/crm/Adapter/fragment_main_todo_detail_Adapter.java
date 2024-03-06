package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.fragment_main_todo_POJO;
import com.example.crm.POJO.fragment_main_todo_detail_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;

import java.util.ArrayList;
import java.util.List;

public class fragment_main_todo_detail_Adapter extends RecyclerView.Adapter<fragment_main_todo_detail_Adapter.viewholder> implements Filterable {

    List<fragment_main_todo_detail_POJO.Result> item;
    ArrayList<fragment_main_todo_detail_POJO.Result> FullList;
    Context context;

    public fragment_main_todo_detail_Adapter(List<fragment_main_todo_detail_POJO.Result> item, Context context) {
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
            Log.d("TAG", "performFiltering: "+"234" );
            ArrayList<fragment_main_todo_detail_POJO.Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (fragment_main_todo_detail_POJO.Result item : FullList) {

                    if (item.getCustomerName() != null || item.getAddress() != null) {
                        if (item.getCustomerName().toLowerCase().contains(filterPattern) || item.getAddress().toLowerCase().contains(filterPattern) )
                        {
                            filteredList.add(item);
                            if (item.getCustomerName().toLowerCase().contains(filterPattern) && item.getAddress().toLowerCase().contains(filterPattern) )
                            {
                                filteredList.remove(item);
                            }
                       }

                        else {
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
    public fragment_main_todo_detail_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mai_todo_detail_holder,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fragment_main_todo_detail_Adapter.viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(item.get(position).getCustomerName());
        holder.time_duration.setText(item.get(position).getDateTime());
        holder.address.setText(item.get(position).getAddress());
        holder.datetime.setText(item.get(position).getDateTime());


        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.d("TAG", "RecyclerElectric1:"+item.get(position).getVisitId());
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
            name= itemView.findViewById(R.id.name);
            time_duration = itemView.findViewById(R.id.time_duration);
            address = itemView.findViewById(R.id.address);
            fra_list_ll1 = itemView.findViewById(R.id.fra_list_ll1);
            fullLL = itemView.findViewById(R.id.fullLL);
            call_now = itemView.findViewById(R.id.call_now);
            datetime = itemView.findViewById(R.id.datetime);
        }
    }
}
