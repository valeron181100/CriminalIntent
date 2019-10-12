package com.weather_app.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class CriminalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            Intent intent = getIntent();
            if(intent == null){
                fragment = new DetailsFragment();
            }
            else{
                fragment = DetailsFragment.newInstance((UUID) intent.getSerializableExtra(DetailsFragment.CRIME_ID_TAG));
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
