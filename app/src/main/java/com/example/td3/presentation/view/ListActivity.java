package com.example.td3.presentation.view;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.td3.R;
import com.example.td3.presentation.model.Skyrimraces;

public class ListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        if(getIntent().hasExtra("list_object")) {
            Skyrimraces skyrimraces = getIntent().getParcelableExtra("list_object");
            showLocalisation(skyrimraces);
            showStartingSkill(skyrimraces);
            showPowerName(skyrimraces);
            showPowerDescription(skyrimraces);
            showRacialEffect(skyrimraces);
        }
    }



    private void showLocalisation(Skyrimraces race){
        String loc = getString(R.string.localisation, race.getLocalisation());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.localisation);
        textview.setText(loc);
    }

    private void showStartingSkill(Skyrimraces race){
        String loc = getString(R.string.start_skill, race.getBest_starting_skill());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.start_skill);
        textview.setText(loc);
    }

    private void showPowerName(Skyrimraces race){
        String loc = getString(R.string.power_name, race.getUnique_power());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.power_name);
        textview.setText(loc);
    }

    private void showPowerDescription(Skyrimraces race){
        String loc = getString(R.string.power_description, race.getPower_description());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.power_description);
        textview.setText(loc);
    }
    private void showRacialEffect(Skyrimraces race){
        String loc = getString(R.string.racial_effect, race.getRacial_Effect());
        SpannableString bruh = new SpannableString(loc);
        bruh.setSpan(new RelativeSizeSpan(2f),0,12,0);
        TextView textview = (TextView)findViewById(R.id.racial_effect);
        textview.setText(loc);
    }
}
