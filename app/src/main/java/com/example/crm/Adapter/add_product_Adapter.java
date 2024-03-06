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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.FormActivity;
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

public abstract class add_product_Adapter extends RecyclerView.Adapter<add_product_Adapter.ViewHolder> {

    List<add_product_POJO> itemList;
    Context context;
    Activity activity;
    String count,name,sbrand,svendor,srate,sqty,sunit,sStock,sProid,sBrandid,sUnitid;
    AlertDialog dialog;
    String feedback = "";
    String product_list;
    String ad_pro_name, ad_pro_id="";
    String ad_exis_name, ad_exis_id;
    String unit_list;
    String exis_list;
    String brand_list;
    ArrayAdapter<String> adapterItems8;
    ArrayAdapter<String> adapterItems7;
    ArrayAdapter<String> adapterItems5;
    String ad_brand_id="",ad_brand_name,ad_unit_id="",ad_unit_name;
    List<String> position_autocompettextview = new ArrayList<>();
    List<String> position_autocompettextviewID = new ArrayList<>();
    List<String> brand_autocompettextview = new ArrayList<>();
    List<String> brand_autocompettextviewID = new ArrayList<>();
    List<String> exis_autocompettextview = new ArrayList<>();
    List<String> exis_autocompettextviewID = new ArrayList<>();
    List<String> unit_autocompettextview = new ArrayList<>();
    List<String> unit_autocompettextviewID = new ArrayList<>();
    List<String> product_autocompettextview = new ArrayList<>();
    List<String> product_autocompettextviewID = new ArrayList<>();
    AutoCompleteTextView  dialog_add_product, dailog_Unit, filledTextField2;
    TextInputEditText filledTextField4, filledTextField5,dailog_stock, filledTextField3;
    String s_tv_date_picker, s_tv_timee, s_p1_agent_code, s_p1_customer_list, s_p1_mobile1, s_p1_mobile2, s_p1_mobile3,
            s_p2_address, s_p2_colony_list, s_edt_height, c_edt_weight, s_p2_site_position_list,
            s_dialog_add_product, s_filledTextField2, s_filledTextField3, s_filledTextField4, s_filledTextField5, s_dailog_Unit, s_dailog_stock,
            s_dialog_type_person_list, s_site_filledTextField2, s_site_filledTextField3, s_site_on, s_site_off,
            s_tv_date_picker_page2, s_tv_time, s_p4_remark;

    public add_product_Adapter(List<add_product_POJO> itemList, Context context,Activity activity) {
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }

    public abstract void onItemChanged(List<add_product_POJO> list);

    @NonNull
    @Override
    public add_product_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_product_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull add_product_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.pname.setText(itemList.get(position).getPname());
        holder.brand.setText(itemList.get(position).getBrand());
        holder.vendor.setText(itemList.get(position).getVendor());
        holder.rate.setText(itemList.get(position).getRate());
        holder.qty.setText(itemList.get(position).getQty());
        holder.unit.setText(itemList.get(position).getUnit());
        holder.Stock.setText(itemList.get(position).getStock());

//        List<String> list = new ArrayList<>();
//        list.add("");
//        list.add("Paul Smith");
//        list.add("Raja P");
//        list.add("Ezhu Malai");
//
//        String s = TextUtils.join(", ", list);
//
//        Log.d("LOGTAG", s);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();
        StringBuilder stringBuilder5 = new StringBuilder();
        StringBuilder stringBuilder6 = new StringBuilder();
        StringBuilder stringBuilder7 = new StringBuilder();
        StringBuilder stringBuilder8 = new StringBuilder();
        StringBuilder stringBuilder9 = new StringBuilder();
        StringBuilder stringBuilder10 = new StringBuilder();

        for (int i = 0; i < itemList.size(); i++)

