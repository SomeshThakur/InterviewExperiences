package com.someshthakur.interviewexperiences;

/**
 * Created by Somesh Thakur on 24-08-2017.
 */

public class Experience {
    private String title, info, company;

    public Experience() {
    }

    public Experience(String company, String title, String info) {
        this.company = company;
        this.title = title;
        this.info = info;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }
}
