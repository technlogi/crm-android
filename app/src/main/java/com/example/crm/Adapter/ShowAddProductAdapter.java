package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.Network.WebService;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.brand_POJO;
import com.example.crm.POJO.product_list_POJO;
import com.example.crm.POJO.unit_list_POJO;
import com.example.crm.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ShowAddProductAdapter extends RecyclerView.Adapter<ShowAddProductAdapter.ViewHolder> {

    List<add_product_POJO> itemList;
    Context context;
    Activity activity;

    public ShowAddProductAdapter(List<add_product_POJO> itemList, Context context, Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }

    public abstract void onItemChanged(List<add_product_POJO> list);

    @NonNull
    @Override
    public ShowAddProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_product_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAddProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.pname.setText(itemList.get(position).getPname());
        holder.brand.setText(itemList.get(position).getBrand());
        holder.vendor.setText(itemList.get(position).getVendor());
        holder.rate.setText(itemList.get(position).getRate());
        holder.qty.setText(itemList.get(position).getQty());
        holder.unit.setText(itemList.get(position).getUnit());
        holder.Stock.setText(itemList.get(position).getStock());

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pname;
        TextView brand;
        TextView vendor;
        TextView rate;
        TextView qty;
        ImageView cancel;
        TextView unit;
        TextView Stock;
        ImageView fullLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.pname);
            brand = itemView.findViewById(R.id.brand);
            vendor = itemView.findViewById(R.id.vendor);
            rate = itemView.findViewById(R.id.rate);
            Stock = itemView.findViewById(R.id.Stock);
            unit = itemView.findViewById(R.id.unit);
            qty = itemView.findViewById(R.id.qty);
            cancel = itemView.findViewById(R.id.cancel);
            fullLL = itemView.findViewById(R.id.fullLL);
            cancel.setVisibility(View.INVISIBLE);
            fullLL.setVisibility(View.INVISIBLE);
        }
    }
}
