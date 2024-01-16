package com.example.crm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.crm.Adapter.NextVisitAdapter;
import com.example.crm.Adapter.dailyReportLocalAdapter;
import com.example.crm.Adapter.last_remark_Adapter;
import com.example.crm.Adapter.visit_todo_Adapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllNextVisitDataBean;
import com.example.crm.POJO.AllReportDataBean;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.visit_next_add_remark_POJO;
import com.example.crm.POJO.visit_todo_POJO;
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

import java.io.File;
import java.io.IOException;
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

public class ActNextVisitLocalData extends AppCompatActivity {

    ImageView back_img,no_data_found;
    LinearLayout list_ll1,list_ll2,list_ll3,list_ll4,list_ll5,list_ll6;
    SearchView searchview2;
    SearchView searchView;
    String customer_id;
    SwipeRefreshLayout container4;

    RecyclerView rvNextVisitData;
    visit_todo_Adapter visit_todo_adapter;
    List<visit_todo_POJO> itemlist;
    SharedPreferences sharedPreferences;
    private LocationRequest locationRequest;
    DBHandler dbHandler;
    ProgressDialog pro;
    LinearLayout lySyncData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.act_next_visit_local);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

         sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(ActNextVisitLocalData.this);
//        Intent intent = getIntent();
//        customer_id= intent.getStringExtra("customer_id");

        back_img = findViewById(R.id.back_img);
        container4 = findViewById(R.id.container4);
        no_data_found = findViewById(R.id.no_data_found);

        lySyncData = findViewById(R.id.ly_sync);
        rvNextVisitData = findViewById(R.id.rec_next_visit_todo);
        itemlist = new ArrayList<>();
        searchview2 = findViewById(R.id.searchview2);
        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Please wait ...");

//        RecyclerElectric();
        GetOfflineData();
        container4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetOfflineData();
                container4.setRefreshing(false);
            }
        });


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lySyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallAPI(1);
            }
        });
