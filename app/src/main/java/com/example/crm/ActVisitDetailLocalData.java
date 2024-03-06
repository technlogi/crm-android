package com.example.crm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crm.Adapter.ShowAddProductAdapter;
import com.example.crm.Adapter.ShowSidePersonAdapter;
import com.example.crm.Adapter.add_product_Adapter;
import com.example.crm.Adapter.all_local_data_adapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.Network.CheckInternetConnection;
import com.example.crm.Network.WebService;
import com.example.crm.POJO.AllDataBean;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.sidePersionPOJO;
import com.example.crm.POJO.visit_form_POJO;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActVisitDetailLocalData extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    ProgressDialog pro;
    AllDataBean alldatabean;
    RecyclerView rec_product, rec_site_persion;
    ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.act_visit_detail_local);
        sharedPreferences = ActVisitDetailLocalData.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(ActVisitDetailLocalData.this);
        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Uploading...");
        String sjosnData=getIntent().getStringExtra("bean");
        alldatabean=new Gson().fromJson(sjosnData, AllDataBean.class);
        InitiComponent();
        SetAllData();

    }
    TextView tvCustType,tvVisitdateTime,tvcustNm,tvMobile1,tvMobile2,tvMobile3,tvAddress,tvColony,tvStatus,tvSize,tvPosition;
