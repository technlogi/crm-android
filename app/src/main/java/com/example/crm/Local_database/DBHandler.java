package com.example.crm.Local_database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.crm.POJO.AllDataBean;
import com.example.crm.POJO.AllNextVisitDataBean;
import com.example.crm.POJO.AllReportDataBean;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "crmdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "visit";
    // below variable is for our id column.  46
    private static final String ID_COL = "id";
    private static final String DATE_COL = "date";
    private static final String TIME_COL = "time";
    private static final String AGETCODE_COL = "agentcode";
    private static final String CUSTOMER_ID_COL = "customer_id";
    private static final String CUSTOMER_NAME_COL = "customer_name";
    private static final String CUSTOMER_MOBILE1_COL = "customer_mobile_1";
    private static final String CUSTOMER_MOBILE2_COL = "customer_mobile_2";
    private static final String CUSTOMER_MOBILE3_COL = "customer_mobile_3";
    private static final String ADDRESS_COL = "address";
    private static final String COLONY_ID_COL = "colony_id";
    private static final String COLONY_NAME_COL = "colony_name";
    private static final String SWITH_TOGGLE_COL = "switch_toggle";
    private static final String WIDTH_COL = "width";
    private static final String HEIGHT_COL = "heigh";
    private static final String SITE_POSITION_COL = "side_postition";
    private static final String SITE_POSITION_ID_COL = "side_postition_id";
    private static final String ADD_PRODUCT = "add_product";
    private static final String ADD_PRODUCT_ID = "add_product_id";
    private static final String ADD_BRAND = "add_brand";
    private static final String ADD_BRAND_ID = "add_brand_id";
    private static final String VENDOR = "vendor";
    private static final String RATE = "rate";
    private static final String QNTY = "qnty";
    private static final String UNIT = "unit";
    private static final String UNIT_ID = "unit_id";
    private static final String STOCK = "stock";
    private static final String SITE_PERSON = "site_person";
    private static final String SITE_PERSON_ID = "site_persion_id";
    private static final String SITE_NAME = "site_name";
    private static final String SITE_PERSON_ID_NAME = "site_persion_id_name";
    private static final String SITE_CONTACT = "site_contact";
    private static final String SITE_ON_SITE = "site_on_site";
    private static final String SITE_OFF_SITE = "site_off_site";
    private static final String DATE_PAGE2 = "date_page2";
    private static final String TIME_PAGE2 = "time_page2";
    private static final String REMARK = "remark";
    private static final String FORM_LATI = "form_lati";
    private static final String FORM_LOGI = "form_logi";
    private static final String IMG_LAT = "img_lat";
    private static final String IMG_LOGI = "img_logi";
    private static final String USER_ID = "user_id";
    private static final String PHOTO_BOOK = "photo_book";
    private static final String IMEI_NO = "imei_no";
    private static final String CATEGORY = "category";
    private static final String CUSTOMER_DOB = "customer_dob";
    private static final String CUSTOMER_S_DOB = "customer_s_dob";


    private static final String REPORT_TABLE_NAME = "report";
    private static final String REPORT_ID_COL = "report_id";//15
    private static final String REPORT_USER_ID_COL = "report_user_id";
    private static final String REPORT_LAT_COL = "report_lat";
    private static final String REPORT_LOGI_COL = "report_logi";
    private static final String REPORT_IMEI_COL = "report_imei";
    private static final String REPORT_DATE_COL = "report_date";

    private static final String REPORT_OPENKM_COL = "report_open";
    private static final String REPORT_OPENKM_PHOTO_COL = "report_open_file";
    private static final String REPORT_OPENING_LAT_COL = "report_opening_lat";
    private static final String REPORT_OPENING_LOGI_COL = "report_opening_logi";

    private static final String REPORT_CLOSEKM_COL = "report_close";
    private static final String REPORT_CLOSEKM_PHOTO_COL = "report_close_file";

    private static final String REPORT_CLOSEING_LAT_COL = "report_closing_lat";
    private static final String REPORT_CLOSEING_LOGI_COL = "report_closing_logi";
    private static final String REPORT_RREMARK_COL = "report_remark_logi";


    private static final String NEXT_VISIT_TABLE_NAME = "visit_next";

    private static final String NEXTVISIT_ID_COL = "next_visit_id";//11
    private static final String NEXTVISIT_PHOTO_COL = "next_visit_photo";
    private static final String NEXTVISIT_USER_ID_COL = "next_visit_user_id";
    private static final String NEXTVISIT_DATE_COL = "next_visit_date";
    private static final String NEXTVISIT_TIME_COL = "next_visit_time";
    private static final String NEXTVISIT_REMARK_COL = "next_visit_remark";
    private static final String NEXTVISIT_LAT_COL = "next_visit_lati";
    private static final String NEXTVISIT_LOGI_COL = "next_visit_logi";
    private static final String NEXTVISIT_VISIT_ID_COL = "next_visit_visit_id";
    private static final String NEXTVISIT_NEXT_DATE_COL = "next_visit_next_date";
    private static final String NEXTVISIT_NEXT_TIME_COL = "next_visit_next_time";
    private static final String NEXTVISIT_IMEI_COL = "next_visit_imei";
    private static final String NEXTVISIT_CONTACT_MODE_COL = "contact_mode";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + AGETCODE_COL + " TEXT,"
                + CUSTOMER_ID_COL + " TEXT,"
                + CUSTOMER_NAME_COL + " TEXT,"
                + CUSTOMER_MOBILE1_COL + " TEXT,"
                + CUSTOMER_MOBILE2_COL + " TEXT,"
                + CUSTOMER_MOBILE3_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + COLONY_ID_COL + " TEXT,"
                + COLONY_NAME_COL + " TEXT,"
                + SWITH_TOGGLE_COL + " TEXT,"

                + WIDTH_COL + " TEXT,"
                + HEIGHT_COL + " TEXT,"
                + SITE_POSITION_COL + " TEXT,"
                + SITE_POSITION_ID_COL + " TEXT,"
                + ADD_PRODUCT + " TEXT,"
                + ADD_PRODUCT_ID + " TEXT,"
                + ADD_BRAND + " TEXT,"
                + ADD_BRAND_ID + " TEXT,"
                + VENDOR + " TEXT,"
                + RATE + " TEXT,"
                + QNTY + " TEXT,"
                + UNIT + " TEXT,"
                + UNIT_ID + " TEXT,"
                + STOCK + " TEXT,"
                + SITE_PERSON + " TEXT,"
                + SITE_PERSON_ID + " TEXT,"
                + SITE_NAME + " TEXT,"
                + SITE_PERSON_ID_NAME + " TEXT,"

                + SITE_CONTACT + " TEXT,"
                + SITE_ON_SITE + " TEXT,"
                + SITE_OFF_SITE + " TEXT,"
                + DATE_PAGE2 + " TEXT,"
                + TIME_PAGE2 + " TEXT,"
                + REMARK + " TEXT,"
                + FORM_LATI + " TEXT,"
                + FORM_LOGI + " TEXT,"
                + IMG_LAT + " TEXT,"
                + IMG_LOGI + " TEXT,"
                + USER_ID + " TEXT,"
                + PHOTO_BOOK + " TEXT,"
                + IMEI_NO + " TEXT,"
                + CATEGORY + " TEXT,"
                + CUSTOMER_DOB + " TEXT,"
                + CUSTOMER_S_DOB + " TEXT"+
                ")";
        String query1 = "CREATE TABLE " + REPORT_TABLE_NAME + " ("
                + REPORT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REPORT_USER_ID_COL + " TEXT,"
                + REPORT_LAT_COL + " TEXT,"
                + REPORT_LOGI_COL + " TEXT,"
                + REPORT_IMEI_COL + " TEXT,"
                + REPORT_DATE_COL + " TEXT,"
                + REPORT_OPENKM_COL + " TEXT,"
                + REPORT_OPENKM_PHOTO_COL + " TEXT,"
                + REPORT_OPENING_LAT_COL + " TEXT,"
                + REPORT_OPENING_LOGI_COL + " TEXT,"
                + REPORT_CLOSEKM_COL + " TEXT,"
                + REPORT_CLOSEKM_PHOTO_COL + " TEXT,"
                + REPORT_CLOSEING_LAT_COL + " TEXT,"
                + REPORT_CLOSEING_LOGI_COL + " TEXT,"
                + REPORT_RREMARK_COL + " TEXT"+
                ")";
        String query2 = "CREATE TABLE " + NEXT_VISIT_TABLE_NAME + " ("
                + NEXTVISIT_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NEXTVISIT_PHOTO_COL + " TEXT,"
                + NEXTVISIT_USER_ID_COL + " TEXT,"
                + NEXTVISIT_DATE_COL + " TEXT,"
                + NEXTVISIT_TIME_COL + " TEXT,"
                + NEXTVISIT_REMARK_COL + " TEXT,"
                + NEXTVISIT_LAT_COL + " TEXT,"
                + NEXTVISIT_LOGI_COL + " TEXT,"
                + NEXTVISIT_VISIT_ID_COL + " TEXT,"
                + NEXTVISIT_NEXT_DATE_COL + " TEXT,"
                + NEXTVISIT_CONTACT_MODE_COL + " TEXT,"
                + NEXTVISIT_NEXT_TIME_COL + " TEXT,"
                + NEXTVISIT_IMEI_COL + " TEXT"+
                ")";
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }
    public boolean SaveData(AllDataBean alldatabean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();//46
        values.put(DATE_COL, alldatabean.getDATE_COL());
        values.put(TIME_COL, alldatabean.getTIME_COL());
        values.put(AGETCODE_COL, alldatabean.getAGETCODE_COL());
        values.put(CUSTOMER_ID_COL, alldatabean.getCUSTOMER_ID_COL());
        values.put(CUSTOMER_NAME_COL, alldatabean.getCUSTOMER_ID_COL());
        values.put(CUSTOMER_MOBILE1_COL, alldatabean.getCUSTOMER_MOBILE1_COL());
        values.put(CUSTOMER_MOBILE2_COL, alldatabean.getCUSTOMER_MOBILE2_COL());
        values.put(CUSTOMER_MOBILE3_COL, alldatabean.getCUSTOMER_MOBILE3_COL());
        values.put(ADDRESS_COL, alldatabean.getADDRESS_COL());
        values.put(COLONY_ID_COL, alldatabean.getCOLONY_ID_COL());
        values.put(COLONY_NAME_COL, alldatabean.getCOLONY_NAME_COL());
        values.put(SWITH_TOGGLE_COL, alldatabean.getSWITH_TOGGLE_COL());
        values.put(WIDTH_COL, alldatabean.getWIDTH_COL());
        values.put(HEIGHT_COL, alldatabean.getHEIGHT_COL());
        values.put(SITE_POSITION_COL, alldatabean.getSITE_POSITION_COL());
        values.put(SITE_POSITION_ID_COL, alldatabean.getSITE_POSITION_ID_COL());
        values.put(ADD_PRODUCT, alldatabean.getADD_PRODUCT());
        values.put(ADD_PRODUCT_ID, alldatabean.getADD_PRODUCT_ID());
        values.put(ADD_BRAND, alldatabean.getADD_BRAND());
        values.put(ADD_BRAND_ID, alldatabean.getADD_BRAND_ID());
        values.put(VENDOR, alldatabean.getVENDOR());
        values.put(RATE, alldatabean.getRATE());
        values.put(QNTY, alldatabean.getQNTY());
        values.put(UNIT, alldatabean.getUNIT());
        values.put(UNIT_ID, alldatabean.getUNIT_ID());
        values.put(STOCK, alldatabean.getSTOCK());
        values.put(SITE_PERSON, alldatabean.getSITE_PERSON());
        values.put(SITE_PERSON_ID, alldatabean.getSITE_PERSON_ID());
        values.put(SITE_NAME, alldatabean.getSITE_NAME());
        values.put(SITE_PERSON_ID_NAME, alldatabean.getSITE_PERSON_ID_NAME());
        values.put(SITE_CONTACT, alldatabean.getSITE_CONTACT());
        values.put(SITE_ON_SITE, alldatabean.getSITE_ON_SITE());
        values.put(SITE_OFF_SITE, alldatabean.getSITE_OFF_SITE());
        values.put(DATE_PAGE2, alldatabean.getDATE_PAGE2());
        values.put(TIME_PAGE2, alldatabean.getTIME_PAGE2());
        values.put(REMARK, alldatabean.getREMARK());
        values.put(FORM_LATI, alldatabean.getFORM_LATI());
        values.put(FORM_LOGI, alldatabean.getFORM_LOGI());
        values.put(IMG_LAT, alldatabean.getIMG_LAT());
        values.put(IMG_LOGI, alldatabean.getIMG_LOGI());
        values.put(USER_ID, alldatabean.getUSER_ID());
        values.put(PHOTO_BOOK, alldatabean.getPHOTO_BOOK());
        values.put(IMEI_NO, alldatabean.getIMEI_NO());
        values.put(CATEGORY, alldatabean.getCATEGORY());
        values.put(CUSTOMER_DOB, alldatabean.getCUSTOMER_DOB());
        values.put(CUSTOMER_S_DOB, alldatabean.getCUSTOMER_S_DOB());

       long resulut= db.insert(TABLE_NAME, null, values);
                     db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;

        }
    }
   //get all saved data
    @SuppressLint("Range")
    public ArrayList<AllDataBean> GetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AllDataBean> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        AllDataBean alldatabean;
        while (cursor.moveToNext()){
            alldatabean = new AllDataBean();
            alldatabean.setTABLE_ITEM_ID(cursor.getInt(cursor.getColumnIndex(ID_COL)));//TABLE COLOM ID
            alldatabean.setDATE_COL(cursor.getString(cursor.getColumnIndex(DATE_COL)));
            alldatabean.setTIME_COL(cursor.getString(cursor.getColumnIndex(TIME_COL)));
            alldatabean.setAGETCODE_COL(cursor.getString(cursor.getColumnIndex(AGETCODE_COL)));
            alldatabean.setCUSTOMER_ID_COL(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID_COL)));
            alldatabean.setCUSTOMER_NAME_COL(cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME_COL)));
            alldatabean.setCUSTOMER_MOBILE1_COL(cursor.getString(cursor.getColumnIndex(CUSTOMER_MOBILE1_COL)));
            alldatabean.setCUSTOMER_MOBILE2_COL(cursor.getString(cursor.getColumnIndex(CUSTOMER_MOBILE2_COL)));
            alldatabean.setCUSTOMER_MOBILE3_COL(cursor.getString(cursor.getColumnIndex(CUSTOMER_MOBILE3_COL)));
            alldatabean.setADDRESS_COL(cursor.getString(cursor.getColumnIndex(ADDRESS_COL)));
            alldatabean.setCOLONY_ID_COL(cursor.getString(cursor.getColumnIndex(COLONY_ID_COL)));
            alldatabean.setCOLONY_NAME_COL(cursor.getString(cursor.getColumnIndex(COLONY_NAME_COL)));
            alldatabean.setSWITH_TOGGLE_COL(cursor.getString(cursor.getColumnIndex(SWITH_TOGGLE_COL)));
            alldatabean.setWIDTH_COL(cursor.getString(cursor.getColumnIndex(WIDTH_COL)));
            alldatabean.setHEIGHT_COL(cursor.getString(cursor.getColumnIndex(HEIGHT_COL)));
            alldatabean.setSITE_POSITION_COL(cursor.getString(cursor.getColumnIndex(SITE_POSITION_COL)));
            alldatabean.setSITE_POSITION_ID_COL(cursor.getString(cursor.getColumnIndex(SITE_POSITION_ID_COL)));
            alldatabean.setADD_PRODUCT(cursor.getString(cursor.getColumnIndex(ADD_PRODUCT)));
            alldatabean.setADD_PRODUCT_ID(cursor.getString(cursor.getColumnIndex(ADD_PRODUCT_ID)));
            alldatabean.setADD_BRAND(cursor.getString(cursor.getColumnIndex(ADD_BRAND)));
            alldatabean.setADD_BRAND_ID(cursor.getString(cursor.getColumnIndex(ADD_BRAND_ID)));
            alldatabean.setVENDOR(cursor.getString(cursor.getColumnIndex(VENDOR)));
            alldatabean.setRATE(cursor.getString(cursor.getColumnIndex(RATE)));
            alldatabean.setQNTY(cursor.getString(cursor.getColumnIndex(QNTY)));
            alldatabean.setUNIT(cursor.getString(cursor.getColumnIndex(UNIT)));
            alldatabean.setUNIT_ID(cursor.getString(cursor.getColumnIndex(UNIT_ID)));
            alldatabean.setSTOCK(cursor.getString(cursor.getColumnIndex(STOCK)));
            alldatabean.setSITE_PERSON(cursor.getString(cursor.getColumnIndex(SITE_PERSON)));
            alldatabean.setSITE_PERSON_ID(cursor.getString(cursor.getColumnIndex(SITE_PERSON_ID)));
            alldatabean.setSITE_NAME(cursor.getString(cursor.getColumnIndex(SITE_NAME)));
            alldatabean.setSITE_PERSON_ID_NAME(cursor.getString(cursor.getColumnIndex(SITE_PERSON_ID_NAME)));
            alldatabean.setSITE_CONTACT(cursor.getString(cursor.getColumnIndex(SITE_CONTACT)));
            alldatabean.setSITE_ON_SITE(cursor.getString(cursor.getColumnIndex(SITE_ON_SITE)));
            alldatabean.setSITE_OFF_SITE(cursor.getString(cursor.getColumnIndex(SITE_OFF_SITE)));
            alldatabean.setDATE_PAGE2(cursor.getString(cursor.getColumnIndex(DATE_PAGE2)));
            alldatabean.setTIME_PAGE2(cursor.getString(cursor.getColumnIndex(TIME_PAGE2)));
            alldatabean.setREMARK(cursor.getString(cursor.getColumnIndex(REMARK)));
            alldatabean.setFORM_LATI(cursor.getString(cursor.getColumnIndex(FORM_LATI)));
            alldatabean.setFORM_LOGI(cursor.getString(cursor.getColumnIndex(FORM_LOGI)));
            alldatabean.setIMG_LAT(cursor.getString(cursor.getColumnIndex(IMG_LAT)));
            alldatabean.setIMG_LOGI(cursor.getString(cursor.getColumnIndex(IMG_LOGI)));
            alldatabean.setUSER_ID(cursor.getString(cursor.getColumnIndex(USER_ID)));
            alldatabean.setPHOTO_BOOK(cursor.getString(cursor.getColumnIndex(PHOTO_BOOK)));
            alldatabean.setIMEI_NO(cursor.getString(cursor.getColumnIndex(IMEI_NO)));
            alldatabean.setCATEGORY(cursor.getString(cursor.getColumnIndex(CATEGORY)));
            alldatabean.setCUSTOMER_DOB(cursor.getString(cursor.getColumnIndex(CUSTOMER_DOB)));
            alldatabean.setCUSTOMER_S_DOB(cursor.getString(cursor.getColumnIndex(CUSTOMER_S_DOB)));

            userList.add(alldatabean);
        }
        return  userList;
    }
    public boolean deleteItem(AllDataBean item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {item.getTABLE_ITEM_ID()+""};
        long resulut=  db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;

        }
    }

    public int getRecordCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public boolean SaveReportData(AllReportDataBean alldatabean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();//46
        values.put(REPORT_USER_ID_COL, alldatabean.getREPORT_USER_ID());
        values.put(REPORT_LAT_COL, alldatabean.getREPORT_LAT());
        values.put(REPORT_LOGI_COL, alldatabean.getREPORT_LOGI());
        values.put(REPORT_IMEI_COL, alldatabean.getREPORT_IMEI_COL());
        values.put(REPORT_DATE_COL, alldatabean.getREPORT_DATE_COL());
        values.put(REPORT_OPENKM_COL, alldatabean.getREPORT_OPENKM_COL());
        values.put(REPORT_OPENKM_PHOTO_COL, alldatabean.getREPORT_OPENKM_PHOTO_COL());
        values.put(REPORT_OPENING_LAT_COL, alldatabean.getREPORT_OPENING_LAT_COL());
        values.put(REPORT_OPENING_LOGI_COL, alldatabean.getREPORT_OPENING_LOGI_COL());
        values.put(REPORT_CLOSEKM_COL, alldatabean.getREPORT_CLOSEKM_COL());
        values.put(REPORT_CLOSEKM_PHOTO_COL, alldatabean.getREPORT_CLOSEKM_PHOTO_COL());
        values.put(REPORT_CLOSEING_LAT_COL, alldatabean.getREPORT_CLOSEING_LAT_COL());
        values.put(REPORT_CLOSEING_LOGI_COL, alldatabean.getREPORT_CLOSEING_LOGI_COL());
        values.put(REPORT_RREMARK_COL, alldatabean.getREPORT_RREMARK_COL());

        long resulut= db.insert(REPORT_TABLE_NAME, null, values);
        db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;

        }
    }

    @SuppressLint("Range")
    public ArrayList<AllReportDataBean> GetAllReportData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AllReportDataBean> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ REPORT_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        AllReportDataBean allReportdatabean;
        while (cursor.moveToNext()){
            allReportdatabean = new AllReportDataBean();
            allReportdatabean.setREPORT_TABLE_ITEM_ID(cursor.getInt(cursor.getColumnIndex(REPORT_ID_COL)));//TABLE COLOM ID
            allReportdatabean.setREPORT_USER_ID(cursor.getString(cursor.getColumnIndex(REPORT_USER_ID_COL)));
            allReportdatabean.setREPORT_LAT(cursor.getString(cursor.getColumnIndex(REPORT_LAT_COL)));
            allReportdatabean.setREPORT_LOGI(cursor.getString(cursor.getColumnIndex(REPORT_LOGI_COL)));
            allReportdatabean.setREPORT_IMEI_COL(cursor.getString(cursor.getColumnIndex(REPORT_IMEI_COL)));
            allReportdatabean.setREPORT_DATE_COL(cursor.getString(cursor.getColumnIndex(REPORT_DATE_COL)));
            allReportdatabean.setREPORT_OPENKM_COL(cursor.getString(cursor.getColumnIndex(REPORT_OPENKM_COL)));
            allReportdatabean.setREPORT_OPENKM_PHOTO_COL(cursor.getString(cursor.getColumnIndex(REPORT_OPENKM_PHOTO_COL)));
            allReportdatabean.setREPORT_OPENING_LAT_COL(cursor.getString(cursor.getColumnIndex(REPORT_OPENING_LAT_COL)));
            allReportdatabean.setREPORT_OPENING_LOGI_COL(cursor.getString(cursor.getColumnIndex(REPORT_OPENING_LOGI_COL)));
            allReportdatabean.setREPORT_CLOSEKM_COL(cursor.getString(cursor.getColumnIndex(REPORT_CLOSEKM_COL)));
            allReportdatabean.setREPORT_CLOSEKM_PHOTO_COL(cursor.getString(cursor.getColumnIndex(REPORT_CLOSEKM_PHOTO_COL)));
            allReportdatabean.setREPORT_CLOSEING_LAT_COL(cursor.getString(cursor.getColumnIndex(REPORT_CLOSEING_LAT_COL)));
            allReportdatabean.setREPORT_CLOSEING_LOGI_COL(cursor.getString(cursor.getColumnIndex(REPORT_CLOSEING_LOGI_COL)));
            allReportdatabean.setREPORT_RREMARK_COL(cursor.getString(cursor.getColumnIndex(REPORT_RREMARK_COL)));

            userList.add(allReportdatabean);
        }
        return  userList;
    }

    public boolean deleteReportItem(AllReportDataBean item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "report_id=?";
        String whereArgs[] = {item.getREPORT_TABLE_ITEM_ID()+""};
        long resulut=  db.delete(REPORT_TABLE_NAME, whereClause, whereArgs);
        db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;

        }
    }
    public int getRecordCountReportItem() {
        String countQuery = "SELECT  * FROM " + REPORT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public boolean SaveNextVisitData(AllNextVisitDataBean allnextvisitdatabean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();//10
        values.put(NEXTVISIT_PHOTO_COL, allnextvisitdatabean.getNEXTVISIT_PHOTO_COL());
        values.put(NEXTVISIT_USER_ID_COL, allnextvisitdatabean.getNEXTVISIT_USER_ID_COL());
        values.put(NEXTVISIT_DATE_COL, allnextvisitdatabean.getNEXTVISIT_DATE_COL());
        values.put(NEXTVISIT_TIME_COL, allnextvisitdatabean.getNEXTVISIT_TIME_COL());
        values.put(NEXTVISIT_REMARK_COL, allnextvisitdatabean.getNEXTVISIT_REMARK_COL());
        values.put(NEXTVISIT_LAT_COL, allnextvisitdatabean.getNEXTVISIT_LAT_COL());
        values.put(NEXTVISIT_LOGI_COL, allnextvisitdatabean.getNEXTVISIT_LOGI_COL());
        values.put(NEXTVISIT_VISIT_ID_COL, allnextvisitdatabean.getNEXTVISIT_VISIT_ID_COL());
        values.put(NEXTVISIT_NEXT_DATE_COL, allnextvisitdatabean.getNEXTVISIT_NEXT_DATE_COL());
        values.put(NEXTVISIT_NEXT_TIME_COL, allnextvisitdatabean.getNEXTVISIT_NEXT_TIME_COL());
        values.put(NEXTVISIT_IMEI_COL, allnextvisitdatabean.getNEXTVISIT_IMEI_COL());

        long resulut= db.insert(NEXT_VISIT_TABLE_NAME, null, values);
        db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @SuppressLint("Range")
    public ArrayList<AllNextVisitDataBean> GetAllNextVisitData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AllNextVisitDataBean> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ NEXT_VISIT_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        AllNextVisitDataBean allnextvisitdatabean;
        while (cursor.moveToNext()){
            allnextvisitdatabean = new AllNextVisitDataBean();
            allnextvisitdatabean.setNEXT_VISIT_TABLE_ITEM_ID(cursor.getInt(cursor.getColumnIndex(NEXTVISIT_ID_COL)));//TABLE COLOM ID
            allnextvisitdatabean.setNEXTVISIT_PHOTO_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_PHOTO_COL)));
            allnextvisitdatabean.setNEXTVISIT_USER_ID_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_USER_ID_COL)));
            allnextvisitdatabean.setNEXTVISIT_DATE_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_DATE_COL)));
            allnextvisitdatabean.setNEXTVISIT_NEXT_TIME_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_TIME_COL)));
            allnextvisitdatabean.setNEXTVISIT_REMARK_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_REMARK_COL)));
            allnextvisitdatabean.setNEXTVISIT_LAT_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_LAT_COL)));
            allnextvisitdatabean.setNEXTVISIT_LOGI_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_LOGI_COL)));
            allnextvisitdatabean.setNEXTVISIT_VISIT_ID_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_VISIT_ID_COL)));
            allnextvisitdatabean.setNEXTVISIT_NEXT_DATE_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_NEXT_DATE_COL)));
            allnextvisitdatabean.setNEXTVISIT_TIME_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_NEXT_TIME_COL)));
            allnextvisitdatabean.setNEXTVISIT_IMEI_COL(cursor.getString(cursor.getColumnIndex(NEXTVISIT_IMEI_COL)));

            userList.add(allnextvisitdatabean);
        }
        return  userList;
    }

    public int getRecordCountNextVisit() {
        String countQuery = "SELECT  * FROM " + NEXT_VISIT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public boolean deleteNextVisitItem(AllNextVisitDataBean item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "next_visit_id=?";
        String whereArgs[] = {item.getNEXT_VISIT_TABLE_ITEM_ID()+""};
        long resulut=  db.delete(NEXT_VISIT_TABLE_NAME, whereClause, whereArgs);
        db.close();
        if(resulut==-1)
        {
            return false;
        }
        else
        {
            return true;

        }
    }
    public void deleteCourse(String courseName) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "name=?", new String[]{courseName});
        db.close();
    }


//    public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DATE_COL, courseName);
//        values.put(TIME_COL, courseDuration);
//        values.put(AGETCODE_COL, courseDescription);
//        values.put(CUSTOMER_ID_COL, courseTracks);
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
