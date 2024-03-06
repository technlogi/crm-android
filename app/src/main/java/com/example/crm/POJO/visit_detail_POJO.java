package com.example.crm.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class visit_detail_POJO {

    public class Result {

        @SerializedName("visit_date")
        @Expose
        private String visitDate;
        @SerializedName("visit_time")
        @Expose
        private String visitTime;
        @SerializedName("category")
        @Expose
        private String category;

        @SerializedName("contact_mode")
        @Expose
        private String contact_mode;

        @SerializedName("market_agent_code")
        @Expose
        private String marketAgentCode;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_dob")
        @Expose
        private String customerDob;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("mobile_1")
        @Expose
        private String mobile1;
        @SerializedName("mobile_2")
        @Expose
        private String mobile2;
        @SerializedName("mobile_3")
        @Expose
        private String mobile3;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("addresss")
        @Expose
        private String addresss;
        @SerializedName("colony")
        @Expose
        private String colony;
        @SerializedName("colony_id")
        @Expose
        private String colonyId;
        @SerializedName("site_status")
        @Expose
        private String siteStatus;
        @SerializedName("site_width")
        @Expose
        private String siteWidth;
        @SerializedName("site_hight")
        @Expose
        private String siteHight;
        @SerializedName("postion")
        @Expose
        private String postion;
        @SerializedName("postion_list_id")
        @Expose
        private String postion_list_id;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;
        @SerializedName("person")
        @Expose
        private List<Person> person = null;
        @SerializedName("next_visit")
        @Expose
        private List<NextVisit> nextVisit = null;

        public String getVisitDate() {
            return visitDate;
        }



        public void setVisitDate(String visitDate) {
            this.visitDate = visitDate;
        }

        public String getVisitTime() {
            return visitTime;
        }

        public void setVisitTime(String visitTime) {
            this.visitTime = visitTime;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getContact_mode() {
            return contact_mode;
        }

        public void setContact_mode(String contact_mode) {
            this.contact_mode = contact_mode;
        }

        public String getPostion_list_id() {
            return postion_list_id;
        }

        public void setPostion_list_id(String postion_list_id) {
            this.postion_list_id = postion_list_id;
        }

        public String getMarketAgentCode() {
            return marketAgentCode;
        }

        public void setMarketAgentCode(String marketAgentCode) {
            this.marketAgentCode = marketAgentCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerDob() {
            return customerDob;
        }

        public void setCustomerDob(String customerDob) {
            this.customerDob = customerDob;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getMobile1() {
            return mobile1;
        }

        public void setMobile1(String mobile1) {
            this.mobile1 = mobile1;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getMobile3() {
            return mobile3;
        }

        public void setMobile3(String mobile3) {
            this.mobile3 = mobile3;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAddresss() {
            return addresss;
        }

        public void setAddresss(String addresss) {
            this.addresss = addresss;
        }

        public String getColony() {
            return colony;
        }

        public void setColony(String colony) {
            this.colony = colony;
        }

        public String getColonyId() {
            return colonyId;
        }

        public void setColonyId(String colonyId) {
            this.colonyId = colonyId;
        }

        public String getSiteStatus() {
            return siteStatus;
        }

        public void setSiteStatus(String siteStatus) {
            this.siteStatus = siteStatus;
        }

        public String getSiteWidth() {
            return siteWidth;
        }

        public void setSiteWidth(String siteWidth) {
            this.siteWidth = siteWidth;
        }

        public String getSiteHight() {
            return siteHight;
        }

        public void setSiteHight(String siteHight) {
            this.siteHight = siteHight;
        }

        public String getPostion() {
            return postion;
        }

        public void setPostion(String postion) {
            this.postion = postion;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        public List<Person> getPerson() {
            return person;
        }

        public void setPerson(List<Person> person) {
            this.person = person;
        }

        public List<NextVisit> getNextVisit() {
            return nextVisit;
        }

        public void setNextVisit(List<NextVisit> nextVisit) {
            this.nextVisit = nextVisit;
        }

    }

    public class Product {

        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("existing_buy_shop")
        @Expose
        private String existingBuyShop;
        @SerializedName("existing_buy_rate")
        @Expose
        private String existingBuyRate;
        @SerializedName("required_qty")
        @Expose
        private String requiredQty;
        @SerializedName("unit")
        @Expose
        private String unit;
        @SerializedName("unit_id")
        @Expose
        private String unitId;
        @SerializedName("stock")
        @Expose
        private String stock;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getExistingBuyShop() {
            return existingBuyShop;
        }

        public void setExistingBuyShop(String existingBuyShop) {
            this.existingBuyShop = existingBuyShop;
        }

        public String getExistingBuyRate() {
            return existingBuyRate;
        }

        public void setExistingBuyRate(String existingBuyRate) {
            this.existingBuyRate = existingBuyRate;
        }

        public String getRequiredQty() {
            return requiredQty;
        }

        public void setRequiredQty(String requiredQty) {
            this.requiredQty = requiredQty;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

    }

    public class Person {

        @SerializedName("type_of_person")
        @Expose
        private String typeOfPerson;
        @SerializedName("type_of_person_id")
        @Expose
        private String typeOfPersonId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("person_id")
        @Expose
        private String personId;
        @SerializedName("person_dob")
        @Expose
        private String personDob;
        @SerializedName("contact_no")
        @Expose
        private String contactNo;
        @SerializedName("ON_Site")
        @Expose
        private String oNSite;
        @SerializedName("OFF_Site")
        @Expose
        private String oFFSite;

        public String getTypeOfPerson() {
            return typeOfPerson;
        }

        public void setTypeOfPerson(String typeOfPerson) {
            this.typeOfPerson = typeOfPerson;
        }

        public String getTypeOfPersonId() {
            return typeOfPersonId;
        }

        public void setTypeOfPersonId(String typeOfPersonId) {
            this.typeOfPersonId = typeOfPersonId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getPersonDob() {
            return personDob;
        }

        public void setPersonDob(String personDob) {
            this.personDob = personDob;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getONSite() {
            return oNSite;
        }

        public void setONSite(String oNSite) {
            this.oNSite = oNSite;
        }

        public String getOFFSite() {
            return oFFSite;
        }

        public void setOFFSite(String oFFSite) {
            this.oFFSite = oFFSite;
        }

    }

    public class NextVisit {

        @SerializedName("contact_mode")
        @Expose
        private String contact_mode;
        @SerializedName("next_visit_date")
        @Expose
        private String nextVisitDate;
        @SerializedName("next_visit_time")
        @Expose
        private String nextVisitTime;
        @SerializedName("remark")
        @Expose
        private String remark;

        public String getContact_mode() {
            return contact_mode;
        }

        public void setContact_mode(String contact_mode) {
            this.contact_mode = contact_mode;
        }

        public String getNextVisitDate() {
            return nextVisitDate;
        }

        public void setNextVisitDate(String nextVisitDate) {
            this.nextVisitDate = nextVisitDate;
        }

        public String getNextVisitTime() {
            return nextVisitTime;
        }

        public void setNextVisitTime(String nextVisitTime) {
            this.nextVisitTime = nextVisitTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }



}
