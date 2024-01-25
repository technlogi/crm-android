package com.example.crm.Network;

import com.example.crm.POJO.Edit_form_POJO;
import com.example.crm.POJO.Slider_img_POJO;
import com.example.crm.POJO.add_product_POJO;
import com.example.crm.POJO.brand_POJO;
import com.example.crm.POJO.colony_list_POJO;
import com.example.crm.POJO.customer_list_POJO;
import com.example.crm.POJO.customer_list_dropdown_POJO;
import com.example.crm.POJO.daily_report_add_info_POJO;
import com.example.crm.POJO.daily_report_editPOJO;
import com.example.crm.POJO.daily_report_list_POJO;
import com.example.crm.POJO.dashboard_status_POJO;
import com.example.crm.POJO.fragment_main_todo_detail_POJO;
import com.example.crm.POJO.home_today_list_POJO;
import com.example.crm.POJO.login_POJO;
import com.example.crm.POJO.person_name_list;
import com.example.crm.POJO.product_list_POJO;
import com.example.crm.POJO.shop_visit_form_POJO;
import com.example.crm.POJO.site_person_list;
import com.example.crm.POJO.site_person_list_POJO;
import com.example.crm.POJO.site_position_list_POJO;

import com.example.crm.POJO.unit_list_POJO;
import com.example.crm.POJO.visit_detail_POJO;
import com.example.crm.POJO.visit_form_POJO;
import com.example.crm.POJO.visit_next_add_remark_POJO;
import com.example.crm.POJO.visit_todo_POJO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public class WebService {

    private static WebServiceInterface webApiInterface;

    public static WebServiceInterface getClient() {
        if (webApiInterface == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okclient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .setLenient()
                    .create();

//             Post post = gson.fromJson(reader, Post.class);

            Retrofit client = new Retrofit.Builder()
                    .baseUrl("http://13.200.250.138/erp/")

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okclient)
                    .build();

            webApiInterface = client.create(WebServiceInterface.class);
        }
        return webApiInterface;
    }

    public interface WebServiceInterface {

        @POST("api/crm/login_otp_verify.php")
        @FormUrlEncoded
        Call<login_POJO> logIn_check(
                @Field("rMobileNo") String rMobileNo,
                @Field("pin") String pin,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/customer_list.php")
        @FormUrlEncoded
        Call<customer_list_dropdown_POJO> customer_list_dropdown(
                @Field("customer_hint") String customer_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no
//                 @Field("mobile_1") String mobile_1,
//                @Field("mobile_2") String mobile_2,
//                @Field("mobile_3") String mobile_3



        );

        @POST("api/crm/colony_list.php")
        @FormUrlEncoded
        Call<colony_list_POJO> colony_list(
                @Field("colony_hint") String colony_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no

        );

        @POST("api/crm/site_position_list.php")
        @FormUrlEncoded
        Call<site_position_list_POJO> site_position_list(
                @Field("position_hint") String position_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no

        );

        @POST("api/crm/product_list.php")
        @FormUrlEncoded
        Call<product_list_POJO> product_list(
                @Field("product_hint") String product_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no

        );

        @POST("api/crm/person_list.php")
        @FormUrlEncoded
        Call<site_person_list_POJO> site_person_list(
                @Field("person_hint") String person_name_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/brand_list.php")
        @FormUrlEncoded
        Call<brand_POJO> brand(
                @Field("brand_hint") String brand_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/unit_list.php")
        @FormUrlEncoded
        Call<unit_list_POJO> unit_list(
                @Field("unit_hint") String unit_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no

        );

//        @GET()
//        Call<visit_form_POJO> sent_in_url_visit_form(@Url String url);

        @POST("api/crm/visit_form.php")
        @Multipart
        Call<visit_form_POJO> visit_form(

                //page1
                @Part("visit_date") RequestBody visit_date,
                @Part("visit_time") RequestBody visit_time,
                @Part("market_agent_code") RequestBody market_agent_code,
                @Part("customer_list__id") RequestBody customer_list__id,


                @Part("c_name") RequestBody c_name,
                @Part("mobile_1") RequestBody mobile_1,
                @Part("mobile_2") RequestBody mobile_2,
                @Part("mobile_3") RequestBody mobile_3,

                @Part("addresss") RequestBody addresss,//page2
                @Part("colony_list_id") RequestBody colony_list_id,
                @Part("colony") RequestBody colony,
                @Part("site_status") RequestBody site_status,
                @Part("Site_width") RequestBody Site_width,
                @Part("Site_Hight") RequestBody Site_Hight,
                @Part("postion") RequestBody pan,
                @Part("postion_list_id") RequestBody postion_list_id,


                @Part("product_name") RequestBody product_name,//page3
                @Part("product_id") RequestBody product_id,//page3
                @Part("brand") RequestBody brand,
                @Part("brand_id") RequestBody brand_id,
                @Part("existing_buy_shop") RequestBody existing_buy_shop,
                @Part("existing_buy_rate") RequestBody existing_buy_rate,
                @Part("required_qty") RequestBody required_qty,
                @Part("unit") RequestBody unit,
                @Part("unit_id") RequestBody unit_id,
                @Part("stock") RequestBody stock,

                @Part("type_of_person") RequestBody type_of_person,//page4
                @Part("type_of_person_id") RequestBody type_of_person_id,
                @Part("name") RequestBody name,
                @Part("name_id") RequestBody name_id,
                @Part("contact_no") RequestBody contact_no,
                @Part("ON_Site") RequestBody ON_Site,
                @Part("OFF_Site") RequestBody OFF_Site,

                @Part("next_visit_date") RequestBody next_visit_date,//page5
                @Part("next_visit_time") RequestBody next_visit_time,
                @Part("remark") RequestBody remark,

                @Part("form_fillup_latittude") RequestBody form_fillup_latittude,
                @Part("form_fillup_longitude") RequestBody form_fillup_longitude,

                @Part("image_upload_latitude") RequestBody image_upload_latitude,
                @Part("image_upload_longitude") RequestBody image_upload_longitude,
                @Part("user_id") RequestBody user_id,
                @Part MultipartBody.Part shop_image,
                @Part("imei_no") RequestBody imei_no,
                @Part("category") RequestBody category,
                @Part("customer_dob") RequestBody customer_dob,
                @Part("person_dob") RequestBody person_dob

        );


        @POST("api/crm/today_visit_list.php")
        @FormUrlEncoded
        Call<home_today_list_POJO> home_today_list_POJO(
                @Field("user_id") String user_id,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/all_customer_list.php")
        @FormUrlEncoded
        Call<customer_list_POJO> customer_list_POJO(
                @Field("user_id") String user_id,
                @Field("customer_id") String customer_id,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/person_name_list.php")
        @FormUrlEncoded
        Call<person_name_list> site_personname_list(
                @Field("person_name_hint") String person_name_hint,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("user_id") String user_id,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/all_site_person_list.php")
        @FormUrlEncoded
        Call<site_person_list> site_person_list_POJO(
                @Field("user_id") String user_id,

                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no
        );



        @POST("api/crm/customer_visit_list.php")
        @FormUrlEncoded
        Call<visit_todo_POJO> visit_todo_POJO(
                @Field("user_id") String user_id,
                @Field("customer_id") String customer_id,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no

        );

        @POST("api/crm/date_wise_visit_list.php")
        @FormUrlEncoded
        Call<fragment_main_todo_detail_POJO> fragment_main_todo_detail_POJO(
                @Field("user_id") String user_id,
                @Field("date") String date,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no

        );

        @POST("api/crm/visit_detail.php")
        @FormUrlEncoded
        Call<visit_detail_POJO> visit_detail_POJO(
                @Field("visit_id") String visit_id,
                @Field("user_id") String user_id,
                @Field("at") String visit_lat,
                @Field("long") String visit_long,
                @Field("imei_no") String imei_no
        );

        @POST("api/crm/daily_report_list.php")
        @FormUrlEncoded
        Call<daily_report_list_POJO> daily_report_list(
                @Field("user_id") String user_id,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no
        );


        @POST("api/crm/visit_form_edit.php")
        @Multipart
        Call<Edit_form_POJO> Edit_form(

                //page1
                @Part("visit_date") RequestBody visit_date,
                @Part("visit_time") RequestBody visit_time,
                @Part("market_agent_code") RequestBody market_agent_code,
                @Part("customer_list__id") RequestBody customer_list__id,


                @Part("c_name") RequestBody c_name,
                @Part("mobile_1") RequestBody mobile_1,
                @Part("mobile_2") RequestBody mobile_2,
                @Part("mobile_3") RequestBody mobile_3,

                @Part("addresss") RequestBody addresss,//page2
                @Part("colony_list_id") RequestBody colony_list_id,
                @Part("colony") RequestBody colony,
                @Part("site_status") RequestBody site_status,
                @Part("Site_width") RequestBody Site_width,
                @Part("Site_Hight") RequestBody Site_Hight,
                @Part("postion") RequestBody pan,
                @Part("postion_list_id") RequestBody postion_list_id,


                @Part("product_name") RequestBody product_name,//page3
                @Part("product_id") RequestBody product_id,//page3
                @Part("brand") RequestBody brand,
                @Part("brand_id") RequestBody brand_id,
                @Part("existing_buy_shop") RequestBody existing_buy_shop,
                @Part("existing_buy_rate") RequestBody existing_buy_rate,
                @Part("required_qty") RequestBody required_qty,
                @Part("unit") RequestBody unit,
                @Part("unit_id") RequestBody unit_id,
                @Part("stock") RequestBody stock,

                @Part("type_of_person") RequestBody type_of_person,//page4
                @Part("type_of_person_id") RequestBody type_of_person_id,
                @Part("name") RequestBody name,
                @Part("name_id") RequestBody name_id,
                @Part("contact_no") RequestBody contact_no,
                @Part("ON_Site") RequestBody ON_Site,
                @Part("OFF_Site") RequestBody OFF_Site,

                @Part("next_visit_date") RequestBody next_visit_date,//page5
                @Part("next_visit_time") RequestBody next_visit_time,
                @Part("remark") RequestBody remark,

                @Part("form_fillup_latittude") RequestBody form_fillup_latittude,
                @Part("form_fillup_longitude") RequestBody form_fillup_longitude,

                @Part("image_upload_latitude") RequestBody image_upload_latitude,
                @Part("image_upload_longitude") RequestBody image_upload_longitude,
                @Part("user_id") RequestBody user_id,
                @Part("visit_id") RequestBody visit_id,
                @Part MultipartBody.Part shop_image,
                @Part("imei_no") RequestBody imei_no,
                @Part("category") RequestBody category,
                @Part("customer_dob") RequestBody customer_dob,
                @Part("person_dob") RequestBody person_dob

        );

        @POST("api/crm/next_visit_add.php")
        @Multipart
        Call<visit_next_add_remark_POJO> visit_next_add_remark(
                @Part("user_id") RequestBody user_id,
//                @Part("market_agent_code") RequestBody market_agent_code,
                @Part("next_visit_date") RequestBody next_visit_date,
                @Part("next_visit_time") RequestBody next_visit_time,
                @Part("remark") RequestBody remark,
                @Part("latitude") RequestBody latittude,
                @Part("longitude") RequestBody longitude,
                @Part("visit_id") RequestBody visit_id,
                @Part("visit_date") RequestBody visit_date,
                @Part("visit_time") RequestBody visit_time,
                @Part("imei_no") RequestBody imei_no,
                @Part MultipartBody.Part image
        );

        @POST("api/crm/daily_report.php")
        @Multipart
        Call<daily_report_add_info_POJO> daily_report_add_info(
                @Part("user_id") RequestBody user_id,
                @Part("latitude") RequestBody latitude,
                @Part("longitude") RequestBody longitude,
                @Part("imei_no") RequestBody imei_no,
                @Part("date") RequestBody date,
                @Part("opening_km") RequestBody opening_km,
                @Part MultipartBody.Part opening_photo,
                @Part("op_lat") RequestBody op_lat,
                @Part("op_long") RequestBody op_long,
                @Part("closing_km") RequestBody closing_km,
                @Part MultipartBody.Part closing_photo,
                @Part("cl_lat") RequestBody cl_lat,
                @Part("cl_long") RequestBody cl_long,
                @Part("next_day_plan") RequestBody next_day_plan

        );

        @POST("api/crm/daily_report_edit.php")
        @Multipart
        Call<daily_report_editPOJO> daily_report_edit(
                @Part("user_id") RequestBody user_id,
                @Part("report_id") RequestBody report_id,
                @Part("latitude") RequestBody latitude,
                @Part("longitude") RequestBody longitude,
                @Part("imei_no") RequestBody imei_no,
                @Part("date") RequestBody date,
                @Part("opening_km") RequestBody opening_km,
                @Part MultipartBody.Part opening_photo,
                @Part("op_lat") RequestBody op_lat,
                @Part("op_long") RequestBody op_long,
                @Part("closing_km") RequestBody closing_km,
                @Part MultipartBody.Part closing_photo,
                @Part("cl_lat") RequestBody cl_lat,
                @Part("cl_long") RequestBody cl_long,
                @Part("next_day_plan") RequestBody next_day_plan

        );

        @GET("api/crm/banner_image.php")
        Call<Slider_img_POJO> Slider_img();

        @POST("api/crm/dashboard_data.php")
        @FormUrlEncoded
        Call<dashboard_status_POJO> dashboard_status(
                @Field("user_id") String user_id,
                @Field("latitude") String latitude,
                @Field("longitude") String longitude,
                @Field("imei_no") String imei_no

        );
    }
}
