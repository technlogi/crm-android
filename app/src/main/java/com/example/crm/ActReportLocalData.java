package com.example.crm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.crm.Adapter.dailyReportLocalAdapter;
import com.example.crm.Adapter.visit_todo_Adapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllReportDataBean;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.visit_form_POJO;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActReportLocalData extends AppCompatActivity {
TextView tvTitle;
    ImageView back_img,no_data_found;
    LinearLayout list_ll1,list_ll2,list_ll3,list_ll4,list_ll5,list_ll6;
    SearchView searchview2;
    SearchView searchView;
    String customer_id;
    SwipeRefreshLayout container4;

    RecyclerView rvReportData;
    visit_todo_Adapter visit_todo_adapter;
    List<visit_todo_POJO> itemlist;
    SharedPreferences sharedPreferences;
    private LocationRequest locationRequest;
    DBHandler dbHandler;
    ProgressDialog pro;
    LinearLayout lySyncData;
    ArrayList<AllReportDataBean>arAllReportData;
    dailyReportLocalAdapter dailyreportadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.act_next_visit_local);
        dbHandler = new DBHandler(ActReportLocalData.this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

         sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

//        Intent intent = getIntent();
//        customer_id= intent.getStringExtra("customer_id");

        back_img = findViewById(R.id.back_img);
        container4 = findViewById(R.id.container4);
        no_data_found = findViewById(R.id.no_data_found);
        tvTitle = findViewById(R.id.tv_next_visit_report);

        lySyncData = findViewById(R.id.ly_sync);
        rvReportData = findViewById(R.id.rec_next_visit_todo);
        itemlist = new ArrayList<>();
        searchview2 = findViewById(R.id.searchview2);
        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Please wait ...");


        tvTitle.setText("Report : Local Data");
//        RecyclerElectric();
        GetOfflineData();
        container4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                RecyclerElectric();
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

    public void GetOfflineData()
    {
        arAllReportData = dbHandler.GetAllReportData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActReportLocalData.this);
        dailyreportadapter = new dailyReportLocalAdapter(arAllReportData, ActReportLocalData.this);
        rvReportData.setLayoutManager(linearLayoutManager);
        rvReportData.setAdapter(dailyreportadapter);
        SetVisible();
    }

    public void SetVisible()
    {
        int  count = dbHandler.getRecordCountReportItem();
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
        if (CheckInternetConnection.checkInternetConnection(ActReportLocalData.this)) {
            switch (pos) {
                case 1:
                    SendDatatoServer();
                    break;
            }
        }
        else {
            Toast.makeText(ActReportLocalData.this, getResources().getString(R.string.txt_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    int countPos=0;
    public void SendDatatoServer()
    {

        if(countPos<arAllReportData.size())
        {
            if(countPos==0)
            {
                pro.show();
            }

            String userid=arAllReportData.get(countPos).getREPORT_USER_ID();
            String imei=arAllReportData.get(countPos).getREPORT_IMEI_COL();
            String str=arAllReportData.get(countPos).getREPORT_DATE_COL();
            String ti_opening_km=arAllReportData.get(countPos).getREPORT_OPENKM_COL();
            String waypathPhoto_book1=arAllReportData.get(countPos).getREPORT_OPENKM_PHOTO_COL();
            String waypathPhoto_book2=arAllReportData.get(countPos).getREPORT_CLOSEKM_PHOTO_COL();
            String opening_latitude=arAllReportData.get(countPos).getREPORT_OPENING_LAT_COL();
            String opening_longitude=arAllReportData.get(countPos).getREPORT_OPENING_LOGI_COL();
            String ti_closing_km=arAllReportData.get(countPos).getREPORT_CLOSEKM_COL();
            String closing_latitude=arAllReportData.get(countPos).getREPORT_CLOSEING_LAT_COL();
            String closing_longitude=arAllReportData.get(countPos).getREPORT_CLOSEING_LOGI_COL();
            String ti_remark=arAllReportData.get(countPos).getREPORT_RREMARK_COL();

            File file1 = new File(waypathPhoto_book1);
            final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
            MultipartBody.Part imagePartPhoto_book12 = MultipartBody.Part.createFormData("opening_photo", file1.getName(), requestBody1);
            MultipartBody.Part imagePartPhoto_book123=null;
            if(waypathPhoto_book2!=null){
                File file2 = new File(waypathPhoto_book2);
                final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                imagePartPhoto_book123 = MultipartBody.Part.createFormData("closing_photo", file2.getName(), requestBody2);
                Log.d("TAG", "onLocationResult111: "+file1+" 2222 "+file2);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(ActReportLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "onClick:1112 ");
                    if (isGPSEnabled()) {
                        MultipartBody.Part finalImagePartPhoto_book12 = imagePartPhoto_book123;
                        LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
                                .requestLocationUpdates(locationRequest, new LocationCallback() {

                                    @SuppressLint("MissingPermission")
                                    @Override
                                    public void onLocationResult(@NonNull LocationResult locationResult) {
                                        Log.d("TAG", "onClick:1113 ");
                                        super.onLocationResult(locationResult);
                                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                        List<Address> addresses = null;

                                        LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
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


//                                                Date date = new Date();
//                                                CharSequence str  = DateFormat.format("dd-MM-yyyy", date.getTime());

                                                if (CheckInternetConnection.checkInternetConnection(ActReportLocalData.this)) {

                                                    WebService.getClient().daily_report_add_info(
                                                            RequestBody.create(MediaType.parse("text/plain"), userid),
                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)),
                                                            RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)),
                                                            RequestBody.create(MediaType.parse("text/plain"), imei),
                                                            RequestBody.create(MediaType.parse("text/plain"), str),
                                                            RequestBody.create(MediaType.parse("text/plain"), ti_opening_km),
                                                            imagePartPhoto_book12,
                                                            RequestBody.create(MediaType.parse("text/plain"), opening_latitude/*sharedPreferences.getString("opening_latitude", "")*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), opening_longitude/*sharedPreferences.getString("opening_longitude", "")*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), ti_closing_km),
                                                            finalImagePartPhoto_book12,
                                                            RequestBody.create(MediaType.parse("text/plain"), closing_latitude/*sharedPreferences.getString("closing_latitude", "")*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), closing_longitude/*sharedPreferences.getString("closing_longitude", "")*/),
                                                            RequestBody.create(MediaType.parse("text/plain"), ti_remark/*.getText().toString()*/)
                                                    ).enqueue(new Callback<daily_report_add_info_POJO>() {
                                                        @Override
                                                        public void onResponse(Call<daily_report_add_info_POJO> call, Response<daily_report_add_info_POJO> response) {


                                                            if (response.body().getSuccess() == 1) {

                                                                if(dbHandler.deleteReportItem(arAllReportData.get(countPos)))
                                                                {
                                                                    Log.e("Msg_shyam", "Delete data successfull");
                                                                    Toast.makeText(ActReportLocalData.this, "Delete data successfull", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Log.e("Msg_shyam", "Delete data issue");
                                                                    Toast.makeText(ActReportLocalData.this, "Delete data is in issue", Toast.LENGTH_SHORT).show();
                                                                }

                                                                if(countPos==(arAllReportData.size()-1))
                                                                {
                                                                    pro.dismiss();
//                                                                    showAlertDialogButtonClicked1();
//                                                                    SetVisible();
                                                                    GetOfflineData();
                                                                    Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    countPos=countPos+1; // 5
                                                                    SendDatatoServer();
                                                                }

//                                                                Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                            } else {

                                                                pro.dismiss();
//                                                                dialog.dismiss();

                                                                Toast.makeText(ActReportLocalData.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(Call<daily_report_add_info_POJO> call, Throwable t) {
                                                            pro.dismiss();
//                                                            dialog.dismiss();


                                                        }
                                                    });

                                                }
                                                else
                                                {
                                                    Toast.makeText(ActReportLocalData.this, "Please check your  internet", Toast.LENGTH_SHORT).show();

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
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) ActReportLocalData.this.getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(ActReportLocalData.this)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(ActReportLocalData.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(ActReportLocalData.this, 2);
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

//    private void RecyclerElectric() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ActivityCompat.checkSelfPermission(ActReportLocalData.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                if (isGPSEnabled()) {
//
//                    LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
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
//                                    LocationServices.getFusedLocationProviderClient(ActReportLocalData.this)
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
//
//
//                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();
//
//
//                                            WebService.getClient().visit_todo_POJO(sharedPreferences.getString("user_id", ""),
//                                                    customer_id,
//                                                    String.valueOf(latitude),
//                                                    String.valueOf(longitude),
//                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<visit_todo_POJO>() {
//                                                @Override
//                                                public void onResponse(Call<visit_todo_POJO> call, Response<visit_todo_POJO> response) {
//
//                                                    if (!response.body().getMessage().equals("No Record Found"))
//                                                    {
//                                                        visit_todo_adapter = new visit_todo_Adapter(response.body().getResult(), ActReportLocalData.this);
//                                                        rec_visit_todo.setLayoutManager(linearLayoutManager);
//                                                        rec_visit_todo.setAdapter(visit_todo_adapter);
//
//                                                        searchview2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                                                            @Override
//                                                            public boolean onQueryTextSubmit(String query) {
//                                                                return false;
//                                                            }
//
//                                                            @Override
//                                                            public boolean onQueryTextChange(String newText) {
//
//                                                                visit_todo_adapter.getFilter().filter(newText);
//                                                                return false;
//                                                            }
//                                                        });
//                                                    }
//                                                    else
//                                                    {
//                                                        Toast.makeText(ActReportLocalData.this, "No data found", Toast.LENGTH_SHORT).show();
//                                                        rec_visit_todo.setVisibility(View.GONE);
//                                                        no_data_found.setVisibility(View.VISIBLE);
//                                                    }
//
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<visit_todo_POJO> call, Throwable t) {
//
//                                                }
//                                            });
//
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
//
//    }
//    
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = null;
//        boolean isEnabled = false;
//
//        if (locationManager == null) {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                .checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(ActReportLocalData.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(ActReportLocalData.this, 2);
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