        {
            count = String.valueOf(itemList.size());
            // stringBuilder.append(getSelected().get(i).getName() + "," + getSelected().get(i).getPhone());
            stringBuilder.append(itemList.get(i).getPname());
            stringBuilder.append(",");

            stringBuilder2.append(itemList.get(i).getBrand());

           Log.d("itemList.get(i()",itemList.get(i).getBrand()) ;
            stringBuilder2.append(",");

            stringBuilder3.append(itemList.get(i).getVendor());
            stringBuilder3.append(",");

            stringBuilder4.append(itemList.get(i).getRate());
            stringBuilder4.append(",");

            stringBuilder5.append(itemList.get(i).getQty());
            stringBuilder5.append(",");

            stringBuilder6.append(itemList.get(i).getUnit());
            stringBuilder6.append(",");

            stringBuilder7.append(itemList.get(i).getStock());
            stringBuilder7.append(",");

            stringBuilder8.append(itemList.get(i).getad_pro_id());
            stringBuilder8.append(",");

            stringBuilder9.append(itemList.get(i).getad_brand_id());
            stringBuilder9.append(",");

            stringBuilder10.append(itemList.get(i).getad_unit_id());
            stringBuilder10.append(",");
        }
        name = String.valueOf(stringBuilder.deleteCharAt(stringBuilder.length() - 1));
        sbrand = String.valueOf(stringBuilder2.deleteCharAt(stringBuilder2.length() - 1));
        svendor = String.valueOf(stringBuilder3.deleteCharAt(stringBuilder3.length() - 1));
        srate = String.valueOf(stringBuilder4.deleteCharAt(stringBuilder4.length() - 1));
        sqty = String.valueOf(stringBuilder5.deleteCharAt(stringBuilder5.length() - 1));
        sunit = String.valueOf(stringBuilder6.deleteCharAt(stringBuilder6.length() - 1));
        sStock = String.valueOf(stringBuilder7.deleteCharAt(stringBuilder7.length() - 1));
        sProid = String.valueOf(stringBuilder8.deleteCharAt(stringBuilder8.length() - 1));
        sBrandid = String.valueOf(stringBuilder9.deleteCharAt(stringBuilder9.length() - 1));
        sUnitid = String.valueOf(stringBuilder10.deleteCharAt(stringBuilder10.length() - 1));

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("add_product", name);
        editor.putString("add_brand", sbrand);
        editor.putString("add_vendor", svendor);
        editor.putString("add_rate", srate);
        editor.putString("add_qty", sqty);
        editor.putString("add_unit", sunit);
        editor.putString("add_Stock", sStock);
        editor.putString("add_proId", sProid);
        editor.putString("add_brandId", sBrandid);
        editor.putString("add_unitId", sUnitid);

