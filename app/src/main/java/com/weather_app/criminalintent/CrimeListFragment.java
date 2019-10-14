package com.weather_app.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.weather_app.criminalintent.androidStuff.CrimeAdapter;
import com.weather_app.criminalintent.androidStuff.CrimeHolder;
import com.weather_app.criminalintent.models.Crime;
import com.weather_app.criminalintent.models.CrimeLab;

import java.util.ArrayList;


public class CrimeListFragment extends Fragment {
    private static final String IS_ACTIONBAR_SUBTITLE_SHOWN = "com.weather_app.criminalintent.isActionBarSubtitleShown";
    private static final String MENU_TAG = "com.weather_app.criminalintent.actionBarMenu";
    private RecyclerView mCrimeRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<Integer> mCrimeIndicesChanged;
    private boolean mIsCrimesAmountShown = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mCrimeIndicesChanged = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        if(savedInstanceState != null){
            mIsCrimesAmountShown = savedInstanceState.getBoolean(IS_ACTIONBAR_SUBTITLE_SHOWN);
        }
        mCrimeRV = v.findViewById(R.id.crimeListRV);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CrimeAdapter(getActivity());
        mCrimeRV.setLayoutManager(mLayoutManager);
        mCrimeRV.setAdapter(mAdapter);
        return v;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_crime_options, menu);
        MenuItem item = menu.findItem(R.id.item_show_amount);
        if(mIsCrimesAmountShown){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(""+CrimeLab.getInstance(getActivity()).getSize());
            item.setIcon(R.drawable.ic_action_visible_off);
            item.setTitle(R.string.hide_crimes_amount);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        CrimeLab lab = CrimeLab.getInstance(getActivity());
        switch(item.getItemId()){
            case R.id.item_new_crime:
                Crime crime = new Crime();
                lab.add(crime);

                mCrimeIndicesChanged.add(lab.getSize() - 1);

                Intent intent = CrimeListFragment.createDetailsIntent(getActivity(), crime);
                startActivity(intent);
                return true;
            case R.id.item_show_amount:
                updateActionBarSubtitle(lab, item);
                return true;
            default:  return super.onOptionsItemSelected(item);
        }
    }

    private void updateActionBarSubtitle(CrimeLab lab, MenuItem item){
        if(!mIsCrimesAmountShown){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(""+lab.getSize());
            if(item != null) {
                item.setIcon(R.drawable.ic_action_visible_off);
                item.setTitle(R.string.hide_crimes_amount);
            }
        }
        else{
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");
            if(item != null) {
                item.setIcon(R.drawable.ic_action_visible);
                item.setTitle(R.string.show_crimes_amount);
            }
        }
        mIsCrimesAmountShown = !mIsCrimesAmountShown;
    }

    public static Intent createDetailsIntent(Context context, Crime crime){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(DetailsFragment.CRIME_ID_TAG, crime.getID());
        return intent;
    }

    public static void addCrimeChangedIndex(int i){
        mCrimeIndicesChanged.add(i);
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
            for (int i = 0; i < mCrimeIndicesChanged.size() ; i++) {
                mAdapter.notifyItemChanged(mCrimeIndicesChanged.get(i));
            }
            mCrimeIndicesChanged.clear();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ACTIONBAR_SUBTITLE_SHOWN, mIsCrimesAmountShown);

    }
}
