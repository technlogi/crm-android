package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import android.widget.TextView;
import android.widget.Toast;


import com.example.crm.Adapter.dailyReportLocalAdapter;
import com.example.crm.Adapter.daily_report_adapter;

import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllReportDataBean;
import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.daily_report_list_POJO;
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

public class dailyActivity extends AppCompatActivity {
    RecyclerView rec_main_Todo;
    ImageView im_add;
    private LocationRequest locationRequest;
    SharedPreferences sharedPreferences;
    com.example.crm.Adapter.daily_report_adapter daily_report_adapter;
    TextInputEditText ti_remark,ti_closing_km,ti_opening_km;
    ImageView preview,preview2;
    TextView dialog_add;
    AlertDialog dialog;
    String waypathPhoto_book1;
    String waypathPhoto_book2;
    MultipartBody.Part imagePartPhoto_book1 = null;
    MultipartBody.Part imagePartPhoto_book2 = null;
    Uri uriPhoto;
    Uri uriPhoto2;
    ProgressDialog pro;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        sharedPreferences = dailyActivity.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(dailyActivity.this);
        rec_main_Todo = findViewById(R.id.rec_main_Todo);
        im_add =findViewById(R.id.im_add);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        if (CheckInternetConnection.checkInternetConnection(dailyActivity.this)) {
            RecyclerElectric();
        }
        else
        {
//            GetOfflineData();
            Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
        }


