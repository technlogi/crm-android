package com.example.crm.POJO;

import android.widget.TextView;

public class detail_site_related_person_pojo {
    String person;
    String name;
    String m_number;

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

    public String getM_number() {
        return m_number;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public detail_site_related_person_pojo(String person, String name, String m_number) {
        this.person = person;
        this.name = name;
        this.m_number = m_number;
    }
}

