package com.example.td3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://raw.githubusercontent.com/Aqualio/TD3_list/master/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeapiCall();
        //showList();

    }

    private void showList(List<Skyrimraces> skyrimraces) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(skyrimraces);
        recyclerView.setAdapter(mAdapter);

    }

    private void makeapiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Skyrimapi skyrimapi = retrofit.create(Skyrimapi.class);

        Call<RestSkyrimResponse> call = skyrimapi.getSkyrimResponse();
        call.enqueue(new Callback<RestSkyrimResponse>() {
            @Override
            public void onResponse(Call<RestSkyrimResponse> call, Response<RestSkyrimResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Skyrimraces> skyrimraces = response.body().getListe();
                    showList(skyrimraces);
                    Toast.makeText(getApplicationContext(), "API dab", Toast.LENGTH_SHORT).show();

                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestSkyrimResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