//        SetVisible();
    }

    ArrayList<AllNextVisitDataBean>arNextVisitData=new ArrayList<>();
    NextVisitAdapter nextVisitDataAdapter;
    public void GetOfflineData()
    {
        arNextVisitData = dbHandler.GetAllNextVisitData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActNextVisitLocalData.this);
        nextVisitDataAdapter = new NextVisitAdapter(arNextVisitData, ActNextVisitLocalData.this);

        rvNextVisitData.setLayoutManager(linearLayoutManager);
        rvNextVisitData.setAdapter(nextVisitDataAdapter);
        SetVisible();
    }



    public void SetVisible()
    {
        int  count = dbHandler.getRecordCountNextVisit();
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
        if (CheckInternetConnection.checkInternetConnection(ActNextVisitLocalData.this)) {
            switch (pos) {
                case 1:
                    SendDatatoServer();
                    break;
            }
        }
        else {
            Toast.makeText(ActNextVisitLocalData.this, getResources().getString(R.string.txt_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    int countPos=0;
    public void SendDatatoServer()
    {

        if(countPos<arNextVisitData.size())
        {
            if(countPos==0)
            {
                pro.show();
            }
            String userid=arNextVisitData.get(countPos).getNEXTVISIT_USER_ID_COL();
            String dialog_tv_date_picker_page=arNextVisitData.get(countPos).getNEXTVISIT_DATE_COL();
            String dialog_tv_time=arNextVisitData.get(countPos).getNEXTVISIT_TIME_COL();
            String dialog_remark=arNextVisitData.get(countPos).getNEXTVISIT_REMARK_COL();
            String visit_id=arNextVisitData.get(countPos).getNEXTVISIT_VISIT_ID_COL();
            String dialog_tv_date_picker_visit=arNextVisitData.get(countPos).getNEXTVISIT_NEXT_DATE_COL();
            String dialog_tv_time_visit=arNextVisitData.get(countPos).getNEXTVISIT_NEXT_TIME_COL();
            String IMEI_no=arNextVisitData.get(countPos).getNEXTVISIT_IMEI_COL();
            String waypathPhoto_book1=arNextVisitData.get(countPos).getNEXTVISIT_PHOTO_COL();


            File file1 = new File(waypathPhoto_book1);
            final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
            MultipartBody.Part imagePartPhoto_book12 = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);

//            MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ActivityCompat.checkSelfPermission(ActNextVisitLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if (isGPSEnabled()) {

                        LocationServices.getFusedLocationProviderClient(ActNextVisitLocalData.this)
                                .requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @SuppressLint("MissingPermission")
                                    @Override
                                    public void onLocationResult(@NonNull LocationResult locationResult) {
                                        super.onLocationResult(locationResult);


                                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                        List<Address> addresses = null;

                                        LocationServices.getFusedLocationProviderClient(ActNextVisitLocalData.this)
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

                                                if (CheckInternetConnection.checkInternetConnection(ActNextVisitLocalData.this)) {
                                                    WebService.getClient().visit_next_add_remark(
                                                            RequestBody.create(MediaType.parse("text/plain"), userid/*sharedPreferences.getString("user_id", "")*/),
//                                                                RequestBody.create(MediaType.parse("text/plain"), dialog_agent_code.getText().toString()),
                                                            RequestBody.create(MediaType.parse("text/plain"), dialog_tv_date_picker_page),
                                                            RequestBody.create(MediaType.parse("text/plain"), dialog_tv_time),
                                                            RequestBody.create(MediaType.parse("text/plain"), dialog_remark),
                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
                                                            RequestBody.create(MediaType.parse("text/plain"), visit_id/*sharedPreferences.getString("visit_id", "")*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), dialog_tv_date_picker_visit),
                                                            RequestBody.create(MediaType.parse("text/plain"), dialog_tv_time_visit/*.getText().toString()*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), IMEI_no/*sharedPreferences.getString("IMEI_no", "")*/),
                                                            imagePartPhoto_book12
                                                    ).enqueue(new Callback<visit_next_add_remark_POJO>() {
                                                        @Override
                                                        public void onResponse(Call<visit_next_add_remark_POJO> call, Response<visit_next_add_remark_POJO> response) {

//                                                            pro.dismiss();

                                                            if(response.body() != null)
                                                            {
                                                                if(dbHandler.deleteNextVisitItem(arNextVisitData.get(countPos)))
                                                                {
                                                                    Log.e("Msg_shyam", "Delete data successfull");
                                                                    Toast.makeText(ActNextVisitLocalData.this, "Delete data successfull", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Log.e("Msg_shyam", "Delete data issue");
                                                                    Toast.makeText(ActNextVisitLocalData.this, "Delete data is in issue", Toast.LENGTH_SHORT).show();
                                                                }

                                                                if(countPos==(arNextVisitData.size()-1))
                                                                {
                                                                    pro.dismiss();
//                                                                    showAlertDialogButtonClicked1();
//                                                                    SetVisible();
                                                                    GetOfflineData();
                                                                    Toast.makeText(ActNextVisitLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    countPos=countPos+1; // 5
                                                                    SendDatatoServer();
                                                                }
                                                            }
                                                            else
                                                            {

                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(Call<visit_next_add_remark_POJO> call, Throwable t) {

                                                            pro.dismiss();
                                                        }
                                                    });
                                                }
                                                else
                                                {
                                                    Toast.makeText(ActNextVisitLocalData.this, "Please check your internet", Toast.LENGTH_SHORT).show();
                                                    //                                                    AllNextVisitDataBean allnextvisitdatabean=new AllNextVisitDataBean();
//                                                    allnextvisitdatabean.setNEXTVISIT_USER_ID_COL(sharedPreferences.getString("user_id", ""));
//                                                    allnextvisitdatabean.setNEXTVISIT_DATE_COL(dialog_tv_date_picker_page.getText().toString());
//                                                    allnextvisitdatabean.setNEXTVISIT_TIME_COL(dialog_tv_time.getText().toString());
//                                                    allnextvisitdatabean.setNEXTVISIT_REMARK_COL(dialog_remark.getText().toString());
//                                                    allnextvisitdatabean.setNEXTVISIT_LAT_COL(String.valueOf(latitude));
//                                                    allnextvisitdatabean.setNEXTVISIT_LOGI_COL(String.valueOf(longitude));
//                                                    allnextvisitdatabean.setNEXTVISIT_VISIT_ID_COL(sharedPreferences.getString("visit_id", ""));
//                                                    allnextvisitdatabean.setNEXTVISIT_NEXT_DATE_COL(dialog_tv_date_picker_visit.getText().toString());
//                                                    allnextvisitdatabean.setNEXTVISIT_NEXT_TIME_COL( dialog_tv_time_visit.getText().toString());
//                                                    allnextvisitdatabean.setNEXTVISIT_IMEI_COL(sharedPreferences.getString("IMEI_no", ""));
//
//
//                                                    if (dbHandler.SaveNextVisitData(allnextvisitdatabean)) {
//                                                        pro.dismiss();
//                                                        dialog.dismiss();
//
//                                                        Log.e("Msg_shyam", "Save data successfull");
//                                                        Toast.makeText(customer_detail_Activity.this, "Save data successfull", Toast.LENGTH_SHORT).show();
//                                                        GetOfflineData();
//
//                                                    } else {
//                                                        Log.e("Msg_shyam", "issue is in Save data");
//                                                        Toast.makeText(customer_detail_Activity.this, "some issue is in inserting data", Toast.LENGTH_SHORT).show();
//                                                    }
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





//            String userid=arNextVisitData.get(countPos).getREPORT_USER_ID();
//            String imei=arNextVisitData.get(countPos).getREPORT_IMEI_COL();
//            String str=arNextVisitData.get(countPos).getREPORT_DATE_COL();
//            String ti_opening_km=arNextVisitData.get(countPos).getREPORT_OPENKM_COL();
//            String waypathPhoto_book1=arNextVisitData.get(countPos).getREPORT_OPENKM_PHOTO_COL();
//            String waypathPhoto_book2=arNextVisitData.get(countPos).getREPORT_CLOSEKM_PHOTO_COL();
//            String opening_latitude=arNextVisitData.get(countPos).getREPORT_OPENING_LAT_COL();
//            String opening_longitude=arNextVisitData.get(countPos).getREPORT_OPENING_LOGI_COL();
//            String ti_closing_km=arNextVisitData.get(countPos).getREPORT_CLOSEKM_COL();
//            String closing_latitude=arNextVisitData.get(countPos).getREPORT_CLOSEING_LAT_COL();
//            String closing_longitude=arNextVisitData.get(countPos).getREPORT_CLOSEING_LOGI_COL();
//            String ti_remark=arNextVisitData.get(countPos).getREPORT_RREMARK_COL();
//
//            File file1 = new File(waypathPhoto_book1);
//            final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
//            MultipartBody.Part imagePartPhoto_book12 = MultipartBody.Part.createFormData("opening_photo", file1.getName(), requestBody1);
//            MultipartBody.Part imagePartPhoto_book123=null;
//            if(waypathPhoto_book2!=null){
//                File file2 = new File(waypathPhoto_book2);
//                final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
//                imagePartPhoto_book123 = MultipartBody.Part.createFormData("closing_photo", file2.getName(), requestBody2);
//                Log.d("TAG", "onLocationResult111: "+file1+" 2222 "+file2);
//            }
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(ActReportLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("TAG", "onClick:1112 ");
//                    if (isGPSEnabled()) {
//                        MultipartBody.Part finalImagePartPhoto_book12 = imagePartPhoto_book123;
//                        LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
//                                .requestLocationUpdates(locationRequest, new LocationCallback() {
//
//                                    @SuppressLint("MissingPermission")
//                                    @Override
//                                    public void onLocationResult(@NonNull LocationResult locationResult) {
//                                        Log.d("TAG", "onClick:1113 ");
//                                        super.onLocationResult(locationResult);
//                                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//                                        List<Address> addresses = null;
//
//                                        LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
//                                                .removeLocationUpdates(this);
//
//                                        if (locationResult != null && locationResult.getLocations().size() > 0) {
//
//                                            int index = locationResult.getLocations().size() - 1;
//                                            double latitude = locationResult.getLocations().get(index).getLatitude();
//                                            double longitude = locationResult.getLocations().get(index).getLongitude();
//
//
//                                            try {
//                                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
//
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//
//
//                                            if (addresses != null && !addresses.isEmpty()) {
//                                                try {
//                                                    String countryName = addresses.get(0).getCountryName();
//                                                    String stateName = addresses.get(0).getAdminArea();
//                                                    String knownName = addresses.get(0).getFeatureName(); // landmark
//                                                    String address = addresses.get(0).getAddressLine(0);
//                                                    String cityName = addresses.get(0).getLocality();
//                                                    String area = addresses.get(0).getPostalCode();
//
//
//                                                    Log.d("locationlattt", String.valueOf(latitude));
//                                                    Log.d("location", String.valueOf(longitude));
//                                                    String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//                                                } catch (Exception e) {
//                                                    e.printStackTrace();
//                                                }
//
//
////                                                Date date = new Date();
////                                                CharSequence str  = DateFormat.format("dd-MM-yyyy", date.getTime());
//
//                                                if (CheckInternetConnection.checkInternetConnection(ActReportLocalData.this)) {
//
//                                                    WebService.getClient().daily_report_add_info(
//                                                            RequestBody.create(MediaType.parse("text/plain"), userid),
//                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
//                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
//                                                            RequestBody.create(MediaType.parse("text/plain"), imei),
//                                                            RequestBody.create(MediaType.parse("text/plain"), str),
//                                                            RequestBody.create(MediaType.parse("text/plain"), ti_opening_km),
//                                                            imagePartPhoto_book12,
//                                                            RequestBody.create(MediaType.parse("text/plain"), opening_latitude/*sharedPreferences.getString("opening_latitude", "")*/),
//                                                            RequestBody.create(MediaType.parse("text/plain"), opening_longitude/*sharedPreferences.getString("opening_longitude", "")*/),
//                                                            RequestBody.create(MediaType.parse("text/plain"), ti_closing_km),
//                                                            finalImagePartPhoto_book12,
//                                                            RequestBody.create(MediaType.parse("text/plain"), closing_latitude/*sharedPreferences.getString("closing_latitude", "")*/),
//                                                            RequestBody.create(MediaType.parse("text/plain"), closing_longitude/*sharedPreferences.getString("closing_longitude", "")*/),
//                                                            RequestBody.create(MediaType.parse("text/plain"), ti_remark/*.getText().toString()*/)
//                                                    ).enqueue(new Callback<daily_report_add_info_POJO>() {
//                                                        @Override
//                                                        public void onResponse(Call<daily_report_add_info_POJO> call, Response<daily_report_add_info_POJO> response) {
//
//
//                                                            if (response.body().getSuccess() == 1) {
//
//                                                                if(dbHandler.deleteReportItem(arNextVisitData.get(countPos)))
//                                                                {
//                                                                    Log.e("Msg_shyam", "Delete data successfull");
//                                                                    Toast.makeText(ActReportLocalData.this, "Delete data successfull", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                                else
//                                                                {
//                                                                    Log.e("Msg_shyam", "Delete data issue");
//                                                                    Toast.makeText(ActReportLocalData.this, "Delete data is in issue", Toast.LENGTH_SHORT).show();
//                                                                }
//
//                                                                if(countPos==(arNextVisitData.size()-1))
//                                                                {
//                                                                    pro.dismiss();
////                                                                    showAlertDialogButtonClicked1();
//                                                                    SetVisible();
//                                                                    Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                }
//                                                                else
//                                                                {
//                                                                    countPos=countPos+1; // 5
//                                                                    SendDatatoServer();
//                                                                }
//
////                                                                Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//                                                            } else {
//
//                                                                pro.dismiss();
////                                                                dialog.dismiss();
//
//                                                                Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                                            }
//
//                                                        }
//
//                                                        @Override
//                                                        public void onFailure(Call<daily_report_add_info_POJO> call, Throwable t) {
//                                                            pro.dismiss();
////                                                            dialog.dismiss();
//
//
//                                                        }
//                                                    });
//
//                                                }
//                                                else
//                                                {
//                                                    Toast.makeText(ActReportLocalData.this, "Please check your  internet", Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            }
//                                        }
//
//                                    }
//                                }, Looper.getMainLooper());
//
//                    } else {
//                        turnOnGPS();
//                    }
//
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                }
//            }


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
                    Toast.makeText(ActNextVisitLocalData.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(ActNextVisitLocalData.this, 2);
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

//    @Override
//    public Filter getFilter() {
//        return null;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
}