package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
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

import com.bumptech.glide.Glide;
import com.example.crm.Adapter.add_product_Adapter;
import com.example.crm.Adapter.site_related_person_Adapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllDataBean;
import com.example.crm.POJO.Edit_form_POJO;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.brand_POJO;
import com.example.crm.POJO.colony_list_POJO;
import com.example.crm.POJO.customer_list_dropdown_POJO;
import com.example.crm.POJO.person_name_list;
import com.example.crm.POJO.product_list_POJO;
import com.example.crm.POJO.site_person_list_POJO;
import com.example.crm.POJO.site_position_list_POJO;
import com.example.crm.POJO.site_related_person_POJO;

import com.example.crm.POJO.unit_list_POJO;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.POJO.visit_form_POJO;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


@RequiresApi(api = Build.VERSION_CODES.N)
public class FormActivity extends AppCompatActivity implements LocationListener {

    public static boolean flag = false;
    public static boolean flag2 = false;

    RecyclerView rec_product, rec_site_persion;
    add_product_Adapter add_product_adapter;
    ArrayList<add_product_POJO> itemlist;

    LinearLayout LocationButton;

    private LocationRequest locationRequest;

    site_related_person_Adapter site_related_person_adapter;
    ArrayList<site_related_person_POJO> itemlist2;

    MultipartBody.Part imagePartPhoto_book1 = null;


    String waypathPhoto_book1="";
    String waypathPhoto_book_edit="";

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
    TextInputLayout p1_customer_list_drownndown;
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


    TextInputEditText p1_agent_code, p1_customer_name, p1_mobile1, p1_mobile2, p1_mobile3,
            p2_address, p2_colony, p2_mobile1,
            p4_remark;
    EditText edt_height, edt_weight, edt_site_status;

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
    AutoCompleteTextView autoCompleteTxt,p1_customer_list,  p2_colony_list, p2_site_position_list, dialog_add_product, dialog_type_person_list,site_filledTextField2, dailog_Unit,filledTextField2;
    TextInputEditText filledTextField3;
    ArrayAdapter<String> adapterItems;

    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems9;
    ArrayAdapter<String> adapterItems10;
    ArrayAdapter<String> adapterItems11;
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
    List<String> autocompettextviewphone1 = new ArrayList<>();
    List<String> autocompettextviewphone2 = new ArrayList<>();
    List<String> autocompettextviewphone3 = new ArrayList<>();

    List<String> autocompettextdob = new ArrayList<>();

    List<String> autocompettextaddress = new ArrayList<>();

    List<String> autocompettextcolonyid = new ArrayList<>();

    List<String> autocompettextcolony = new ArrayList<>();

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
    String ad_cusomer_name, ad_cusomer_id,ad_customer_phone1,ad_customer_phone2,ad_customer_phone3,ad_customer_dob, ad_customer_address;
    String ad_colony_name, ad_colony_id;
    String ad_position_name, ad_position_id;
    String ad_site_person_name, ad_site_person_id;
    String ad_site_person_namename, ad_site_person_idname, ad_site_person_contact_no, ad_site_person_dob, ad_site_person_on_site, ad_site_person_off_site;
    String ad_brand_name, ad_brand_id;
    String ad_exis_name, ad_exis_id;
    String ad_unit_name, ad_unit_id;

    List<String> person_autocompettextview = new ArrayList<>();
    List<String> person_autocompettextviewID = new ArrayList<>();

    List<String> personname_autocompettextview = new ArrayList<>();
    List<String> personname_autocompettextviewID = new ArrayList<>();

    List<String> personname_autocompettextcontactno = new ArrayList<>();

    List<String> personname_autocompettextDob = new ArrayList<>();

    List<String> personname_autocompettextOnSite = new ArrayList<>();

    List<String> personname_autocompettextOffSite = new ArrayList<>();

    String date, time, agent_code, customer_name,customer_id="", mobile_1, mobile_2, mobile_3;
    String addresss, site_width, site_hight;
    RadioButton rb_ihb,rb_retailer,rb_dealer,rb_er,rb_contractor;
    RadioGroup rb_group;
    RelativeLayout rl_site_status;
    ConstraintLayout rl_site_size;
    TextInputLayout position_TxtInput;
    TextInputEditText site_person_mobile;
    AutoCompleteTextView customer_dob_picker,site_peron_dob;
    final Calendar myCalendar= Calendar.getInstance();


    String P1Category = "IHB";

    String ContactMode = "";

    String P2Colony= "";

    String P2Position = "";
    DBHandler dbHandler;

    String url;


    TextView TextView2,TextView1,TextView3,TextView4,TextView5;

    TextView contactModeSiteVisit, contactModeOnCall;
    private okhttp3.Response.Builder response;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_form);

        dbHandler = new DBHandler(FormActivity.this);

        TextView1 = findViewById(R.id.TextView1);
        TextView2 = findViewById(R.id.TextView2);
        TextView3 = findViewById(R.id.TextView3);
        TextView4 = findViewById(R.id.TextView4);
        TextView5 = findViewById(R.id.TextView5);
        edt_site_status = findViewById(R.id.edt_site_status);
        p1_customer_list_drownndown = findViewById(R.id.p1_customer_list_drownndown);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        TextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P1Category = "IHB";
                rl_site_size.setVisibility(View.VISIBLE);
                rl_site_status.setVisibility(View.VISIBLE);
                position_TxtInput.setVisibility(View.VISIBLE);
                TextView1.setBackgroundColor(getColor(R.color.main));
                TextView1.setTextColor(getColor(R.color.white));

                TextView2.setBackground(getDrawable(R.drawable.radio_border));
                TextView2.setTextColor(getColor(R.color.main));
                TextView3.setBackground(getDrawable(R.drawable.radio_border));
                TextView3.setTextColor(getColor(R.color.main));
                TextView4.setBackground(getDrawable(R.drawable.radio_border));
                TextView4.setTextColor(getColor(R.color.main));
                TextView5.setBackground(getDrawable(R.drawable.radio_border));
                TextView5.setTextColor(getColor(R.color.main));

            }
        });
        TextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P1Category = "RETAILER";
                Log.d("TAG", "onCheckedChanged: "+ P1Category);
                rl_site_size.setVisibility(View.GONE);
                rl_site_status.setVisibility(View.GONE);
                position_TxtInput.setVisibility(View.GONE);
                TextView2.setBackgroundColor(getColor(R.color.main));
                TextView2.setTextColor(getColor(R.color.white));

                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));
                TextView3.setBackground(getDrawable(R.drawable.radio_border));
                TextView3.setTextColor(getColor(R.color.main));
                TextView4.setBackground(getDrawable(R.drawable.radio_border));
                TextView4.setTextColor(getColor(R.color.main));
                TextView5.setBackground(getDrawable(R.drawable.radio_border));
                TextView5.setTextColor(getColor(R.color.main));

            }
        });
        TextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P1Category = "DEALER";
                rl_site_size.setVisibility(View.GONE);
                rl_site_status.setVisibility(View.GONE);
                position_TxtInput.setVisibility(View.GONE);
                TextView3.setBackgroundColor(getColor(R.color.main));
                TextView3.setTextColor(getColor(R.color.white));

                TextView2.setBackground(getDrawable(R.drawable.radio_border));
                TextView2.setTextColor(getColor(R.color.main));
                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));

                TextView4.setBackground(getDrawable(R.drawable.radio_border));
                TextView4.setTextColor(getColor(R.color.main));
                TextView5.setBackground(getDrawable(R.drawable.radio_border));
                TextView5.setTextColor(getColor(R.color.main));

            }
        });
        TextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P1Category = "ER";
                rl_site_size.setVisibility(View.GONE);
                rl_site_status.setVisibility(View.GONE);
                position_TxtInput.setVisibility(View.GONE);
                TextView4.setBackgroundColor(getColor(R.color.main));
                TextView4.setTextColor(getColor(R.color.white));

                TextView2.setBackground(getDrawable(R.drawable.radio_border));
                TextView2.setTextColor(getColor(R.color.main));
                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));
                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));
                TextView3.setBackground(getDrawable(R.drawable.radio_border));
                TextView3.setTextColor(getColor(R.color.main));

                TextView5.setBackground(getDrawable(R.drawable.radio_border));
                TextView5.setTextColor(getColor(R.color.main));

            }
        });
        TextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P1Category = "CONTRACTOR";
                rl_site_size.setVisibility(View.GONE);
                rl_site_status.setVisibility(View.GONE);
                position_TxtInput.setVisibility(View.GONE);
                TextView5.setBackgroundColor(getColor(R.color.main));
                TextView5.setTextColor(getColor(R.color.white));

                TextView2.setBackground(getDrawable(R.drawable.radio_border));
                TextView2.setTextColor(getColor(R.color.main));
                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));
                TextView1.setBackground(getDrawable(R.drawable.radio_border));
                TextView1.setTextColor(getColor(R.color.main));
                TextView3.setBackground(getDrawable(R.drawable.radio_border));
                TextView3.setTextColor(getColor(R.color.main));
                TextView4.setBackground(getDrawable(R.drawable.radio_border));
                TextView4.setTextColor(getColor(R.color.main));

            }
        });


        contactModeSiteVisit = findViewById(R.id.contactModeSiteVisit);
        contactModeOnCall = findViewById(R.id.contactModeOnCall);


        contactModeSiteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactMode = "SiteVisit";

                contactModeSiteVisit.setBackgroundColor(getColor(R.color.main));
                contactModeSiteVisit.setTextColor(getColor(R.color.white));

                contactModeOnCall.setBackground(getDrawable(R.drawable.radio_border));
                contactModeOnCall.setTextColor(getColor(R.color.main));


            }
        });
        contactModeOnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactMode = "OnCall";
                Log.d("TAG", "onCheckedChanged: "+ ContactMode);

                contactModeOnCall.setBackgroundColor(getColor(R.color.main));
                contactModeOnCall.setTextColor(getColor(R.color.white));

                contactModeSiteVisit.setBackground(getDrawable(R.drawable.radio_border));
                contactModeSiteVisit.setTextColor(getColor(R.color.main));


            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        sbrand = sharedPreferences.getString("sbrand", "");
        spname = sharedPreferences.getString("spname", "");
        svendor = sharedPreferences.getString("svendor", "");
        srate = sharedPreferences.getString("srate", "");
        sqty = sharedPreferences.getString("sqty", "");
        next1 = findViewById(R.id.next1);
        next2 = findViewById(R.id.next2);
        next3 = findViewById(R.id.next3);
        next4 = findViewById(R.id.next4);

        rb_ihb = findViewById(R.id.rb_ihb);
        rb_retailer = findViewById(R.id.rb_retailer);
        rb_dealer = findViewById(R.id.rb_dealer);
        rb_er = findViewById(R.id.rb_er);
        rb_contractor = findViewById(R.id.rb_contractor);
        rb_group = findViewById(R.id.rb_group);
        rl_site_status = findViewById(R.id.rl_site_status);
        rl_site_size = findViewById(R.id.rl_site_size);
        position_TxtInput = findViewById(R.id.position_TxtInput);
        customer_dob_picker = findViewById(R.id.customer_dob_picker);
//        site_person_mobile = findViewById(R.id.site_person_mobile);
//        site_peron_dob = findViewById(R.id.site_peron_dob);


        submit_next5 = findViewById(R.id.submit_next5);
        pre1 = findViewById(R.id.pre1);
        pre2 = findViewById(R.id.pre2);
        pre3 = findViewById(R.id.pre3);
        pre4 = findViewById(R.id.pre4);
        pre5 = findViewById(R.id.pre5);
        edt_height = findViewById(R.id.edt_height);
        edt_weight = findViewById(R.id.edt_weight);
        cal = findViewById(R.id.cal);
        rec_product = findViewById(R.id.rec_product);
        rec_site_persion = findViewById(R.id.rec_site_persion);
        p1_agent_code = findViewById(R.id.p1_agent_code);
//        p1_customer_name = findViewById(R.id.p1_customer_name);
        p1_mobile1 = findViewById(R.id.p1_mobile1);
        p1_mobile2 = findViewById(R.id.p1_mobile2);
        p1_mobile3 = findViewById(R.id.p1_mobile3);
        p2_address = findViewById(R.id.p2_address);

