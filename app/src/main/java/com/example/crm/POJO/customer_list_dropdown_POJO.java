package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class customer_list_dropdown_POJO {


    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;

        @SerializedName("mobile_1")
        @Expose
        private String customerphone1;
        @SerializedName("mobile_2")
        @Expose
        private String customerphone2;
        @SerializedName("mobile_3")
        @Expose
        private String customerphone3;
        @SerializedName("customer_dob")
        @Expose
        private String customerDob;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("colony_id")
        @Expose
        private String colonyId;
        @SerializedName("colony_name")
        @Expose
        private String colony;



        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }


        public String getCustomerphone1() {
            return customerphone1;
        }

        public void setCustomerphone1(String customerphone1) {
            this.customerphone1 = customerphone1;
        }

        public String getCustomerphone2() {
            return customerphone2;
        }

        public void setCustomerphone2(String customerphone2) {
            this.customerphone2 = customerphone2;
        }

        public String getCustomerphone3() {
            return customerphone3;
        }

        public void setCustomerphone3(String customerphone3) {
            this.customerphone3 = customerphone3;
        }

        public String getCustomerDob() {
            return customerDob;
        }

        public void setCustomerDob(String customerDob) {
            this.customerDob = customerDob;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String addresss) {
            this.address = addresss;
        }

        public String getColonyId() {
            return colonyId;
        }

        public void setColonyId(String colonyId) {
            this.colonyId = colonyId;
        }

        public String getColony() {
            return colony;
        }

        public void setColony(String colony) {
            this.colony = colony;
        }
    }
}
