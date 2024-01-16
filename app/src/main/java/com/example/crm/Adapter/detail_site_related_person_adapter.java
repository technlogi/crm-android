package com.example.crm.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.detail_add_product_pojo;
import com.example.crm.POJO.detail_site_related_person_pojo;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;

import java.util.List;

public class detail_site_related_person_adapter extends RecyclerView.Adapter<detail_site_related_person_adapter.ViewHolder> {


    List<visit_detail_POJO.Person> detail_site_person;
    Context context;

    public detail_site_related_person_adapter(List<visit_detail_POJO.Person> detail_site_person, Context context) {
        this.detail_site_person = detail_site_person;
        this.context = context;
    }


    @NonNull
    @Override
    public detail_site_related_person_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_site_related_person,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull detail_site_related_person_adapter.ViewHolder holder, int position) {
        holder.person.setText(detail_site_person.get(position).getTypeOfPerson());
        holder.name.setText(detail_site_person.get(position).getName());
        holder.m_number.setText(detail_site_person.get(position).getContactNo());
        holder.on_site.setText(detail_site_person.get(position).getONSite());
        holder.off_site.setText(detail_site_person.get(position).getOFFSite());
        holder.dob.setText(detail_site_person.get(position).getPersonDob());

    }

    @Override
    public int getItemCount() {
        //Log.d("TAG", "getItemCount: "+detail_site_person.size());
        return detail_site_person.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView person;
        TextView name;
        TextView m_number;
        TextView on_site;
        TextView off_site;
        TextView dob;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            person = itemView.findViewById(R.id.person);
            name = itemView.findViewById(R.id.name);
            m_number = itemView.findViewById(R.id.m_number);
            on_site = itemView.findViewById(R.id.on_site);
            off_site = itemView.findViewById(R.id.off_site);
            dob = itemView.findViewById(R.id.dob);
        }
    }
}
