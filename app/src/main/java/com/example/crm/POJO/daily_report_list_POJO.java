package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

public class daily_report_list_POJO {
    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("op_km")
        @Expose
        private String opKm;
        @SerializedName("cl_km")
        @Expose
        private String clKm;
        @SerializedName("running_km")
        @Expose
        private String runningKm;
        @SerializedName("opening_photo")
        @Expose
        private String openingPhoto;
        @SerializedName("closing_photo")
        @Expose
        private String closingPhoto;
        @SerializedName("next_day_plan")
        @Expose
        private String nextDayPlan;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOpKm() {
            return opKm;
        }

        public void setOpKm(String opKm) {
            this.opKm = opKm;
        }

        public String getClKm() {
            return clKm;
        }

        public void setClKm(String clKm) {
            this.clKm = clKm;
        }

        public String getRunningKm() {
            return runningKm;
        }

        public void setRunningKm(String runningKm) {
            this.runningKm = runningKm;
        }

        public String getOpeningPhoto() {
            return openingPhoto;
        }

        public void setOpeningPhoto(String openingPhoto) {
            this.openingPhoto = openingPhoto;
        }

        public String getClosingPhoto() {
            return closingPhoto;
        }

        public void setClosingPhoto(String closingPhoto) {
            this.closingPhoto = closingPhoto;
        }

        public String getNextDayPlan() {
            return nextDayPlan;
        }

        public void setNextDayPlan(String nextDayPlan) {
            this.nextDayPlan = nextDayPlan;
        }

    }

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
}
