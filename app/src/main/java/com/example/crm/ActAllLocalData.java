package com.example.crm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crm.Adapter.all_local_data_adapter;
import com.example.crm.Adapter.daily_report_adapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllDataBean;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.daily_report_list_POJO;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ActAllLocalData extends AppCompatActivity {
    RecyclerView rec_main_Todo;
//    ImageView im_add;
    LinearLayout lySyncData;
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    ProgressDialog pro;
    ImageView back_img,no_data_found;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.act_next_visit_local);
        sharedPreferences = ActAllLocalData.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(ActAllLocalData.this);
        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Uploading...");
        back_img = findViewById(R.id.back_img);
//        container4 = findViewById(R.id.container4);
        no_data_found = findViewById(R.id.no_data_found);
        tvTitle = findViewById(R.id.tv_next_visit_report);
        rec_main_Todo = findViewById(R.id.rec_next_visit_todo);
        lySyncData = findViewById(R.id.ly_sync);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActAllLocalData.this);
        rec_main_Todo.setLayoutManager(linearLayoutManager);

//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(2000);
//        RecyclerElectric();
        tvTitle.setText("Visit : Local Data");
        lySyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallAPI(1);
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SetAdpater();

    }
    all_local_data_adapter localDataAdpter;
    ArrayList<AllDataBean> arAllData;
    public  void SetAdpater()
    {
         arAllData = dbHandler.GetAllData();
        localDataAdpter = new all_local_data_adapter(arAllData, ActAllLocalData.this);
      rec_main_Todo.setAdapter(localDataAdpter);
        SetVisible();
    }
