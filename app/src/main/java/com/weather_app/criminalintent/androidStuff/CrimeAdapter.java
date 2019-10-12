package com.weather_app.criminalintent.androidStuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather_app.criminalintent.R;
import com.weather_app.criminalintent.models.Crime;
import com.weather_app.criminalintent.models.CrimeLab;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
    private CrimeLab mCrimeLab;
    private Context mContext;


    public CrimeAdapter(Context context){
        mContext = context;
        mCrimeLab = CrimeLab.getInstance(context);
    }

    @NonNull
    @Override
    public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_crime, parent, false);
        CrimeHolder holder = new CrimeHolder(mContext,v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
        Crime crime = mCrimeLab.getCrimeByIndex(position);
        holder.bind(crime);
    }

    @Override
    public int getItemCount() {
        return mCrimeLab.getSize();
    }


}