//    TextView tvsitePersion/*,tvSitePerType,tvSitePerNm,tvSitePerContact,tvDob,tvOnsite,tvOffSite,tvNextVisitDateTime,tvRemark*/;
    ImageView ivPic;
    private void InitiComponent() {

        tvCustType=findViewById(R.id.tv_customer_type);
        tvVisitdateTime=findViewById(R.id.tv_visit_date);
        tvcustNm=findViewById(R.id.tv_customer_name);
        tvMobile1=findViewById(R.id.tv_mobile_no1);
        tvMobile2=findViewById(R.id.tv_mobile_no2);
        tvMobile3=findViewById(R.id.tv_mobile_no3);
        tvAddress=findViewById(R.id.tv_address);
        tvColony=findViewById(R.id.tv_colony_name);
        tvStatus=findViewById(R.id.tv_side_status);
        tvSize=findViewById(R.id.tv_size);
        tvPosition=findViewById(R.id.tv_position);


//        tvsitePersion=findViewById(R.id.tv_side_persone_detail);

        ivPic=findViewById(R.id.iv_pic);
        back_img = findViewById(R.id.back_img);
        rec_site_persion = findViewById(R.id.rec_related_person);
        rec_product = findViewById(R.id.rec_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActVisitDetailLocalData.this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ActVisitDetailLocalData.this);
        rec_product.setLayoutManager(linearLayoutManager);
        rec_site_persion.setLayoutManager(linearLayoutManager1);


//        rec_site_persion = findViewById(R.id.rec_site_persion);

    }

    public void SetAllData()
    {
        tvCustType.setText(": "+alldatabean.getCATEGORY());
        tvVisitdateTime.setText(": "+alldatabean.getDATE_COL()+","+alldatabean.getTIME_COL());
        tvcustNm.setText(": "+alldatabean.getCUSTOMER_NAME_COL());
        tvMobile1.setText(": "+alldatabean.getCUSTOMER_MOBILE1_COL());
        tvMobile2.setText(": "+alldatabean.getCUSTOMER_MOBILE2_COL());
        tvMobile3.setText(": "+alldatabean.getCUSTOMER_MOBILE3_COL());
        tvAddress.setText(": "+alldatabean.getADDRESS_COL());
        tvColony.setText(": "+alldatabean.getCOLONY_NAME_COL());
        tvStatus.setText(": "+alldatabean.getSWITH_TOGGLE_COL());
        tvSize.setText(": "+alldatabean.getWIDTH_COL()+"x"+alldatabean.getHEIGHT_COL());
        tvPosition.setText(": "+alldatabean.getSITE_POSITION_COL());

        if(!TextUtils.isEmpty(alldatabean.getPHOTO_BOOK())) {
            Glide.with(ActVisitDetailLocalData.this).load(new File(alldatabean.getPHOTO_BOOK())).into(ivPic);
        }
        else
        {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
        SetAdapter();
        SetSidePersonAdapter();

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    ShowAddProductAdapter add_product_adapter;

    public void SetAdapter()
    {
        String[] aProduct = alldatabean.getADD_PRODUCT().split(",");
        String[] aProductId = alldatabean.getADD_PRODUCT_ID().split(",");
        String[] aBrand = alldatabean.getADD_BRAND().split(",");
        String[] aBrandId = alldatabean.getADD_BRAND_ID().split(",");
        String[] aVendor = alldatabean.getVENDOR().split(",");
        String[] aRate = alldatabean.getRATE().split(",");
        String[] aQnty = alldatabean.getQNTY().split(",");
        String[] aUnit = alldatabean.getUNIT().split(",");
        String[] aUnitId = alldatabean.getUNIT_ID().split(",");
        String[] aStock = alldatabean.getSTOCK().split(",");

        List<String> arProductNm = Arrays.asList(aProduct); // step three : copy fixed list to an
        List<String> arProductId = Arrays.asList(aProductId); // step three : copy fixed list to an
        List<String> arBrand = Arrays.asList(aBrand);
        List<String> arBrandId = Arrays.asList(aBrandId);
        List<String> arVendor = Arrays.asList(aVendor); // step three : copy fixed list to an
        List<String> arRate = Arrays.asList(aRate);
        List<String> arQNTY = Arrays.asList(aQnty);
        List<String> arUNIT = Arrays.asList(aUnit);
        List<String> arUNITID = Arrays.asList(aUnitId);
        List<String> arStock = Arrays.asList(aStock);

//                 ArrayList<String> arProduct = new ArrayList<String>(arProductNm);

        Log.e("Size","arProductNm.size()= "+arProductNm.size()/*+" arProductNm.size()= "+arProduct.size()*/);

        ArrayList<add_product_POJO> itemlist = new ArrayList<add_product_POJO>();

for(int i=0;i<arProductNm.size();i++) {
        itemlist.add(new add_product_POJO(
                arProductNm.get(i),
                arBrand.get(i),
                arVendor.get(i),
                arRate.get(i),
                arQNTY.get(i),
                arUNIT.get(i),
                arStock.get(i),
                arProductId.get(i),
                arBrandId.get(i),
                arUNITID.get(i)
        ));
}
        add_product_adapter = new ShowAddProductAdapter(itemlist, ActVisitDetailLocalData.this, ActVisitDetailLocalData.this) {
            @Override
            public void onItemChanged(List<add_product_POJO> list) {


            }
        };
        rec_product.setAdapter(add_product_adapter);
    }

    ShowSidePersonAdapter sidePersionAdapter;

    public void SetSidePersonAdapter()
    {
        String[] aSitePerson = alldatabean.getSITE_PERSON().split(",");
        String[] aSitePersonName = alldatabean.getSITE_PERSON_ID_NAME().split(",");
        String[] aContact = alldatabean.getSITE_CONTACT().split(",");
        String[] aDob = alldatabean.getCUSTOMER_DOB().split(",");
        String[] aOnSite = alldatabean.getSITE_ON_SITE().split(",");
        String[] aOffSite = alldatabean.getSITE_OFF_SITE().split(",");
        String[] aDate = alldatabean.getDATE_PAGE2().split(",");
        String[] aTime = alldatabean.getTIME_PAGE2().split(",");
        String[] aRemark = alldatabean.getREMARK().split(",");

        List<String> arSitePerson = Arrays.asList(aSitePerson);
        List<String> arSitePersonName = Arrays.asList(aSitePersonName);
        List<String> arContact = Arrays.asList(aContact);
        List<String> arDob = Arrays.asList(aDob);
        List<String> arOnSite = Arrays.asList(aOnSite); // step three : copy fixed list to an
        List<String> arOffSite = Arrays.asList(aOffSite);
        List<String> arDate = Arrays.asList(aDate);
        List<String> arTime = Arrays.asList(aTime);
        List<String> arRemark = Arrays.asList(aRemark);

        Log.e("Size","arProductNm.size()= "+arSitePerson.size());

        ArrayList<sidePersionPOJO> itemlist = new ArrayList<sidePersionPOJO>();

for(int i=0;i<arSitePerson.size();i++) {

        itemlist.add(new sidePersionPOJO(
                arSitePerson.get(i),
                arSitePersonName.get(i),
                arContact.get(i),
                arDob.get(0),
                arOnSite.get(i),
                arOffSite.get(i),
                arDate.get(0),
                arTime.get(0),
                arRemark.get(0)
        ));
}
        sidePersionAdapter = new ShowSidePersonAdapter(itemlist, ActVisitDetailLocalData.this, ActVisitDetailLocalData.this) {
            @Override
            public void onItemChanged(List<add_product_POJO> list) {

//                itemlist.clear();
//                itemlist.addAll(list);
//                add_product_adapter.notifyDataSetChanged();

            }
        };
        rec_site_persion.setAdapter(sidePersionAdapter);
    }
}