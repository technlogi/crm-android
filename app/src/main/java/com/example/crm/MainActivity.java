package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crm.Fragment.CustomerFragment;
import com.example.crm.Fragment.HomeFragment;
import com.example.crm.Fragment.ListFragment;
import com.example.crm.Fragment.ProfileFragment;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton from_btn;
    TextView name, number;
    String Name, Contact;
    LinearLayout logout, support,ll_daily_report,ly_my_local_data,lyNextVisitData,lyReportData;
    private LocationRequest locationRequest;

    ImageView drawer,drawer_image;
    DrawerLayout drawer_layour;
    private long pressedTime;

    private static final int REQUEST_CODE = 101;

    String IMEINumber;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main);




        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        Name = sharedPreferences.getString("user_name", "");
        Contact = sharedPreferences.getString("user_contact", "");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        }
//        logout = findViewById(R.id.logout);
//        support = findViewById(R.id.support);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        ll_daily_report = findViewById(R.id.ll_daily_report);
        ly_my_local_data = findViewById(R.id.ll_my_local_data);
        lyNextVisitData = findViewById(R.id.ll_nextVisit_local_data);
        lyReportData = findViewById(R.id.ll_report_local_data);
        drawer_image = findViewById(R.id.drawer_image);

        name.setText(Name);
        number.setText(Contact);

//        geIMEI();

        drawer = findViewById(R.id.drawer);
        from_btn = findViewById(R.id.from_btn);
//        from_btn.setBackgroundColor(Color.parseColor("#ffffff"));

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        drawer_layour = findViewById(R.id.drawer_layour);

        drawer_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,drawer_profile.class);
                startActivity(i);
            }
        });

//        support.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + "9425327566"));
//                startActivity(callIntent);
//            }
//        });
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final SharedPreferences preferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.apply();
//                Intent i = new Intent(MainActivity.this, LoginScreenActivity.class);
//                startActivity(i);
//            }
//        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationbar);
        bottomNavigationView.setBackground(null);
        /*bottomNavigationView.getMenu().getItem(2).setEnabled(false);*/

        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.mHome:
                        temp = new HomeFragment();
                        break;
                    case R.id.mCustomer:
                        temp = new CustomerFragment();
                        break;
                    case R.id.mlist:
                        temp = new ListFragment();
                        break;
                    case R.id.mProfile:
                        temp = new ProfileFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, temp).commit();
                return true;
            }
        });

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer_layour.openDrawer(GravityCompat.START);
            }
        });

        ll_daily_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, dailyActivity.class);
                startActivity(i);
                drawer_layour.close();

            }
        });

        ly_my_local_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActAllLocalData.class);
                startActivity(i);
                drawer_layour.close();
            }
        });
        lyNextVisitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActNextVisitLocalData.class);
                startActivity(i);
                drawer_layour.close();
            }
        });

        lyReportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActReportLocalData.class);
                startActivity(i);
                drawer_layour.close();
            }
        });


        from_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {


//                if (checkConnection())
                {
//                    if (isGPSEnabled()) {
                        SharedPreferences location = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = location.edit();

                        editor.putString("ad_unit_id", "");
                        editor.putString("ad_unit_name", "");
                        editor.putString("site_soff_site", "");
                        editor.putString("site_son_site", "");
                        editor.putString("site_scontact", "");
                        editor.putString("site_sname", "");
                        editor.putString("ad_site_person_id", "");
                        editor.putString("site_sperson", "");
                        editor.putString("add_Stock", "");
                        editor.putString("ad_unit_id", "");
                        editor.putString("ad_unit_name", "");
                        editor.putString("add_qty", "");
                        editor.putString("add_rate", "");
                        editor.putString("add_vendor", "");
                        editor.putString("ad_brand_id", "");
                        editor.putString("ad_brand_name", "");
                        editor.putString("pro_id", "");
                        editor.putString("add_product", "");
                        editor.putString("ad_position_id", "");
                        editor.putString("ad_colony_id", "");
                        editor.putString("ad_cusomer_id", "");
                        editor.apply();
                        Intent i = new Intent(MainActivity.this, FormActivity.class);
                        FormActivity.flag = false;
                        startActivity(i);
//                    } else {
//                        turnOnGPS();
//                    }
                }
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void geIMEI() {

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
        IMEINumber = telephonyManager.getImei();
        Log.d("TAG", "geIMEI: "+ IMEINumber );

        myEdit.putString("IMEI_no", String.valueOf(IMEINumber));
        myEdit.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 1)  {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
                Log.d("TAG", "onRequestPermissionsResult: "+grantResults[0]);
                if (isGPSEnabled()) {

                    getCurrentLocation();

                } else {

                    turnOnGPS();
                }
            }
        }


    }

    private boolean checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = (ConnectionReceiver.ReceiverListener) this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            showSnackBar(isConnected);


        }

        // display snack bar


        return isConnected;
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected) {
            // when internet is connected
            // set message
            message = "Connected to Internet";
            // set text color
            color = Color.GREEN;

        } else {

            // when internet
            // is disconnected
            // set message
            message = "Not Connected to Internet";

            // set text color
            color = Color.RED;
        }

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.name), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
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
//                                            String knownName = addresses.get(0).getFeatureName(); // landmark
                                            String address = addresses.get(0).getAddressLine(0);
                                            String cityName = addresses.get(0).getLocality();
                                            String area = addresses.get(0).getPostalCode();

                                            try {
                                                Log.d("location", countryName);
                                                Log.d("location", stateName);
                                                Log.d("location", cityName);
//                                            Log.d("location", knownName);
                                                Log.d("location", area);
                                                Log.d("location", address);
                                                Log.d("locationlattt", String.valueOf(latitude));
                                                Log.d("location", String.valueOf(longitude));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
//                                            String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                            myEdit.putString("country", countryName);
                                            myEdit.putString("state", stateName);
                                            myEdit.putString("city", cityName);
                                            myEdit.putString("latitude", String.valueOf(latitude));
                                            myEdit.putString("longitude", String.valueOf(longitude));
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
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
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

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (checkConnection()) {
            getCurrentLocation();
        }
    }
}