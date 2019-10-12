package com.weather_app.criminalintent.androidStuff;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weather_app.criminalintent.CrimeListFragment;
import com.weather_app.criminalintent.R;
import com.weather_app.criminalintent.models.Crime;
import com.weather_app.criminalintent.models.CrimeLab;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mCrimeNumTV;
    private TextView mCrimeTitleTV;
    private TextView mCrimeDateTV;
    private Context mContext;
    private Crime mCrime;

    public CrimeHolder(Context context, @NonNull View itemView) {
        super(itemView);
        mContext = context;
        itemView.setOnClickListener(this);
        mCrimeTitleTV = itemView.findViewById(R.id.crimeTitleTV);
        mCrimeNumTV = itemView.findViewById(R.id.crimeNumTV);
        mCrimeDateTV = itemView.findViewById(R.id.crimeDateTV);
    }

    public void bind(Crime crime){
        mCrime = crime;
        mCrimeTitleTV.setText(crime.getTitle());
        mCrimeDateTV.setText(crime.getDate().toString());
        String crimeNum = "Crime #" + CrimeLab.getInstance(mContext).getCrimes().indexOf(crime);
        mCrimeNumTV.setText(crimeNum);
    }

    @Override
    public void onClick(View v) {
        Intent intent = CrimeListFragment.createDetailsIntent(mContext, mCrime);
        mContext.startActivity(intent);
    }
}
