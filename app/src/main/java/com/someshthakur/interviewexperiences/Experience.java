package com.someshthakur.interviewexperiences;

/**
 * Created by Somesh Thakur on 24-08-2017.
 */

public class Experience {
    private String expText;
    private int expCountNum;
    private int image;

    public Experience() {
    }

    public Experience(String expText, int expCountNum, int image) {
        this.expText = expText;
        this.image = image;
        this.expCountNum = expCountNum;
    }

    public int getExpCountNum() {
        return expCountNum;
    }

    public void setExpCountNum(int expCountNum) {
        this.expCountNum = expCountNum;
    }

    public String getExpText() {
        return expText;
    }

    public void setExpText(String expText) {
        this.expText = expText;
    }

    public int getImage() {
        return image;
    }
}