//        p2_colony = findViewById(R.id.p2_colony);
        p4_remark = findViewById(R.id.p4_remark);

        //change
        add_product = findViewById(R.id.add_product);
        add_site_related_persion = findViewById(R.id.add_site_related_persion);
        upload_img = findViewById(R.id.upload_img);
        preview = findViewById(R.id.preview);
        tv_date_picker = findViewById(R.id.tv_date_picker);
        tv_date_picker_page2 = findViewById(R.id.tv_date_picker_page2);
        tv_time = findViewById(R.id.tv_time);
        tv_timee = findViewById(R.id.tv_timee);

        layout_page1 = findViewById(R.id.layout_page1);
        layout_page2 = findViewById(R.id.layout_page2);
        layout_page3 = findViewById(R.id.layout_page3);
        layout_page4 = findViewById(R.id.layout_page4);
        layout_page5 = findViewById(R.id.layout_page5);


        //add old data in form(autofill)
        Intent i = getIntent();

        date = i.getStringExtra("date");
        time = i.getStringExtra("time");
        agent_code = i.getStringExtra("agent_code");
        customer_name = i.getStringExtra("customer_name");

        mobile_1 = i.getStringExtra("mobile_1");
        mobile_2 = i.getStringExtra("mobile_2");
        mobile_3 = i.getStringExtra("mobile_3");

//        Log.d("detail_add_productlist", customer_detail_Activity.detail_add_productlist.toString());
        tv_date_picker.setText(date);
//        customer_dob_picker.setText(date);
//        site_peron_dob.setText(date);
        tv_timee.setText(time);
        tv_timee.setFocusable(false);
        p1_agent_code.setText(agent_code);
//        p1_customer_list.setText(Integer.parseInt(customer_name));
//        p1_customer_list.setSelection(ad_position_id);
//        p1_customer_list.setText(customer_name, false);
        p1_mobile1.setText(mobile_1);
        p1_mobile2.setText(mobile_2);
        p1_mobile3.setText(mobile_3);

        addresss = i.getStringExtra("addresss");
        site_width = i.getStringExtra("site_width");
        site_hight = i.getStringExtra("site_hight");

//        mobile_1=i.getStringExtra("mobile_1");
//        mobile_2=i.getStringExtra("mobile_2");
//        mobile_3=i.getStringExtra("mobile_3");

        p2_address.setText(addresss);
        edt_height.setText(site_width);
        edt_weight.setText(site_hight);


        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.TextView1:
                        P1Category = "IHB";
                        Log.d("TAG", "onCheckedChanged: "+ P1Category);
                        rl_site_size.setVisibility(View.VISIBLE);
                        rl_site_status.setVisibility(View.VISIBLE);
                        position_TxtInput.setVisibility(View.VISIBLE);
                        break;
                    case R.id.TextView2:
                        P1Category = "RETAILER";
                        Log.d("TAG", "onCheckedChanged: "+ P1Category);
                        rl_site_size.setVisibility(View.GONE);
                        rl_site_status.setVisibility(View.GONE);
                        position_TxtInput.setVisibility(View.GONE);
                        break;
                    case R.id.TextView3:
                        P1Category = "DEALER";
                        Log.d("TAG", "onCheckedChanged: "+ P1Category);
                        rl_site_size.setVisibility(View.GONE);
                        rl_site_status.setVisibility(View.GONE);
                        position_TxtInput.setVisibility(View.GONE);
                        break;
                    case R.id.TextView4:
                        P1Category = "ER";
                        rl_site_size.setVisibility(View.GONE);
                        rl_site_status.setVisibility(View.GONE);
                        position_TxtInput.setVisibility(View.GONE);
                        break;
                    case R.id.TextView5:
                        P1Category = "CONTRACTOR";
                        rl_site_size.setVisibility(View.GONE);
                        rl_site_status.setVisibility(View.GONE);
                        position_TxtInput.setVisibility(View.GONE);
                        break;
                }
            }
        });

        pro = new ProgressDialog(this);

        pro.setCancelable(false);
        pro.setMessage("Uploading...");

        Log.d("spr", "latitude: "+sharedPreferences.getString("latitude", "")+" longitude "+sharedPreferences.getString("longitude", "")+" user_id "+sharedPreferences.getString("user_id", "")+" IMEI_no "+sharedPreferences.getString("IMEI_no", ""));
        if (flag) {
            pro.show();
            flag2 = true;
            WebService.getClient().visit_detail_POJO(sharedPreferences.getString("visit_id", ""),
                    sharedPreferences.getString("user_id", ""),
                    sharedPreferences.getString("latitude", ""),
                    sharedPreferences.getString("longitude", ""),
                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<visit_detail_POJO>() {
                @Override
                public void onResponse(Call<visit_detail_POJO> call, Response<visit_detail_POJO> response) {
                    //flag = false;

                    pro.dismiss();

                    if (response.body() != null) {
                        if(response.body().getResult().getProduct()!=null){
                            for (int i = 0; i < response.body().getResult().getProduct().size(); i++) {
                                itemlist.add(new add_product_POJO(
                                        response.body().getResult().getProduct().get(i).getProductName(),
                                        response.body().getResult().getProduct().get(i).getBrand(),
                                        response.body().getResult().getProduct().get(i).getExistingBuyShop(),
                                        response.body().getResult().getProduct().get(i).getExistingBuyRate(),
                                        response.body().getResult().getProduct().get(i).getRequiredQty(),
                                        response.body().getResult().getProduct().get(i).getUnit(),
                                        response.body().getResult().getProduct().get(i).getStock(),
                                        response.body().getResult().getProduct().get(i).getProductId(),
                                        response.body().getResult().getProduct().get(i).getBrandId(),
                                        response.body().getResult().getProduct().get(i).getUnitId()
                                ));
                            }
                        }


                        add_product_adapter = new add_product_Adapter(itemlist, FormActivity.this, FormActivity.this) {
                            @Override
                            public void onItemChanged(List<add_product_POJO> list) {
                                itemlist = new ArrayList<>(list);
                                add_product_adapter.notifyDataSetChanged();
                            }
                        };
                        rec_product.setAdapter(add_product_adapter);

                        if (response.body().getResult().getPerson()!=null){

                            for (int i = 0; i < response.body().getResult().getPerson().size(); i++) {


                                itemlist2.add(new site_related_person_POJO(
//                                    dialog_type_person_list.getText().toString(),
                                        response.body().getResult().getPerson().get(i).getTypeOfPerson(),
                                        response.body().getResult().getPerson().get(i).getName(),
                                        response.body().getResult().getPerson().get(i).getContactNo(),
                                        response.body().getResult().getPerson().get(i).getTypeOfPersonId(),
                                        response.body().getResult().getPerson().get(i).getONSite(),
                                        response.body().getResult().getPerson().get(i).getOFFSite(),
                                        response.body().getResult().getPerson().get(i).getPersonDob(),
                                        response.body().getResult().getPerson().get(i).getTypeOfPersonId(),
                                        response.body().getResult().getPerson().get(i).getPersonId()
                                ));

                            }

                        }



                        site_related_person_adapter = new site_related_person_Adapter(itemlist2, FormActivity.this,FormActivity.this);
                        rec_site_persion.setAdapter(site_related_person_adapter);
                        url=response.body().getResult().getImage();

                        Log.e("url123",url);

//                        Glide.with(FormActivity.this).load(url).into(preview);
                        waypathPhoto_book1.isEmpty();
                        System.out.println("hhhhh" + waypathPhoto_book1);


//                        tv_date_picker.setText(response.body().getResult().getVisitDate());

                        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                        tv_date_picker.setText(dateTime);

//                        customer_dob_picker.setText(response.body().getResult().getVisitDate());
//                        site_peron_dob.setText(response.body().getResult().getVisitDate());
                        customer_id = response.body().getResult().getCustomerId();
                        P2Colony = response.body().getResult().getColonyId();
                        P2Position = response.body().getResult().getPostion_list_id();

                        if (response.body().getResult().getCategory().equals("IHB")){

                            P1Category = "IHB";
                            rl_site_size.setVisibility(View.VISIBLE);
                            rl_site_status.setVisibility(View.VISIBLE);
                            position_TxtInput.setVisibility(View.VISIBLE);
                            TextView1.setBackgroundColor(getColor(R.color.main));
                            TextView1.setTextColor(getColor(R.color.white));

                            TextView2.setBackground(getDrawable(R.drawable.radio_border));
                            TextView2.setTextColor(getColor(R.color.main));
                            TextView3.setBackground(getDrawable(R.drawable.radio_border));
                            TextView3.setTextColor(getColor(R.color.main));
                            TextView4.setBackground(getDrawable(R.drawable.radio_border));
                            TextView4.setTextColor(getColor(R.color.main));
                            TextView5.setBackground(getDrawable(R.drawable.radio_border));
                            TextView5.setTextColor(getColor(R.color.main));

                        }else if(response.body().getResult().getCategory().equals("RETAILER")){

                            P1Category = "RETAILER";
                            Log.d("TAG", "onCheckedChanged: "+ P1Category);
                            rl_site_size.setVisibility(View.GONE);
                            rl_site_status.setVisibility(View.GONE);
                            position_TxtInput.setVisibility(View.GONE);
                            TextView2.setBackgroundColor(getColor(R.color.main));
                            TextView2.setTextColor(getColor(R.color.white));

                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));
                            TextView3.setBackground(getDrawable(R.drawable.radio_border));
                            TextView3.setTextColor(getColor(R.color.main));
                            TextView4.setBackground(getDrawable(R.drawable.radio_border));
                            TextView4.setTextColor(getColor(R.color.main));
                            TextView5.setBackground(getDrawable(R.drawable.radio_border));
                            TextView5.setTextColor(getColor(R.color.main));

                        }else if(response.body().getResult().getCategory().equals("DEALER")){

                            P1Category = "DEALER";
                            rl_site_size.setVisibility(View.GONE);
                            rl_site_status.setVisibility(View.GONE);
                            position_TxtInput.setVisibility(View.GONE);
                            TextView3.setBackgroundColor(getColor(R.color.main));
                            TextView3.setTextColor(getColor(R.color.white));

                            TextView2.setBackground(getDrawable(R.drawable.radio_border));
                            TextView2.setTextColor(getColor(R.color.main));
                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));

                            TextView4.setBackground(getDrawable(R.drawable.radio_border));
                            TextView4.setTextColor(getColor(R.color.main));
                            TextView5.setBackground(getDrawable(R.drawable.radio_border));
                            TextView5.setTextColor(getColor(R.color.main));

                        }else if(response.body().getResult().getCategory().equals("ER")){

                            P1Category = "ER";
                            rl_site_size.setVisibility(View.GONE);
                            rl_site_status.setVisibility(View.GONE);
                            position_TxtInput.setVisibility(View.GONE);
                            TextView4.setBackgroundColor(getColor(R.color.main));
                            TextView4.setTextColor(getColor(R.color.white));

                            TextView2.setBackground(getDrawable(R.drawable.radio_border));
                            TextView2.setTextColor(getColor(R.color.main));
                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));
                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));
                            TextView3.setBackground(getDrawable(R.drawable.radio_border));
                            TextView3.setTextColor(getColor(R.color.main));

                            TextView5.setBackground(getDrawable(R.drawable.radio_border));
                            TextView5.setTextColor(getColor(R.color.main));

                        }else if(response.body().getResult().getCategory().equals("CONTRACTOR")){

                            P1Category = "CONTRACTOR";
                            rl_site_size.setVisibility(View.GONE);
                            rl_site_status.setVisibility(View.GONE);
                            position_TxtInput.setVisibility(View.GONE);
                            TextView5.setBackgroundColor(getColor(R.color.main));
                            TextView5.setTextColor(getColor(R.color.white));

                            TextView2.setBackground(getDrawable(R.drawable.radio_border));
                            TextView2.setTextColor(getColor(R.color.main));
                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));
                            TextView1.setBackground(getDrawable(R.drawable.radio_border));
                            TextView1.setTextColor(getColor(R.color.main));
                            TextView3.setBackground(getDrawable(R.drawable.radio_border));
                            TextView3.setTextColor(getColor(R.color.main));
                            TextView4.setBackground(getDrawable(R.drawable.radio_border));
                            TextView4.setTextColor(getColor(R.color.main));

                        }


                        System.out.println("Contact Mode: " + response.body().getResult().getNextVisit().get(0).getContact_mode());

