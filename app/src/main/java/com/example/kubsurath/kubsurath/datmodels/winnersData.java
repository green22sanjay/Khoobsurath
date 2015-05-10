package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 24-Apr-15.
 */
public class winnersData {
    private String propic,usrname,usremail;

    public winnersData(){}


    public winnersData(String propic, String usrname, String usremail) {
        this.propic = propic;
        this.usrname = usrname;
        this.usremail = usremail;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getUsremail() {
        return usremail;
    }

    public void setUsremail(String usremail) {
        this.usremail = usremail;
    }
}
