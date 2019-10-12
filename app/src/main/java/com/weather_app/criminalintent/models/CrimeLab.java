package com.weather_app.criminalintent.models;

import android.content.Context;
import android.widget.Toast;

import com.weather_app.criminalintent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab instance;
    private ArrayList<Crime> mCrimes;
    private Context mContext;

    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        mContext = context;
        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle(getRandomWord());
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }

    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID uuid){
        for (Crime crime : mCrimes){
            if(crime.getID().equals(uuid)){
                return crime;
            }
        }
        return null;
    }

    public Crime getCrimeByIndex(int pos){
        return mCrimes.get(pos);
    }

    public int getSize(){
        return mCrimes.size();
    }

    public static CrimeLab getInstance(Context context) {
        if(instance == null){
            instance = new CrimeLab(context);
        }
        return instance;
    }

    private String getRandomWord(){
        InputStream fileIS = mContext.getResources().openRawResource(R.raw.verbs);
        StringBuilder fileContent = new StringBuilder();
        String result = "";

        Scanner scanner = new Scanner(fileIS);
        while(scanner.hasNextLine()){
            fileContent.append(scanner.nextLine());
        }

        try {
            JSONObject jsonObject = new JSONObject(fileContent.toString());
            JSONArray array = jsonObject.getJSONArray("data");
            result = array.getString(new Random().nextInt(array.length()));

        } catch (JSONException e) {
            Toast.makeText(mContext, "Json is not correct", Toast.LENGTH_SHORT).show();
        }

        return result;
    }


}
