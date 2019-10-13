package com.weather_app.criminalintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.weather_app.criminalintent.androidStuff.ZoomOutPageTransformer;
import com.weather_app.criminalintent.models.Crime;
import com.weather_app.criminalintent.models.CrimeLab;

import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private Crime mCrime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        Intent intent = getIntent();
        if(intent != null){
            mCrime = CrimeLab.getInstance(this).getCrime((UUID)intent.getSerializableExtra(DetailsFragment.CRIME_ID_TAG));
        }

        mViewPager = findViewById(R.id.crimePager);

        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new SrollCrimeFragmentsPagerAdapter(this, fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        mViewPager.setCurrentItem(CrimeLab.getInstance(this).getCrimes().indexOf(mCrime));

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }


    public void setCurrentPagerItem(int index){
        mViewPager.setCurrentItem(index);
    }

    private class SrollCrimeFragmentsPagerAdapter extends FragmentStatePagerAdapter{
        private Context mContext;

        public SrollCrimeFragmentsPagerAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            mContext = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Crime crime = CrimeLab.getInstance(mContext).getCrimes().get(position);
            return DetailsFragment.newInstance(crime.getID());
        }

        @Override
        public int getCount() {
            return CrimeLab.getInstance(mContext).getSize();
        }
    }

}
