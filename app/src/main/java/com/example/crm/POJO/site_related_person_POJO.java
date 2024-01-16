package com.example.crm.POJO;

public class site_related_person_POJO {

    String person;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getdob() {
        return dob;
    }

    public void setdob(String dob) {
        this.dob = dob;
    }

    public String getOn_sitee() {
        return on_sitee;
    }



    public void setOn_sitee(String on_sitee) {
        this.on_sitee = on_sitee;
    }

    public String getOf_sitee() {
        return of_sitee;
    }

    public void setOf_sitee(String of_sitee) {
        this.of_sitee = of_sitee;
    }

    public String getad_site_person_id() {
        return ad_site_person_id;
    }

    public void setad_site_person_id(String ad_site_person_id) {
        this.ad_site_person_id = ad_site_person_id;
    }

    public String getad_site_person_idname() {
        return ad_site_person_idname;
    }

    public void setad_site_person_idname(String ad_site_person_idname) {
        this.ad_site_person_idname = ad_site_person_idname;
    }



    public site_related_person_POJO( String person, String name, String contact, String id, String on_sitee, String of_sitee, String dob,String ad_site_person_id,String ad_site_person_idname) {
        this.person = person;
        this.name = name;
        this.contact = contact;
        this.id = id;
        this.on_sitee = on_sitee;
        this.of_sitee = of_sitee;
        this.dob = dob;
        this.ad_site_person_id = ad_site_person_id;
        this.ad_site_person_idname = ad_site_person_idname;
    }

    String name;
    String contact;


    String id;
    String on_sitee;
    String of_sitee;
    String dob;
    String ad_site_person_id;
    String ad_site_person_idname;
}
