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
import com.example.td3.presentation.controller.MainController;
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


    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ArrayList<Skyrimraces> arrayskyrimraces = new ArrayList<Skyrimraces>();

    private MainController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        controller = new MainController(
                this,
                new GsonBuilder()
                        .setLenient()
                        .create(),

                getSharedPreferences("Project_mobile_prog", Context.MODE_PRIVATE)
        );
        controller.onStart();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates action menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    public void showList(List<Skyrimraces> skyrimraces) {

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




    public void showError() {
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
