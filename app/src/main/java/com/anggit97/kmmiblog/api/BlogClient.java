package com.anggit97.kmmiblog.api;

import com.anggit97.kmmiblog.api.model.CreatePostRequest;
import com.anggit97.kmmiblog.api.model.CreatePostResponse;
import com.anggit97.kmmiblog.api.model.DeletePostResponse;
import com.anggit97.kmmiblog.api.model.EditPostRequest;
import com.anggit97.kmmiblog.api.model.EditPostResponse;
import com.anggit97.kmmiblog.api.model.PostList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Anggit Prayogo on 25,August,2021
 * GitHub : https://github.com/anggit97
 */
public interface BlogClient {

    @GET("posts.php?function=get_posts")
    Call<PostList> getListPost();

    @POST("posts.php?function=insert_posts")
    Call<CreatePostResponse> createPostRequest(@Body CreatePostRequest request);

    @DELETE("posts.php?function=delete_posts")
    Call<DeletePostResponse> deletePost(@Query("id") String id);

    @PATCH("posts.php?function=update_posts")
    Call<EditPostResponse> editPost(@Body EditPostRequest request, @Query("id") String id);
}
