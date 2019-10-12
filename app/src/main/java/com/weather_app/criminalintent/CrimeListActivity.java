package com.weather_app.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.weather_app.criminalintent.models.CrimeLab;

public class CrimeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);

        CrimeLab.getInstance(this);

        FragmentManager fm = getSupportFragmentManager();

        Fragment crimeListFragment = fm.findFragmentById(R.id.crimeListFrame);

        if(crimeListFragment == null){
            crimeListFragment = new CrimeListFragment();
            fm.beginTransaction().add(R.id.crimeListFrame, crimeListFragment).commit();
        }
    }
}
