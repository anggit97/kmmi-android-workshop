package com.anggit97.kmmiblog.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anggit Prayogo on 25,August,2021
 * GitHub : https://github.com/anggit97
 */
public class BlogServiceGenerator {

    public static final String IP = "192.168.8.100";
    private static final String PORT = "8888";
    private static final String BASE_URL = "http://"+IP+":"+PORT+"/blog-api/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
