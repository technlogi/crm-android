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
    }
}
