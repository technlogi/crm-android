package com.example.crm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.crm.Adapter.visit_todo_Adapter;
import com.example.crm.Network.WebService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class view_all_todo_list extends AppCompatActivity {

    ImageView back_img,no_data_found;
    LinearLayout list_ll1,list_ll2,list_ll3,list_ll4,list_ll5,list_ll6;
    SearchView searchview2;
    SearchView searchView;
    String customer_id;
    SwipeRefreshLayout container4;

    RecyclerView rec_visit_todo;
    visit_todo_Adapter visit_todo_adapter;
    List<visit_todo_POJO> itemlist;
    SharedPreferences sharedPreferences;
    private LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_view_all_todo_list);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

         sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        customer_id= intent.getStringExtra("customer_id");

        back_img = findViewById(R.id.back_img);
        container4 = findViewById(R.id.container4);
        no_data_found = findViewById(R.id.no_data_found);


        rec_visit_todo = findViewById(R.id.rec_visit_todo);
        itemlist = new ArrayList<>();
        searchview2 = findViewById(R.id.searchview2);

        RecyclerElectric();
        container4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                RecyclerElectric();
                container4.setRefreshing(false);
            }
        });


//        visit_todo_adapter = new visit_todo_Adapter(itemlist, this);
//        rec_visit_todo.setLayoutManager(linearLayoutManager);
//        rec_visit_todo.setAdapter(visit_todo_adapter);



//        searchview2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
////                parentItemAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void RecyclerElectric() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(view_all_todo_list.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(view_all_todo_list.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(view_all_todo_list.this)
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



                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();


                                            WebService.getClient().visit_todo_POJO(sharedPreferences.getString("user_id", ""),
                                                    customer_id,
                                                    String.valueOf(latitude),
                                                    String.valueOf(longitude),
                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<visit_todo_POJO>() {
                                                @Override
                                                public void onResponse(Call<visit_todo_POJO> call, Response<visit_todo_POJO> response) {

                                                    if (!response.body().getMessage().equals("No Record Found"))
                                                    {
                                                        visit_todo_adapter = new visit_todo_Adapter(response.body().getResult(), view_all_todo_list.this);
                                                        rec_visit_todo.setLayoutManager(linearLayoutManager);
                                                        rec_visit_todo.setAdapter(visit_todo_adapter);

                                                        searchview2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                            @Override
                                                            public boolean onQueryTextSubmit(String query) {
                                                                return false;
                                                            }

                                                            @Override
                                                            public boolean onQueryTextChange(String newText) {

                                                                visit_todo_adapter.getFilter().filter(newText);
                                                                return false;
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(view_all_todo_list.this, "No data found", Toast.LENGTH_SHORT).show();
                                                        rec_visit_todo.setVisibility(View.GONE);
                                                        no_data_found.setVisibility(View.VISIBLE);
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<visit_todo_POJO> call, Throwable t) {

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
                    Toast.makeText(view_all_todo_list.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(view_all_todo_list.this, 2);
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