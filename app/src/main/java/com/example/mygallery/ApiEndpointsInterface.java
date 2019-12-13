package com.example.mygallery;

import com.example.mygallery.network_domain.NetworkImage;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Retrofit api calls.
 */
public interface ApiEndpointsInterface {

    @GET("get/cftPFNNHsi")
    Call<List<NetworkImage>> getImages();

    @GET
    Call<ResponseBody> getImage(@Url String url);
}