        im_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked1(view);
            }
        });




    }

    private void showAlertDialogButtonClicked1(View view) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("opening_latitude", "");
        editor.putString("opening_longitude", "");
        editor.putString("closing_latitude", "");
        editor.putString("closing_longitude", "");

        editor.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(dailyActivity.this, R.style.CustomAlertDialog);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_daily_report_add, null);
        builder.setView(customLayout);

        SimpleDateFormat simpleDateFormat,simpleDateFormat2;
        String dateTime, TIME;
        String dateTime2, TIME2;
        ImageView upload_img;
        ImageView upload_img2;
        ImageView close_dialog;




        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        MaterialDatePicker.Builder materialDateBuilder2 = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder2.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker2 = materialDateBuilder.build();

        ti_remark = customLayout.findViewById(R.id.ti_remark);
        ti_closing_km = customLayout.findViewById(R.id.ti_closing_km);
        ti_opening_km = customLayout.findViewById(R.id.ti_opening_km);
        close_dialog = customLayout.findViewById(R.id.close_dialog);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        preview2 = customLayout.findViewById(R.id.preview2);

        dialog_add = customLayout.findViewById(R.id.dialog_add);
        upload_img2 = customLayout.findViewById(R.id.upload_img2);
        dialog_add = customLayout.findViewById(R.id.dialog_add);
        upload_img = customLayout.findViewById(R.id.upload_img);
        preview = customLayout.findViewById(R.id.preview);

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImagePicker.create(EditProfileActivity.this).single().start();
                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                gal.setType("image/*");
                startActivityForResult(gal, 1);*/
                ImagePicker.with(dailyActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .cameraOnly()    //User can only capture image using Camera
//                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(1);
                getCurrentLocation();


            }
        });

        upload_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImagePicker.create(EditProfileActivity.this).single().start();
                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                gal.setType("image/*");
                startActivityForResult(gal, 1);*/
                ImagePicker.with(dailyActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .cameraOnly()    //User can only capture image using Camera
//                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(2);
                getCurrentLocationOnSubmit();


            }
        });




        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Please wait ...");

        dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro.show();
                if(validation5()){
                    File file1 = new File(waypathPhoto_book1);
                    final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                    imagePartPhoto_book1 = MultipartBody.Part.createFormData("opening_photo", file1.getName(), requestBody1);
                    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;
                    if(waypathPhoto_book2!=null){
                        File file2 = new File(waypathPhoto_book2);
                        final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                        imagePartPhoto_book2 = MultipartBody.Part.createFormData("closing_photo", file2.getName(), requestBody2);
                        Log.d("TAG", "onLocationResult111: "+file1+" 2222 "+file2);
                    }
                    MultipartBody.Part imagePartPhoto_book123 = imagePartPhoto_book2;
                    Log.d("TAG", "onClick:111 "+imagePartPhoto_book2);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(dailyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Log.d("TAG", "onClick:1112 ");
                            if (isGPSEnabled()) {
                                LocationServices.getFusedLocationProviderClient(dailyActivity.this)
                                        .requestLocationUpdates(locationRequest, new LocationCallback() {

                                            @SuppressLint("MissingPermission")
                                            @Override
                                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                                Log.d("TAG", "onClick:1113 ");
                                                super.onLocationResult(locationResult);
                                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                                List<Address> addresses = null;

                                                LocationServices.getFusedLocationProviderClient(dailyActivity.this)
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


                                                            Log.d("locationlattt", String.valueOf(latitude));
                                                            Log.d("location", String.valueOf(longitude));
                                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                        Date date = new Date();
                                                        CharSequence str  = DateFormat.format("dd-MM-yyyy", date.getTime());
                                                        if (CheckInternetConnection.checkInternetConnection(dailyActivity.this)) {

                                                            WebService.getClient().daily_report_add_info(
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
                                                                    RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("IMEI_no", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), str.toString()),
                                                                    RequestBody.create(MediaType.parse("text/plain"), ti_opening_km.getText().toString()),
                                                                    imagePartPhoto_book12,
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("opening_latitude", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("opening_longitude", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), ti_closing_km.getText().toString()),
                                                                    imagePartPhoto_book123,
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("closing_latitude", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("closing_longitude", "")),
                                                                    RequestBody.create(MediaType.parse("text/plain"), ti_remark.getText().toString())
                                                            ).enqueue(new Callback<daily_report_add_info_POJO>() {
                                                                @Override
                                                                public void onResponse(Call<daily_report_add_info_POJO> call, Response<daily_report_add_info_POJO> response) {


                                                                    if (response.body().getSuccess() == 1) {

//                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker);
//                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker);
//
//                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + dateTracker2);
//                                                                    Log.d("TAG", "v_form_res_s_tv_date_picker: " + timetracker2);
                                                                        pro.dismiss();
                                                                        dialog.dismiss();


                                                                        Toast.makeText(dailyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        RecyclerElectric();
//                                                                ss.add(new last_remark_POJO(dialog_tv_date_picker_page.getText().toString(),
//                                                                        dialog_tv_time.getText().toString(),
//                                                                        dialog_remark.getText().toString(),
//                                                                        dialog_agent_code.getText().toString()
//                                                                ));
//                                                                    last_remark_adapter = new last_remark_Adapter(ss, dailyActivity.this);
//                                                                    rec_last_remark.setAdapter(last_remark_adapter);
//                                                                    last_remark_adapter.notifyDataSetChanged();
//                                                                        dialog.dismiss();
                                                                    } else {

                                                                        pro.dismiss();
                                                                        dialog.dismiss();

                                                                        Toast.makeText(dailyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }

                                                                @Override
                                                                public void onFailure(Call<daily_report_add_info_POJO> call, Throwable t) {
                                                                    pro.dismiss();
                                                                    dialog.dismiss();


                                                                }
                                                            });

                                                        }
                                                        else
                                                        {
                                                            Log.e("Date ","str= "+str);
                                                            AllReportDataBean alldatabean=new AllReportDataBean();
                                                            alldatabean.setREPORT_USER_ID(sharedPreferences.getString("user_id", ""));
                                                            alldatabean.setREPORT_LAT(String.valueOf(latitude));
                                                            alldatabean.setREPORT_LOGI(String.valueOf(longitude));
                                                            alldatabean.setREPORT_IMEI_COL(sharedPreferences.getString("IMEI_no", ""));
                                                            alldatabean.setREPORT_DATE_COL(str.toString());
                                                            alldatabean.setREPORT_OPENKM_COL(ti_opening_km.getText().toString());
                                                            alldatabean.setREPORT_OPENKM_PHOTO_COL(waypathPhoto_book1);
                                                            alldatabean.setREPORT_OPENING_LAT_COL(sharedPreferences.getString("opening_latitude", ""));
                                                            alldatabean.setREPORT_OPENING_LOGI_COL(sharedPreferences.getString("opening_longitude", ""));
                                                            alldatabean.setREPORT_CLOSEKM_COL(ti_closing_km.getText().toString());
                                                            alldatabean.setREPORT_CLOSEKM_PHOTO_COL(waypathPhoto_book2);
                                                            alldatabean.setREPORT_CLOSEING_LAT_COL(sharedPreferences.getString("closing_latitude", ""));
                                                            alldatabean.setREPORT_CLOSEING_LOGI_COL(sharedPreferences.getString("closing_longitude", ""));
                                                            alldatabean.setREPORT_RREMARK_COL(ti_remark.getText().toString());

                                                            if (dbHandler.SaveReportData(alldatabean)) {
                                                                pro.dismiss();
                                                                dialog.dismiss();

                                                                Log.e("Msg_shyam", "Save data successfull");
                                                                Toast.makeText(dailyActivity.this, "Save data successfull", Toast.LENGTH_SHORT).show();
//                                                                GetOfflineData();

                                                            } else {
                                                                Log.e("Msg_shyam", "issue is in Save data");
                                                                Toast.makeText(dailyActivity.this, "some issue is in inserting data", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
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
                else {
                    pro.dismiss();
//                    dialog.dismiss();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uriPhoto = data.getData();
                    waypathPhoto_book1 = getFilePath(dailyActivity.this, uriPhoto);
                    Uri selectedImageUri = data.getData();
                    preview.setImageURI(selectedImageUri);
                    //profile_image.setImageURI(uri);
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == 2) {
                if (resultCode == Activity.RESULT_OK) {


                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uriPhoto2 = data.getData();
                    waypathPhoto_book2 = getFilePath(dailyActivity.this, uriPhoto2);
                    Uri selectedImageUri = data.getData();
                    preview2.setImageURI(selectedImageUri);
                    //profile_image.setImageURI(uri);
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == 2) {
                if (resultCode == Activity.RESULT_OK) {


                }
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

    private boolean validation5() {
        if (TextUtils.isEmpty(waypathPhoto_book1)) {
            Toast.makeText(dailyActivity.this, "Select Shop image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(ti_opening_km.getText().toString())) {
            ti_opening_km.setError("Select agent code");
            ti_opening_km.requestFocus();
            return false;
        }
//        if (TextUtils.isEmpty(dialog_tv_date_picker_page.getText().toString())) {
//            dialog_tv_date_picker_page.setError("Select date");
//            dialog_tv_date_picker_page.requestFocus();
//            return false;
//        }
//        if (TextUtils.isEmpty(dialog_tv_time.getText().toString())) {
//            dialog_tv_time.setError("Enter time");
//            dialog_tv_time.requestFocus();
//            return false;
//
//        }
//        if (TextUtils.isEmpty(dialog_remark.getText().toString())) {
//            dialog_remark.setError("Enter Remark");
//            dialog_remark.requestFocus();
//            return false;
//
//        }
        return true;
    }

    private void RecyclerElectric() {
        pro = new ProgressDialog(this);

        pro.setCancelable(false);
        pro.setMessage("Please wait ...");

        pro.show();

        Log.d("TAG", "onResponse111: 6");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dailyActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("TAG", "onResponse111: 6");
            if (ActivityCompat.checkSelfPermission(dailyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "onResponse111: 5");
                if (isGPSEnabled()) {
                    Log.d("TAG", "onResponse111: 4");

                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    Log.d("TAG", "onResponse111: 3");


                                    Geocoder geocoder = new Geocoder(dailyActivity.this, Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                                        Log.d("TAG", "onResponse111: 2");

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();


                                        try {
                                            addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                        if (addresses != null && !addresses.isEmpty()) {
                                            Log.d("TAG", "onResponse111: 1");
                                            String countryName = addresses.get(0).getCountryName();
                                            String stateName = addresses.get(0).getAdminArea();
                                            String knownName = addresses.get(0).getFeatureName(); // landmark
                                            String address = addresses.get(0).getAddressLine(0);
                                            String cityName = addresses.get(0).getLocality();
                                            String area = addresses.get(0).getPostalCode();



                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                            WebService.getClient().daily_report_list (
                                                    sharedPreferences.getString("user_id", ""),

                                                    String.valueOf(latitude),
                                                    String.valueOf(longitude),
                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<daily_report_list_POJO>() {

                                                @Override
                                                public void onResponse(Call<daily_report_list_POJO> call, Response<daily_report_list_POJO> response) {
                                                    //Log.d("TAG", "onResponse111: "+response.body().getResult().get(0).getId());
                                                    pro.dismiss();
                                                    if (response.body() == null)
                                                    {

                                                        Toast.makeText(dailyActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {

                                                        if (response.body().getResult().size() != 0)
                                                        {
                                                            daily_report_adapter = new daily_report_adapter(response.body().getResult(), dailyActivity.this);
                                                            rec_main_Todo.setLayoutManager(linearLayoutManager);
                                                            rec_main_Todo.setAdapter(daily_report_adapter);

//                                                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                                                                @Override
//                                                                public boolean onQueryTextSubmit(String query) {
//                                                                    return false;
//                                                                }
//
//                                                                @Override
//                                                                public boolean onQueryTextChange(String newText) {
//
//                                                                    customer_list_adapter.getFilter().filter(newText);
//                                                                    return false;
//                                                                }
//                                                            });




                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<daily_report_list_POJO> call, Throwable t) {
                                                    pro.dismiss();
                                                    Log.d("dbjcbsjbj",t.getMessage());

                                                }
                                            });






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
    ArrayList<AllReportDataBean>arAllReportData;
    dailyReportLocalAdapter dailyreportadapter;
 public void GetOfflineData()
 {
     arAllReportData = dbHandler.GetAllReportData();

     LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dailyActivity.this);

     dailyreportadapter = new dailyReportLocalAdapter(arAllReportData, dailyActivity.this);
     rec_main_Todo.setLayoutManager(linearLayoutManager);
     rec_main_Todo.setAdapter(dailyreportadapter);

//     for(int i=0;i<arAllReportData.size();i++)
//     {
//         Log.e("Date_shyam","arAllReportData.get("+i+").getREPORT_DATE_COL()= "+arAllReportData.get(i).getREPORT_DATE_COL());
//     }

 }

    private void getCurrentLocationOnSubmit() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("opening_latitude", "");
        editor.putString("opening_longitude", "");
        editor.apply();
        editor.apply();

// Storing the key and its value as the data fetched from edittext


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(dailyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
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

//                                            Log.d("location", countryName);
//                                            Log.d("location", stateName);
//                                            Log.d("location", cityName);
//                                            Log.d("location", knownName);
//                                            Log.d("location", area);
//                                            Log.d("location", address);
                                            Log.d("locationlattt", String.valueOf(latitude));
                                            Log.d("location", String.valueOf(longitude));
                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                            myEdit.putString("opening_latitude", String.valueOf(latitude));
                                            myEdit.putString("opening_longitude", String.valueOf(longitude));
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
        editor.putString("closing_latitude", "");
        editor.putString("closing_longitude", "");

        editor.apply();

// Storing the key and its value as the data fetched from edittext


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(dailyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {


                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(dailyActivity.this)
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

//                                            Log.d("location", countryName);
//                                            Log.d("location", stateName);
//                                            Log.d("location", cityName);
//                                            Log.d("location", knownName);
//                                            Log.d("location", area);
//                                            Log.d("location", address);
                                            Log.d("locationlattt", String.valueOf(latitude));
                                            Log.d("location", String.valueOf(longitude));
                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                            myEdit.putString("closing_latitude", String.valueOf(latitude));
                                            myEdit.putString("closing_longitude", String.valueOf(longitude));
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
            locationManager = (LocationManager) dailyActivity.this.getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(dailyActivity.this)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(dailyActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(dailyActivity.this, 2);
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