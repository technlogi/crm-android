package com.example.crm.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crm.Adapter.customer_list_Adapter;
import com.example.crm.Adapter.fragment_main_todo_Adapter;

import com.example.crm.Adapter.fragment_main_todo_detail_Adapter;
import com.example.crm.Adapter.home_today_list_Adapter;
import com.example.crm.FormActivity;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.fragment_main_todo_POJO;
import com.example.crm.POJO.fragment_main_todo_detail_POJO;
import com.example.crm.POJO.home_today_list_POJO;
import com.example.crm.R;
import com.example.crm.customer_detail_Activity;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ListFragment extends Fragment {

    View view;
    RecyclerView rec_main_todo_cal, rec_main_Todo;
    fragment_main_todo_Adapter fragment_main_todo_adapter;
    fragment_main_todo_detail_Adapter fragment_main_todo_detail_adapter;
    List<fragment_main_todo_POJO> itemlist;
    SearchView searchView;
    List<fragment_main_todo_detail_POJO> itemlist2;
    ImageView no_data_found;
    TextView tv_date_picker;
    SimpleDateFormat simpleDateFormat;
    String dateTime;
    String dateTracker = "";
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout container2;
    private LocationRequest locationRequest;
    ProgressBar progressBar;
    final Calendar myCalendar= Calendar.getInstance();

    String s_tv_date_picker;


    public ListFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = getContext().getSharedPreferences().edit();

        view = inflater.inflate(R.layout.fragment_list, container, false);
        rec_main_todo_cal = view.findViewById(R.id.rec_main_todo_cal);
        rec_main_Todo = view.findViewById(R.id.rec_main_Todo);
        searchView = view.findViewById(R.id.searchView);
        no_data_found = view.findViewById(R.id.no_data_found);
        progressBar = view.findViewById(R.id.progress_bar);
        container2 = view.findViewById(R.id.container2);

        tv_date_picker = view.findViewById(R.id.tv_date_picker);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        itemlist = new ArrayList();
        itemlist2 = new ArrayList();


        //horizontal recycle view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        itemlist.add(new fragment_main_todo_POJO("mon", "1"));
        itemlist.add(new fragment_main_todo_POJO("tue", "2"));
        itemlist.add(new fragment_main_todo_POJO("thus", "3"));
        itemlist.add(new fragment_main_todo_POJO("wen", "4"));
        itemlist.add(new fragment_main_todo_POJO("fri", "5"));
        itemlist.add(new fragment_main_todo_POJO("fri", "6"));
        itemlist.add(new fragment_main_todo_POJO("fri", "7"));
        itemlist.add(new fragment_main_todo_POJO("fri", "8"));
        itemlist.add(new fragment_main_todo_POJO("fri", "9"));

        //date picker1

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

//        callweb();

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        tv_date_picker.setText(dateTime);

        dateTracker = dateTime;

        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                callweb();
                updateLabel();
            }
        };

        tv_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getContext(),/* R.style.datepicker,*/date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();


              /*  materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {


                        dateTracker = materialDatePicker.getHeaderText();
                        callweb();

                        if (!dateTracker.equals(""))
                        {

                        }
                        tv_date_picker.setText(dateTracker);
                        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        dateTime = simpleDateFormat.format(selection);
                        tv_date_picker.setText(dateTime);
                    }
                });*/
            }
        });

        container2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callweb();
                container2.setRefreshing(false);

            }
        });

        fragment_main_todo_adapter = new fragment_main_todo_Adapter(itemlist, getContext());
        rec_main_todo_cal.setLayoutManager(linearLayoutManager);
        rec_main_todo_cal.setAdapter(fragment_main_todo_adapter);



        //vertical recycle view

//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//
//        WebService.getClient().fragment_main_todo_detail_POJO(sharedPreferences.getString("user_id", ""),"").enqueue(new Callback<fragment_main_todo_detail_POJO>() {
//
//            @Override
//            public void onResponse(Call<fragment_main_todo_detail_POJO> call, Response<fragment_main_todo_detail_POJO> response) {
//
//
//                    if (!response.body().getMessage().equals("No Record Found")) {
//                        fragment_main_todo_detail_adapter = new fragment_main_todo_detail_Adapter(response.body().getResult(), view.getContext());
//                        rec_main_Todo.setLayoutManager(linearLayoutManager1);
//                        rec_main_Todo.setAdapter(fragment_main_todo_detail_adapter);
//                    }else
//                    {
//                        Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
//                        rec_main_Todo.setVisibility(View.GONE);
//                        no_data_found.setVisibility(View.VISIBLE);
//                    }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<fragment_main_todo_detail_POJO> call, Throwable t) {
//
//            }
//        });
//
//
//        fragment_main_todo_detail_adapter = new fragment_main_todo_detail_Adapter(itemlist2, getContext());
//        rec_main_Todo.setLayoutManager(linearLayoutManager1);
//        rec_main_Todo.setAdapter(fragment_main_todo_detail_adapter);


        callweb();

        return view;

    }

    private void updateLabel() {
        String myFormat="dd-MM-yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        tv_date_picker.setText(dateFormat.format(myCalendar.getTime()));
    }


//    private void RecyclerElectric() {
//        callweb();
//    }



    private void callweb() {


        no_data_found.setVisibility(View.GONE);
        rec_main_Todo.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(getContext())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
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





                                            WebService.getClient().fragment_main_todo_detail_POJO(sharedPreferences.getString("user_id", ""),
                                                    tv_date_picker.getText().toString(),
                                                    String.valueOf(latitude),
                                                    String.valueOf(longitude),
                                                    sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<fragment_main_todo_detail_POJO>() {

                                                @Override
                                                public void onResponse(Call<fragment_main_todo_detail_POJO> call, Response<fragment_main_todo_detail_POJO> response) {


                                                    progressBar.setVisibility(View.GONE);
                                                    if (!response.body().getMessage().equals("No Record Found")) {
                                                        fragment_main_todo_detail_adapter = new fragment_main_todo_detail_Adapter(response.body().getResult(), view.getContext());
                                                        rec_main_Todo.setLayoutManager(linearLayoutManager1);
                                                        rec_main_Todo.setAdapter(fragment_main_todo_detail_adapter);

                                                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                            @Override
                                                            public boolean onQueryTextSubmit(String query) {
                                                                return false;
                                                            }

                                                            @Override
                                                            public boolean onQueryTextChange(String newText) {

                                                                fragment_main_todo_detail_adapter.getFilter().filter(newText);
                                                                return false;
                                                            }
                                                        });
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        rec_main_Todo.setVisibility(View.GONE);
                                                        no_data_found.setVisibility(View.VISIBLE);
                                                    }


                                                }

                                                @Override
                                                public void onFailure(Call<fragment_main_todo_detail_POJO> call, Throwable t) {

                                                    progressBar.setVisibility(View.GONE);
                                                    rec_main_Todo.setVisibility(View.GONE);
                                                    no_data_found.setVisibility(View.VISIBLE);
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