package com.example.crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crm.Adapter.ShowAddProductAdapter;
import com.example.crm.Local_database.DBHandler;
import com.example.crm.POJO.AllDataBean;
import com.example.crm.POJO.add_product_POJO;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActDetailLocalData1 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DBHandler dbHandler;
    ProgressDialog pro;
    AllDataBean alldatabean;
    RecyclerView rec_product/*, rec_site_persion*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.act_detail_localdata);
        sharedPreferences = ActDetailLocalData1.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(ActDetailLocalData1.this);
        pro = new ProgressDialog(this);
        pro.setCancelable(false);
        pro.setMessage("Uploading...");
        String sjosnData=getIntent().getStringExtra("bean");
        alldatabean=new Gson().fromJson(sjosnData, AllDataBean.class);
        InitiComponent();
        SetAllData();

//        rec_main_Todo = findViewById(R.id.rec_local_data);
//        lySyncData = findViewById(R.id.ly_sync);
//        im_add =findViewById(R.id.im_add);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActDetailLocalData.this);
//        rec_main_Todo.setLayoutManager(linearLayoutManager);

//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5000);
//        locationRequest.setFastestInterval(2000);
//        RecyclerElectric();

//        lySyncData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CallAPI(1);
//            }
//        });
//        SetAdpater();

    }
    TextView tvCustType,tvVisitdateTime,tvcustNm,tvMobile1,tvMobile2,tvMobile3,tvAddress,tvColony,tvStatus,tvSize,tvPosition;
//    TextView tvProductName,tvBrand,tvVendor,tvRate,tvQnty,tvUnit,tvStock;
    TextView tvsitePersion,tvSitePerType,tvSitePerNm,tvSitePerContact,tvDob,tvOnsite,tvOffSite,tvNextVisitDateTime,tvRemark;
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

        /*tvProductName=findViewById(R.id.tv_product_name);
        tvBrand=findViewById(R.id.tv_brand);
        tvVendor=findViewById(R.id.tv_vendor);
        tvRate=findViewById(R.id.tv_rate);
        tvQnty=findViewById(R.id.tv_qty);
        tvUnit=findViewById(R.id.tv_unit);
        tvStock=findViewById(R.id.tv_stock);*/


        tvsitePersion=findViewById(R.id.tv_side_persone_detail);
        tvSitePerType=findViewById(R.id.tv_type_of_person);
        tvSitePerNm=findViewById(R.id.tv_person_name);
        tvSitePerContact=findViewById(R.id.tv_contact);
        tvDob=findViewById(R.id.tv_dob);
        tvOnsite=findViewById(R.id.tv_on_site);
        tvOffSite=findViewById(R.id.tv_off_site);
        tvNextVisitDateTime=findViewById(R.id.tv_next_visit_date);
        tvRemark=findViewById(R.id.tv_remark);
        ivPic=findViewById(R.id.iv_pic);

        rec_product = findViewById(R.id.rec_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActDetailLocalData1.this);
        rec_product.setLayoutManager(linearLayoutManager);
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

//        tvProductName.setText(alldatabean.getADD_PRODUCT()+" :");
//        tvBrand.setText(": "+alldatabean.getADD_BRAND());
//        tvVendor.setText(": "+alldatabean.getVENDOR());
//        tvRate.setText(": "+alldatabean.getRATE());
//        tvQnty.setText(": "+alldatabean.getQNTY());
//        tvUnit.setText(": "+alldatabean.getUNIT());
//        tvStock.setText(": "+alldatabean.getSTOCK());

        tvsitePersion.setText(alldatabean.getSITE_PERSON()+" :");
        tvSitePerType.setText(": "+alldatabean.getSITE_PERSON());
        tvSitePerNm.setText(": "+alldatabean.getSITE_PERSON_ID_NAME());
        tvSitePerContact.setText(": "+alldatabean.getSITE_CONTACT());
        tvDob.setText(": "+alldatabean.getCUSTOMER_DOB());
        tvOnsite.setText(": "+alldatabean.getSITE_ON_SITE());
        tvOffSite.setText(": "+alldatabean.getSITE_OFF_SITE());
        tvNextVisitDateTime.setText(": "+alldatabean.getDATE_PAGE2()+","+alldatabean.getTIME_PAGE2());
        tvRemark.setText(": "+alldatabean.getREMARK());

        if(!TextUtils.isEmpty(alldatabean.getPHOTO_BOOK())) {
            Glide.with(ActDetailLocalData1.this).load(new File(alldatabean.getPHOTO_BOOK())).into(ivPic);
        }
        else
        {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
        SetAdapter();
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
//        List<String> arExistingShop= Arrays.asList(alldatabean.get);

//
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
        add_product_adapter = new ShowAddProductAdapter(itemlist, ActDetailLocalData1.this, ActDetailLocalData1.this) {
            @Override
            public void onItemChanged(List<add_product_POJO> list) {

//                itemlist.clear();
//                itemlist.addAll(list);
//                add_product_adapter.notifyDataSetChanged();

            }
        };
        rec_product.setAdapter(add_product_adapter);
    }
}