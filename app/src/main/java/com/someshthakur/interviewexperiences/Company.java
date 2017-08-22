package com.someshthakur.interviewexperiences;

/**
 * Created by Somesh Thakur on 21-08-2017.
 */

public class Company {
    private String title;
    int image;

    public Company() {
    }

    public Company(String title, int image) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