//                                                tv_time.setText(response.body().getResult().getNextVisit().get(0).getContact_mode());

                        if (response.body() != null && response.body().getResult() != null && response.body().getResult().getNextVisit() != null
                                && response.body().getResult().getNextVisit().get(0).getContact_mode() != null ) {
                            if (response.body().getResult().getNextVisit().get(0).getContact_mode().equals("SiteVisit")){
                                ContactMode = "SiteVisit";

                                contactModeSiteVisit.setBackgroundColor(getColor(R.color.main));
                                contactModeSiteVisit.setTextColor(getColor(R.color.white));
                                contactModeOnCall.setBackground(getDrawable(R.drawable.radio_border));
                                contactModeOnCall.setTextColor(getColor(R.color.main));

                            } else if(response.body().getResult().getNextVisit().get(0).getContact_mode().equals("OnCall")){
                                ContactMode = "OnCall";
                                Log.d("TAG", "onCheckedChanged: "+ ContactMode);

                                contactModeOnCall.setBackgroundColor(getColor(R.color.main));
                                contactModeOnCall.setTextColor(getColor(R.color.white));

                                contactModeSiteVisit.setBackground(getDrawable(R.drawable.radio_border));
                                contactModeSiteVisit.setTextColor(getColor(R.color.main));
                            }
                        } else {
                            ContactMode = "";

                            // Handle the case when response.body(), response.body().getResult(), or response.body().getResult().getContactMode() is null
                        }



//                        tv_timee.setText(response.body().getResult().getVisitTime());

                        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        TIME = simpleDateFormat.format(Calendar.getInstance().getTime());
                        tv_timee.setText(TIME);

                        p1_agent_code.setText(response.body().getResult().getMarketAgentCode());
                        p1_customer_list.setText(response.body().getResult().getCustomerName());

                        p1_mobile1.setText(response.body().getResult().getMobile1());
                        p1_mobile2.setText(response.body().getResult().getMobile2());
                        p1_mobile3.setText(response.body().getResult().getMobile3());

//                        if (response.body().getResult().getSiteStatus().equals("ON")){
//                            switch_togglebtn.setChecked(true);
//                            switch_togglebtn.setThumbResource(R.color.main);
//                            switch_togglebtn.setTin
//                        }else {
//                            switch_togglebtn.setChecked(false);
//                        }

                        if (response.body().getResult().getSiteStatus().equals("ON")) {
                            switch_togglebtn.setChecked(true);
                            switch_togglebtn.getThumbDrawable().setTint(getColor(R.color.main)); // Set thumb color when checked
                            switch_togglebtn.getTrackDrawable().setTint(getColor(R.color.main)); // Set track color when checked
                        } else {
                            switch_togglebtn.setChecked(false);
                            switch_togglebtn.getThumbDrawable().setTint(getColor(R.color.gray)); // Set thumb color when unchecked
                            switch_togglebtn.getTrackDrawable().setTint(getColor(R.color.gray)); // Set track color when unchecked
                        }

                        customer_dob_picker.setText(response.body().getResult().getCustomerDob());
                        p2_address.setText(response.body().getResult().getAddresss());
                        p2_colony_list.setText(response.body().getResult().getColony());

                        ad_colony_name = response.body().getResult().getColony();
                        ad_colony_id = response.body().getResult().getColonyId();
                        P2Colony = ad_colony_id;

                        edt_height.setText(response.body().getResult().getSiteHight());
                        edt_weight.setText(response.body().getResult().getSiteWidth());
                        p2_site_position_list.setText(response.body().getResult().getPostion());

                        ad_position_name = response.body().getResult().getPostion();
                        ad_position_id = response.body().getResult().getPostion_list_id();
                        P2Position = ad_position_id;

                        System.out.println("resssss " + response.body().getResult().getPostion_list_id());

                        SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = location.edit();

                        editor.putString("ad_position_name", ad_position_name);
                        editor.putString("ad_position_id", ad_position_id);
                        editor.putString("contact_mode", ContactMode);
                        editor.apply();

//                        p4_remark.setText(response.body().getResult().getNextVisit().get(0).getRemark());
//                        tv_date_picker_page2.setText(response.body().getResult().getNextVisit().get(0).getNextVisitDate());
//                        tv_time.setText(response.body().getResult().getNextVisit().get(0).getNextVisitTime());



                        p4_remark.setText("");
                        tv_date_picker_page2.setText("");
                        tv_time.setText("");

                        p1_customer_list.setEnabled(false);
                        p1_customer_list_drownndown.setEnabled(false);

//                    Glide.with(FormActivity.this).load(response.body().getResult().getImage()).into(preview);

                    }
                }

                @Override
                public void onFailure(Call<visit_detail_POJO> call, Throwable t) {
                    flag = false;
                    pro.dismiss();
                }
            });


        }
        else{
            flag2 = true;
        }

        p2_site_position_list = findViewById(R.id.p2_site_position_list);
        switch_togglebtn = findViewById(R.id.switch_togglebtn);
        edt_site_status.setText("");
        edt_site_status.setEnabled(false);
        switch_togglebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_togglebtn.getThumbDrawable().setTint(getColor(R.color.main)); // Set thumb color when checked
                    switch_togglebtn.getTrackDrawable().setTint(getColor(R.color.main));
                    s_switch_togglebtn = switch_togglebtn.getTextOn().toString();
                    edt_site_status.setText("");
                    edt_site_status.setEnabled(false);
                    // The toggle is enabled
                } else {
                    switch_togglebtn.getThumbDrawable().setTint(getColor(R.color.gray)); // Set thumb color when unchecked
                    switch_togglebtn.getTrackDrawable().setTint(getColor(R.color.gray));
                    s_switch_togglebtn = switch_togglebtn.getTextOff().toString();
                    edt_site_status.setEnabled(false);
                    // The toggle is disabled
                }
//                Toast.makeText(getApplicationContext(), s_switch_togglebtn, Toast.LENGTH_SHORT).show();

            }
        });

        p2_colony_list = findViewById(R.id.p2_colony_list);
        p1_customer_list = findViewById(R.id.p1_customer_list);


        //REC1
        itemlist = new ArrayList<>();

        linearLayout = new LinearLayoutManager(this);


        rec_product.setLayoutManager(linearLayout);
        add_product_Adapter add_product_adapters = new add_product_Adapter(itemlist, this, FormActivity.this) {
            @Override
            public void onItemChanged(List<add_product_POJO> list) {
                itemlist.clear();//kkkkkkkkkkkkkkkkkkk
                itemlist.addAll(list);
            }
        };
        rec_product.setAdapter(add_product_adapters);

        //REC2
        itemlist2 = new ArrayList<>();

        linearLayoutManager2 = new LinearLayoutManager(this);

        rec_site_persion.setLayoutManager(linearLayoutManager2);
        site_related_person_Adapter site_related_person_adapter = new site_related_person_Adapter(itemlist2, this,FormActivity.this);
        rec_site_persion.setAdapter(site_related_person_adapter);

       /* MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();*/


        //customer list API


        p1_customer_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                customer_list = String.valueOf(charSequence);
                if (customer_list.length() >= 2)
                {
                    callWeb3();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });




        p1_customer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String itemselect = adapterView.getItemAtPosition(i).toString();

                fetchuserDataofuser(itemselect);
                Log.d("arrayOK", Arrays.toString(new List[]{autocompettextcolonyid}));
                Log.d("arrayColonyId", autocompettextcolonyid.get(i));
                ad_cusomer_name = autocompettextview.get(i);//kkkkkkkkkkkkkkk
                ad_cusomer_id = autocompettextviewID.get(i);
                ad_customer_phone1 = autocompettextviewphone1.get(i);
                ad_customer_phone2 = autocompettextviewphone2.get(i);
                ad_customer_phone3 = autocompettextviewphone3.get(i);
                ad_customer_dob = autocompettextdob.get(i);
                ad_customer_address = autocompettextaddress.get(i);

                ad_colony_name = autocompettextcolony.get(i);
                p2_colony_list.setText(ad_colony_name);

                ad_colony_id = autocompettextcolonyid.get(i);

                Log.d("ad_ColonyId_Final", ad_colony_id);

//                ad_customer_phone1 = autocompettextviewID.set(i,ad_customer_phone1);
//                ad_customer_phone2 = autocompettextviewID.set(i,ad_customer_phone2);
//                ad_customer_phone3 = autocompettextviewID.set(i,ad_customer_phone3);

                p1_mobile1.setText(ad_customer_phone1);
                p1_mobile2.setText(ad_customer_phone2);
                p1_mobile3.setText(ad_customer_phone3);
                Log.d("ad_colony_id_Final1", ad_colony_id);
                // Parse the original date string into a Date object
                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = originalFormat.parse(ad_customer_dob);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Log.d("ad_colony_id_Final2", ad_colony_id);
                SimpleDateFormat desiredFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = desiredFormat.format(date);
                customer_dob_picker.setText(formattedDate);
                Log.d("ad_colony_id_Final3", ad_colony_id);

                p2_address.setText(ad_customer_address);
//                p2_colony_list.setText(ad_colony_name);


//                Log.d("TAG", "ad_cusomer_name: " + ad_cusomer_name);
//                Log.d("TAG", "ad_cusomer_id: " + ad_cusomer_id);
//                Log.d("TAG", "ad_cusomer_id: " + ad_customer_phone1);
//                Log.d("TAG", "ad_cusomer_id: " + ad_customer_phone2);
//                Log.d("TAG", "ad_cusomer_id: " + ad_customer_phone3);
//
//                Log.d("TAG", "ad_customer_dob: " + ad_customer_dob);
//                Log.d("TAG", "ad_colony_id: " + ad_colony_id);
                Log.d("ad_colony_id_Final4", ad_colony_id);


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_cusomer_name", ad_cusomer_name);
                editor.putString("ad_cusomer_id", ad_cusomer_id);
                editor.putString("ad_cusomer_phone1", ad_customer_phone1);
                editor.putString("ad_cusomer_phone2", ad_customer_phone2);
                editor.putString("ad_cusomer_phone3", ad_customer_phone3);
                editor.putString("ad_customer_dob", ad_customer_dob);
                editor.putString("ad_customer_address", ad_customer_address);
                editor.putString("ad_colony_id", ad_colony_id);
                editor.putString("ad_colony_name", ad_colony_name);

                editor.apply();

            }
        });


        //colonu list
        p2_colony_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                p1_mobile1, p1_mobile2, p1_mobile3

                Log.d("TAG", "afsdfsdf2: " + charSequence);

                colony_list = String.valueOf(charSequence);
                ad_colony_name = colony_list;
                ad_colony_id = "";
                P2Colony = ad_colony_id;

                Log.d("TAG", "ad_colony_name: " + ad_colony_name);
                Log.d("TAG", "ad_colony_id: " + ad_colony_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_colony_name", ad_colony_name);
                editor.putString("ad_colony_id", ad_colony_id);
                editor.apply();

                if (colony_list.length() >= 2) {
                    callWeb2();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        p4_remark.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {

                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (validation2()) {
                                s_p2_address = p2_address.getText().toString().trim();
                                s_p2_colony_list = p2_colony_list.getText().toString().trim();
                                s_edt_height = edt_height.getText().toString().trim();
                                c_edt_weight = edt_weight.getText().toString().trim();
                                s_p2_site_position_list = p2_site_position_list.getText().toString().trim();

                                layout_page1.setVisibility(View.GONE);
                                layout_page2.setVisibility(View.GONE);
                                layout_page3.setVisibility(View.VISIBLE);

                            }
                            return true;
                        default:
                            break;
                    }
                }

                return false;
            }
        });



        p2_colony_list.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
            {

                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER://kkkkkkkkkkkkkkk
                        if (validation2()) {
                            s_p2_address = p2_address.getText().toString().trim();
                            s_p2_colony_list = p2_colony_list.getText().toString().trim();
                            s_edt_height = edt_height.getText().toString().trim();
                            c_edt_weight = edt_weight.getText().toString().trim();
                            s_p2_site_position_list = p2_site_position_list.getText().toString().trim();

                            layout_page1.setVisibility(View.GONE);
                            layout_page2.setVisibility(View.GONE);
                            layout_page3.setVisibility(View.VISIBLE);

                        }
                        return true;
                    default:
                        break;
                }
            }

                return false;
            }
        });

        p2_colony_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ad_colony_name = colony_autocompettextview.get(i);
                ad_colony_id = colony_autocompettextviewID.get(i);
                P2Colony = ad_colony_id;

                Log.d("TAG", "ad_colony_name: " + ad_colony_name);
                Log.d("TAG", "ad_colony_id: " + ad_colony_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_colony_name", ad_colony_name);
                editor.putString("ad_colony_id", ad_colony_id);
                editor.apply();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });
