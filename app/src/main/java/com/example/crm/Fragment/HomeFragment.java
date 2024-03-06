package com.example.crm.Fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.crm.Adapter.home_today_list_Adapter;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.Slider_img_POJO;
import com.example.crm.POJO.dashboard_status_POJO;
import com.example.crm.POJO.home_today_list_POJO;
import com.example.crm.R;
import com.example.crm.view_all_todo_list;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    View view;
    TextView view_all, to_do_list;
    ScrollView page1_sccrollview;
    LinearLayout page2_linear;
    RecyclerView rec_hom_today_visit;
    home_today_list_Adapter home_today_list_adapterr;
    List<home_today_list_POJO.Result> itemlist;
    ImageView no_data_found;
    SwipeRefreshLayout container1;
    SharedPreferences sharedPreferences;
    String multiimage;
    ImageSlider image_slider;
    TextView total_visit, today_visit, pending_visit;
    ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);



        image_slider = view.findViewById(R.id.image_slider);
        no_data_found = view.findViewById(R.id.no_data_found);
container1 = view.findViewById(R.id.container1);
        rec_hom_today_visit = view.findViewById(R.id.rec_hom_today_visit);

        total_visit = view.findViewById(R.id.total_visit);
        today_visit = view.findViewById(R.id.today_visit);
        progressBar = view.findViewById(R.id.progress_bar);
        pending_visit = view.findViewById(R.id.pending_visit);
        itemlist = new ArrayList();

        getBanner();

        RecyclerElectric();

        container1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // Log.d("TAG", "onRefresh: 123");
                RecyclerElectric();
                WebService.getClient().dashboard_status(sharedPreferences.getString("user_id", ""),
                        sharedPreferences.getString("latitude", ""),
                        sharedPreferences.getString("longitude", ""),
                        sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<dashboard_status_POJO>() {
                    @Override
                    public void onResponse(Call<dashboard_status_POJO> call, Response<dashboard_status_POJO> response) {

                        if (response.body() != null)
                        {

                            total_visit.setText(response.body().getResult().get(0).getTotalVisit());
                            today_visit.setText(response.body().getResult().get(0).getTodayVisit());
                            pending_visit.setText(response.body().getResult().get(0).getPendingVisit());

                            Log.d("TAG", "xsvcscxc: " + sharedPreferences.getString("user_id", ""));
                            Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("longitude", ""));
                            Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("latitude", ""));
                            Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("IMEI_no", ""));

                        }
                    }

                    @Override
                    public void onFailure(Call<dashboard_status_POJO> call, Throwable t) {

                    }
                });
                container1.setRefreshing(false);
            }
        });


        //numberrr
        WebService.getClient().dashboard_status(sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<dashboard_status_POJO>() {
            @Override
            public void onResponse(Call<dashboard_status_POJO> call, Response<dashboard_status_POJO> response) {

                if (response.body() != null)
                {

                    total_visit.setText(response.body().getResult().get(0).getTotalVisit());
                    today_visit.setText(response.body().getResult().get(0).getTodayVisit());
                    pending_visit.setText(response.body().getResult().get(0).getPendingVisit());

                    Log.d("TAG", "xsvcscxc: " + sharedPreferences.getString("user_id", ""));
                    Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("longitude", ""));
                    Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("latitude", ""));
                    Log.d("TAG", "xsvcscxc: " +  sharedPreferences.getString("IMEI_no", ""));

                }
            }

            @Override
            public void onFailure(Call<dashboard_status_POJO> call, Throwable t) {

            }
        });

//        home_today_list_adapterr = new home_today_list_Adapter(itemlist, getContext());
//        rec_hom_today_visit.setLayoutManager(linearLayoutManager);
//        rec_hom_today_visit.setAdapter(home_today_list_adapterr);


                //slider image
                ArrayList < SlideModel > slideModels = new ArrayList<>();
        page1_sccrollview = view.findViewById(R.id.page1_sccrollview);

        view_all = view.findViewById(R.id.view_all);
        to_do_list = view.findViewById(R.id.to_do_list);

