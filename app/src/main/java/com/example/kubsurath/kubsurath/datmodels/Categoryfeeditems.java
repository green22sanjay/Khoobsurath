package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 15-Apr-15.
 */
public class Categoryfeeditems {

    private String catid,catname,catimg;

    public Categoryfeeditems(){

    }

    public Categoryfeeditems(String catid, String catname, String catimg) {
        super();
        this.catid = catid;
        this.catname = catname;
        this.catimg = catimg;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimg() {
        return catimg;
    }

    public void setCatimg(String catimg) {
        this.catimg = catimg;
    }
}
