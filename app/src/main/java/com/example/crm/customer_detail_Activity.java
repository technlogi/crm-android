package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.crm.Adapter.add_product_Adapter;
import com.example.crm.Adapter.dailyReportLocalAdapter;
import com.example.crm.Adapter.detail_add_product_adptr;
import com.example.crm.Adapter.detail_site_related_person_adapter;
import com.example.crm.Adapter.last_remark_Adapter;
import com.example.crm.Adapter.nextVisitAdptr;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllNextVisitDataBean;
import com.example.crm.POJO.AllReportDataBean;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.detail_add_product_pojo;
import com.example.crm.POJO.detail_site_related_person_pojo;
import com.example.crm.POJO.last_remark_POJO;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.POJO.visit_next_add_remark_POJO;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class customer_detail_Activity extends AppCompatActivity {

    ProgressDialog pro;

    ImageView back_img, edit_data,edit_remark_data,shop_img;
    SwipeRefreshLayout container5;
    RecyclerView rec_detail_add_product;
    detail_add_product_adptr  detailAddProductAdptr;

    RecyclerView rec_site_related_person;
    detail_site_related_person_adapter detailSiteRelatedPersonAdapter;
    List<detail_site_related_person_pojo> detail_site_person;

    RecyclerView rec_last_remark;
    last_remark_Adapter last_remark_adapter;
    List<last_remark_POJO> last_remark_list;

    String dateTracker = "";
    String dateTracker2 = "";
    String chooser = "";
    private MaterialTimePicker picker;
    private Calendar calendar = Calendar.getInstance();
    String timetracker = "";
    String timetracker2 = "";
    String visit_id,url;

    AlertDialog dialog;
    public static List<visit_detail_POJO.Product> detail_add_productlist;

    AutoCompleteTextView dialog_tv_date_picker_page,dialog_tv_time;
    AutoCompleteTextView dialog_tv_date_picker_visit,dialog_tv_time_visit;
    TextView date,time,agent_code,customer_name,mobile_1,mobile_2,mobile_3;
    TextView addresss,colony,site_status,site_width,site_hight,positon,detail_dob,customer_category;

    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManager2;
    LinearLayoutManager linearLayoutManager3;
    TextInputEditText dialog_agent_code,dialog_remark;
    SharedPreferences sharedPreferences;

    String s_dialog_agent_code,s_dialog_remark;
    List<last_remark_POJO> ss;

    Uri uriPhoto;
    String waypathPhoto_book1;
    ImageView preview,upload_img;
    private LocationRequest locationRequest;
    MultipartBody.Part imagePartPhoto_book1 = null;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "onResponse555: 222");
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_customer_detail);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = getIntent();
        visit_id=intent.getStringExtra("visit_id");
        dbHandler = new DBHandler(customer_detail_Activity.this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        back_img = findViewById(R.id.back_img);
        edit_data = findViewById(R.id.edit_data);
        edit_remark_data = findViewById(R.id.edit_remark_data);
        shop_img = findViewById(R.id.shop_img);
        detail_dob = findViewById(R.id.detail_dob);
        customer_category = findViewById(R.id.customer_category);

//        container5 = findViewById(R.id.container5);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        agent_code = findViewById(R.id.agent_code);
        customer_name = findViewById(R.id.customer_name);
        mobile_1 = findViewById(R.id.mobile_1);
        mobile_2 = findViewById(R.id.mobile_2);
        mobile_3 = findViewById(R.id.mobile_3);

        addresss = findViewById(R.id.addresss);
        colony = findViewById(R.id.colony);
        site_status = findViewById(R.id.site_status);
        site_width = findViewById(R.id.site_width);
        site_hight = findViewById(R.id.site_hight);
        positon = findViewById(R.id.positon);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editor.putString("visit_id",visit_id);
        editor.apply();

        shop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Dialog settingsDialog = new Dialog(customer_detail_Activity.this);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.detail_image_popup
                        , null));
                settingsDialog.show();