        editor.apply();
        if (stringBuilder.length() > 1)Log.d("TAG", "onClick12: " + stringBuilder.deleteCharAt(stringBuilder.length() - 1));
        Log.d("TAG", "namecontact: " + name);
        Log.d("TAG", "sbrand: " + sbrand);
        Log.d("TAG", "Count: " + count);


        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.remove(position);
                onItemChanged(itemList);
            }
        });

        holder.fullLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_pro_id="";
                ad_unit_id="";
                ad_brand_id="";
                Log.d("TAG", "onClick: 2323");
                showAlertDialogButtonClicked2(v,position);
            }
        });

    }

    public void showAlertDialogButtonClicked2(View view,int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.add_product_dialog, null);
        builder.setView(customLayout);


        dialog_add_product = customLayout.findViewById(R.id.dialog_add_product);
        filledTextField2 = customLayout.findViewById(R.id.filledTextField2);
        filledTextField3 = customLayout.findViewById(R.id.filledTextField3);
        filledTextField4 = customLayout.findViewById(R.id.filledTextField4);
        filledTextField5 = customLayout.findViewById(R.id.filledTextField5);
        dailog_Unit = customLayout.findViewById(R.id.dailog_Unit);
        dailog_stock = customLayout.findViewById(R.id.dailog_stock);
        TextView add = customLayout.findViewById(R.id.add);

        dialog_add_product.setText(itemList.get(i).getPname());
        filledTextField2.setText(itemList.get(i).getBrand());
        filledTextField3.setText(itemList.get(i).getVendor());
        filledTextField4.setText(itemList.get(i).getRate());
        filledTextField5.setText(itemList.get(i).getQty());
        dailog_Unit.setText(itemList.get(i).getUnit());
        dailog_stock.setText(itemList.get(i).getStock());


        //product_list
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_add_product.getText().toString().equals(itemList.get(i).getPname())){
                    ad_pro_id = itemList.get(i).getad_pro_id();
                }
                if (filledTextField2.getText().toString().equals(itemList.get(i).getBrand())){
                    ad_brand_id =itemList.get(i).getad_brand_id();
                }
                if (dailog_Unit.getText().toString().equals(itemList.get(i).getUnit())){
                    ad_unit_id =itemList.get(i).getad_unit_id();
                }




                if (validation3()) {
                    s_dialog_add_product = dialog_add_product.getText().toString().trim();
                    s_filledTextField2 = filledTextField2.getText().toString().trim();
                    s_filledTextField3 = filledTextField3.getText().toString().trim();
                    s_filledTextField4 = filledTextField4.getText().toString().trim();
                    s_filledTextField5 = filledTextField5.getText().toString().trim();
                    s_dailog_Unit = dailog_Unit.getText().toString().trim();
                    s_dailog_stock = dailog_stock.getText().toString().trim();

                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    itemList.remove(i);
                    itemList.add(i,new add_product_POJO(dialog_add_product.getText().toString(), filledTextField2.getText().toString(), filledTextField3.getText().toString(), filledTextField4.getText().toString(), filledTextField5.getText().toString(), dailog_Unit.getText().toString(), dailog_stock.getText().toString(),ad_pro_id,ad_brand_id,ad_unit_id));


//                    itemlist.add(new add_product_POJO(dialog_add_product.getText().toString(), filledTextField2.getText().toString(), filledTextField3.getText().toString(), filledTextField4.getText().toString(), filledTextField5.getText().toString(), dailog_Unit.getText().toString(), dailog_stock.getText().toString(),ad_pro_id,ad_brand_id,ad_unit_id));
//                    add_product_adapter = new add_product_Adapter(itemlist, activity,activity);
//                    rec_product.setAdapter(add_product_adapter);
                    onItemChanged(itemList);
                    dialog.dismiss();
                }


            }


            private boolean validation3() {
                if (TextUtils.isEmpty(dialog_add_product.getText().toString())) {
                    dialog_add_product.setError("Select Product Name");
                    dialog_add_product.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(filledTextField2.getText().toString())) {
                    filledTextField2.setError("Enter Brand Name");
                    filledTextField2.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(filledTextField3.getText().toString())) {
                    filledTextField3.setError("Enter Existing buy shop");
                    filledTextField3.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(filledTextField4.getText().toString())) {
                    filledTextField4.setError("Enter Buy Rate");
                    filledTextField4.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(filledTextField5.getText().toString())) {
                    filledTextField5.setError("Enter Qty");
                    filledTextField5.requestFocus();
                    return false;

                }
                //change shubham
                if (TextUtils.isEmpty(dailog_Unit.getText().toString())) {
                    dailog_Unit.setError("Enter Unit");
                    dailog_Unit.requestFocus();
                    return false;

                }
                if (TextUtils.isEmpty(dailog_stock.getText().toString())) {
                    dailog_stock.setError("Enter Stock");
                    dailog_stock.requestFocus();
                    return false;

                }
                return true;

            }
        });


        //sproduct list
        dialog_add_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                product_list = String.valueOf(charSequence);
                if (product_list.length() >= 2) {
                    callWeb4();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        dialog_add_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ad_pro_name = position_autocompettextview.get(position);
                ad_pro_id = position_autocompettextviewID.get(position);

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                Log.d("TAG", "idkje: " + ad_pro_id);
                Log.d("TAG", "idkjewqe: " + ad_pro_name);

                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_pro_id", ad_pro_id);
                editor.putString("pro_name", ad_pro_name);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog_add_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                feedback = item;

            }
        });

        //brand
        filledTextField2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                brand_list = String.valueOf(charSequence);
                if (brand_list.length() >= 2) {
                    callWeb6();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        filledTextField3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                exis_list = String.valueOf(charSequence);
                if (exis_list.length() >= 2) {
                    callWebnew();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        filledTextField2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ad_brand_name = brand_autocompettextview.get(position);
                ad_brand_id = brand_autocompettextviewID.get(position);

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                Log.d("TAG", "idkje: " + ad_brand_id);
                Log.d("TAG", "idkjewqe: " + ad_brand_name);

                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_brand_id", ad_brand_id);
                editor.putString("ad_brand_name", ad_brand_name);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        filledTextField3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                ad_exis_name = exis_autocompettextview.get(position);
//                ad_exis_id = exis_autocompettextviewID.get(position);
//
////                            ad_pro_name = parent.getItemAtPosition(position).toString();
////                            ad_pro_id = parent.getItemAtPosition(position).toString();
//
//
//                Log.d("TAG", "idkje: " + ad_exis_id);
//                Log.d("TAG", "idkjewqe: " + ad_exis_name);
//
//                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = location.edit();
//
//                editor.putString("ad_exis_id", ad_exis_id);
//                editor.putString("ad_exis_name", ad_exis_name);
//                editor.apply();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        filledTextField2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                feedback = item;

            }
        });

