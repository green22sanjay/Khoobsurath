package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 11-Apr-15.
 */
public class LoginRecyclerDataItems {

    private String ProPicImg,ProName,ProEmail;

    public LoginRecyclerDataItems(){

    }

    public LoginRecyclerDataItems( String proPicImg, String proName, String proEmail) {
        super();

        this.ProPicImg = proPicImg;
        this.ProName = proName;
        this.ProEmail = proEmail;
    }



    public String getProPicImg() {
        return ProPicImg;
    }

    public void setProPicImg(String proPicImg) {
        this.ProPicImg = proPicImg;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        this.ProName = proName;
    }

    public String getProEmail() {
        return ProEmail;
    }

    public void setProEmail(String proEmail) {
        this.ProEmail = proEmail;
    }
}