*/

                final Dialog dialog = new Dialog(customer_detail_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //dialog.setContentView(R.layout.custom_fullimage_dialog);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.detail_image_popup,
                        (ViewGroup) findViewById(R.id.root_layout));
                ImageView image = (ImageView) layout.findViewById(R.id.img_view);
                ImageView img_ok = (ImageView) layout.findViewById(R.id.img_ok);

//                Button cancel = (Button) layout.findViewById(R.id.btn_ok);
                Glide.with(customer_detail_Activity.this).load(url).into(image);

                img_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setContentView(layout);
                dialog.show();

               /* Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                shop_img.startAnimation(animZoomIn);*/
            }
        });

//        shop_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
//                shop_img.startAnimation(animZoomOut);
//            }
//        });


        edit_data.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(customer_detail_Activity.this, FormActivity.class);
                FormActivity.flag=true;

//                i.putExtra("date", date.getText().toString());
//                i.putExtra("time", time.getText().toString());
//                i.putExtra("agent_code", agent_code.getText().toString());
//                i.putExtra("customer_name", customer_name.getText().toString());
//                i.putExtra("mobile_1", mobile_1.getText().toString());
//                i.putExtra("mobile_2", mobile_2.getText().toString());
//                i.putExtra("mobile_3", mobile_3.getText().toString());
//
//                i.putExtra("addresss", addresss.getText().toString());
//                i.putExtra("colony", colony.getText().toString());
//                i.putExtra("site_status", site_status.getText().toString());
//                i.putExtra("site_width", site_width.getText().toString());
//                i.putExtra("site_hight", site_hight.getText().toString());
//                i.putExtra("positon", positon.getText().toString());

                startActivity(i);
            }
        });

        edit_remark_data.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked1(view);
            }
        });


        //rec1
        rec_detail_add_product = findViewById(R.id.rec_detail_add_product);
        detail_add_productlist = new ArrayList<>();

         linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);

        //rec2
        rec_site_related_person = findViewById(R.id.rec_site_related_person);
         linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);


        //rec3
        rec_last_remark = findViewById(R.id.rec_last_remark);
         linearLayoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        pro = new ProgressDialog(this);

        pro.setCancelable(false);
        pro.setMessage("Please wait ...");
        RecyclerElectric();
