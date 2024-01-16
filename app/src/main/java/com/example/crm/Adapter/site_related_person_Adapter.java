package com.example.crm.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.FormActivity;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.person_name_list;
import com.example.crm.POJO.site_person_list_POJO;
import com.example.crm.POJO.site_related_person_POJO;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class site_related_person_Adapter extends RecyclerView.Adapter<site_related_person_Adapter.ViewHolder> {

    List<site_related_person_POJO> itemList2;
    Context context;
    Activity activity;
    String count, sperson, sname, scontact,son_site,soff_site,sperson_id,sdob,sad_site_person_idname,sad_site_person_id;
    String waypathPhoto_book1;

    String dealer_id;
    String position_list;
    String colony_list;
    String customer_list;
    String product_list;
    String productname_list;
    String brand_list;
    String exis_list;
    String unit_list;
    String type_person_list;
    String type_personname_list;
    TextView next1, next2, next3, next4, submit_next5, pre1, pre2, pre3, pre4, pre5, add_product, add_site_related_persion;

    ProgressDialog pro;

    TextView page1_back_btn, page2_back_btn, page3_back_btn;
    TextView brand, vendor, rate, qty, pname, cal;
    List<visit_detail_POJO.Product> detail_add_productlist = new ArrayList<>();

    RelativeLayout layout_page1, layout_page2, layout_page3, layout_page4, layout_page5;
    AlertDialog dialog;
    ImageView back;
    String feedback = "";
    String chooser = "";
    String timetracker = "";
    String height;
    String width;
    String calculation;
    //p3 dialog
    TextInputEditText filledTextField1,  filledTextField4, filledTextField5, dailog_stock;
    TextInputEditText  site_filledTextField3, site_on, site_off;
    //p4 dialog

    final Calendar myCalendar= Calendar.getInstance();

    TextInputEditText p1_agent_code, p1_customer_name, p1_mobile1, p1_mobile2, p1_mobile3,
            p2_address, p2_colony, p2_mobile1,
            p4_remark;
    EditText edt_height, edt_weight;

    String s_tv_date_picker, s_tv_timee, s_p1_agent_code, s_p1_customer_list, s_p1_mobile1, s_p1_mobile2, s_p1_mobile3,
            s_p2_address, s_p2_colony_list, s_edt_height, c_edt_weight, s_p2_site_position_list,
            s_dialog_add_product, s_filledTextField2, s_filledTextField3, s_filledTextField4, s_filledTextField5, s_dailog_Unit, s_dailog_stock,
            s_dialog_type_person_list, s_site_filledTextField2, s_site_filledTextField3, s_site_on, s_site_off,
            s_tv_date_picker_page2, s_tv_time, s_p4_remark;

    String s_switch_togglebtn = "OFF";
    Switch switch_togglebtn;
    ImageView upload_img;
    ImageView preview;
    Uri uriPhoto;
    String waypathPhoto_book;
    TextView tv_date_picker;
    TextView tv_date_picker_page2;
    TextView tv_time, tv_timee;
    String dateTracker = "";
    private MaterialTimePicker picker;
    private Calendar calendar = Calendar.getInstance();
    List<site_related_person_POJO> person = new ArrayList<>();
    //    List<String> autocompettextviewID = new ArrayList<>();
    public static String sbrand, svendor, srate, sqty, spname;
    AutoCompleteTextView autoCompleteTxt, p1_customer_list, p2_colony_list, p2_site_position_list, dialog_add_product, dialog_type_person_list,site_filledTextField2, dailog_Unit, filledTextField2,filledTextField3;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems3;
    ArrayAdapter<String> adapterItems4;
    ArrayAdapter<String> adapterItems5;
    ArrayAdapter<String> adapterItems6;
    ArrayAdapter<String> adapterItemsname;
    ArrayAdapter<String> adapterItems7;
    ArrayAdapter<String> adapterItems8;
    int i = 0;
    String[] items = {"Position 1", "Position 2", "Position 3", "Position 4"};
    String[] items2 = {"jai", "harsh"};
    String dateTime, TIME;
    SimpleDateFormat simpleDateFormat;
    LinearLayoutManager linearLayout, linearLayoutManager2;

    List<String> autocompettextview = new ArrayList<>();
    List<String> autocompettextviewID = new ArrayList<>();

    List<String> colony_autocompettextview = new ArrayList<>();
    List<String> colony_autocompettextviewID = new ArrayList<>();

    List<String> position_autocompettextview = new ArrayList<>();
    List<String> position_autocompettextviewID = new ArrayList<>();

    List<String> product_autocompettextview = new ArrayList<>();
    List<String> product_autocompettextviewID = new ArrayList<>();

    List<String> brand_autocompettextview = new ArrayList<>();
    List<String> brand_autocompettextviewID = new ArrayList<>();

    List<String> exis_autocompettextview = new ArrayList<>();
    List<String> exis_autocompettextviewID = new ArrayList<>();

    List<String> unit_autocompettextview = new ArrayList<>();
    List<String> unit_autocompettextviewID = new ArrayList<>();

    String ad_pro_name, ad_pro_id;
    String ad_cusomer_name, ad_cusomer_id;
    String ad_colony_name, ad_colony_id;
    String ad_position_name, ad_position_id;
    String ad_site_person_name, ad_site_person_id;
    String ad_site_person_namename, ad_site_person_idname;
    String ad_brand_name, ad_brand_id;
    String ad_exis_name, ad_exis_id;
    String ad_unit_name, ad_unit_id;

    List<String> person_autocompettextview = new ArrayList<>();
    List<String> person_autocompettextviewID = new ArrayList<>();

    List<String> personname_autocompettextview = new ArrayList<>();
    List<String> personname_autocompettextviewID = new ArrayList<>();

    String date, time, agent_code, customer_name,customer_id="", mobile_1, mobile_2, mobile_3;
    String addresss, site_width, site_hight;
    RadioButton rb_ihb,rb_retailer,rb_dealer,rb_er,rb_contractor;
    RadioGroup rb_group;
    RelativeLayout rl_site_status;
    ConstraintLayout rl_site_size;
    TextInputLayout position_TxtInput;
    TextInputEditText site_person_mobile;
    AutoCompleteTextView customer_dob_picker,site_peron_dob;


    String P1Category = "IHB";
    String P2Colony= "";

    public site_related_person_Adapter(List<site_related_person_POJO> itemList2, Context context,Activity activity) {
        this.itemList2 = itemList2;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public site_related_person_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_related_problem_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull site_related_person_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.person.setText(itemList2.get(position).getPerson());
//        holder.name.setText(itemList2.get(position).getName() +"---"+ itemList2.get(position).getId());
        holder.name.setText(itemList2.get(position).getName());
        holder.contact.setText(itemList2.get(position).getContact());
        holder.on_site.setText(itemList2.get(position).getOn_sitee());
        holder.off_site.setText(itemList2.get(position).getOf_sitee());
        holder.dob.setText(itemList2.get(position).getdob());

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();
        StringBuilder stringBuilder5 = new StringBuilder();
        StringBuilder stringBuilder6 = new StringBuilder();
        StringBuilder stringBuilder7 = new StringBuilder();
        StringBuilder stringBuilderdob = new StringBuilder();

        for (int i = 0; i < itemList2.size(); i++) {
            count = String.valueOf(itemList2.size());
            // stringBuilder.append(getSelected().get(i).getName() + "," + getSelected().get(i).getPhone());

            stringBuilder.append(itemList2.get(i).getPerson());
            stringBuilder.append(",");

            stringBuilder2.append(itemList2.get(i).getName());
            stringBuilder2.append(",");

            stringBuilder3.append(itemList2.get(i).getContact());
            stringBuilder3.append(",");

            stringBuilder4.append(itemList2.get(i).getOn_sitee());
            stringBuilder4.append(",");

            stringBuilder5.append(itemList2.get(i).getOf_sitee());
            stringBuilder5.append(",");

            stringBuilder6.append(itemList2.get(i).getad_site_person_id());
            stringBuilder6.append(",");

            stringBuilder7.append(itemList2.get(i).getad_site_person_idname());
            stringBuilder7.append(",");

            stringBuilderdob.append(itemList2.get(i).getdob());
            stringBuilderdob.append(",");


        }

        sperson = String.valueOf(stringBuilder.deleteCharAt(stringBuilder.length() - 1));
        sname = String.valueOf(stringBuilder2.deleteCharAt(stringBuilder2.length() - 1));
        scontact = String.valueOf(stringBuilder3.deleteCharAt(stringBuilder3.length() - 1));
        son_site = String.valueOf(stringBuilder4.deleteCharAt(stringBuilder4.length() - 1));
        soff_site = String.valueOf(stringBuilder5.deleteCharAt(stringBuilder5.length() - 1));
        sad_site_person_id = String.valueOf(stringBuilder6.deleteCharAt(stringBuilder6.length() - 1));
        sad_site_person_idname = String.valueOf(stringBuilder7.deleteCharAt(stringBuilder7.length() - 1));
        sdob = String.valueOf(stringBuilderdob.deleteCharAt(stringBuilderdob.length() - 1));

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("site_sperson", sperson);
        editor.putString("site_sname", sname);
        editor.putString("site_scontact", scontact);
        editor.putString("site_son_site", son_site);
        editor.putString("site_soff_site", soff_site);
        editor.putString("site_sad_site_person_id", sad_site_person_id);
        editor.putString("site_sad_site_person_idname", sad_site_person_idname);
        editor.putString("site_sdob", sdob);
        editor.apply();

        Log.d("TAG", "sitenamecontact: " + sname);
        Log.d("TAG", "siteCount: " + count);


        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList2.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.fullLL.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad_site_person_id="";
                        ad_site_person_idname="";

                        Log.d("TAG", "onClick: 2323");
                        showAlertDialogButtonClicked3(v,position);
                    }
                }
        );
    }

    public void showAlertDialogButtonClicked3(View view1,int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.add_site_related_persion_dialog, null);
        builder.setView(customLayout);


        dialog_type_person_list = customLayout.findViewById(R.id.dialog_type_person_list);
        site_filledTextField2 = customLayout.findViewById(R.id.site_filledTextField2);
        site_filledTextField3 = customLayout.findViewById(R.id.site_filledTextField3);
        site_on = customLayout.findViewById(R.id.site_on);
        site_off = customLayout.findViewById(R.id.site_off);
        site_peron_dob = customLayout.findViewById(R.id.site_peron_dob);
        TextView add = customLayout.findViewById(R.id.add);

        dialog_type_person_list.setText(itemList2.get(i).getPerson());
        site_filledTextField2.setText(itemList2.get(i).getName());
        site_filledTextField3.setText(itemList2.get(i).getContact());
        site_on.setText(itemList2.get(i).getOn_sitee());
        site_off.setText(itemList2.get(i).getOf_sitee());
        site_peron_dob.setText(itemList2.get(i).getdob());


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="dd-MM-yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                site_peron_dob.setText(dateFormat.format(myCalendar.getTime()));

            }
        };

