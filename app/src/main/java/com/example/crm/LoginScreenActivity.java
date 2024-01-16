package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crm.Network.WebService;
import com.example.crm.POJO.login_POJO;
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

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends AppCompatActivity {

    TextView login, login_txt;
    TextInputEditText edt1_mobile, edt2_pass;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_login_screen);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        login = findViewById(R.id.login);
        edt1_mobile = findViewById(R.id.edt1_mobile);
        edt2_pass = findViewById(R.id.edt2_pass);
//        login_txt = findViewById(R.id.login_txt);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {

                    WebService.getClient().logIn_check(edt1_mobile.getText().toString(),
                            edt2_pass.getText().toString(),
                            "",
                            "",
                            sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<login_POJO>() {

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<login_POJO> call, Response<login_POJO> response) {

                            if (response.body().getMessage().equals("Logged-in successfully")) {

                                Toast.makeText(LoginScreenActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
                                startActivity(intent);

                                myEdit.putString("user_id", response.body().getUserId());
                                myEdit.putString("user_name", response.body().getUserFullName());
                                myEdit.putString("user_contact", response.body().getUserContact());
                                myEdit.apply();
                                Log.d("iiiiiiiiiii", sharedPreferences.getString("user_id", "0"));

                            } else {

                                Toast.makeText(LoginScreenActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<login_POJO> call, Throwable t) {

                        }
                    });

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
                    Toast.makeText(LoginScreenActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(LoginScreenActivity.this, 2);
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

    private boolean validation() {

        if (edt1_mobile.length() != 10) {
            edt1_mobile.setError("10 Digit");
            edt1_mobile.requestFocus();
            Toast.makeText(this, "enter mobile proper", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edt2_pass.getText().toString())) {
            edt2_pass.setError("Enter Password");
            edt2_pass.requestFocus();
            return false;
        }
        return true;
    }

}