//        AutoCompleteTextView autocomplete = (AutoCompleteTextView) findViewById(R.id.p2_site_position_list);
//        final ArrayAdapter<String> autoAdapter = new ArrayAdapter<site_position_list_POJO>(this, android.R.layout.simple_dropdown_item_1line, );
//        autocomplete.setAdapter(autoAdapter);

        //siteposition
        p2_site_position_list.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("TAG", "afsdfsdf2: " + charSequence);
                position_list = String.valueOf(charSequence);

                ad_position_name = position_list;
                P2Position = ad_position_id;
                ad_position_id = "";


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_position_name", ad_position_name);
                editor.putString("ad_position_id", ad_position_id);
                editor.apply();



                Log.d("TAG", "ad_position_id: " + ad_position_id);
                Log.d("TAG", "P2Position: " + P2Position);



                if (position_list.length() >= 2) {
                    callWeb();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("TAG", "ddddddd: " + editable);
            }
        });

        p2_site_position_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ad_position_name = position_autocompettextview.get(i);
                ad_position_id = position_autocompettextviewID.get(i);
                P2Position = ad_position_id;


                Log.d("TAG", "ad_position_name: " + ad_position_name);
                Log.d("TAG", "ad_position_id: " + ad_position_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_position_name", ad_position_name);
                editor.putString("ad_position_id", ad_position_id);
                editor.apply();
            }
        });


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


        pre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_page2.setVisibility(View.GONE);
                layout_page1.setVisibility(View.VISIBLE);
            }
        });

        pre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_page3.setVisibility(View.GONE);
                layout_page2.setVisibility(View.VISIBLE);
            }
        });

        pre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_page4.setVisibility(View.GONE);
                layout_page3.setVisibility(View.VISIBLE);
            }
        });
        pre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_page5.setVisibility(View.GONE);
                layout_page4.setVisibility(View.VISIBLE);
            }
        });

        //site size
        edt_height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                height = edt_height.getText().toString();
                width = edt_weight.getText().toString();
                calculation();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                height = edt_height.getText().toString();
                width = edt_weight.getText().toString();
                calculation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //date picker1
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        tv_date_picker.setText(dateTime);
//        tv_date_picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onPositiveButtonClick(Object selection) {
//
//                        dateTracker = materialDatePicker.getHeaderText();
//
//                        if (!dateTracker.equals("")) {
//                        }
//                        tv_date_picker.setText(dateTracker);
//
//                        // in the above statement, getHeaderText
//                        // is the selected date preview from the
//                        // dialog
//                    }
//                });
//            }
//        });

//       customer_dob_picker.setText(dateTime);
        customer_dob_picker.setEnabled(true);

        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.YEAR, year);
                updateLabel();
            }
        };

        customer_dob_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                customer_dob_picker.setEnabled(true);
                new DatePickerDialog(FormActivity.this,/* R.style.datepicker,*/date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
             /*   materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        try {

                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                            calendar.setTimeInMillis(selection);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate  = format.format(calendar.getTime());

                            dateTracker = materialDatePicker.getHeaderText();


                            if (!dateTracker.equals("")) {
                            }

                            customer_dob_picker.setText(formattedDate);
//                            customer_dob_picker.setEnabled(false);

                            // in the above statement, getHeaderText
                            // is the selected date preview from the
                            // dialog
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/
            }
        });

//
////        date picker2
//        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
////        dateTime = simpleDateFormat.format(s_tv_date_picker_page2);
//        tv_date_picker_page2.setText(dateTime);
//        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH,month);
//                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//
//                String myFormat="dd-MM-yyyy";
//                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
//                tv_date_picker_page2.setText(dateFormat.format(myCalendar.getTime()));
//
//
//
//            }
//        };
//        tv_date_picker_page2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(FormActivity.this,/* R.style.datepicker,*/date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//
//
//                /*materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onPositiveButtonClick(Long selection) {
//
//                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//                        calendar.setTimeInMillis(selection);
//                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                        String formattedDate  = format.format(calendar.getTime());
//
//                        dateTracker = materialDatePicker.getHeaderText();
//
//                        if (!dateTracker.equals("")) {
//                        }
//                        tv_date_picker_page2.setText(formattedDate);
//
//                        // in the above statement, getHeaderText
//                        // is the selected date preview from the
//                        // dialog
//                    }
//                });*/
//            }
//        });


        //        date picker2
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
//        dateTime = simpleDateFormat.format(s_tv_date_picker_page2);
        tv_date_picker_page2.setText(dateTime);
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                tv_date_picker_page2.setText(dateFormat.format(myCalendar.getTime()));

            }
        };
        tv_date_picker_page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,/* R.style.datepicker,*/ date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set minimum date to current date
                datePickerDialog.show();
            }
        });



        //time picker
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        TIME = simpleDateFormat.format(Calendar.getInstance().getTime());
        tv_time.setText(TIME);
        Log.d("TAG", "onCreate: time123 "+tv_time+" 222 "+TIME);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooser = "picker";
                showTimePicker();


            }

            private void showTimePicker() {

                picker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(00)

                        .setTitleText("select time")
                        .build();

                picker.show(FormActivity.this.getSupportFragmentManager(), "hello");


                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        calendar.set(Calendar.MINUTE, picker.getMinute());
                        calendar.set(Calendar.SECOND, 00);
                        calendar.set(Calendar.MILLISECOND, 00);

                        Log.d("picker", String.valueOf(picker.getHour()));
                        Log.d("picker", String.valueOf(picker.getMinute()));
                        String min;
                        if(picker.getMinute() == 0){
                            min = "00";
                        }
                        else{
                            min = String.valueOf(picker.getMinute());
                        }
                        timetracker = String.valueOf(picker.getHour()) + ":" + min + ":00";
                        tv_time.setText(timetracker);
                        Log.d("TAG", "timetaker: time123 " + tv_time);

                    }
                });
            }
        });

        //time picker2
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        TIME = simpleDateFormat.format(Calendar.getInstance().getTime());
        tv_timee.setText(TIME);


//        tv_timee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chooser = "picker";
//                showTimePicker();
//            }
//
//            private void showTimePicker() {
//
//                picker = new MaterialTimePicker.Builder()
//                        .setTimeFormat(TimeFormat.CLOCK_12H)
//                        .setHour(12)
//                        .setMinute(0)
//                        .setTitleText("select time")
//                        .build();
//
//                picker.show(FormActivity.this.getSupportFragmentManager(), "hello");
//
//
//                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onClick(View v) {
//                        calendar = Calendar.getInstance();
//                        calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
//                        calendar.set(Calendar.MINUTE, picker.getMinute());
//                        calendar.set(Calendar.SECOND, 0);
//                        calendar.set(Calendar.MILLISECOND, 0);
//
//                        Log.d("picker", String.valueOf(picker.getHour()));
//                        Log.d("picker", String.valueOf(picker.getMinute()));
//                        timetracker = String.valueOf(picker.getHour()) + ":" + String.valueOf(picker.getMinute());
//                        tv_time.setText(timetracker);
//
//                    }
//                });
//            }
//        });

        //img upload
//        upload_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent gal = new Intent(Intent.ACTION_PICK);
//                gal.setType("image/*");
//                startActivityForResult(gal, 2);
//                empUPdateProfile();
//
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
////                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
////                startActivityForResult(intent, 1);
//            }
//        });


        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImagePicker.create(EditProfileActivity.this).single().start();
                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                gal.setType("image/*");
                startActivityForResult(gal, 1);*/
                ImagePicker.with(FormActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .cameraOnly()    //User can only capture image using Camera
//                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                getCurrentLocation();
//                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//                if (ActivityCompat.checkSelfPermission(FormActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FormActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0,  FormActivity.this);
//                Criteria criteria = new Criteria();
//                String bestProvider = locationManager.getBestProvider(criteria, true);
//                Location location = locationManager.getLastKnownLocation(bestProvider);
//
//                if (location == null) {
//                    Toast.makeText(getApplicationContext(), "GPS signal not found", Toast.LENGTH_SHORT).show();
//                }
//                if (location != null) {
//                    Log.e("locatin", "location--" + location);
//
//                    Log.e("latitude at beginning",
//                            "@@@@@@@@@@@@@@@" + location.getLatitude());
//                    onLocationChanged(location);
//                }


            }
        });


        //customer name list
        p1_customer_list = findViewById(R.id.p1_customer_list);

//            p1_customer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    ad_cusomer_name = autocompettextview.get(position);
//                    ad_cusomer_id = autocompettextviewID.get(position);
//                    ad_customer_phone1 = autocompettextviewphone1.get(position);
//
//                    p1_mobile1.setText(sharedPreferences.getString("ad_cusomer_phone1", ad_customer_phone1));
//
//
//
//                    Log.d("TAG", "ad_cusomer_name: " + ad_cusomer_name);
//                    Log.d("TAG", "ad_cusomer_id: " + ad_cusomer_id);
//
//                    SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = location.edit();
//
//                    editor.putString("ad_cusomer_name", ad_cusomer_name);
//                    editor.putString("ad_cusomer_id", ad_cusomer_id);
//                    editor.putString("ad_customer_phone1" , ad_customer_phone1);
//
//                    editor.apply();
//
//                    String item = parent.getItemAtPosition(position).toString();//kkkkkkkkkkkkkkkk
//                    feedback = item;
//
//                }
//            });

        //colony name list
        p2_colony_list = findViewById(R.id.p2_colony_list);

//        p2_colony_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                String item = parent.getItemAtPosition(position).toString();
//                feedback = item;
//
//            }
//        });

        //site_position_list
        p2_site_position_list = findViewById(R.id.p2_site_position_list);

//        p2_site_position_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String item = parent.getItemAtPosition(position).toString();
//                feedback = item;
//
//            }
//        });


        //drop down
//            autoCompleteTxt = findViewById(R.id.auto_complete_txt);
//
//            adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
//            autoCompleteTxt.setAdapter(adapterItems);
//
//            autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String item = parent.getItemAtPosition(position).toString();
//                    feedback = item;
//                }
//            });


        //drop down finish
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation1()) {
                    s_tv_date_picker = tv_date_picker.getText().toString().trim();
                    s_tv_timee = tv_timee.getText().toString().trim();
                    s_p1_agent_code = p1_agent_code.getText().toString().trim();
                    s_p1_customer_list = p1_customer_list.getText().toString().trim();
                    s_p1_mobile1 = p1_mobile1.getText().toString().trim();
                    s_p1_mobile2 = p1_mobile2.getText().toString().trim();
                    s_p1_mobile3 = p1_mobile3.getText().toString().trim();

                    layout_page1.setVisibility(View.GONE);
                    layout_page2.setVisibility(View.VISIBLE);
                }

            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation2()) {
                    s_p2_address = p2_address.getText().toString().trim();
                    s_p2_colony_list = p2_colony_list.getText().toString().trim();
                    s_edt_height = edt_height.getText().toString().trim();
                    c_edt_weight = edt_weight.getText().toString().trim();
                    s_p2_site_position_list = p2_site_position_list.getText().toString().trim();

                    layout_page1.setVisibility(View.GONE);
                    layout_page2.setVisibility(View.GONE);
                    layout_page3.setVisibility(View.VISIBLE);

                }

            }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_page1.setVisibility(View.GONE);
                layout_page2.setVisibility(View.GONE);
                layout_page3.setVisibility(View.GONE);
                layout_page4.setVisibility(View.VISIBLE);
            }
        });

        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_page1.setVisibility(View.GONE);
                layout_page2.setVisibility(View.GONE);
                layout_page3.setVisibility(View.GONE);
                layout_page4.setVisibility(View.GONE);
                layout_page5.setVisibility(View.VISIBLE);
                getCurrentLocationOnSubmit();
            }
        });




        submit_next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro.show();


                if (validation5()) {

                    s_tv_date_picker_page2 = tv_date_picker_page2.getText().toString().trim();
                    s_tv_time = tv_time.getText().toString().trim();
                    s_p4_remark = p4_remark.getText().toString().trim();

//                    stringinitialization();

//                    if (url!=null){
//                       /* try {
//                            File file2= new File(new URL(url).toURI());
//                            final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file2);
//                            imagePartPhoto_book1 = MultipartBody.Part.createFormData("image", file2.getName(), requestBody1);
//
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }*/
//                    }else {
                        if (!waypathPhoto_book1.equals("")){

                            Log.e("TAG","waypathPhoto_book1---"+waypathPhoto_book1);
                            File file1 = new File(waypathPhoto_book1);
                            final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                            imagePartPhoto_book1 = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);
                        }else{
                            Log.d("TAG", "v_form_res_s_p2_imgg11: " + url);
                        }
