package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.sidePersionPOJO;
import com.example.crm.R;

import java.util.List;

public abstract class ShowSidePersonAdapter extends RecyclerView.Adapter<ShowSidePersonAdapter.ViewHolder> {

    List<sidePersionPOJO> itemList;
    Context context;
    Activity activity;

    public ShowSidePersonAdapter(List<sidePersionPOJO> itemList, Context context, Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }

    public abstract void onItemChanged(List<add_product_POJO> list);

    @NonNull
    @Override
    public ShowSidePersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_side_person_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSidePersonAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvsitePersion.setText(itemList.get(position).getSITE_PERSON()+" :");
        holder.tvSitePerType.setText(": "+itemList.get(position).getSITE_PERSON());
        holder.tvSitePerNm.setText(": "+itemList.get(position).getSITE_PERSON_ID_NAME());
        holder.tvSitePerContact.setText(": "+itemList.get(position).getSITE_CONTACT());
        holder.tvDob.setText(": "+itemList.get(position).getCUSTOMER_DOB());
        holder.tvOnsite.setText(": "+itemList.get(position).getSITE_ON_SITE());
        holder.tvOffSite.setText(": "+itemList.get(position).getSITE_OFF_SITE());
        holder.tvNextVisitDateTime.setText(": "+itemList.get(position).getDATE_PAGE2()+","+itemList.get(position).getTIME_PAGE2());
        holder.tvRemark.setText(": "+itemList.get(position).getREMARK());

//
//        holder.cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
//
//        holder.fullLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
TextView tvSitePerType,tvSitePerNm,tvSitePerContact,tvDob,tvOnsite,tvOffSite,tvNextVisitDateTime,tvRemark,tvsitePersion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        tvSitePerType= itemView.findViewById(R.id.tv_type_of_person);
        tvSitePerNm= itemView.findViewById(R.id.tv_person_name);
        tvSitePerContact= itemView.findViewById(R.id.tv_contact);
        tvDob= itemView.findViewById(R.id.tv_dob);
        tvOnsite= itemView.findViewById(R.id.tv_on_site);
        tvOffSite= itemView.findViewById(R.id.tv_off_site);
        tvNextVisitDateTime= itemView.findViewById(R.id.tv_next_visit_date);
        tvRemark= itemView.findViewById(R.id.tv_remark);
        tvsitePersion=itemView.findViewById(R.id.tv_side_persone_detail);
        }
    }
}