//        container5.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                RecyclerElectric();
//                container5.setRefreshing(false);
//            }
//        });







    }

    ArrayList<AllNextVisitDataBean>arAllNextVisitData;
    nextVisitAdptr nextVisitAdptr;
    public void GetOfflineData()
    {
        arAllNextVisitData = dbHandler.GetAllNextVisitData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(customer_detail_Activity.this);
        rec_site_related_person.setLayoutManager(linearLayoutManager);
        nextVisitAdptr = new nextVisitAdptr(arAllNextVisitData, customer_detail_Activity.this);
        rec_site_related_person.setAdapter(nextVisitAdptr);

//        rec_site_related_person.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        detailSiteRelatedPersonAdapter = new detail_site_related_person_adapter(response.body().getResult().getPerson(), getApplicationContext());
//        //rec_site_related_person.setLayoutManager(linearLayoutManager2);
//        rec_site_related_person.setAdapter(detailSiteRelatedPersonAdapter);


    }

    private void RecyclerElectric() {

        pro.show();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("TAG", "RecyclerElectric1:"+visit_id+":"+sharedPreferences.getString("user_id", "")+":"+sharedPreferences.getString("latitude", "")+":"+sharedPreferences.getString("longitude", "")+":"+sharedPreferences.getString("IMEI_no", ""));

        WebService.getClient().visit_detail_POJO(visit_id,
                sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<visit_detail_POJO>() {
            @Override
            public void onResponse(Call<visit_detail_POJO> call, Response<visit_detail_POJO> response) {

                pro.dismiss();


                if (response.body() != null){
                    if (response.body().getResult().getProduct()!=null){
                        detail_add_productlist = response.body().getResult().getProduct();
                        rec_detail_add_product.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        detailAddProductAdptr = new detail_add_product_adptr(response.body().getResult().getProduct(), getApplicationContext());

//                        rec_detail_add_product.setLayoutManager(linearLayoutManager);
                        rec_detail_add_product.setAdapter(detailAddProductAdptr);
                    }

                    if (response.body().getResult().getPerson()!=null){
                        rec_site_related_person.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        detailSiteRelatedPersonAdapter = new detail_site_related_person_adapter(response.body().getResult().getPerson(), getApplicationContext());
                        //rec_site_related_person.setLayoutManager(linearLayoutManager2);
                        rec_site_related_person.setAdapter(detailSiteRelatedPersonAdapter);
                    }

                    if (response.body().getResult().getNextVisit()!=null){
                        ss = new ArrayList<>();
                        for (int i = 0 ; i < response.body().getResult().getNextVisit().size();i++){

                            ss.add(new last_remark_POJO(
                                    response.body().getResult().getNextVisit().get(i).getContact_mode(),
                                    response.body().getResult().getNextVisit().get(i).getNextVisitDate(),
                                    response.body().getResult().getNextVisit().get(i).getNextVisitTime(),
                                    response.body().getResult().getNextVisit().get(i).getRemark()));
                        }

                        rec_last_remark.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        last_remark_adapter = new last_remark_Adapter(ss, getApplicationContext());
                        //rec_last_remark.setLayoutManager(linearLayoutManager3);
                        rec_last_remark.setAdapter(last_remark_adapter);
                    }

                    date.setText(response.body().getResult().getVisitDate());
                    time.setText(response.body().getResult().getVisitTime());
                    agent_code.setText(response.body().getResult().getMarketAgentCode());
                    customer_name.setText(response.body().getResult().getCustomerName());
                    mobile_1.setText(response.body().getResult().getMobile1());
                    mobile_2.setText(response.body().getResult().getMobile2());
                    mobile_3.setText(response.body().getResult().getMobile3());
                    detail_dob.setText(response.body().getResult().getCustomerDob());

                    addresss.setText(response.body().getResult().getAddresss());
                    colony.setText(response.body().getResult().getColony());
                    site_status.setText(response.body().getResult().getSiteStatus());
                    site_width.setText(response.body().getResult().getSiteWidth());
                    site_hight.setText(response.body().getResult().getSiteHight());
                    positon.setText(response.body().getResult().getPostion());
                    customer_category.setText(response.body().getResult().getCategory());

                    url=response.body().getResult().getImage();

                    Glide.with(customer_detail_Activity.this).load(url).into(shop_img);

                    Log.d("imgggurllll",response.body().getResult().getImage());
                }
            }

            @Override
            public void onFailure(Call<visit_detail_POJO> call, Throwable t) {
                pro.dismiss();
                Log.d("TAG", "onResponse: 223 "+t);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    private void showAlertDialogButtonClicked1(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(customer_detail_Activity.this, R.style.CustomAlertDialog);
        final View customLayout = getLayoutInflater().inflate(R.layout.dailog_add_remark, null);
        builder.setView(customLayout);

        SimpleDateFormat simpleDateFormat,simpleDateFormat2;
        String dateTime, TIME;
        String dateTime2, TIME2;
//        ImageView upload_img;
        ImageView close_dialog;

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        MaterialDatePicker.Builder materialDateBuilder2 = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder2.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker2 = materialDateBuilder.build();

         dialog_tv_date_picker_page = customLayout.findViewById(R.id.dialog_tv_date_picker_page);
         dialog_tv_time = customLayout.findViewById(R.id.dialog_tv_time);
        dialog_tv_date_picker_visit = customLayout.findViewById(R.id.dialog_tv_date_picker_visit);
        dialog_tv_date_picker_visit.setFocusable(false);
        dialog_tv_time_visit = customLayout.findViewById(R.id.dialog_tv_time_visit);
        dialog_tv_time_visit.setFocusable(false);
        TextView dialog_add = customLayout.findViewById(R.id.dialog_add);
//         dialog_agent_code = customLayout.findViewById(R.id.dialog_agent_code);
         dialog_remark = customLayout.findViewById(R.id.dialog_remark);
        upload_img = customLayout.findViewById(R.id.upload_img);
        preview = customLayout.findViewById(R.id.preview);
        close_dialog = customLayout.findViewById(R.id.close_dialog);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImagePicker.create(EditProfileActivity.this).single().start();
                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                gal.setType("image/*");
                startActivityForResult(gal, 1);*/
                ImagePicker.with(customer_detail_Activity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .cameraOnly()    //User can only capture image using Camera
//                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
//                getCurrentLocation();


            }
        });


        //date picker1
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        dialog_tv_date_picker_page.setText("");
        dialog_tv_date_picker_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis(selection);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate  = format.format(calendar.getTime());

                        dateTracker = materialDatePicker.getHeaderText();

                        if (!dateTracker.equals("")) {
                        }
                        dialog_tv_date_picker_page.setText(formattedDate);

                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
            }
        });



        //time picker
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        TIME = simpleDateFormat.format(Calendar.getInstance().getTime());
        dialog_tv_time.setText("");
        dialog_tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooser = "picker";
                showTimePicker();


            }

            private void showTimePicker() {

                picker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("select time")
                        .build();

                picker.show(customer_detail_Activity.this.getSupportFragmentManager(), "hello");


                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        calendar.set(Calendar.MINUTE, picker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        Log.d("picker", String.valueOf(picker.getHour()));
                        Log.d("picker", String.valueOf(picker.getMinute()));
                        timetracker = String.valueOf(picker.getHour()) + ":" + String.valueOf(picker.getMinute());
                        dialog_tv_time.setText(timetracker);

                    }
                });
            }
        });


        //date picker2
        simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        dateTime2 = simpleDateFormat2.format(Calendar.getInstance().getTime());
        dialog_tv_date_picker_visit.setText(dateTime2);