//                    }

                    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;

                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + s_tv_date_picker);
                    Log.d("TAG", "v_form_res_s_tv_timee: " + s_tv_timee);
                    Log.d("TAG", "v_form_res_s_p1_agent_code: " + s_p1_agent_code);
                    Log.d("TAG", "v_form_res_s_p1_customer_list: " + s_p1_customer_list);
                    Log.d("TAG", "v_form_res_s_p1_mobile1: " + s_p1_mobile1);
                    Log.d("TAG", "v_form_res_s_p1_mobile2: " + s_p1_mobile2);
                    Log.d("TAG", "v_form_res_s_p1_mobile3: " + s_p1_mobile3);

                    Log.d("TAG", "v_form_res_s_p2_address: " + s_p2_address);
                    Log.d("TAG", "v_form_res_s_p2_colony_list: " + s_p2_colony_list);
                    Log.d("TAG", "v_form_res_s_edt_height: " + s_edt_height);
                    Log.d("TAG", "v_form_res_c_edt_weight: " + c_edt_weight);
                    Log.d("TAG", "v_form_res_s_p2_site_position_list: " + s_p2_site_position_list);
                    Log.d("TAG", "v_form_res_s_p2_imgg: " + url);

                    Log.d("TAG", "v_form_res_s_dialog_add_product: " + s_dialog_add_product);
                    Log.d("TAG", "v_form_res_s_filledTextField2: " + s_filledTextField2);
                    Log.d("TAG", "v_form_res_s_filledTextField3: " + s_filledTextField3);
                    Log.d("TAG", "v_form_res_s_filledTextField4: " + s_filledTextField4);
                    Log.d("TAG", "v_form_res_s_filledTextField5: " + s_filledTextField5);

                    Log.d("TAG", "v_form_res_s_dialog_type_person_list: " + s_dialog_type_person_list);
                    Log.d("TAG", "v_form_res_s_site_filledTextField2: " + s_site_filledTextField2);
                    Log.d("TAG", "v_form_res_s_site_filledTextField3: " + s_site_filledTextField3);

                    if (sharedPreferences.getString("ad_cusomer_id", "").isEmpty() && !TextUtils.isEmpty(ad_cusomer_id)){
                        sharedPreferences.edit().putString("ad_cusomer_id", ad_cusomer_id).apply();
                    }

                    Log.d("TAG", "v_form_res_s_tv_date_picker_page2: " + s_tv_date_picker_page2);
                    Log.d("TAG", "v_form_res_s_tv_time: " + s_tv_time);
                    Log.d("TAG", "v_form_res_s_p4_remark: " + s_p4_remark);
                    Log.d("TAG", "formuser_id: " + sharedPreferences.getString("user_id", ""));
                    Log.d("TAG", "ad_cusomer_id: " + sharedPreferences.getString("ad_cusomer_id", ""));
                    Log.d("TAG", "ad_pro_id: " + sharedPreferences.getString("ad_pro_id", ""));
                    Log.d("TAG", "ad_colony_id: " + sharedPreferences.getString("ad_colony_id", ""));
                    Log.d("TAG", "ad_sssite_person_id: " + sharedPreferences.getString("ad_site_person_id", ""));

                    if (CheckInternetConnection.checkInternetConnection(FormActivity.this)) {
                        if (!flag) {   //for all data submit

//                        WebService.getClient().sent_in_url_visit_form("api/crm/visit_form.php?"
//                                + "visit_date=" + s_tv_date_picker + "&"
//                                + "visit_time=" + s_tv_timee + "&"
//                                + "market_agent_code=" + s_p1_agent_code + "&"
//                                + "customer_list__id=" + sharedPreferences.getString("ad_cusomer_id", "") + "&"
//                                + "c_name=" + s_p1_customer_list + "&"
//                                + "mobile_1=" + s_p1_mobile1 + "&"
//                                + "mobile_2=" + s_p1_mobile2 + "&"
//                                + "mobile_3=" + s_p1_mobile3 + "&"
//
//                                + "addresss=" + s_p2_address + "&"
//                                + "colony_list_id=" + sharedPreferences.getString("ad_colony_id", "") + "&"
//                                + "colony=" + s_p2_colony_list + "&"
//                                + "site_status=" + s_switch_togglebtn + "&"
//                                + "Site_width=" + c_edt_weight + "&"
//                                + "Site_Hight=" + s_edt_height + "&"
//                                + "postion=" + s_p2_site_position_list + "&"
//                                + "postion_list_id=" + sharedPreferences.getString("ad_position_id", "") + "&"
//
//                                + "product_name=" + sharedPreferences.getString("add_product", "") + "&"
//                                + "product_id=" + sharedPreferences.getString("pro_id", "") + "&"
//                                + "brand=" + sharedPreferences.getString("ad_brand_name", "") + "&"
//                                + "brand_id=" + sharedPreferences.getString("ad_brand_id", "") + "&"
//                                + "existing_buy_shop=" + sharedPreferences.getString("add_vendor", "") + "&"
//                                + "existing_buy_rate=" + sharedPreferences.getString("add_rate", "") + "&"
//                                + "required_qty=" + sharedPreferences.getString("add_qty", "") + "&"
//                                + "unit=" + sharedPreferences.getString("ad_unit_name", "") + "&"
//                                + "unit_id=" + sharedPreferences.getString("ad_unit_id", "") + "&"
//                                + "stock=" + sharedPreferences.getString("add_Stock", "") + "&"
//
//                                + "type_of_person=" + sharedPreferences.getString("site_sperson", "") + "&"
//                                + "type_of_person_id=" + sharedPreferences.getString("ad_site_person_id", "") + "&"
//                                + "name=" + sharedPreferences.getString("site_sname", "") + "&"
//                                + "contact_no=" + sharedPreferences.getString("site_scontact", "") + "&"
//                                + "ON_Site=" + sharedPreferences.getString("site_son_site", "") + "&"
//                                + "OFF_Site=" + sharedPreferences.getString("site_soff_site", "") + "&"
//
//                                + "next_visit_date=" + s_tv_date_picker_page2 + "&"
//                                + "next_visit_time=" + s_tv_time + "&"
//                                + "remark=" + s_p4_remark + "&"
//
//                                + "form_fillup_latittude=" + sharedPreferences.getString("latitude", "") + "&"
//                                + "form_fillup_longitude=" + sharedPreferences.getString("longitude", "") + "&"
//
//                                + "image_upload_latitude=" + sharedPreferences.getString("latitude", "") + "&"
//                                + "image_upload_longitude=" + sharedPreferences.getString("longitude", "") + "&"
//                                + "user_id=" + sharedPreferences.getString("user_id", "")
//
//
//                        ).enqueue(new Callback<visit_form_POJO>() {
//                            @Override
//                            public void onResponse(Call<visit_form_POJO> call, Response<visit_form_POJO> response) {
//
//                                Toast.makeText(FormActivity.this, "", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<visit_form_POJO> call, Throwable t) {
//
//                            }
//                        });



                            //official
                            WebService.getClient().visit_form(
                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker),
                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_timee),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_agent_code),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("ad_cusomer_id", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_customer_list),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile1),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile2),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile3),

                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_address),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("ad_colony_id", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_colony_list),
                                    RequestBody.create(MediaType.parse("text/plain"), s_switch_togglebtn),
                                    RequestBody.create(MediaType.parse("text/plain"), c_edt_weight),
                                    RequestBody.create(MediaType.parse("text/plain"), s_edt_height),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_site_position_list),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("ad_position_id", "")),


                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_product", "")),//product name
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_proId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_brand", "")),//brand
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_brandId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_vendor", "")),//vendor
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_rate", "")),//rate
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_qty", "")),//qty
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_unit", "")),//unit
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_unitId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_Stock", "")),//stock

                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sperson", "")),//related
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sad_site_person_id", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sname", "")),//name
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sad_site_person_idname", "")),//
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_scontact", "")),// contact
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_son_site", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_soff_site", "")),


                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker_page2),
                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_time),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p4_remark),


                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("form_latitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("form_longitude", "")),

                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("img_latitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("img_longitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
                                    imagePartPhoto_book12,
                                    RequestBody.create(MediaType.parse("text/plain"),sharedPreferences.getString("IMEI_no", "")),
                                    RequestBody.create(MediaType.parse("text/plain"),P1Category),
                                    RequestBody.create(MediaType.parse("text/plain"),ContactMode),
                                    RequestBody.create(MediaType.parse("text/plain"),customer_dob_picker.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"),sharedPreferences.getString("site_sdob", ""))
                            ).enqueue(new Callback<visit_form_POJO>() {
                                @Override
                                public void onResponse(Call<visit_form_POJO> call, Response<visit_form_POJO> response) {
                                    pro.dismiss();
                                    if (response.body().getSuccess() == 1) {

//                                    Toast.makeText(FormActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("img_latitude", "");
                                        editor.putString("img_longitude", "");
                                        editor.putString("form_latitude", "");
                                        editor.putString("form_longitude", "");
                                        editor.apply();

                                        showAlertDialogButtonClicked1(view);

                                        //Toast.makeText(MakenewApplicationActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
//                            Dialog dialog = new Dialog(FormActivity.this, R.style.DialogStyal);
//                            dialog.setContentView(R.layout.submut_applicayion_dilog_layout);
//                            ImageView close = dialog.findViewById(R.id.close);
//                            dialog.setCanceledOnTouchOutside(false);
//                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_corner);
//                            close.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    dialog.dismiss();
//                                    startActivity(new Intent(FormActivity.this, MainActivity.class));
//                                }
//                            });
                                        dialog.show();
                                    } else {
//                                    Toast.makeText(FormActivity.this, "Data Not Submitted", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<visit_form_POJO> call, Throwable t) {
                                    pro.dismiss();
//                                Toast.makeText(FormActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                        else {//for edit data
                            flag2 = false;


                            Log.d("imagepreview", "onClick: "+imagePartPhoto_book12);
                            WebService.getClient().Edit_form(

                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker),
                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_timee),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_agent_code),
                                    RequestBody.create(MediaType.parse("text/plain"), customer_id),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_customer_list),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile1),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile2),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile3),

                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_address),
                                    RequestBody.create(MediaType.parse("text/plain"), P2Colony),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_colony_list),
                                    RequestBody.create(MediaType.parse("text/plain"), s_switch_togglebtn),
                                    RequestBody.create(MediaType.parse("text/plain"), c_edt_weight),
                                    RequestBody.create(MediaType.parse("text/plain"), s_edt_height),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p2_site_position_list),
                                    RequestBody.create(MediaType.parse("text/plain"), P2Position),
