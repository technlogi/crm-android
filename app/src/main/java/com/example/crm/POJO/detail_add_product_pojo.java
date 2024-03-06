package com.example.crm.POJO;

import android.widget.TextView;

public class detail_add_product_pojo {

    String p_name;

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_brand() {
        return p_brand;
    }

    public void setP_brand(String p_brand) {
        this.p_brand = p_brand;
    }

    public String getExisting_buy_shop() {
        return existing_buy_shop;
    }

    public void setExisting_buy_shop(String existing_buy_shop) {
        this.existing_buy_shop = existing_buy_shop;
    }

    public String getExisting_buy_rate() {
        return existing_buy_rate;
    }

    public void setExisting_buy_rate(String existing_buy_rate) {
        this.existing_buy_rate = existing_buy_rate;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public detail_add_product_pojo(String p_name, String p_brand, String existing_buy_shop, String existing_buy_rate, String qnt) {
        this.p_name = p_name;
        this.p_brand = p_brand;
        this.existing_buy_shop = existing_buy_shop;
        this.existing_buy_rate = existing_buy_rate;
        this.qnt = qnt;
    }

    String p_brand;
    String existing_buy_shop;
    String existing_buy_rate;
    String qnt;
}