//        slideModels.add(new SlideModel(R.drawable.ic_add,null));
//        slideModels.add(new SlideModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fanodius.com%2Fwp-content%2Fuploads%2F2018%2F06%2Fwhat-is-crm-system-anodius.jpg&imgrefurl=https%3A%2F%2Fwww.anodius.com%2Fen%2Fwhat-is-crm-system%2F&tbnid=f-VEstAUqBlCWM&vet=12ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygJegUIARDrAQ..i&docid=OYJoscBVl2QHAM&w=1412&h=841&q=crm%20image&ved=2ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygJegUIARDrAQ", null));
////        slideModels.add(new SlideModel(String.valueOf(getResources().getDrawable(R.drawable.preeview)), null));
//        slideModels.add(new SlideModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fsoffront.com%2Fwp-content%2Fuploads%2F2020%2F03%2Fsistema-crm-processi-organici.png&imgrefurl=https%3A%2F%2Fsoffront.com%2Fblog%2Fcovid-19-outbreak-crm-best-tool-times%2F&tbnid=gD0_fMB_JWZbWM&vet=12ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygGegUIARDlAQ..i&docid=cRbxaXS9on9XvM&w=1068&h=427&q=crm%20image&ved=2ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygGegUIARDlAQ", null));
//        slideModels.add(new SlideModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.trujay.com%2Fhs-fs%2Fhubfs%2FImported_Blog_Media%2FOperational-CRM.png%3Fwidth%3D1000%26height%3D600%26name%3DOperational-CRM.png&imgrefurl=https%3A%2F%2Fwww.trujay.com%2Fblog%2Foperational-crm-key-features-and-benefits&tbnid=c2M88c-ncPHxxM&vet=12ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygzegQIARBe..i&docid=Hm8iw2w30BmBgM&w=1000&h=600&q=crm%20image&ved=2ahUKEwjO4eyb2ob3AhVZk9gFHe4_ByQQMygzegQIARBe", null));


//        slideModels.add(new SlideModel(R.drawable.slider1, null));
//        slideModels.add(new SlideModel(R.drawable.slider2, null));
//        slideModels.add(new SlideModel(R.drawable.slider3, null));
//        image_slider.setImageList(slideModels, ScaleTypes.FIT);

        return view;
    }

    private void getBanner() {

        WebService.getClient().Slider_img().enqueue(new Callback<Slider_img_POJO>() {
            @Override
            public void onResponse(Call<Slider_img_POJO> call, Response<Slider_img_POJO> response) {
//                multiimage = response.body().getResult().get();

                ArrayList<SlideModel> slideModels;
                slideModels = new ArrayList<>();

                for (int i = 0; i < response.body().getResult().size(); i++) {
                    slideModels.add(new SlideModel(response.body().getResult().get(i).getImageSmall(), null));
                }

                image_slider.setImageList(slideModels);
            }

            @Override
            public void onFailure(Call<Slider_img_POJO> call, Throwable t) {

            }
        });
    }

    private void RecyclerElectric() {
        progressBar.setVisibility(View.VISIBLE);
        no_data_found.setVisibility(View.GONE);
        rec_hom_today_visit.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        WebService.getClient().home_today_list_POJO(sharedPreferences.getString("user_id", ""),
                sharedPreferences.getString("latitude", ""),
                sharedPreferences.getString("longitude", ""),
                sharedPreferences.getString("IMEI_no", "")).enqueue(new Callback<home_today_list_POJO>() {

            @Override
            public void onResponse(Call<home_today_list_POJO> call, Response<home_today_list_POJO> response) {
                //Log.d("TAG", "onRefresh: 123456");
//                if (response.body() == null) {
//
//                    Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
//
//                } else {


//                Toast.makeText(view.getContext(), "sgs", Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                if (!response.body().getMessage().equals("No Record Found")) {

//                    editor.putString("visit_id",response.body().getResult().);
                    home_today_list_adapterr = new home_today_list_Adapter(response.body().getResult(), view.getContext());
                    rec_hom_today_visit.setLayoutManager(linearLayoutManager);
                    rec_hom_today_visit.setHasFixedSize(true);
                    rec_hom_today_visit.setAdapter(home_today_list_adapterr);

                } else {
                    Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    rec_hom_today_visit.setVisibility(View.GONE);
                    no_data_found.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<home_today_list_POJO> call, Throwable t) {
                rec_hom_today_visit.setVisibility(View.GONE);
                no_data_found.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}