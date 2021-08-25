package com.anggit97.kmmiblog.api;

import com.anggit97.kmmiblog.api.model.PostList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Anggit Prayogo on 25,August,2021
 * GitHub : https://github.com/anggit97
 */
public interface BlogClient {

    @GET("posts.php?function=get_posts")
    Call<PostList> getListPost();
}
