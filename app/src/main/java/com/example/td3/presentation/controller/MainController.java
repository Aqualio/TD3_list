package com.example.td3.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.Toast;

import com.example.td3.Constants;
import com.example.td3.Injection;
import com.example.td3.R;
import com.example.td3.data.Skyrimapi;
import com.example.td3.presentation.model.RestSkyrimResponse;
import com.example.td3.presentation.model.Skyrimraces;
import com.example.td3.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;

    }

    public void onStart(){

        // makeapiCall();

        List<Skyrimraces> skyrimraces = getDataFromCache();
        if(skyrimraces != null){
            view.showList(skyrimraces);
        }else {
            makeapiCall();
        }
        //*/
        view.arrayskyrimraces = getArrayDataFromCache();



    }

    private List<Skyrimraces> getDataFromCache() {

        String jsonSkyrim = sharedPreferences.getString("jsonSkyrimList",null);

        if(jsonSkyrim == null) {return null;}
        else {
            Type listType = new TypeToken<List<Skyrimraces>>(){}.getType();
            return gson.fromJson(jsonSkyrim, listType);
        }
    }

    private ArrayList<Skyrimraces> getArrayDataFromCache(){
        String jsonSkyrim = sharedPreferences.getString("jsonSkyrimList", null);

        if(jsonSkyrim == null) {return null;}
        else {
            Type listType = new TypeToken<ArrayList<Skyrimraces>>() {}.getType();
            return gson.fromJson(jsonSkyrim, listType);
        }

    }
    private void makeapiCall(){

        Call<RestSkyrimResponse> call = Injection.getSkyrimapi().getSkyrimResponse();
        call.enqueue(new Callback<RestSkyrimResponse>() {
            @Override
            public void onResponse(Call<RestSkyrimResponse> call, Response<RestSkyrimResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Skyrimraces> skyrimraces = response.body().getListe();
                    saveList(skyrimraces);
                    view.showList(skyrimraces);
                    Toast.makeText(view.getApplicationContext(), "API dab", Toast.LENGTH_SHORT).show();

                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestSkyrimResponse> call, Throwable t) {
                view.showError();
            }
        });

    }

    private void saveList(List<Skyrimraces> skyrimraces) {
        String jsonString = gson.toJson(skyrimraces);
        sharedPreferences
                .edit()
                // .putInt("cle_integer", 3)
                .putString(Constants.KEY_SKYRIM_LIST, jsonString)
                .apply();
        Toast.makeText(view.getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }


}
