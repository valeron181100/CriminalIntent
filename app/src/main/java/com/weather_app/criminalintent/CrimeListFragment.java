package com.weather_app.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather_app.criminalintent.androidStuff.CrimeAdapter;
import com.weather_app.criminalintent.androidStuff.CrimeHolder;
import com.weather_app.criminalintent.models.Crime;


public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRV = v.findViewById(R.id.crimeListRV);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CrimeAdapter(getActivity());
        mCrimeRV.setLayoutManager(mLayoutManager);
        mCrimeRV.setAdapter(mAdapter);
        return v;

    }

    public static Intent createDetailsIntent(Context context, Crime crime){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(DetailsFragment.CRIME_ID_TAG, crime.getID());
        return intent;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        if(mAdapter == null){
            mAdapter = new CrimeAdapter(getActivity());
            mCrimeRV.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
    }
}
