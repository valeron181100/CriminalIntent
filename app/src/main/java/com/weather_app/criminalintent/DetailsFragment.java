package com.weather_app.criminalintent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.weather_app.criminalintent.models.Crime;
import com.weather_app.criminalintent.models.CrimeLab;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    public static String CRIME_ID_TAG = "com.weather_app.criminalintent.crimeID";
    private Crime mCrime;
    private EditText mTitleET;
    private Button mDateButton;
    private CheckBox mSolvedCB;


    public static DetailsFragment newInstance(UUID crimeId) {

        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID_TAG, crimeId);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mCrime = CrimeLab.getInstance(getContext()).getCrime((UUID) args.getSerializable(CRIME_ID_TAG));


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_details, container, false);

        mTitleET = v.findViewById(R.id.titleET);
        mDateButton = v.findViewById(R.id.dateButton);
        mSolvedCB = v.findViewById(R.id.solvedCB);


        mDateButton = v.findViewById(R.id.dateButton);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mTitleET.setText(mCrime.getTitle());
        mSolvedCB.setChecked(mCrime.isSolved());

        mTitleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mSolvedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
