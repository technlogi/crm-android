package com.example.crm.POJO;

import android.widget.TextView;

public class last_remark_POJO {

    public String getNext_date() {
        return next_date;
    }

    public void setNext_date(String next_date) {
        this.next_date = next_date;
    }

    public String getNext_time() {
        return next_time;
    }

    public void setNext_time(String next_time) {
        this.next_time = next_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContact_mode() {
        return contact_mode;
    }

    public void setContact_mode(String contact_mode) {
        this.contact_mode = contact_mode;
    }

    public void setAgent_code(String agent_code) {
        this.agent_code = agent_code;
    }

    String next_date;
    String next_time;

    String contact_mode;

    public last_remark_POJO( String contact_mode, String next_date, String next_time, String remark) {
        this.contact_mode = contact_mode;
        this.next_date = next_date;
        this.next_time = next_time;
        this.remark = remark;
    }

    public String getAgent_code() {
        return agent_code;
    }

    String remark;
    String agent_code;
}