//                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("ad_position_id", "")),


                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_product", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_proId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_brand", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_brandId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_vendor", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_rate", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_qty", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_unit", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_unitId", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("add_Stock", "")),

                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sperson", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sad_site_person_id", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sname", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_sad_site_person_idname", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_scontact", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_son_site", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("site_soff_site", "")),


                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker_page2),
                                    RequestBody.create(MediaType.parse("text/plain"), s_tv_time),
                                    RequestBody.create(MediaType.parse("text/plain"), s_p4_remark),


                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("latitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("longitude", "")),

                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("latitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("longitude", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("visit_id", "")),

                                    imagePartPhoto_book12,
                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("IMEI_no", "")),
                                    RequestBody.create(MediaType.parse("text/plain"),P1Category),
                                    RequestBody.create(MediaType.parse("text/plain"),ContactMode),
                                    RequestBody.create(MediaType.parse("text/plain"),customer_dob_picker.getText().toString()),
                                    RequestBody.create(MediaType.parse("text/plain"),sharedPreferences.getString("site_sdob", ""))


                            ).enqueue(new Callback<Edit_form_POJO>() {

                                @Override
                                public void onResponse(Call<Edit_form_POJO> call, Response<Edit_form_POJO> response) {
                                    pro.dismiss();
                                    if (response.body().getSuccess() == 1) {

//
//                                    Toast.makeText(FormActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("img_latitude", "");
                                        editor.putString("img_longitude", "");
                                        editor.putString("form_latitude", "");
                                        editor.putString("form_longitude", "");
                                        editor.apply();

                                        showAlertDialogButtonClicked1(view);

                                        dialog.show();
                                    } else {
                                        Toast.makeText(FormActivity.this, "Data Not Submitted", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Edit_form_POJO> call, Throwable t) {

                                    if (t instanceof JsonSyntaxException){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("img_latitude", "");
                                        editor.putString("img_longitude", "");
                                        editor.putString("form_latitude", "");
                                        editor.putString("form_longitude", "");
                                        editor.apply();

                                        showAlertDialogButtonClicked1(view);

                                        dialog.show();
                                        return;
                                    }
                                    pro.dismiss();
//                                Toast.makeText(FormActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    //Internet Offfffff
                    else
                    {
                        pro.dismiss();
                        if (!flag) {
                            AllDataBean alldatabean = new AllDataBean();
                            alldatabean.setDATE_COL(s_tv_date_picker);
                            alldatabean.setTIME_COL(s_tv_timee);
                            alldatabean.setAGETCODE_COL(s_p1_agent_code);
                            alldatabean.setCUSTOMER_ID_COL(sharedPreferences.getString("ad_cusomer_id", ""));
                            alldatabean.setCUSTOMER_ID_COL(s_p1_customer_list);
                            alldatabean.setCUSTOMER_MOBILE1_COL(s_p1_mobile1);
                            alldatabean.setCUSTOMER_MOBILE2_COL(s_p1_mobile2);
                            alldatabean.setCUSTOMER_MOBILE3_COL(s_p1_mobile3);
                            alldatabean.setADDRESS_COL(s_p2_address);
                            alldatabean.setCOLONY_ID_COL(sharedPreferences.getString("ad_colony_id", ""));
                            alldatabean.setCOLONY_NAME_COL(s_p2_colony_list);
                            alldatabean.setSWITH_TOGGLE_COL(s_switch_togglebtn);
                            alldatabean.setWIDTH_COL(c_edt_weight);
                            alldatabean.setHEIGHT_COL(s_edt_height);
                            alldatabean.setSITE_POSITION_COL(s_p2_site_position_list);
                            alldatabean.setSITE_POSITION_ID_COL(sharedPreferences.getString("ad_position_id", ""));
                            alldatabean.setADD_PRODUCT(sharedPreferences.getString("add_product", ""));
                            alldatabean.setADD_PRODUCT_ID(sharedPreferences.getString("add_proId", ""));
                            alldatabean.setADD_BRAND(sharedPreferences.getString("add_brand", ""));
                            alldatabean.setADD_BRAND_ID(sharedPreferences.getString("add_brandId", ""));
                            alldatabean.setVENDOR(sharedPreferences.getString("add_vendor", ""));
                            alldatabean.setRATE(sharedPreferences.getString("add_rate", ""));
                            alldatabean.setQNTY(sharedPreferences.getString("add_qty", ""));
                            alldatabean.setUNIT(sharedPreferences.getString("add_unit", ""));
                            alldatabean.setUNIT_ID(sharedPreferences.getString("add_unitId", ""));
                            alldatabean.setSTOCK(sharedPreferences.getString("add_Stock", ""));
                            alldatabean.setSITE_PERSON(sharedPreferences.getString("site_sperson", ""));
                            alldatabean.setSITE_PERSON_ID(sharedPreferences.getString("site_sad_site_person_id", ""));
                            alldatabean.setSITE_NAME(sharedPreferences.getString("site_sname", ""));
                            alldatabean.setSITE_PERSON_ID_NAME(sharedPreferences.getString("site_sad_site_person_idname", ""));
                            alldatabean.setSITE_CONTACT(sharedPreferences.getString("site_scontact", ""));
                            alldatabean.setSITE_ON_SITE(sharedPreferences.getString("site_son_site", ""));
                            alldatabean.setSITE_OFF_SITE(sharedPreferences.getString("site_soff_site", ""));
                            alldatabean.setDATE_PAGE2(s_tv_date_picker_page2);
                            alldatabean.setTIME_PAGE2(s_tv_time);
                            alldatabean.setREMARK(s_p4_remark);
                            alldatabean.setFORM_LATI(sharedPreferences.getString("form_latitude", ""));
                            alldatabean.setFORM_LOGI(sharedPreferences.getString("form_longitude", ""));
                            alldatabean.setIMG_LAT(sharedPreferences.getString("img_latitude", ""));
                            alldatabean.setIMG_LOGI(sharedPreferences.getString("img_longitude", ""));
                            alldatabean.setUSER_ID(sharedPreferences.getString("user_id", ""));
//                            alldatabean.setPHOTO_BOOK(imagePartPhoto_book12 + "");
                            alldatabean.setPHOTO_BOOK(waypathPhoto_book1);
                            alldatabean.setIMEI_NO(sharedPreferences.getString("IMEI_no", ""));
                            alldatabean.setCATEGORY(P1Category);
                            alldatabean.setCONTACTMODE(ContactMode);
                            alldatabean.setCUSTOMER_DOB(customer_dob_picker.getText().toString());
                            alldatabean.setCUSTOMER_S_DOB(sharedPreferences.getString("site_sdob", ""));

                            if (dbHandler.SaveData(alldatabean)) {
                                Log.e("Msg_shyam", "Save data successfull");
                                Toast.makeText(FormActivity.this, "Save data successfull", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("img_latitude", "");
                                editor.putString("img_longitude", "");
                                editor.putString("form_latitude", "");
                                editor.putString("form_longitude", "");
                                editor.apply();

                                showAlertDialogButtonClicked1(view);

                            } else {
                                Log.e("Msg_shyam", "issue is in Save data");
                                Toast.makeText(FormActivity.this, "some issue is in inserting data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {//for edit data
                            flag2 = false;
//                            WebService.getClient().Edit_form(

                                                }
//                        Toast.makeText(FormActivity.this, getResources().getString(R.string.txt_no_internet), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    pro.dismiss();
                }
            }

            private boolean validation5() {

                if (TextUtils.isEmpty(tv_date_picker_page2.getText().toString())) {
                    tv_date_picker_page2.setError("Select Next visit date");
                    tv_date_picker_page2.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(tv_time.getText().toString())) {
                    tv_time.setError("Select Next visit time");
                    tv_time.requestFocus();
                    return false;
                }
                if (TextUtils.isEmpty(p4_remark.getText().toString())) {
                    p4_remark.setError("Enter Remark");
                    p4_remark.requestFocus();
                    return false;

                }
                return true;
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad_pro_id="";
                ad_brand_id="";
                ad_unit_id="";

                showAlertDialogButtonClicked2(view);
            }
        });

        add_site_related_persion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad_site_person_id="";
                ad_site_person_idname="";
                showAlertDialogButtonClicked3(view);
            }
        });
    }

    private void fetchuserDataofuser(String itemselect) {


    }

    private void updateLabel() {
        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        customer_dob_picker.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void onLocationChanged(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.e("latitude", "latitude--" + latitude);
        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

//                ro_gps_location.setText(state + " , " + city + " , " + country);
//                ro_address.setText(address + " , " + knownName + " , " + postalCode);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getCurrentLocationOnSubmit() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(FormActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(FormActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(FormActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        try {
                                            addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                        if (addresses != null && !addresses.isEmpty()) {
                                            String countryName = addresses.get(0).getCountryName();
                                            String stateName = addresses.get(0).getAdminArea();
                                            String knownName = addresses.get(0).getFeatureName(); // landmark
                                            String address = addresses.get(0).getAddressLine(0);
                                            String cityName = addresses.get(0).getLocality();
                                            String area = addresses.get(0).getPostalCode();

                                            try {
                                                Log.d("location", countryName);
                                                Log.d("location", stateName);
                                                Log.d("location", cityName);
                                                Log.d("location", knownName);
                                                Log.d("location", area);
                                                Log.d("location", address);
                                                Log.d("locationlattt", String.valueOf(latitude));
                                                Log.d("location", String.valueOf(longitude));
                                                String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            myEdit.putString("form_latitude", latitude+"");
                                            myEdit.putString("form_longitude", longitude+"");
                                            myEdit.apply();
                                            myEdit.apply();
                                        }
                                    }

                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void getCurrentLocation() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img_latitude", "");
        editor.putString("img_longitude", "");

        editor.apply();

// Storing the key and its value as the data fetched from edittext


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(FormActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {


                    LocationServices.getFusedLocationProviderClient(FormActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(FormActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        try {
                                            addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                            if (addresses != null && !addresses.isEmpty()) {
                                                try {
                                                    String countryName = addresses.get(0).getCountryName();
                                                String stateName = addresses.get(0).getAdminArea();
                                                String knownName = addresses.get(0).getFeatureName(); // landmark
                                                String address = addresses.get(0).getAddressLine(0);
                                                String cityName = addresses.get(0).getLocality();
                                                String area = addresses.get(0).getPostalCode();

                                                Log.d("location", countryName);
                                                Log.d("location", stateName);
                                                Log.d("location", cityName);
                                                Log.d("location", knownName);
                                                Log.d("location", area);
                                                Log.d("location", address);
                                                Log.d("locationlattt", String.valueOf(latitude));
                                                Log.d("location", String.valueOf(longitude));
//                                                String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                myEdit.putString("img_latitude", String.valueOf(latitude));
                                                myEdit.putString("img_longitude", String.valueOf(longitude));
                                                myEdit.apply();
                                                myEdit.apply();
                                            }
                                    }

                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(FormActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(FormActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }



    private boolean validation2() {
        // Form Photo validation

        if(flag){
            // If always new photo required to be upload
            // for details
            if (TextUtils.isEmpty(waypathPhoto_book1)) {

                Toast.makeText(FormActivity.this, "Select Shop image", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        else{

            if (TextUtils.isEmpty(waypathPhoto_book1)) {

                Toast.makeText(FormActivity.this, "Select Shop image", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        if (TextUtils.isEmpty(p2_address.getText().toString())) {
            p2_address.setError("Plz Enter Address");
            p2_address.requestFocus();
            return false;
        }

//        if (site_person_mobile.length() != 10) {
//            site_person_mobile.setError("10 Digit");
//            site_person_mobile.requestFocus();
//            Toast.makeText(this, "enter proper mobile no", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(site_person_mobile.getText().toString())) {
//            site_person_mobile.setError("Plz Enter mobile no");
//            site_person_mobile.requestFocus();
//            return false;
//        }

//        Log.d("helloID", ad_colony_id);
//        Log.d("helloCustomerID", ad_cusomer_id);

        if (TextUtils.isEmpty(p2_colony_list.getText().toString())) {
            p2_colony_list.setError("Plz Select Colony List");
            p2_colony_list.requestFocus();
            return false;

        }



//        Log.d("helloID", ad_colony_id);
//        Log.d("helloCustomerID", ad_cusomer_id);

        if (TextUtils.isEmpty(ad_colony_id)) {
            p2_colony_list.setError("Plz Select Colony List");
            p2_colony_list.requestFocus();
            return false;

        }

        if ("".equals(s_switch_togglebtn)) {
            Toast.makeText(FormActivity.this, "Select check site status", Toast.LENGTH_SHORT).show();
            return false;

        }


        if (!TextUtils.isEmpty(p2_site_position_list.getText().toString())) {
            if (TextUtils.isEmpty(ad_position_id)) {
                p2_site_position_list.setError("Plz select position from list");
                p2_site_position_list.requestFocus();
                return false;

            }

        }






//        if (TextUtils.isEmpty(edt_height.getText().toString())) {
//            edt_height.setError("Enter height");
//            edt_height.requestFocus();
//            return false;
//        }
//        if (TextUtils.isEmpty(edt_weight.getText().toString())) {
//            edt_weight.setError("Enter Width");
//            edt_weight.requestFocus();
//            return false;
//        }
//        if (TextUtils.isEmpty(p2_site_position_list.getText().toString())) {
//            p2_site_position_list.setError("Plz Select Colony List");
//            p2_site_position_list.requestFocus();
//            return false;
//        }
        return true;

    }

    private boolean validation1() {
        if (TextUtils.isEmpty(ContactMode)) {
            contactModeSiteVisit.setError("Plz Select Contact Mode");
            contactModeSiteVisit.requestFocus();

            contactModeOnCall.setError("Plz Select Contact Mode");
            contactModeOnCall.requestFocus();
            return false;

        }


        if (TextUtils.isEmpty(tv_date_picker.getText().toString())) {
            tv_date_picker.setError("Plz Select Visit Date");
            tv_date_picker.requestFocus();
            return false;

        }

        if (TextUtils.isEmpty(tv_timee.getText().toString())) {
            tv_timee.setError("Plz Select Visit Time");
            tv_timee.requestFocus();
            return false;

        }

//        if (TextUtils.isEmpty(p1_agent_code.getText().toString())) {
//            p1_agent_code.setError("Plz Enter Agent Code");
//            p1_agent_code.requestFocus();
//            return false;
//        }

        if (TextUtils.isEmpty(p1_customer_list.getText().toString())) {
            p1_customer_list.setError("Plz Enter Customer List");
            p1_customer_list.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(customer_dob_picker.getText().toString())) {
            customer_dob_picker.setError("Plz Enter DOB");
            customer_dob_picker.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(p1_mobile1.getText().toString())) {
            p1_mobile1.setError("Plz Enter Mobile 1");
            p1_mobile1.requestFocus();
            return false;
        }
        if (p1_mobile1.length() != 10) {
            p1_mobile1.setError("10 Digit");
            p1_mobile1.requestFocus();
            Toast.makeText(this, "enter proper mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (p1_mobile3.getText().toString().length() > 0){
            if (p1_mobile3.length() != 10) {
                p1_mobile3.setError("10 Digit");
                p1_mobile3.requestFocus();
                Toast.makeText(this, "enter proper mobile no", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (p1_mobile2.getText().toString().length() > 0){
            if (p1_mobile2.length() != 10) {
                p1_mobile2.setError("10 Digit");
                p1_mobile2.requestFocus();
                Toast.makeText(this, "enter proper mobile no", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

//        if (TextUtils.isEmpty(p1_mobile2.getText().toString())) {
//            p1_mobile2.setError("Plz Enter Mobile 2");
//            p1_mobile2.requestFocus();
//            return false;
//        }
//        if (TextUtils.isEmpty(p1_mobile3.getText().toString())) {
//            p1_mobile3.setError("Plz Enter Mobile 3");
//            p1_mobile3.requestFocus();
//            return false;
//
//        }
        return true;

    }

    private void stringinitialization() {

        //p1
        s_tv_date_picker = tv_date_picker.getText().toString().trim();
        s_tv_timee = tv_timee.getText().toString().trim();
        s_p1_agent_code = p1_agent_code.getText().toString().trim();
        s_p1_customer_list = p1_customer_list.getText().toString().trim();
        s_p1_mobile1 = p1_mobile1.getText().toString().trim();
        s_p1_mobile2 = p1_mobile2.getText().toString().trim();
        s_p1_mobile3 = p1_mobile3.getText().toString().trim();

        ///p2
        s_p2_address = p2_address.getText().toString().trim();
        s_p2_colony_list = p2_colony_list.getText().toString().trim();
        s_edt_height = edt_height.getText().toString().trim();
        c_edt_weight = edt_weight.getText().toString().trim();
        s_p2_site_position_list = p2_site_position_list.getText().toString().trim();

        //p3
        s_dialog_add_product = dialog_add_product.getText().toString().trim();
        s_filledTextField2 = filledTextField2.getText().toString().trim();

        s_filledTextField3 = filledTextField3.getText().toString().trim();
        s_filledTextField4 = filledTextField4.getText().toString().trim();
        s_filledTextField5 = filledTextField5.getText().toString().trim();

        //p4
        s_dialog_type_person_list = dialog_type_person_list.getText().toString().trim();
        s_site_filledTextField2 = site_filledTextField2.getText().toString().trim();
        s_site_filledTextField3 = site_filledTextField3.getText().toString().trim();
        s_site_on = site_on.getText().toString().trim();
        s_site_off = site_off.getText().toString().trim();

        //p5
        s_tv_date_picker_page2 = tv_date_picker_page2.getText().toString().trim();
        s_tv_time = tv_time.getText().toString().trim();
        s_p4_remark = p4_remark.getText().toString().trim();

    }

    private void callWeb() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        WebService.getClient().site_position_list(position_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<site_position_list_POJO>() {
            @Override
            public void onResponse(Call<site_position_list_POJO> call, Response<site_position_list_POJO> response) {

                if (response.body().getResult() != null) {


                    if (position_autocompettextview.size() > 0) {

                        position_autocompettextview.clear();
                        position_autocompettextviewID.clear();
                    }
                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        position_autocompettextview.add(response.body().getResult().get(i).getPositionName());
                        position_autocompettextviewID.add(response.body().getResult().get(i).getPositionId());
                    }


                    Log.d("TAG", "position_autocompettextview: " + position_autocompettextview);
                    Log.d("TAG", "position_autocompettextviewID: " + position_autocompettextviewID);

                    adapterItems4 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, position_autocompettextview);
                    p2_site_position_list.setAdapter(adapterItems4);
                    adapterItems4.notifyDataSetChanged();

                } else {

//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<site_position_list_POJO> call, Throwable t) {

            }
        });

    }

    private void callWeb2() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        WebService.getClient().colony_list(colony_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),

                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<colony_list_POJO>() {
            @Override
            public void onResponse(Call<colony_list_POJO> call, Response<colony_list_POJO> response) {

                if (response.body().getResult() != null) {

                    if (colony_autocompettextview.size() > 0) {

                        colony_autocompettextview.clear();
                        colony_autocompettextviewID.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        colony_autocompettextview.add(response.body().getResult().get(i).getColonyName());
                        colony_autocompettextviewID.add(response.body().getResult().get(i).getColonyId());

                    }

                    Log.d("TAG", "colony_autocompettextviewID: " + colony_autocompettextview);
                    Log.d("TAG", "colony_autocompettextviewID: " + colony_autocompettextviewID);


                    adapterItems3 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, colony_autocompettextview);
                    p2_colony_list.setAdapter(adapterItems3);
                    adapterItems3.notifyDataSetChanged();



                } else {

//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<colony_list_POJO> call, Throwable t) {

            }

        });

    }

    private void callWeb3() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        WebService.getClient().customer_list_dropdown(customer_list,
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("user_id", ""),
//                sharedPreferences.getString("mobile_1", ""),
//                sharedPreferences.getString("mobile_2", ""),
//                sharedPreferences.getString("mobile_3", ""),

                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<customer_list_dropdown_POJO>() {
            @Override
            public void onResponse(Call<customer_list_dropdown_POJO> call, Response<customer_list_dropdown_POJO> response) {
                Log.d("respone_body","response"+response.body().getResult());

                if (response.body().getResult() != null) {

                    if (autocompettextview.size() > 0) {

                        autocompettextview.clear();
                        autocompettextviewID.clear();
                        autocompettextviewphone1.clear();
                        autocompettextviewphone2.clear();
                        autocompettextviewphone3.clear();
                        autocompettextdob.clear();
                        autocompettextaddress.clear();
                        autocompettextcolonyid.clear();
                        autocompettextcolony.clear();
                    }
                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        autocompettextview.add(response.body().getResult().get(i).getCustomerName());

                        autocompettextviewID.add(response.body().getResult().get(i).getCustomerId());
                        autocompettextviewphone1.add(response.body().getResult().get(i).getCustomerphone1());
                        autocompettextviewphone2.add(response.body().getResult().get(i).getCustomerphone2());
                        autocompettextviewphone3.add(response.body().getResult().get(i).getCustomerphone3());
                        autocompettextdob.add(response.body().getResult().get(i).getCustomerDob());
                        autocompettextaddress.add(response.body().getResult().get(i).getAddress());
                        autocompettextcolonyid.add(response.body().getResult().get(i).getColonyId());
                        autocompettextcolony.add(response.body().getResult().get(i).getColony());


                        Log.d("TAG", "onResponse12444: " + autocompettextview);
                        Log.d("TAG", "onResponse12444sds: " + autocompettextviewID);
                        Log.d("TAG", "onResponsePhone1: " + autocompettextviewphone1);
                        Log.d("TAG", "onResponsephone2: " + autocompettextviewphone2);
                        Log.d("TAG", "onResponsephone3: " + autocompettextviewphone3);
                        Log.d("TAG", "onResponsephoneDob: " + autocompettextdob);
                        Log.d("TAG", "onResponsephoneAddress: " + autocompettextaddress);
                        Log.d("TAG", "onResponsephoneColonyId: " + autocompettextcolonyid);
                        Log.d("TAG", "onResponsephoneColony: " + autocompettextcolony);
                    }


                    adapterItems2 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, autocompettextview);
                    p1_customer_list.setAdapter(adapterItems2);
                    adapterItems2.notifyDataSetChanged();
//                    if (autocompettextviewphone1 != null ){
//
//                       // p1_mobile1.setText(autocompettextviewphone1.indexOf());
//                        p1_mobile1.setText(autocompettextviewphone1.toString());
//                        Toast.makeText(FormActivity.this, "Phone number is"+autocompettextviewphone1, Toast.LENGTH_SHORT).show();
//                    }
//                    if (autocompettextviewphone2!=null){
//                        p1_mobile2.setText(autocompettextviewphone2.toString());
//                        Toast.makeText(FormActivity.this, "Phone number is"+autocompettextviewphone2, Toast.LENGTH_SHORT).show();
//                    }
//                    if (autocompettextviewphone3!=null){
//                        p1_mobile3.setText(autocompettextviewphone3.toString());
//                        Toast.makeText(FormActivity.this, "Phone number is"+autocompettextviewphone3, Toast.LENGTH_SHORT).show();
//                    }

                } else {

//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<customer_list_dropdown_POJO> call, Throwable t) {

            }
        });
    }

    private void callWeb4() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

                    adapterItems5 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, product_autocompettextview);
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


                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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
                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<product_list_POJO> call, Throwable t) {

            }
        });
    }

    private void callWeb5() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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
                    adapterItems6 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, person_autocompettextview);
                    dialog_type_person_list.setAdapter(adapterItems6);
                    adapterItems6.notifyDataSetChanged();

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

                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();
                            editor.putString("ad_site_person_id", ad_site_person_id);
                            editor.putString("ad_site_person_name", ad_site_person_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<site_person_list_POJO> call, Throwable t) {

            }
        });
    }


    private void callWebname() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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
                        personname_autocompettextcontactno.clear();
                        personname_autocompettextDob.clear();
                        personname_autocompettextOnSite.clear();
                        personname_autocompettextOffSite.clear();
                    }

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        personname_autocompettextview.add(response.body().getResult().get(i).getPersonName());
                        personname_autocompettextviewID.add(response.body().getResult().get(i).getPersonId());
                        personname_autocompettextcontactno.add(response.body().getResult().get(i).getContactNo());
                        personname_autocompettextDob.add(response.body().getResult().get(i).getPersonDob());
                        personname_autocompettextOnSite.add(response.body().getResult().get(i).getoNSite());
                        personname_autocompettextOffSite.add(response.body().getResult().get(i).getoFFSite());
                    }
                    Log.d("TAG", "person_autocompettextview: " + personname_autocompettextview);
                    Log.d("TAG", "person_autocompettextviewID: " + personname_autocompettextviewID);
                    Log.d("TAG", "person_autocompettextviewContactNo: " + personname_autocompettextcontactno);
                    adapterItemsname = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, personname_autocompettextview);
                    site_filledTextField2.setAdapter(adapterItemsname);
                    adapterItemsname.notifyDataSetChanged();

                    site_filledTextField2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            if (personname_autocompettextview.size() == 0) {

                            } else {
                                ad_site_person_namename = personname_autocompettextview.get(i);
                                ad_site_person_idname = personname_autocompettextviewID.get(i);
                                ad_site_person_contact_no = personname_autocompettextcontactno.get(i);
                                ad_site_person_dob = personname_autocompettextDob.get(i);
                                ad_site_person_on_site = personname_autocompettextOnSite.get(i);
                                ad_site_person_off_site = personname_autocompettextOffSite.get(i);
                            }

//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();

                            // Parse the original date string into a Date object
                            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = originalFormat.parse(ad_site_person_dob);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            SimpleDateFormat desiredFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = desiredFormat.format(date);


                            Log.d("TAG", "wdcwedcwdecwd: " + "wedcwdecwdc");

                            Log.d("TAG", "ad_site_persossn_id: " + ad_site_person_idname);
                            Log.d("TAG", "ad_site_person_name: " + ad_site_person_namename);
                            Log.d("TAG", "ad_site_person_contactno: " + ad_site_person_contact_no);

                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();
                            editor.putString("ad_site_person_idname", ad_site_person_idname);
                            editor.putString("ad_site_person_namename", ad_site_person_namename);
                            editor.putString("ad_site_person_contact_no", ad_site_person_contact_no);
                            editor.putString("ad_site_person_dob", ad_site_person_dob);
                            editor.putString("ad_site_person_on_site", ad_site_person_on_site);
                            editor.putString("ad_site_person_off_site", ad_site_person_off_site);
                            editor.apply();

                            site_filledTextField3.setText(ad_site_person_contact_no);
                            site_peron_dob.setText(formattedDate);
                            site_on.setText(ad_site_person_on_site);
                            site_off.setText(ad_site_person_off_site);

                            adapterItemsname.notifyDataSetChanged();
                        }
                    });

                } else {
//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<person_name_list> call, Throwable t) {

            }
        });
    }



    private void callWeb6() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

                    adapterItems7 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brand_autocompettextview);
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


                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();

                            editor.putString("ad_brand_id", ad_brand_id);
                            editor.putString("ad_brand_name", ad_brand_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<brand_POJO> call, Throwable t) {

            }
        });

    }

    private void callWebnew() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

