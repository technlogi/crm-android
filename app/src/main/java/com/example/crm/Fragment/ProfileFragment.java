package com.example.crm.Fragment;

import static android.content.Context.MODE_PRIVATE;

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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crm.Adapter.customer_list_Adapter;
import com.example.crm.LoginScreenActivity;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.site_person_list;
import com.example.crm.POJO.site_person_list_POJO;
import com.example.crm.R;
import com.example.crm.view_all_todo_list;
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

public class ProfileFragment extends Fragment {
    View view;
//    TextView logout, user_name, user_contact;
//    String name, contact;

    RecyclerView rec_customer_list;
    SearchView searchView;
    customer_list_Adapter customer_list_adapter;
    List<customer_list_POJO> itemlist;
    SwipeRefreshLayout container3;
    SharedPreferences sharedPreferences;
    private LocationRequest locationRequest;
    ImageView no_data_found;
    ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

//        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
//
//        name = sharedPreferences.getString("user_name", "");
//        contact = sharedPreferences.getString("user_contact", "");
//
//        logout = view.findViewById(R.id.logout);
//        user_name = view.findViewById(R.id.user_name);
//        user_contact = view.findViewById(R.id.user_contact);
//
//        user_name.setText(name);
//        user_contact.setText(contact);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final SharedPreferences preferences = view.getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.apply();
//                Intent i = new Intent(view.getContext(), LoginScreenActivity.class);
//                startActivity(i);
//            }
//        });
        sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        rec_customer_list = view.findViewById(R.id.rec_customer_list);
        searchView = view.findViewById(R.id.searchView);
        container3 = view.findViewById(R.id.container3);
        no_data_found = view.findViewById(R.id.no_data_found);
        progressBar = view.findViewById(R.id.progress_bar);
        itemlist = new ArrayList();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        RecyclerElectric();
        container3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                RecyclerElectric();
                container3.setRefreshing(false);
            }
        });


//        customer_list_adapter = new customer_list_Adapter(itemlist, getContext());
//        rec_customer_list.setLayoutManager(linearLayoutManager);
//        rec_customer_list.setAdapter(customer_list_adapter);


        return view;
    }

    private void RecyclerElectric() {

        progressBar.setVisibility(View.VISIBLE);
        no_data_found.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(getContext())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(getContext())
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

                                            WebService.getClient().site_person_list_POJO(sharedPreferences.getString("user_id", ""),

                                                    String.valueOf(latitude),
                                                    String.valueOf(longitude),
                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<site_person_list>() {

                                                @Override
                                                public void onResponse(Call<site_person_list> call, Response<site_person_list> response) {

                                                    progressBar.setVisibility(View.GONE);
                                                    if (response.body() == null)
                                                    {
                                                        no_data_found.setVisibility(View.VISIBLE);
                                                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {

                                                        if (response.body().getResult().size() != 0)
                                                        {
                                                            customer_list_adapter = new customer_list_Adapter(response.body().getResult(), getContext());
                                                            rec_customer_list.setLayoutManager(linearLayoutManager);
                                                            rec_customer_list.setAdapter(customer_list_adapter);
                                                            no_data_found.setVisibility(View.GONE);
                                                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                                @Override
                                                                public boolean onQueryTextSubmit(String query) {
                                                                    return false;
                                                                }

                                                                @Override
                                                                public boolean onQueryTextChange(String newText) {

                                                                    customer_list_adapter.getFilter().filter(newText);
                                                                    return false;
                                                                }
                                                            });




                                                        }else {
                                                            no_data_found.setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<site_person_list> call, Throwable t) {
                                                    no_data_found.setVisibility(View.VISIBLE);
                                                    progressBar.setVisibility(View.GONE);
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
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(getContext(), "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
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