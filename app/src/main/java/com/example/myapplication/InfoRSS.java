package com.example.myapplication;

public class InfoRSS {
    private String Title;
    private String Description;
    public String Link;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String toString(){
        return   Title + " \n " ;
    }

}
