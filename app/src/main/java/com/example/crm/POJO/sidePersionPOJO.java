package com.example.crm.POJO;

public class sidePersionPOJO {

    String SITE_PERSON;
    String SITE_PERSON_ID_NAME;
    String SITE_CONTACT;
    String CUSTOMER_DOB;
    String SITE_ON_SITE;
    String SITE_OFF_SITE;
    String DATE_PAGE2;
    String TIME_PAGE2;
    String REMARK;
    public sidePersionPOJO(String pname, String SITE_PERSON_ID_NAME, String SITE_CONTACT, String CUSTOMER_DOB, String SITE_ON_SITE,
                           String SITE_OFF_SITE,String DATE_PAGE2,String TIME_PAGE2,String REMARK) {
        this.SITE_PERSON = pname;
        this.SITE_PERSON_ID_NAME = SITE_PERSON_ID_NAME;
        this.SITE_CONTACT = SITE_CONTACT;
        this.CUSTOMER_DOB = CUSTOMER_DOB;
        this.SITE_ON_SITE = SITE_ON_SITE;
        this.SITE_OFF_SITE = SITE_OFF_SITE;
        this.DATE_PAGE2 = DATE_PAGE2;
        this.TIME_PAGE2 = TIME_PAGE2;
        this.REMARK =REMARK;

    }
    public String getSITE_PERSON() {
        return SITE_PERSON;
    }

    public void setSITE_PERSON(String SITE_PERSON) {
        this.SITE_PERSON = SITE_PERSON;
    }

    public String getSITE_PERSON_ID_NAME() {
        return SITE_PERSON_ID_NAME;
    }

    public void setSITE_PERSON_ID_NAME(String SITE_PERSON_ID_NAME) {
        this.SITE_PERSON_ID_NAME = SITE_PERSON_ID_NAME;
    }

    public String getSITE_CONTACT() {
        return SITE_CONTACT;
    }

    public void setSITE_CONTACT(String SITE_CONTACT) {
        this.SITE_CONTACT = SITE_CONTACT;
    }

    public String getCUSTOMER_DOB() {
        return CUSTOMER_DOB;
    }

    public void setCUSTOMER_DOB(String CUSTOMER_DOB) {
        this.CUSTOMER_DOB = CUSTOMER_DOB;
    }

    public String getSITE_ON_SITE() {
        return SITE_ON_SITE;
    }

    public void setSITE_ON_SITE(String SITE_ON_SITE) {
        this.SITE_ON_SITE = SITE_ON_SITE;
    }

    public String getSITE_OFF_SITE() {
        return SITE_OFF_SITE;
    }

    public void setSITE_OFF_SITE(String SITE_OFF_SITE) {
        this.SITE_OFF_SITE = SITE_OFF_SITE;
    }

    public String getDATE_PAGE2() {
        return DATE_PAGE2;
    }

    public void setDATE_PAGE2(String DATE_PAGE2) {
        this.DATE_PAGE2 = DATE_PAGE2;
    }

    public String getTIME_PAGE2() {
        return TIME_PAGE2;
    }

    public void setTIME_PAGE2(String TIME_PAGE2) {
        this.TIME_PAGE2 = TIME_PAGE2;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
