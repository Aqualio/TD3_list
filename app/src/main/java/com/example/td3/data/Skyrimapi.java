package com.example.td3.data;

import com.example.td3.presentation.model.RestSkyrimResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Skyrimapi {

    @GET("Skyrimraceapi.json")
    Call<RestSkyrimResponse> getSkyrimResponse();

}
