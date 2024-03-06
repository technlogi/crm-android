package com.example.crm.POJO;

import android.widget.TextView;

public class add_product_POJO {

    String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public add_product_POJO(String pname, String brand, String vendor, String rate, String qty,String unit,String stock,String ad_pro_id,String ad_brand_id,String ad_unit_id) {
        this.pname = pname;
        this.brand = brand;
        this.vendor = vendor;
        this.rate = rate;
        this.qty = qty;
        this.unit = unit;
        this.stock = stock;
        this.ad_pro_id = ad_pro_id;
        this.ad_brand_id =ad_brand_id;
        this.ad_unit_id = ad_unit_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getad_pro_id() {
        return ad_pro_id;
    }

    public void setad_pro_id(String ad_pro_id) {
        this.ad_pro_id = ad_pro_id;
    }

    public String getad_brand_id() {
        return ad_brand_id;
    }

    public void setad_brand_id(String ad_brand_id) {
        this.ad_brand_id = ad_brand_id;
    }

    public String getad_unit_id() {
        return ad_unit_id;
    }

    public void setad_unit_id(String ad_unit_id) {
        this.ad_unit_id = ad_unit_id;
    }

    String brand;
    String vendor;
    String ad_pro_id;
    String ad_brand_id;
    String ad_unit_id;

    public add_product_POJO(String unit, String stock) {
        this.unit = unit;
        this.stock = stock;
    }

    String rate;
    String qty;
    String unit;
    String stock;
}