//                    adapterItems7 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, exis_autocompettextview);
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
//                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = location.edit();
//
//                            editor.putString("ad_exis_id", ad_exis_id);
//                            editor.putString("ad_exis_name", ad_exis_name);
//                            editor.apply();
//                        }
//                    });

                } else {
//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<brand_POJO> call, Throwable t) {

            }
        });

    }

    private void callWeb7() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
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

                    adapterItems8 = new ArrayAdapter<String>(FormActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unit_autocompettextview);
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


                            SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = location.edit();

                            editor.putString("ad_unit_id", ad_unit_id);
                            editor.putString("ad_unit_name", ad_unit_name);
                            editor.apply();
                        }
                    });

                } else {
//                    Toast.makeText(FormActivity.this, "No response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<unit_list_POJO> call, Throwable t) {

            }
        });
    }

    private void calculation() {
        if (height.equals("") || width.equals("")){

        }
        else{
            calculation = String.valueOf(Integer.parseInt(height) * Integer.parseInt(width));
            cal.setText(calculation);
        }


    }

    public void showAlertDialogButtonClicked1(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this, R.style.CustomAlertDialog).setCancelable(false);
        final View customLayout = getLayoutInflater().inflate(R.layout.forndialog, null);
        builder.setView(customLayout);




//        svendor = filledTextField1.getText().toString().trim();
//        sbrand = filledTextField1.getText().toString().trim();
//        sbrand = filledTextField1.getText().toString().trim();
//        sbrand = filledTextField1.getText().toString().trim();

        ImageView close_dialog = customLayout.findViewById(R.id.close_dialog);
        TextView dailog_next = customLayout.findViewById(R.id.dailog_next);