public void SetVisible()
{
   int  count = dbHandler.getRecordCount();
    if(count>0)
    {
        lySyncData.setVisibility(View.VISIBLE);
    }
    else
    {
        lySyncData.setVisibility(View.GONE);
        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
    }
}
    public void CallAPI(int pos)
    {
        if (CheckInternetConnection.checkInternetConnection(ActAllLocalData.this)) {
            switch (pos) {
                case 1:
                    SendDatatoServer();
                    break;
            }
        }
         else {
            Toast.makeText(ActAllLocalData.this, getResources().getString(R.string.txt_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

 int countPos=0;
    public void SendDatatoServer()
    {

if(countPos<arAllData.size())
{
if(countPos==0)
    {
        pro.show();
    }

String s_tv_date_picker=arAllData.get(countPos).getDATE_COL();
String s_tv_timee=arAllData.get(countPos).getTIME_COL();
String s_p1_agent_code=arAllData.get(countPos).getAGETCODE_COL();
String cusomer_id=arAllData.get(countPos).getCUSTOMER_ID_COL();
String s_p1_customer_list=arAllData.get(countPos).getCUSTOMER_ID_COL();
String s_p1_mobile1=arAllData.get(countPos).getCUSTOMER_MOBILE1_COL();
String s_p1_mobile2=arAllData.get(countPos).getCUSTOMER_MOBILE2_COL();
String s_p1_mobile3=arAllData.get(countPos).getCUSTOMER_MOBILE3_COL();
String s_p2_address=arAllData.get(countPos).getADDRESS_COL();
String colony_id=arAllData.get(countPos).getCOLONY_ID_COL();
String s_p2_colony_list=arAllData.get(countPos).getCOLONY_NAME_COL();
String s_switch_togglebtn=arAllData.get(countPos).getSWITH_TOGGLE_COL();
String c_edt_weight=arAllData.get(countPos).getWIDTH_COL();
String s_edt_height=arAllData.get(countPos).getHEIGHT_COL();
String s_p2_site_position_list=arAllData.get(countPos).getSITE_POSITION_COL();
String ad_position_id=arAllData.get(countPos).getSITE_POSITION_ID_COL();
String add_product=arAllData.get(countPos).getADD_PRODUCT();
String add_proId=arAllData.get(countPos).getADD_PRODUCT_ID();
String add_brand=arAllData.get(countPos).getADD_BRAND();
String add_brandId=arAllData.get(countPos).getADD_BRAND_ID();
String add_vendor=arAllData.get(countPos).getVENDOR();
String add_rate=arAllData.get(countPos).getRATE();
String add_qty=arAllData.get(countPos).getQNTY();
String add_unit=arAllData.get(countPos).getUNIT();
String add_unitId=arAllData.get(countPos).getUNIT_ID();
String add_Stock=arAllData.get(countPos).getSTOCK();
String site_sperson=arAllData.get(countPos).getSITE_PERSON();
String site_sad_site_person_id=arAllData.get(countPos).getSITE_PERSON_ID();
String site_sname=arAllData.get(countPos).getSITE_NAME();
String site_sad_site_person_idname=arAllData.get(countPos).getSITE_PERSON_ID_NAME();
String site_scontact=arAllData.get(countPos).getSITE_CONTACT();
String site_son_site=arAllData.get(countPos).getSITE_ON_SITE();
String site_soff_site=arAllData.get(countPos).getSITE_OFF_SITE();
String s_tv_date_picker_page2=arAllData.get(countPos).getDATE_PAGE2();
String s_tv_time=arAllData.get(countPos).getTIME_PAGE2();
String s_p4_remark=arAllData.get(countPos).getREMARK();
String form_latitude=arAllData.get(countPos).getFORM_LATI();
String form_longitude=arAllData.get(countPos).getFORM_LOGI();
String img_latitude=arAllData.get(countPos).getIMG_LAT();
String img_longitude=arAllData.get(countPos).getIMG_LOGI();
String user_id=arAllData.get(countPos).getUSER_ID();
String waypathPhoto_book1=arAllData.get(countPos).getPHOTO_BOOK();
String IMEI_no= arAllData.get(countPos).getIMEI_NO();
String P1Category=arAllData.get(countPos).getCATEGORY();
    String ContactMode=arAllData.get(countPos).getCONTACTMODE();
String customer_dob_picker= arAllData.get(countPos).getCUSTOMER_DOB();
String site_sdob=arAllData.get(countPos).getCUSTOMER_S_DOB();
    MultipartBody.Part imagePartPhoto_book12=null;
    if (waypathPhoto_book1!=null||!TextUtils.isEmpty(waypathPhoto_book1)){
        File file1 = new File(waypathPhoto_book1);
        final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
        imagePartPhoto_book12 = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);
    }

//    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;


    WebService.getClient().visit_form(
            RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker),
            RequestBody.create(MediaType.parse("text/plain"), s_tv_timee),
            RequestBody.create(MediaType.parse("text/plain"), s_p1_agent_code),
            RequestBody.create(MediaType.parse("text/plain"), cusomer_id/*sharedPreferences.getString("ad_cusomer_id", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), s_p1_customer_list),
            RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile1),
            RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile2),
            RequestBody.create(MediaType.parse("text/plain"), s_p1_mobile3),

            RequestBody.create(MediaType.parse("text/plain"), s_p2_address),
            RequestBody.create(MediaType.parse("text/plain"), colony_id/*sharedPreferences.getString("ad_colony_id", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), s_p2_colony_list),
            RequestBody.create(MediaType.parse("text/plain"), s_switch_togglebtn),
            RequestBody.create(MediaType.parse("text/plain"), c_edt_weight),
            RequestBody.create(MediaType.parse("text/plain"), s_edt_height),
            RequestBody.create(MediaType.parse("text/plain"), s_p2_site_position_list),
            RequestBody.create(MediaType.parse("text/plain"), ad_position_id/*sharedPreferences.getString("ad_position_id", "")*/),


            RequestBody.create(MediaType.parse("text/plain"),add_product/* sharedPreferences.getString("add_product", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_proId/*sharedPreferences.getString("add_proId", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_brand/*sharedPreferences.getString("add_brand", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_brandId/*sharedPreferences.getString("add_brandId", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_vendor/*sharedPreferences.getString("add_vendor", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_rate/*sharedPreferences.getString("add_rate", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_qty/*sharedPreferences.getString("add_qty", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_unit/*sharedPreferences.getString("add_unit", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_unitId/*sharedPreferences.getString("add_unitId", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), add_Stock/*sharedPreferences.getString("add_Stock", "")*/),

            RequestBody.create(MediaType.parse("text/plain"), site_sperson/*sharedPreferences.getString("site_sperson", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_sad_site_person_id/*sharedPreferences.getString("site_sad_site_person_id", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_sname/*sharedPreferences.getString("site_sname", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_sad_site_person_idname/*sharedPreferences.getString("site_sad_site_person_idname", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_scontact/*sharedPreferences.getString("site_scontact", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_son_site/*sharedPreferences.getString("site_son_site", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), site_soff_site/*sharedPreferences.getString("site_soff_site", "")*/),


            RequestBody.create(MediaType.parse("text/plain"), s_tv_date_picker_page2),
            RequestBody.create(MediaType.parse("text/plain"), s_tv_time),
            RequestBody.create(MediaType.parse("text/plain"), s_p4_remark),


            RequestBody.create(MediaType.parse("text/plain"), form_latitude/*sharedPreferences.getString("form_latitude", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), form_longitude/*sharedPreferences.getString("form_longitude", "")*/),

            RequestBody.create(MediaType.parse("text/plain"), img_latitude/*sharedPreferences.getString("img_latitude", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), img_longitude/*sharedPreferences.getString("img_longitude", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), user_id/*sharedPreferences.getString("user_id", "")*/),
            imagePartPhoto_book12,
            RequestBody.create(MediaType.parse("text/plain"), IMEI_no/*sharedPreferences.getString("IMEI_no", "")*/),
            RequestBody.create(MediaType.parse("text/plain"), P1Category),
            RequestBody.create(MediaType.parse("text/plain"), ContactMode),
            RequestBody.create(MediaType.parse("text/plain"), customer_dob_picker),
            RequestBody.create(MediaType.parse("text/plain"), site_sdob/*sharedPreferences.getString("site_sdob", "")*/)
    ).enqueue(new Callback<visit_form_POJO>() {
        @Override
        public void onResponse(Call<visit_form_POJO> call, Response<visit_form_POJO> response) {
        Log.e("result","countPos=  "+countPos+"response.body() "+response.body());
            if (response.body().getSuccess() == 1) {

//                                    Toast.makeText(FormActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("img_latitude", "");
//                editor.putString("img_longitude", "");
//                editor.putString("form_latitude", "");
//                editor.putString("form_longitude", "");
//                editor.apply();
//                showAlertDialogButtonClicked1(view);
//                dialog.show();

                if(dbHandler.deleteItem(arAllData.get(countPos)))
                {
                    Log.e("Msg_shyam", "Delete data successfull");
                    Toast.makeText(ActAllLocalData.this, "Delete data successfull", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.e("Msg_shyam", "Delete data issue");
                    Toast.makeText(ActAllLocalData.this, "Delete data is in issue", Toast.LENGTH_SHORT).show();
                }

                if(countPos==(arAllData.size()-1))
                {
                    pro.dismiss();
                    showAlertDialogButtonClicked1();
                    SetVisible();
                }
                else
                {
                    countPos=countPos+1; // 5
                    SendDatatoServer();
                }

            } else {
            }
        }

        @Override
        public void onFailure(Call<visit_form_POJO> call, Throwable t) {
            pro.dismiss();
            Log.e("error_shyam","error= "+t);
        }
    });
}
    }
    AlertDialog dialog;
    public void showAlertDialogButtonClicked1(/*View view*/) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActAllLocalData.this, R.style.CustomAlertDialog).setCancelable(false);
        final View customLayout = getLayoutInflater().inflate(R.layout.forndialog, null);
        builder.setView(customLayout);

//        ImageView close_dialog = customLayout.findViewById(R.id.close_dialog);
        TextView dailog_next = customLayout.findViewById(R.id.dailog_next);
        dailog_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(ActAllLocalData.this, MainActivity.class);
                startActivity(i);

//                if (!flag)
//                {
////                    dialog.dismiss();
//                    finish();
//                    Intent i = new Intent(ActAllLocalData.this, MainActivity.class);
//                    startActivity(i);
//                }else{
////                    dialog.dismiss();
////                    finish();
////                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
////                    Intent i = new Intent(ActAllLocalData.this, customer_detail_Activity.class);
////                    i.putExtra("visit_id",sharedPreferences.getString("visit_id", ""));
////                    startActivity(i);
//                }


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

//
//    private void showAlertDialogButtonClicked1(View view) {
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("opening_latitude", "");
//        editor.putString("opening_longitude", "");
//        editor.putString("closing_latitude", "");
//        editor.putString("closing_longitude", "");
//
//        editor.apply();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ActAllLocalData.this, R.style.CustomAlertDialog);
//        final View customLayout = getLayoutInflater().inflate(R.layout.alert_daily_report_add, null);
//        builder.setView(customLayout);
//
//        SimpleDateFormat simpleDateFormat,simpleDateFormat2;
//        String dateTime, TIME;
//        String dateTime2, TIME2;
//        ImageView upload_img;
//        ImageView upload_img2;
//        ImageView close_dialog;
//
//
//
//
//        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
//        materialDateBuilder.setTitleText("SELECT A DATE");
//        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
//
//        MaterialDatePicker.Builder materialDateBuilder2 = MaterialDatePicker.Builder.datePicker();
//        materialDateBuilder2.setTitleText("SELECT A DATE");
//        final MaterialDatePicker materialDatePicker2 = materialDateBuilder.build();
//
//        ti_remark = customLayout.findViewById(R.id.ti_remark);
//        ti_closing_km = customLayout.findViewById(R.id.ti_closing_km);
//        ti_opening_km = customLayout.findViewById(R.id.ti_opening_km);
//        close_dialog = customLayout.findViewById(R.id.close_dialog);
//
//        close_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        preview2 = customLayout.findViewById(R.id.preview2);
//
//        dialog_add = customLayout.findViewById(R.id.dialog_add);
//        upload_img2 = customLayout.findViewById(R.id.upload_img2);
//        dialog_add = customLayout.findViewById(R.id.dialog_add);
//        upload_img = customLayout.findViewById(R.id.upload_img);
//        preview = customLayout.findViewById(R.id.preview);
//
//        upload_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //ImagePicker.create(EditProfileActivity.this).single().start();
//                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                gal.setType("image/*");
//                startActivityForResult(gal, 1);*/
//                ImagePicker.with(ActAllLocalData.this)
//                        .crop()                    //Crop image(Optional), Check Customization for more option
//                        .cameraOnly()    //User can only capture image using Camera
////                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                        .start(1);
//                getCurrentLocation();
//
//
//            }
//        });
//
//        upload_img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //ImagePicker.create(EditProfileActivity.this).single().start();
//                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                gal.setType("image/*");
//                startActivityForResult(gal, 1);*/
//                ImagePicker.with(ActAllLocalData.this)
//                        .crop()                    //Crop image(Optional), Check Customization for more option
//                        .cameraOnly()    //User can only capture image using Camera
////                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
//                        .start(2);
//                getCurrentLocationOnSubmit();
//
//
//            }
//        });
//
//
//
//
//        pro = new ProgressDialog(this);
//
//        pro.setCancelable(false);
//        pro.setMessage("Please wait ...");
//
//        dialog_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                pro.show();
//
//
//
//
//                if(validation5()){
//
//
//
//
//
//                    File file1 = new File(waypathPhoto_book1);
//                    final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
//                    imagePartPhoto_book1 = MultipartBody.Part.createFormData("opening_photo", file1.getName(), requestBody1);
//
//                    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;
//
//
//                    if(waypathPhoto_book2!=null){
//                        File file2 = new File(waypathPhoto_book2);
//                        final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
//                        imagePartPhoto_book2 = MultipartBody.Part.createFormData("closing_photo", file2.getName(), requestBody2);
//                        Log.d("TAG", "onLocationResult111: "+file1+" 2222 "+file2);
//                    }
//
//
//
//                    MultipartBody.Part imagePartPhoto_book123 = imagePartPhoto_book2;
//
//                    Log.d("TAG", "onClick:111 "+imagePartPhoto_book2);
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                        if (ActivityCompat.checkSelfPermission(ActAllLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                            Log.d("TAG", "onClick:1112 ");
//                            if (isGPSEnabled()) {
//
//                                LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                                        .requestLocationUpdates(locationRequest, new LocationCallback() {
//
//                                            @SuppressLint("MissingPermission")
//                                            @Override
//                                            public void onLocationResult(@NonNull LocationResult locationResult) {
//                                                Log.d("TAG", "onClick:1113 ");
//                                                super.onLocationResult(locationResult);
//
//
//                                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                                                List<Address> addresses = null;
//
//                                                LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                                                        .removeLocationUpdates(this);
//
//                                                if (locationResult != null && locationResult.getLocations().size() > 0) {
//
//                                                    int index = locationResult.getLocations().size() - 1;
//                                                    double latitude = locationResult.getLocations().get(index).getLatitude();
//                                                    double longitude = locationResult.getLocations().get(index).getLongitude();
//
//
//                                                    try {
//                                                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//                                                    } catch (IOException e) {
//                                                        e.printStackTrace();
//                                                    }
//
//
//                                                    if (addresses != null && !addresses.isEmpty()) {
//                                                        String countryName = addresses.get(0).getCountryName();
//                                                        String stateName = addresses.get(0).getAdminArea();
//                                                        String knownName = addresses.get(0).getFeatureName(); // landmark
//                                                        String address = addresses.get(0).getAddressLine(0);
//                                                        String cityName = addresses.get(0).getLocality();
//                                                        String area = addresses.get(0).getPostalCode();
//
//
//                                                        Log.d("locationlattt", String.valueOf(latitude));
//                                                        Log.d("location", String.valueOf(longitude));
//                                                        String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//
//                                                        Date date = new Date();
//                                                        CharSequence str  = DateFormat.format("dd-MM-yyyy", date.getTime());
//
//
//                                                        WebService.getClient().daily_report_add_info(
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
//                                                                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("IMEI_no", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), str.toString()),
//                                                                RequestBody.create(MediaType.parse("text/plain"), ti_opening_km.getText().toString()),
//                                                                imagePartPhoto_book12,
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("opening_latitude", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("opening_longitude", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), ti_closing_km.getText().toString()),
//                                                                imagePartPhoto_book123,
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("closing_latitude", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("closing_longitude", "")),
//                                                                RequestBody.create(MediaType.parse("text/plain"), ti_remark.getText().toString())
//                                                        ).enqueue(new Callback<daily_report_add_info_POJO>() {
//                                                            @Override
//                                                            public void onResponse(Call<daily_report_add_info_POJO> call, Response<daily_report_add_info_POJO> response) {
//
//
//                                                                if(response.body().getSuccess() == 1)
//                                                                {
//
//
////
////                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker);
////                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker);
////
////                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker2);
////                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker2);
//                                                                    pro.dismiss();
//                                                                    dialog.dismiss();
//
//
//
//                                                                    Toast.makeText(ActAllLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                    RecyclerElectric();
////                                                                ss.add(new last_remark_POJO(dialog_tv_date_picker_page.getText().toString(),
////                                                                        dialog_tv_time.getText().toString(),
////                                                                        dialog_remark.getText().toString(),
////                                                                        dialog_agent_code.getText().toString()
////                                                                ));
////                                                                    last_remark_adapter = new last_remark_Adapter(ss, dailyActivity.this);
////                                                                    rec_last_remark.setAdapter(last_remark_adapter);
////                                                                    last_remark_adapter.notifyDataSetChanged();
//                                                                    dialog.dismiss();
//                                                                }
//                                                                else
//                                                                {
//
//                                                                    pro.dismiss();
//                                                                    dialog.dismiss();
//
//                                                                    Toast.makeText(ActAllLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                }
//
//                                                            }
//
//                                                            @Override
//                                                            public void onFailure(Call<daily_report_add_info_POJO> call, Throwable t) {
//                                                                pro.dismiss();
//                                                                dialog.dismiss();
//
//
//                                                            }
//                                                        });
//
//
//
//
//
//                                                    }
//
//
//                                                }
//
//                                            }
//                                        }, Looper.getMainLooper());
//
//                            } else {
//                                turnOnGPS();
//                            }
//
//                        } else {
//                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                        }
//                    }
//                }
//                else {
//                    pro.dismiss();
////                    dialog.dismiss();
//
//                }
//
//
//            }
//        });
//        dialog = builder.create();
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
//        dialog.show();
//        dialog.setCanceledOnTouchOutside(true);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    uriPhoto = data.getData();
//                    waypathPhoto_book1 = getFilePath(ActAllLocalData.this, uriPhoto);
//                    Uri selectedImageUri = data.getData();
//                    preview.setImageURI(selectedImageUri);
//                    //profile_image.setImageURI(uri);
//                }
//            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
//            }
//
//            if (requestCode == 2) {
//                if (resultCode == Activity.RESULT_OK) {
//
//
//                }
//            }
//        }
//        if (requestCode == 2) {
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    uriPhoto2 = data.getData();
//                    waypathPhoto_book2 = getFilePath(ActAllLocalData.this, uriPhoto2);
//                    Uri selectedImageUri = data.getData();
//                    preview2.setImageURI(selectedImageUri);
//                    //profile_image.setImageURI(uri);
//                }
//            } else if (resultCode == ImagePicker.RESULT_ERROR) {
//                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
//            }
//
//            if (requestCode == 2) {
//                if (resultCode == Activity.RESULT_OK) {
//
//
//                }
//            }
//        }
//
//    }

//    public String getFilePath(Context context, Uri uri) {
//        String selection = null;
//        String[] selectionArgs = null;
//        // Uri is different in versions after KITKAT (Android 4.4), we need to
//        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                return Environment.getExternalStorageDirectory() + "/" + split[1];
//            } else if (isDownloadsDocument(uri)) {
//                final String id = DocumentsContract.getDocumentId(uri);
//                uri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/all_downloads"), Long.valueOf(id));
//            } else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                if ("image".equals(type)) {
//                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                selection = "_id=?";
//                selectionArgs = new String[]{
//                        split[1]
//                };
//            }
//        }
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = {
//                    MediaStore.Images.Media.DATA
//            };
//            Cursor cursor = null;
//            try {
//                cursor = context.getContentResolver()
//                        .query(uri, projection, selection, selectionArgs, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    public boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    public boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    public boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
//
//    private boolean validation5() {
//        if (TextUtils.isEmpty(waypathPhoto_book1)) {
//            Toast.makeText(ActAllLocalData.this, "Select Shop image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(ti_opening_km.getText().toString())) {
//            ti_opening_km.setError("Select agent code");
//            ti_opening_km.requestFocus();
//            return false;
//        }
////        if (TextUtils.isEmpty(dialog_tv_date_picker_page.getText().toString())) {
////            dialog_tv_date_picker_page.setError("Select date");
////            dialog_tv_date_picker_page.requestFocus();
////            return false;
////        }
////        if (TextUtils.isEmpty(dialog_tv_time.getText().toString())) {
////            dialog_tv_time.setError("Enter time");
////            dialog_tv_time.requestFocus();
////            return false;
////
////        }
////        if (TextUtils.isEmpty(dialog_remark.getText().toString())) {
////            dialog_remark.setError("Enter Remark");
////            dialog_remark.requestFocus();
////            return false;
////
////        }
//        return true;
//    }
//
//    private void RecyclerElectric() {
//        pro = new ProgressDialog(this);
//
//        pro.setCancelable(false);
//        pro.setMessage("Please wait ...");
//
//        pro.show();
//
//        Log.d("TAG", "onResponse111: 6");
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActAllLocalData.this);

//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Log.d("TAG", "onResponse111: 6");
//            if (ActivityCompat.checkSelfPermission(ActAllLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                Log.d("TAG", "onResponse111: 5");
//                if (isGPSEnabled()) {
//                    Log.d("TAG", "onResponse111: 4");
//
//                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @SuppressLint("MissingPermission")
//                                @Override
//                                public void onLocationResult(@NonNull LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//                                    Log.d("TAG", "onResponse111: 3");
//
//
//                                    Geocoder geocoder = new Geocoder(ActAllLocalData.this, Locale.getDefault());
//                                    List<Address> addresses = null;
//
//                                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                                            .removeLocationUpdates(this);
//
//                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
//                                        Log.d("TAG", "onResponse111: 2");
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
//                                            Log.d("TAG", "onResponse111: 1");
//                                            String countryName = addresses.get(0).getCountryName();
//                                            String stateName = addresses.get(0).getAdminArea();
//                                            String knownName = addresses.get(0).getFeatureName(); // landmark
//                                            String address = addresses.get(0).getAddressLine(0);
//                                            String cityName = addresses.get(0).getLocality();
//                                            String area = addresses.get(0).getPostalCode();
//
//
//
//                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//
//                                            WebService.getClient().daily_report_list (
//                                                    sharedPreferences.getString("user_id", ""),
//
//                                                    String.valueOf(latitude),
//                                                    String.valueOf(longitude),
//                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<daily_report_list_POJO>() {
//
//                                                @Override
//                                                public void onResponse(Call<daily_report_list_POJO> call, Response<daily_report_list_POJO> response) {
//                                                    //Log.d("TAG", "onResponse111: "+response.body().getResult().get(0).getId());
//                                                    pro.dismiss();
//                                                    if (response.body() == null)
//                                                    {
//
//                                                        Toast.makeText(ActAllLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                    else
//                                                    {
//
//                                                        if (response.body().getResult().size() != 0)
//                                                        {
//                                                            daily_report_adapter = new daily_report_adapter(response.body().getResult(), ActAllLocalData.this);
//                                                            rec_main_Todo.setLayoutManager(linearLayoutManager);
//                                                            rec_main_Todo.setAdapter(daily_report_adapter);
//
////                                                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////                                                                @Override
////                                                                public boolean onQueryTextSubmit(String query) {
////                                                                    return false;
////                                                                }
////
////                                                                @Override
////                                                                public boolean onQueryTextChange(String newText) {
////
////                                                                    customer_list_adapter.getFilter().filter(newText);
////                                                                    return false;
////                                                                }
////                                                            });
//
//
//
//
//                                                        }
//                                                    }
//
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<daily_report_list_POJO> call, Throwable t) {
//                                                    pro.dismiss();
//                                                    Log.d("dbjcbsjbj",t.getMessage());
//
//                                                }
//                                            });
//
//
//
//
//
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
//
//
//
//    }
//
//    private void getCurrentLocationOnSubmit() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//
//// Creating an Editor object to edit(write to the file)
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("opening_latitude", "");
//        editor.putString("opening_longitude", "");
//
//
//        editor.apply();
//
//        editor.apply();
//
//// Storing the key and its value as the data fetched from edittext
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ActivityCompat.checkSelfPermission(ActAllLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @SuppressLint("MissingPermission")
//                                @Override
//                                public void onLocationResult(@NonNull LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//
//
//                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                                    List<Address> addresses = null;
//
//                                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
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
//                                            String countryName = addresses.get(0).getCountryName();
//                                            String stateName = addresses.get(0).getAdminArea();
//                                            String knownName = addresses.get(0).getFeatureName(); // landmark
//                                            String address = addresses.get(0).getAddressLine(0);
//                                            String cityName = addresses.get(0).getLocality();
//                                            String area = addresses.get(0).getPostalCode();
//
////                                            Log.d("location", countryName);
////                                            Log.d("location", stateName);
////                                            Log.d("location", cityName);
////                                            Log.d("location", knownName);
////                                            Log.d("location", area);
////                                            Log.d("location", address);
//                                            Log.d("locationlattt", String.valueOf(latitude));
//                                            Log.d("location", String.valueOf(longitude));
//                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//
//                                            myEdit.putString("opening_latitude", String.valueOf(latitude));
//                                            myEdit.putString("opening_longitude", String.valueOf(longitude));
//                                            myEdit.apply();
//
//                                            myEdit.apply();
//
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
//
//    private void getCurrentLocation() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//
//// Creating an Editor object to edit(write to the file)
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("closing_latitude", "");
//        editor.putString("closing_longitude", "");
//
//        editor.apply();
//
//// Storing the key and its value as the data fetched from edittext
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ActivityCompat.checkSelfPermission(ActAllLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//
//                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
//                            .requestLocationUpdates(locationRequest, new LocationCallback() {
//                                @SuppressLint("MissingPermission")
//                                @Override
//                                public void onLocationResult(@NonNull LocationResult locationResult) {
//                                    super.onLocationResult(locationResult);
//
//
//                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                                    List<Address> addresses = null;
//
//                                    LocationServices.getFusedLocationProviderClient(ActAllLocalData.this)
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
//                                            String countryName = addresses.get(0).getCountryName();
//                                            String stateName = addresses.get(0).getAdminArea();
//                                            String knownName = addresses.get(0).getFeatureName(); // landmark
//                                            String address = addresses.get(0).getAddressLine(0);
//                                            String cityName = addresses.get(0).getLocality();
//                                            String area = addresses.get(0).getPostalCode();
//
////                                            Log.d("location", countryName);
////                                            Log.d("location", stateName);
////                                            Log.d("location", cityName);
////                                            Log.d("location", knownName);
////                                            Log.d("location", area);
////                                            Log.d("location", address);
//                                            Log.d("locationlattt", String.valueOf(latitude));
//                                            Log.d("location", String.valueOf(longitude));
//                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//
//                                            myEdit.putString("closing_latitude", String.valueOf(latitude));
//                                            myEdit.putString("closing_longitude", String.valueOf(longitude));
//                                            myEdit.apply();
//
//                                            myEdit.apply();
//
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
//
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = null;
//        boolean isEnabled = false;
//
//        if (locationManager == null) {
//            locationManager = (LocationManager) ActAllLocalData.this.getSystemService(Context.LOCATION_SERVICE);
//        }
//
//        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        return isEnabled;
//
//    }
//
//    private void turnOnGPS() {
//
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(ActAllLocalData.this)
//                .checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(ActAllLocalData.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(ActAllLocalData.this, 2);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            //Device does not have location
//                            break;
//                    }
//                }
//            }
//        });
//
//    }
}