//        dialog_tv_date_picker_visit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                materialDatePicker2.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
//                materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onPositiveButtonClick(Object selection) {
//
//                        dateTracker2 = materialDatePicker2.getHeaderText();
//
//                        if (!dateTracker2.equals("")) {
//                        }
//                        dialog_tv_date_picker_visit.setText(dateTracker2);
//
//                        // in the above statement, getHeaderText
//                        // is the selected date preview from the
//                        // dialog
//                    }
//                });
//            }
//        });



        //time picker2
        simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        TIME2 = simpleDateFormat.format(Calendar.getInstance().getTime());
        dialog_tv_time_visit.setText(TIME2);

//        dialog_tv_time_visit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chooser = "picker";
//                showTimePicker();
//
//
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
//                picker.show(customer_detail_Activity.this.getSupportFragmentManager(), "hello");
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
//                        Log.d("picker", String.valueOf(picker.getHour()));
//                        Log.d("picker", String.valueOf(picker.getMinute()));
//                        timetracker2 = String.valueOf(picker.getHour()) + ":" + String.valueOf(picker.getMinute());
//                        dialog_tv_time_visit.setText(timetracker2);
//
//                    }
//                });
//            }
//        });
        pro = new ProgressDialog(this);

        pro.setCancelable(false);
        pro.setMessage("Please wait ...");
        dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pro.show();
                if(validation5()){

//                    s_dialog_agent_code = dialog_agent_code.getText().toString().trim();
                    dateTracker = dialog_tv_date_picker_page.getText().toString().trim();
                    timetracker = dialog_tv_time.getText().toString().trim();
                    dateTracker2 = dialog_tv_date_picker_visit.getText().toString().trim();
                    timetracker2 = dialog_tv_time_visit.getText().toString().trim();
                    s_dialog_remark = dialog_remark.getText().toString().trim();

                    File file1 = new File(waypathPhoto_book1);
                    final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                    imagePartPhoto_book1 = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);

                    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (ActivityCompat.checkSelfPermission(customer_detail_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            if (isGPSEnabled()) {

                                LocationServices.getFusedLocationProviderClient(customer_detail_Activity.this)
                                        .requestLocationUpdates(locationRequest, new LocationCallback() {
                                            @SuppressLint("MissingPermission")
                                            @Override
                                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                                super.onLocationResult(locationResult);


                                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                                List<Address> addresses = null;

                                                LocationServices.getFusedLocationProviderClient(customer_detail_Activity.this)
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
                                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (CheckInternetConnection.checkInternetConnection(customer_detail_Activity.this)) {
                                                        WebService.getClient().visit_next_add_remark(
                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_agent_code.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_tv_date_picker_page.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_tv_time.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_remark.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
                                                                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("visit_id", "")),
                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_tv_date_picker_visit.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_tv_time_visit.getText().toString()),
                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("IMEI_no", "")),
                                                                imagePartPhoto_book12
                                                        ).enqueue(new Callback<visit_next_add_remark_POJO>() {
                                                            @Override
                                                            public void onResponse(Call<visit_next_add_remark_POJO> call, Response<visit_next_add_remark_POJO> response) {

                                                                pro.dismiss();

                                                                if(response.body() != null)
                                                                {
//
                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker);
                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker);

                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker2);
                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker2);

                                                                    Toast.makeText(customer_detail_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                ss.add(new last_remark_POJO(dialog_tv_date_picker_page.getText().toString(),
//                                                                        dialog_tv_time.getText().toString(),
//                                                                        dialog_remark.getText().toString(),
//                                                                        dialog_agent_code.getText().toString()
//                                                                ));
                                                                    last_remark_adapter = new last_remark_Adapter(ss, customer_detail_Activity.this);
                                                                    rec_last_remark.setAdapter(last_remark_adapter);
                                                                    last_remark_adapter.notifyDataSetChanged();
                                                                    RecyclerElectric();

                                                                }
                                                                else
                                                                {


                                                                }
                                                                dialog.dismiss();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<visit_next_add_remark_POJO> call, Throwable t) {

                                                                pro.dismiss();
                                                            }
                                                        });
                                                        }
                                                        else
                                                        {
//                                                            Log.e("Date ","str= "+str);
                                                            AllNextVisitDataBean allnextvisitdatabean=new AllNextVisitDataBean();
                                                            allnextvisitdatabean.setNEXTVISIT_PHOTO_COL(waypathPhoto_book1);
                                                            allnextvisitdatabean.setNEXTVISIT_USER_ID_COL(sharedPreferences.getString("user_id", ""));
                                                            allnextvisitdatabean.setNEXTVISIT_DATE_COL(dialog_tv_date_picker_page.getText().toString());
                                                            allnextvisitdatabean.setNEXTVISIT_TIME_COL(dialog_tv_time.getText().toString());
                                                            allnextvisitdatabean.setNEXTVISIT_REMARK_COL(dialog_remark.getText().toString());
                                                            allnextvisitdatabean.setNEXTVISIT_LAT_COL(String.valueOf(latitude));
                                                            allnextvisitdatabean.setNEXTVISIT_LOGI_COL(String.valueOf(longitude));
                                                            allnextvisitdatabean.setNEXTVISIT_VISIT_ID_COL(sharedPreferences.getString("visit_id", ""));
                                                            allnextvisitdatabean.setNEXTVISIT_NEXT_DATE_COL(dialog_tv_date_picker_visit.getText().toString());
                                                            allnextvisitdatabean.setNEXTVISIT_NEXT_TIME_COL( dialog_tv_time_visit.getText().toString());
                                                            allnextvisitdatabean.setNEXTVISIT_IMEI_COL(sharedPreferences.getString("IMEI_no", ""));


                                                            if (dbHandler.SaveNextVisitData(allnextvisitdatabean)) {
                                                                pro.dismiss();
                                                                dialog.dismiss();

                                                                Log.e("Msg_shyam", "Save data successfull");
                                                                Toast.makeText(customer_detail_Activity.this, "Save data successfull", Toast.LENGTH_SHORT).show();
                                                                GetOfflineData();

                                                            } else {
                                                                Log.e("Msg_shyam", "issue is in Save data");
                                                                Toast.makeText(customer_detail_Activity.this, "some issue is in inserting data", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }


                                                    }


                                                }

                                            }
                                        }, Looper.getMainLooper());

                            } else {
                                pro.dismiss();
                                turnOnGPS();
                            }

                        } else {
                            pro.dismiss();
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                    }else{
                        pro.dismiss();
                    }
                }
                else{
                    pro.dismiss();
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
        dialog.setCanceledOnTouchOutside(true);
    }

    private boolean validation5() {
        if (TextUtils.isEmpty(waypathPhoto_book1)) {
            Toast.makeText(customer_detail_Activity.this, "Select Shop image", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (TextUtils.isEmpty(dialog_agent_code.getText().toString())) {
//            dialog_agent_code.setError("Select agent code");
//            dialog_agent_code.requestFocus();
//            return false;
//        }
        if (TextUtils.isEmpty(dialog_tv_date_picker_page.getText().toString())) {
            dialog_tv_date_picker_page.setError("Select date");
            dialog_tv_date_picker_page.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(dialog_tv_time.getText().toString())) {
            dialog_tv_time.setError("Enter time");
            dialog_tv_time.requestFocus();
            return false;

        }
        if (TextUtils.isEmpty(dialog_remark.getText().toString())) {
            dialog_remark.setError("Enter Remark");
            dialog_remark.requestFocus();
            return false;

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                uriPhoto = data.getData();
                waypathPhoto_book1 = getFilePath(customer_detail_Activity.this, uriPhoto);
                Uri selectedImageUri = data.getData();
                preview.setImageURI(selectedImageUri);
                //profile_image.setImageURI(uri);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
        /*if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            image_im1 = ImagePicker.getFirstImageOrNull(data);
            Log.d("TAG", "imageimonActivityResult: " + image_im1);
            Glide.with(this).load(image_im1.getPath()).into(profile_image);

        }*/
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

//                getCurrentLocation();
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

//    private void getCurrentLocation() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//
//// Creating an Editor object to edit(write to the file)
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//// Storing the key and its value as the data fetched from edittext
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(customer_detail_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//                    LocationServices.getFusedLocationProviderClient(customer_detail_Activity.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @SuppressLint("MissingPermission")
//                                @Override
//                                public void onLocationResult(@NonNull LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//
//                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                                    List<Address> addresses = null;
//
//                                    LocationServices.getFusedLocationProviderClient(customer_detail_Activity.this)
//                                            .removeLocationUpdates(this);
//
//                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
//
//                                        int index = locationResult.getLocations().size() - 1;
//                                        double latitude = locationResult.getLocations().get(index).getLatitude();
//                                        double longitude = locationResult.getLocations().get(index).getLongitude();
//
//
//                                        try {
//                                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//
//
//                                        if (addresses != null && !addresses.isEmpty()) {
//
//                                            myEdit.putString("img_latitude", String.valueOf(latitude));
//                                            myEdit.putString("img_longitude", String.valueOf(longitude));
//                                            myEdit.apply();
//
//                                            //Log.d("TAG", "onLocationResult: " + latitude + "jhga " + longitude);
//                                            Log.d("location67", String.valueOf(latitude));
//                                            Log.d("location67", String.valueOf(longitude));
//
//                                        }
//
//
//                                    }
//
//                                }
//                            }, Looper.getMainLooper());
//
//                } else {
//                    turnOnGPS();
//                }
//
//            } else {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            }
//        }
//    }

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
                    Toast.makeText(customer_detail_Activity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(customer_detail_Activity.this, 2);
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

}
