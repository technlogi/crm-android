package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class colony_list_POJO {

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

        @SerializedName("colony_id")
        @Expose
        private String colonyId;
        @SerializedName("colony_name")
        @Expose
        private String colonyName;

        public String getColonyId() {
            return colonyId;
        }

        public void setColonyId(String colonyId) {
            this.colonyId = colonyId;
        }

        public String getColonyName() {
            return colonyName;
        }

        public void setColonyName(String colonyName) {
            this.colonyName = colonyName;
        }
    }
}
