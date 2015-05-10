package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 09-Apr-15.
 */
public class LoginDatarecycle {

    private String text;
    private int imgurl;

    public LoginDatarecycle(String text, int imgurl) {
        this.text = text;
        this.imgurl = imgurl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }
}
