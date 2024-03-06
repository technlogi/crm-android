package com.example.crm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.daily_report_editPOJO;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
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

public class edit_daily_report extends AppCompatActivity {


    ImageView upload_img;
    ImageView upload_img2;
    TextInputEditText ti_remark,ti_closing_km,ti_opening_km;
    ImageView preview,preview2;
    TextView dialog_add;
    String opening_km,closing_km,next_plan,report_id,opening_photo,closing_photo,comeFrom;
    private LocationRequest locationRequest;
    ProgressDialog pro;
    String waypathPhoto_book1;
    String waypathPhoto_book2;
    MultipartBody.Part imagePartPhoto_book1 = null;
    MultipartBody.Part imagePartPhoto_book2 = null;
    SharedPreferences sharedPreferences;
    Uri uriPhoto;
    Uri uriPhoto2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_daily_report);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        Intent intent = getIntent();
        opening_km = intent.getStringExtra("opening km");
        closing_km = intent.getStringExtra("closing km");
        next_plan = intent.getStringExtra("next_plan");
        report_id = intent.getStringExtra("report_id");
        opening_photo = intent.getStringExtra("opening_photo");
        closing_photo = intent.getStringExtra("closing_photo");
        comeFrom = intent.getStringExtra("coming_from");

        ti_remark = findViewById(R.id.ti_remark);
        ti_closing_km = findViewById(R.id.ti_closing_km);
        ti_opening_km = findViewById(R.id.ti_opening_km);

        preview2 = findViewById(R.id.preview2);

        sharedPreferences = edit_daily_report.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

        dialog_add = findViewById(R.id.dialog_add);
        upload_img2 = findViewById(R.id.upload_img2);
        dialog_add = findViewById(R.id.dialog_add);
        upload_img = findViewById(R.id.upload_img);
        preview = findViewById(R.id.preview);

        ti_remark.setText(next_plan);
        ti_closing_km.setText(closing_km);
        ti_opening_km.setText(opening_km);
if(comeFrom.equals("API")) {
    Glide.with(edit_daily_report.this).load(opening_photo).into(preview);
}
else
{
    Glide.with(edit_daily_report.this).load(new File(opening_photo)).into(preview);
}
        if(preview2!=null){
            if(comeFrom.equals("API")) {
            Glide.with(edit_daily_report.this).load(closing_photo).into(preview2);
            }
            else
            {
                Glide.with(edit_daily_report.this).load(new File(closing_photo)).into(preview);
            }
        }else{

        }



        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImagePicker.create(EditProfileActivity.this).single().start();
                /*Intent gal = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                gal.setType("image/*");
                startActivityForResult(gal, 1);*/
                ImagePicker.with(edit_daily_report.this)
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
                ImagePicker.with(edit_daily_report.this)
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
                    if(waypathPhoto_book1!=null){
                        File file1 = new File(waypathPhoto_book1);
                        final RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                        imagePartPhoto_book1 = MultipartBody.Part.createFormData("opening_photo", file1.getName(), requestBody1);
                    }

                    if(waypathPhoto_book2!=null){
                        File file2 = new File(waypathPhoto_book2);
                        final RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), file2);
                        imagePartPhoto_book2 = MultipartBody.Part.createFormData("closing_photo", file2.getName(), requestBody2);
                    }

                    MultipartBody.Part imagePartPhoto_book12 = imagePartPhoto_book1;

                    MultipartBody.Part imagePartPhoto_book123 = imagePartPhoto_book2;

                    Log.d("TAG", "onClick:111 "+imagePartPhoto_book2);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (ActivityCompat.checkSelfPermission(edit_daily_report.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            if (isGPSEnabled()) {

                                LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
                                        .requestLocationUpdates(locationRequest, new LocationCallback() {

                                            @SuppressLint("MissingPermission")
                                            @Override
                                            public void onLocationResult(@NonNull LocationResult locationResult) {

                                                super.onLocationResult(locationResult);


                                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                                List<Address> addresses = null;

                                                LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
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


                                                        Log.d("locationlattt", String.valueOf(latitude));
                                                        Log.d("location", String.valueOf(longitude));
                                                        String c = Currency.getInstance(addresses.get(0).getLocale()).getDisplayName();

                                                        Date date = new Date();
                                                        CharSequence str = DateFormat.format("dd-MM-yyyy", date.getTime());


                                                        if (CheckInternetConnection.checkInternetConnection(edit_daily_report.this)) {
                                                        WebService.getClient().daily_report_edit(
                                                                RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", "")),
                                                                RequestBody.create(MediaType.parse("text/plain"), report_id),
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


                                                        ).enqueue(new Callback<daily_report_editPOJO>() {
                                                            @Override
                                                            public void onResponse(Call<daily_report_editPOJO> call, Response<daily_report_editPOJO> response) {


                                                                if (response.body().getSuccess() == 1) {
                                                                    pro.dismiss();
                                                                    finish();
                                                                    Intent i = new Intent(edit_daily_report.this, dailyActivity.class);
                                                                    startActivity(i);
                                                                    Toast.makeText(edit_daily_report.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//

                                                                } else {
                                                                    pro.dismiss();
                                                                    Toast.makeText(edit_daily_report.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<daily_report_editPOJO> call, Throwable t) {
                                                                pro.dismiss();
                                                            }
                                                        });
                                                    }
                                                        else
                                                        {
                                                            pro.dismiss();

                                                            Toast.makeText(edit_daily_report.this, "coming soon", Toast.LENGTH_SHORT).show();
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




    }

    private boolean validation5() {
//        if (TextUtils.isEmpty(waypathPhoto_book1)) {
//            Toast.makeText(edit_daily_report.this, "Select Shop image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
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

            if (ActivityCompat.checkSelfPermission(edit_daily_report.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
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

            if (ActivityCompat.checkSelfPermission(edit_daily_report.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {


                    LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                    List<Address> addresses = null;

                                    LocationServices.getFusedLocationProviderClient(edit_daily_report.this)
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
            locationManager = (LocationManager) edit_daily_report.this.getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(edit_daily_report.this)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(edit_daily_report.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(edit_daily_report.this, 2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uriPhoto = data.getData();
                    waypathPhoto_book1 = getFilePath(edit_daily_report.this, uriPhoto);
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
                    waypathPhoto_book2 = getFilePath(edit_daily_report.this, uriPhoto2);
                    Uri selectedImageUri = data.getData();
                    preview2.setImageURI(selectedImageUri);
                    //profile_image.setImageURI(uri);
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
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

}