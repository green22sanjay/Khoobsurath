package com.example.kubsurath.kubsurath.datmodels;

/**
 * Created by dinesh on 21-Apr-15.
 */
public class Person {
    String PostTitle,PostHashTAg,PostDescription;

    public Person(){

    }
    public Person(String postTitle, String postHashTAg, String postDescription) {
        PostTitle = postTitle;
        PostHashTAg = postHashTAg;
        PostDescription = postDescription;
    }

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }

    public String getPostHashTAg() {
        return PostHashTAg;
    }

    public void setPostHashTAg(String postHashTAg) {
        PostHashTAg = postHashTAg;
    }

    public String getPostDescription() {
        return PostDescription;
    }

    public void setPostDescription(String postDescription) {
        PostDescription = postDescription;
    }
}
