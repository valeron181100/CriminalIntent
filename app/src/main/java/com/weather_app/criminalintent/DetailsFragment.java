package com.weather_app.criminalintent;

import android.content.Intent;
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

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DetailsFragment extends Fragment {

    public static String CRIME_ID_TAG = "com.weather_app.criminalintent.crimeID";
    public static final String DIALOG_DATE = "Dialog Date";
    public static final int DATE_REQUEST_CODE = 0;
    private Crime mCrime;
    private EditText mTitleET;
    private Button mDateButton;
    private CheckBox mSolvedCB;
    private Button mLastButton;
    private Button mBeginningButton;



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
        mLastButton = v.findViewById(R.id.lastButton);
        mDateButton = v.findViewById(R.id.dateButton);
        mBeginningButton = v.findViewById(R.id.beginningButton);
        mDateButton.setText(mCrime.getDate().toString());

        final FragmentManager fm = getFragmentManager();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(DetailsFragment.this, DATE_REQUEST_CODE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTitleET.setText(mCrime.getTitle());
        mSolvedCB.setChecked(mCrime.isSolved());

        mTitleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                CrimeListFragment.addCrimeChangedIndex(CrimeLab.getInstance(getContext()).getCrimes().indexOf(mCrime));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mSolvedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                CrimeListFragment.addCrimeChangedIndex(CrimeLab.getInstance(getContext()).getCrimes().indexOf(mCrime));
            }
        });


        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CrimePagerActivity) Objects.requireNonNull(getActivity())).setCurrentPagerItem(CrimeLab.getInstance(getContext()).getSize() - 1);
            }
        });

        mBeginningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CrimePagerActivity) Objects.requireNonNull(getActivity())).setCurrentPagerItem(0);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == DATE_REQUEST_CODE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.DATE_CRIME_DETAILS_TAG);
            mDateButton.setText(date.toString());
            mCrime.setDate(date);
            CrimeListFragment.addCrimeChangedIndex(CrimeLab.getInstance(getContext()).getCrimes().indexOf(mCrime));
        }
    }
}
