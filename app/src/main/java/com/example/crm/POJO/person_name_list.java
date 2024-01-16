package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class person_name_list {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<person_name_list.Result> result = null;

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

    public List<person_name_list.Result> getResult() {
        return result;
    }

    public void setResult(List<person_name_list.Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("person_name_id")
        @Expose
        private String personId;
        @SerializedName("person_name_name")
        @Expose
        private String personName;

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String customerName) {
            this.personName = personName;
        }
    }
}
