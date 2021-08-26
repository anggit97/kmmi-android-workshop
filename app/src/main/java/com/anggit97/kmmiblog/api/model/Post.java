package com.anggit97.kmmiblog.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.anggit97.kmmiblog.api.BlogServiceGenerator;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anggit Prayogo on 20,August,2021
 * GitHub : https://github.com/anggit97
 */
public class Post implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("image_path")
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl.replace("localhost", BlogServiceGenerator.IP);
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(thumbnailUrl);
    }

    protected Post(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.body = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
