package com.example.td3;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        if(getIntent().hasExtra("list_object")) {
            Skyrimraces skyrimraces = getIntent().getParcelableExtra("list_object");
            showLocalisation(skyrimraces);
        }
    }



    private void showLocalisation(Skyrimraces race){
        String loc = getString(R.string.localisation, race.getLocalisation());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.localisation);
        textview.setText(loc);
    }

}