//        site_peron_dob.setText(dateTime);
        site_peron_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(context,/* R.style.datepicker,*/date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();


/*

                MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                materialDateBuilder.setTitleText("SELECT A DATE");
                final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

                materialDatePicker.show(((AppCompatActivity) context).getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        Calendar calendar = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            calendar.setTimeInMillis(selection);
                        }
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate  = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            formattedDate = format.format(calendar.getTime());
                        }

                        dateTracker = materialDatePicker.getHeaderText();

                        if (!dateTracker.equals("")) {
                        }
                        site_peron_dob.setText(formattedDate);

                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
*/
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dialog_type_person_list.getText().toString().equals(itemList2.get(i).getPerson())){
                    ad_pro_id = itemList2.get(i).getad_site_person_id();
                }
                if (site_filledTextField2.getText().toString().equals(itemList2.get(i).getName())){
                    ad_brand_id =itemList2.get(i).getad_site_person_idname();
                }

                if (validation4()) {
                    s_dialog_type_person_list = dialog_type_person_list.getText().toString().trim();
                    s_site_filledTextField2 = site_filledTextField2.getText().toString().trim();
                    s_site_filledTextField3 = site_filledTextField3.getText().toString().trim();
                    s_site_on = site_on.getText().toString().trim();
                    s_site_off = site_off.getText().toString().trim();

                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    itemList2.remove(i);
                    itemList2.add(i,new site_related_person_POJO(dialog_type_person_list.getText().toString(), site_filledTextField2.getText().toString(), site_filledTextField3.getText().toString(), ad_site_person_id, site_on.getText().toString(), site_off.getText().toString(),site_peron_dob.getText().toString(),ad_site_person_id,ad_site_person_idname));

//                    itemlist2.add(new site_related_person_POJO(dialog_type_person_list.getText().toString(), site_filledTextField2.getText().toString(), site_filledTextField3.getText().toString(), ad_site_person_id, site_on.getText().toString(), site_off.getText().toString(),site_peron_dob.getText().toString(),ad_site_person_id,ad_site_person_idname));
//                    site_related_person_adapter = new site_related_person_Adapter(itemlist2, activity);
//                    rec_site_persion.setLayoutManager(linearLayoutManager2);
//                    rec_site_persion.setAdapter(site_related_person_adapter);
//                    site_related_person_adapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                    dialog.dismiss();
                }

            }

            private boolean validation4() {
                if (TextUtils.isEmpty(dialog_type_person_list.getText().toString())) {
                    dialog_type_person_list.setError("Select person Name");
                    dialog_type_person_list.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(site_filledTextField2.getText().toString())) {
                    site_filledTextField2.setError("Enter Name");
                    site_filledTextField2.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(site_filledTextField3.getText().toString())) {
                    site_filledTextField3.setError("Enter Contact Number");
                    site_filledTextField3.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(site_peron_dob.getText().toString())) {
                    site_peron_dob.setError("Enter DOB");
                    site_peron_dob.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(site_on.getText().toString())) {
                    site_on.setError("Enter Number of site ON");
                    site_on.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(site_off.getText().toString())) {
                    site_off.setError("Enter Number of site OFF");
                    site_off.requestFocus();
                    return false;
                }
                return true;

            }
        });

        //person_list
        dialog_type_person_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                type_person_list = String.valueOf(charSequence);
                Log.d("TAG", "dggh: " + charSequence);
                if (type_person_list.length() == 2) {
                    callWeb5();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }

        });

        dialog_type_person_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ad_site_person_name = person_autocompettextview.get(position);
                ad_site_person_id = person_autocompettextviewID.get(position);

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();

                Log.d("TAG", "wdcwedcwdecwd: " + "wedcwdecwdc");

                Log.d("TAG", "ad_site_persossn_id: " + ad_site_person_id);
                Log.d("TAG", "ad_site_person_name: " + ad_site_person_name);

                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_site_person_id", ad_site_person_id);
                editor.putString("ad_site_person_name", ad_site_person_name);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog_type_person_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                feedback = item;

            }
        });

        site_filledTextField2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                productname_list = String.valueOf(charSequence);
                if (productname_list.length() == 2) {
                    callWebname();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        site_filledTextField2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ad_site_person_namename = personname_autocompettextview.get(position);
                ad_site_person_idname = personname_autocompettextviewID.get(position);

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                Log.d("TAG", "idkje: " + ad_site_person_idname);
                Log.d("TAG", "idkjewqe: " + ad_site_person_namename);

                SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_site_person_idname", ad_site_person_idname);
                editor.putString("ad_site_person_namename", ad_site_person_namename);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        site_filledTextField2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    private void callWeb5() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Log.d("spr", "latitude: "+sharedPreferences.getString("latitude", "")+" longitude "+sharedPreferences.getString("longitude", "")+" user_id "+sharedPreferences.getString("user_id", "")+" IMEI_no "+sharedPreferences.getString("IMEI_no", ""));

        WebService.getClient().site_person_list(type_person_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<site_person_list_POJO>() {
            @Override
            public void onResponse(Call<site_person_list_POJO> call, Response<site_person_list_POJO> response) {

                if (response.body().getResult() != null) {
//                    if (response.body().getMessage().equals("Customer list")) {

                    if (person_autocompettextviewID.size() > 0) {

                        person_autocompettextview.clear();
                        person_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        person_autocompettextview.add(response.body().getResult().get(i).getPersonName());
                        person_autocompettextviewID.add(response.body().getResult().get(i).getPersonId());
                    }
                    Log.d("TAG", "person_autocompettextview: " + person_autocompettextview);
                    Log.d("TAG", "person_autocompettextviewID: " + person_autocompettextviewID);
                    adapterItems6 = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, person_autocompettextview);
                    dialog_type_person_list.setAdapter(adapterItems6);


                    dialog_type_person_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            if (person_autocompettextview.size() == 0) {

                            } else {
                                ad_site_person_name = person_autocompettextview.get(i);
                                ad_site_person_id = person_autocompettextviewID.get(i);
                            }

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                            Log.d("TAG", "wdcwedcwdecwd: " + "wedcwdecwdc");

                            Log.d("TAG", "ad_site_persossn_id: " + ad_site_person_id);
                            Log.d("TAG", "ad_site_person_name: " + ad_site_person_name);

                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();
                            editor.putString("ad_site_person_id", ad_site_person_id);
                            editor.putString("ad_site_person_name", ad_site_person_name);
                            editor.apply();
                        }
                    });

                } else {
                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<site_person_list_POJO> call, Throwable t) {

            }
        });
    }


    private void callWebname() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        Log.d("spr", "latitude: "+sharedPreferences.getString("latitude", "")+" longitude "+sharedPreferences.getString("longitude", "")+" user_id "+sharedPreferences.getString("user_id", "")+" IMEI_no "+sharedPreferences.getString("IMEI_no", ""));

        WebService.getClient().site_personname_list(productname_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<person_name_list>() {
            @Override
            public void onResponse(Call<person_name_list> call, Response<person_name_list> response) {

                if (response.body().getResult() != null) {
//                    if (response.body().getMessage().equals("Customer list")) {

                    if (personname_autocompettextviewID.size() > 0) {

                        personname_autocompettextview.clear();
                        personname_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        personname_autocompettextview.add(response.body().getResult().get(i).getPersonName());
                        personname_autocompettextviewID.add(response.body().getResult().get(i).getPersonId());
                    }
                    Log.d("TAG", "person_autocompettextview: " + personname_autocompettextview);
                    Log.d("TAG", "person_autocompettextviewID: " + personname_autocompettextviewID);
                    adapterItemsname = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, personname_autocompettextview);
                    site_filledTextField2.setAdapter(adapterItemsname);


                    site_filledTextField2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            if (personname_autocompettextview.size() == 0) {

                            } else {
                                ad_site_person_namename = personname_autocompettextview.get(i);
                                ad_site_person_idname = personname_autocompettextviewID.get(i);
                            }

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                            Log.d("TAG", "wdcwedcwdecwd: " + "wedcwdecwdc");

                            Log.d("TAG", "ad_site_persossn_id: " + ad_site_person_idname);
                            Log.d("TAG", "ad_site_person_name: " + ad_site_person_namename);

                            SharedPreferences location = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();
                            editor.putString("ad_site_person_idname", ad_site_person_idname);
                            editor.putString("ad_site_person_namename", ad_site_person_namename);
                            editor.apply();
                        }
                    });

                } else {
                    Toast.makeText(context, "No response", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<person_name_list> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView person;
        TextView name;
        TextView contact;
        TextView on_site;
        TextView off_site,dob;
        ImageView cancel;
        ImageView fullLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            person = itemView.findViewById(R.id.person);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            cancel = itemView.findViewById(R.id.cancel);
            on_site = itemView.findViewById(R.id.on_site);
            off_site = itemView.findViewById(R.id.off_site);
            dob = itemView.findViewById(R.id.dob);
            fullLL = itemView.findViewById(R.id.fullLL);

        }
    }
}
