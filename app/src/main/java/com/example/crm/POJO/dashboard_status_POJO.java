package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dashboard_status_POJO {


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

        //class 2
        public class Result {

            @SerializedName("total_visit")
            @Expose
            private String totalVisit;
            @SerializedName("today_visit")
            @Expose
            private String todayVisit;
            @SerializedName("pending_visit")
            @Expose
            private String pendingVisit;

            public String getTotalVisit() {
                return totalVisit;
            }

            public void setTotalVisit(String totalVisit) {
                this.totalVisit = totalVisit;
            }

            public String getTodayVisit() {
                return todayVisit;
            }

            public void setTodayVisit(String todayVisit) {
                this.todayVisit = todayVisit;
            }

            public String getPendingVisit() {
                return pendingVisit;
            }

            public void setPendingVisit(String pendingVisit) {
                this.pendingVisit = pendingVisit;
            }
        }
    }
