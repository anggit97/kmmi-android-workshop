package com.anggit97.kmmiblog.ui.home;

/**
 * Created by Anggit Prayogo on 20,August,2021
 * GitHub : https://github.com/anggit97
 */
public class Post {

    private int id;
    private String title;
    private String date;
    private String thumbnailUrl;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