//        close_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                Intent i = new Intent(FormActivity.this, HomeFragment.class);
//                startActivity(i);
//
//            }
//        });

        dailog_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!flag){
                    dialog.dismiss();
                    finish();
                    Intent i = new Intent(FormActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    dialog.dismiss();
                    finish();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    Intent i = new Intent(FormActivity.this, customer_detail_Activity.class);
                    i.putExtra("visit_id",sharedPreferences.getString("visit_id", ""));
                    startActivity(i);
                }


            }
        });

        dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    public void showAlertDialogButtonClicked2(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this, R.style.CustomAlertDialog);
        final View customLayout = getLayoutInflater().inflate(R.layout.add_product_dialog, null);
        builder.setView(customLayout);

        dialog_add_product = customLayout.findViewById(R.id.dialog_add_product);
        filledTextField2 = customLayout.findViewById(R.id.filledTextField2);
        filledTextField3 = customLayout.findViewById(R.id.filledTextField3);
        filledTextField4 = customLayout.findViewById(R.id.filledTextField4);
        filledTextField5 = customLayout.findViewById(R.id.filledTextField5);
        dailog_Unit = customLayout.findViewById(R.id.dailog_Unit);
        dailog_stock = customLayout.findViewById(R.id.dailog_stock);
        TextView add = customLayout.findViewById(R.id.add);

        //product_list
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation3()) {
                    s_dialog_add_product = dialog_add_product.getText().toString().trim();
                    s_filledTextField2 = filledTextField2.getText().toString().trim();
                    s_filledTextField3 = filledTextField3.getText().toString().trim();
                    s_filledTextField4 = filledTextField4.getText().toString().trim();
                    s_filledTextField5 = filledTextField5.getText().toString().trim();
                    s_dailog_Unit = dailog_Unit.getText().toString().trim();
                    s_dailog_stock = dailog_stock.getText().toString().trim();

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    itemlist.add(new add_product_POJO(dialog_add_product.getText().toString(), filledTextField2.getText().toString(), filledTextField3.getText().toString(), filledTextField4.getText().toString(), filledTextField5.getText().toString(), dailog_Unit.getText().toString(), dailog_stock.getText().toString(),ad_pro_id,ad_brand_id,ad_unit_id));
                    add_product_adapter = new add_product_Adapter(itemlist, FormActivity.this, FormActivity.this) {
                        @Override
                        public void onItemChanged(List<add_product_POJO> list) {
                            itemlist.clear();
                            itemlist.addAll(list);
                            add_product_adapter.notifyDataSetChanged();

                        }
                    };
                    rec_product.setAdapter(add_product_adapter);
                    add_product_adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }


            }


            private boolean validation3() {
                if (TextUtils.isEmpty(dialog_add_product.getText().toString())) {
                    dialog_add_product.setError("Select product from list");
                    dialog_add_product.requestFocus();
                    return false;
                }

                if (TextUtils.isEmpty(ad_pro_id)) {
                    dialog_add_product.setError("Select product from list");
                    dialog_add_product.requestFocus();
                    return false;
                }


                if (TextUtils.isEmpty(filledTextField2.getText().toString())) {
                    filledTextField2.setError("Select brand from list");
                    filledTextField2.requestFocus();
                    return false;
                }

                if (TextUtils.isEmpty(ad_brand_id)) {
                    filledTextField2.setError("Select brand from list");
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
                    dailog_Unit.setError("Select unit from list");
                    dailog_Unit.requestFocus();
                    return false;

                }

                if (TextUtils.isEmpty(ad_unit_id)) {
                    dailog_Unit.setError("Select unit from list");
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

                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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
                ad_exis_name = brand_list;
                ad_exis_id = "";
                Log.d("TAG", "ad_pro_name: " + ad_exis_name);
                Log.d("TAG", "ad_pro_id: " + ad_exis_id);


//                            ad_pro_name = parent.getItemAtPosition(position).toString();
//                            ad_pro_id = parent.getItemAtPosition(position).toString();


                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = location.edit();

                editor.putString("ad_exis_id", ad_exis_id);
                editor.putString("ad_exis_name", ad_exis_name);
                editor.apply();
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
//                brand_list = String.valueOf(charSequence);
//                if (brand_list.length() >= 2) {
//                    callWebnew();
//                }
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

                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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
//                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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

                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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

    public void showAlertDialogButtonClicked3(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this, R.style.CustomAlertDialog);
        final View customLayout = getLayoutInflater().inflate(R.layout.add_site_related_persion_dialog, null);
        builder.setView(customLayout);


        dialog_type_person_list = customLayout.findViewById(R.id.dialog_type_person_list);
        site_filledTextField2 = customLayout.findViewById(R.id.site_filledTextField2);
        site_filledTextField3 = customLayout.findViewById(R.id.site_filledTextField3);
        site_on = customLayout.findViewById(R.id.site_on);
        site_off = customLayout.findViewById(R.id.site_off);
        site_peron_dob = customLayout.findViewById(R.id.site_peron_dob);
        TextView add = customLayout.findViewById(R.id.add);

//        site_peron_dob.setText(dateTime);

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
        site_peron_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                materialDateBuilder.setTitleText("SELECT A DATE");
                final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        try {
                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                            calendar.setTimeInMillis(selection);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate  = format.format(calendar.getTime());

                            dateTracker = materialDatePicker.getHeaderText();

                            if (!dateTracker.equals("")) {
                            }
                            site_peron_dob.setText(formattedDate);

                            // in the above statement, getHeaderText
                            // is the selected date preview from the
                            // dialog
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
           */
                new DatePickerDialog(FormActivity.this,/* R.style.datepicker,*/date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation4()) {
                    s_dialog_type_person_list = dialog_type_person_list.getText().toString().trim();
                    s_site_filledTextField2 = site_filledTextField2.getText().toString().trim();
                    s_site_filledTextField3 = site_filledTextField3.getText().toString().trim();
                    s_site_on = site_on.getText().toString().trim();
                    s_site_off = site_off.getText().toString().trim();

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    itemlist2.add(new site_related_person_POJO(dialog_type_person_list.getText().toString(), site_filledTextField2.getText().toString(), site_filledTextField3.getText().toString(), ad_site_person_id, site_on.getText().toString(), site_off.getText().toString(),site_peron_dob.getText().toString(),ad_site_person_id,ad_site_person_idname));
                    site_related_person_adapter = new site_related_person_Adapter(itemlist2, FormActivity.this,FormActivity.this);
                    rec_site_persion.setLayoutManager(linearLayoutManager2);
                    rec_site_persion.setAdapter(site_related_person_adapter);
                    site_related_person_adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

            }

            private boolean validation4() {
                if (TextUtils.isEmpty(dialog_type_person_list.getText().toString())) {
                    dialog_type_person_list.setError("Select site person from list Name");
                    dialog_type_person_list.requestFocus();
                    return false;
                }

                if (TextUtils.isEmpty(ad_site_person_id)) {
                    dialog_type_person_list.setError("Select site person from list Name");
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
                ad_site_person_id = "";
                type_person_list = String.valueOf(charSequence);
                Log.d("TAG", "dggh: " + charSequence);
                if (type_person_list.length() >= 2) {
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

                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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
                if (productname_list.length() >= 2) {
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

                SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                uriPhoto = data.getData();
                waypathPhoto_book1 = getFilePath(FormActivity.this, uriPhoto);
                Uri selectedImageUri = data.getData();
                preview.setImageURI(selectedImageUri);
                //profile_image.setImageURI(uri);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
        /*if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            image_im1 = ImagePicker.getFirstImageOrNull(data);
            Log.d("TAG", "imageimonActivityResult: " + image_im1);
            Glide.with(this).load(image_im1.getPath()).into(profile_image);

        }*/
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }


    public String getFilePath(Context context, Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/all_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



}