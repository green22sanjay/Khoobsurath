package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 19-Apr-15.
 */
public class FeedItem {
    private int id;
    private String name, propic, posttile, posthashtag, posturl, textdec;


    public FeedItem() {
    }

    public FeedItem(int id, String name, String propic, String posttile, String posthashtag, String posturl, String textdec) {
        super();
        this.id = id;
        this.name = name;
        this.propic = propic;
        this.posttile = posttile;
        this.posthashtag = posthashtag;
        this.posturl = posturl;
        this.textdec =textdec;
    }

    public int getId() {
        return id;
    }
    public String getTextdec() {
        return textdec;
    }

    public void setTextdec(String textdec) {
        this.textdec = textdec;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getPosttile() {
        return posttile;
    }

    public void setPosttile(String posttile) {
        this.posttile = posttile;
    }

    public String getPosthashtag() {
        return posthashtag;
    }

    public void setPosthashtag(String posthashtag) {
        this.posthashtag = posthashtag;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }
}