package com.example.td3.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.td3.Constants;
import com.example.td3.R;
import com.example.td3.data.Skyrimapi;
import com.example.td3.presentation.model.RestSkyrimResponse;
import com.example.td3.presentation.model.Skyrimraces;
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

public class MainActivity extends AppCompatActivity implements ListAdapter.OnListListener {

    private static final String BASE_URL = "https://raw.githubusercontent.com/Aqualio/TD3_list/master/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private ArrayList<Skyrimraces> arrayskyrimraces = new ArrayList<Skyrimraces>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("Project_mobile_prog", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

       // makeapiCall();

        List<Skyrimraces> skyrimraces = getDataFromCache();
        if(skyrimraces != null){
            showList(skyrimraces);
        }else {
            makeapiCall();
        }
        //showList();*/
        arrayskyrimraces = getArrayDataFromCache();
       /* if(arrayskyrimraces == null){
            makeapiCall();
        }*/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates action menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void makeapiCall(){

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
                    saveList(skyrimraces);
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
        mAdapter = new ListAdapter(skyrimraces, this);
        recyclerView.setAdapter(mAdapter);

    }


    private void saveList(List<Skyrimraces> skyrimraces) {
        String jsonString = gson.toJson(skyrimraces);
        sharedPreferences
                .edit()
               // .putInt("cle_integer", 3)
                .putString(Constants.KEY_SKYRIM_LIST, jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListClick(int position) {
        //Log.d(TAG, "onListClick: clicked.");
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list_object", arrayskyrimraces.get(position));
        startActivity(intent);
    }
}
