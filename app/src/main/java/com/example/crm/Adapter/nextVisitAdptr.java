package com.example.crm.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.POJO.AllNextVisitDataBean;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.R;

import java.util.List;

public class nextVisitAdptr extends RecyclerView.Adapter<nextVisitAdptr.ViewHolder> {

    List<AllNextVisitDataBean> detail_add_productlist;
    Context context;

    public nextVisitAdptr(List<AllNextVisitDataBean> detail_add_productlist, Context context) {
            this.detail_add_productlist = detail_add_productlist;
            this.context = context;
    }

    @NonNull
    @Override
    public nextVisitAdptr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_add_product_holder,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull nextVisitAdptr.ViewHolder holder, int position) {
//        holder.p_name.setText(detail_add_productlist.get(position).getProductName());
//        holder.p_brand.setText(detail_add_productlist.get(position).getBrand());
//        holder.existing_buy_shop.setText(detail_add_productlist.get(position).getExistingBuyShop());
//        holder.existing_buy_rate.setText(detail_add_productlist.get(position).getExistingBuyRate());
//        holder.qnt.setText(detail_add_productlist.get(position).getRequiredQty());
//        holder.Unit.setText(detail_add_productlist.get(position).getUnit());
//        holder.Stock.setText(detail_add_productlist.get(position).getStock());
    }

    @Override
    public int getItemCount()
    {
        Log.d("TAG", "getItemCount: "+detail_add_productlist.size());
        return detail_add_productlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        TextView p_name;
        TextView p_brand;
        TextView existing_buy_shop;
        TextView existing_buy_rate;
        TextView qnt;
        TextView Unit;
        TextView Stock;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            p_name = itemView.findViewById(R.id.p_name);
            p_brand = itemView.findViewById(R.id.p_brand);
            existing_buy_shop = itemView.findViewById(R.id.existing_buy_shop);
            existing_buy_rate = itemView.findViewById(R.id.existing_buy_rate);
            qnt = itemView.findViewById(R.id.qnt);
            Unit = itemView.findViewById(R.id.Unit);
            Stock = itemView.findViewById(R.id.Stock);
        }
    }
}