//        filledTextField3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String item = parent.getItemAtPosition(position).toString();
//                feedback = item;
//
//            }
//        });


        //unit
        dailog_Unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                unit_list = String.valueOf(charSequence);
                if (unit_list.length() >= 2) {
                    callWeb7();
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        dailog_Unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ad_unit_name = unit_autocompettextview.get(position);
                ad_unit_id = unit_autocompettextviewID.get(position);

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                Log.d("TAG", "idkje: " + ad_unit_id);
                Log.d("TAG", "idkjewqe: " + ad_unit_name);

                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_unit_id", ad_unit_id);
                editor.putString("ad_unit_name", ad_unit_name);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dailog_Unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                feedback = item;

            }
        });


        ImageView product_dia_cancel = customLayout.findViewById(R.id.product_dia_cancel);

        product_dia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    private void callWeb7() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        WebService.getClient().unit_list(unit_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<unit_list_POJO>() {
            @Override
            public void onResponse(Call<unit_list_POJO> call, Response<unit_list_POJO> response) {
                Log.d("TAG", "check123 "+response.body().getResult());
                if (response.body().getResult() != null) {

                    if (unit_autocompettextviewID.size() > 0) {

                        unit_autocompettextview.clear();
                        unit_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        unit_autocompettextview.add(response.body().getResult().get(i).getUnitName());
                        unit_autocompettextviewID.add(response.body().getResult().get(i).getUnitId());
                    }

                    Log.d("TAG", "position_autocompettextview: " + unit_autocompettextview);
                    Log.d("TAG", "position_autocompettextviewID: " + unit_autocompettextviewID);

                    adapterItems8 = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unit_autocompettextview);
                    dailog_Unit.setAdapter(adapterItems8);
                    adapterItems8.notifyDataSetChanged();

                    dailog_Unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (unit_autocompettextview.size() == 0) {

                            } else {

                                ad_unit_name = unit_autocompettextview.get(i);
                                ad_unit_id = unit_autocompettextviewID.get(i);

                            }
                            Log.d("TAG", "adqw_pro_name: " + ad_unit_name);
                            Log.d("TAG", "ad_prqwo_id: " + ad_unit_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();

                            editor.putString("ad_unit_id", ad_unit_id);
                            editor.putString("ad_unit_name", ad_unit_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<unit_list_POJO> call, Throwable t) {

            }
        });
    }

    private void callWebnew() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        WebService.getClient().brand(brand_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<brand_POJO>() {
            @Override
            public void onResponse(Call<brand_POJO> call, Response<brand_POJO> response) {
                if (response.body().getResult() != null) {

                    if (exis_autocompettextviewID.size() > 0) {

                        exis_autocompettextview.clear();
                        exis_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        exis_autocompettextview.add(response.body().getResult().get(i).getBrandName());
                        exis_autocompettextviewID.add(response.body().getResult().get(i).getBrandId());
                    }

                    Log.d("TAG", "position_autocompettextview: " + exis_autocompettextview);
                    Log.d("TAG", "position_autocompettextviewID: " + exis_autocompettextviewID);

//                    adapterItems7 = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, exis_autocompettextview);
//                    filledTextField3.setAdapter(adapterItems7);
//                    adapterItems7.notifyDataSetChanged();

//                    filledTextField3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                            if (exis_autocompettextview.size() == 0) {
//
//                            } else {
//
//                                ad_exis_name = exis_autocompettextview.get(i);
//                                ad_exis_id = exis_autocompettextviewID.get(i);
//
//                            }
//                            Log.d("TAG", "ad_pro_name: " + ad_exis_name);
//                            Log.d("TAG", "ad_pro_id: " + ad_exis_id);
//
//
////                            ad_pro_name = parent.getItemAtPosition(position).toString();
////                            ad_pro_id = parent.getItemAtPosition(position).toString();
//
//
//                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = location.edit();
//
//                            editor.putString("ad_exis_id", ad_exis_id);
//                            editor.putString("ad_exis_name", ad_exis_name);
//                            editor.apply();
//                        }
//                    });

                } else {
//                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<brand_POJO> call, Throwable t) {

            }
        });

    }


    private void callWeb6() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        WebService.getClient().brand(brand_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<brand_POJO>() {
            @Override
            public void onResponse(Call<brand_POJO> call, Response<brand_POJO> response) {
                if (response.body().getResult() != null) {

                    if (brand_autocompettextviewID.size() > 0) {

                        brand_autocompettextview.clear();
                        brand_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        brand_autocompettextview.add(response.body().getResult().get(i).getBrandName());
                        brand_autocompettextviewID.add(response.body().getResult().get(i).getBrandId());
                    }

                    Log.d("TAG", "position_autocompettextview: " + brand_autocompettextview);
                    Log.d("TAG", "position_autocompettextviewID: " + brand_autocompettextviewID);

                    adapterItems7 = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brand_autocompettextview);
                    filledTextField2.setAdapter(adapterItems7);
                    adapterItems7.notifyDataSetChanged();

                    filledTextField2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (brand_autocompettextview.size() == 0) {

                            } else {

                                ad_brand_name = brand_autocompettextview.get(i);
                                ad_brand_id = brand_autocompettextviewID.get(i);

                            }
                            Log.d("TAG", "ad_pro_name: " + ad_brand_name);
                            Log.d("TAG", "ad_pro_id: " + ad_brand_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();

                            editor.putString("ad_brand_id", ad_brand_id);
                            editor.putString("ad_brand_name", ad_brand_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(activity, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<brand_POJO> call, Throwable t) {

            }
        });

    }


    private void callWeb4() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        WebService.getClient().product_list(product_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<product_list_POJO>() {
            @Override
            public void onResponse(Call<product_list_POJO> call, Response<product_list_POJO> response) {
                if (response.body().getResult() != null) {

                    if (product_autocompettextviewID.size() > 0) {

                        product_autocompettextview.clear();
                        product_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        product_autocompettextview.add(response.body().getResult().get(i).getProductName());
                        product_autocompettextviewID.add(response.body().getResult().get(i).getProductId());
                    }

                    Log.d("TAG", "position_autocompettextview: " + product_autocompettextview);
                    Log.d("TAG", "position_autocompettextviewID: " + product_autocompettextviewID);

                    adapterItems5 = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, product_autocompettextview);
                    dialog_add_product.setAdapter(adapterItems5);
                    adapterItems5.notifyDataSetChanged();

                    dialog_add_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (product_autocompettextview.size() == 0) {

                            } else {

                                ad_pro_name = product_autocompettextview.get(i);
                                ad_pro_id = product_autocompettextviewID.get(i);

                            }
                            Log.d("TAG", "ad_pro_name: " + ad_pro_name);
                            Log.d("TAG", "ad_pro_id: " + ad_pro_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();

//                            if (location.getString()){
//
//                            }else{
//
//                            }

                            editor.putString("pro_id", ad_pro_id);
                            editor.putString("pro_name", ad_pro_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(activity, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<product_list_POJO> call, Throwable t) {

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
        }
    }
}
