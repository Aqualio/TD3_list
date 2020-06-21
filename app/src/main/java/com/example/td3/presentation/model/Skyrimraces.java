package com.example.td3.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Skyrimraces implements Parcelable {

    private String Name;
    private String Localisation;
    private String Best_starting_skill;
    private String Unique_power;
    private String Power_description;
    private String Racial_Effect;

    protected Skyrimraces(Parcel in) {
        Name = in.readString();
        Localisation = in.readString();
        Best_starting_skill = in.readString();
        Unique_power = in.readString();
        Power_description = in.readString();
        Racial_Effect = in.readString();
    }

    public static final Creator<Skyrimraces> CREATOR = new Creator<Skyrimraces>() {
        @Override
        public Skyrimraces createFromParcel(Parcel in) {
            return new Skyrimraces(in);
        }

        @Override
        public Skyrimraces[] newArray(int size) {
            return new Skyrimraces[size];
        }
    };

    public String getName() {
        return Name;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public String getBest_starting_skill() {
        return Best_starting_skill;
    }

    public String getUnique_power() {
        return Unique_power;
    }

    public String getPower_description() {
        return Power_description;
    }

    public String getRacial_Effect() {
        return Racial_Effect;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Localisation);
        dest.writeString(Best_starting_skill);
        dest.writeString(Unique_power);
        dest.writeString(Power_description);
        dest.writeString(Racial_Effect);
    }
}
