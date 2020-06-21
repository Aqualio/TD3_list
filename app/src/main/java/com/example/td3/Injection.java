package com.example.td3;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.td3.data.Skyrimapi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injection {

    private static Gson gsonInstance;
    private static Skyrimapi skyrimapiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static Skyrimapi getSkyrimapi(){
        if(skyrimapiInstance == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            skyrimapiInstance = retrofit.create(Skyrimapi.class);

        }
        return skyrimapiInstance;

    }
    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance == null) {
            sharedPreferencesInstance = context.getSharedPreferences("Project_mobile_prog", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
