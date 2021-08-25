package com.anggit97.kmmiblog.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anggit Prayogo on 25,August,2021
 * GitHub : https://github.com/anggit97
 */
public class CreatePostResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public CreatePostResponse() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
