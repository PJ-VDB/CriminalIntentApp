package com.example.pieter_jan.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by pieter-jan on 11/15/2016.
 */



public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";

    // A request code for the FragmentManager
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField; // EditText for the title
    private Button mDateButton; // Button that displays the date
    private CheckBox mSolvedCheckBox; // Checkbox that shows if the crime has been solved

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false); // second parameter is the view's parent;
        // third parameter tells the layout inflater whether to add the inflated view to the view's parent
        // we pass in flase because the view will be added in the activity's code

        // Also wire up widgets here
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle()); // Set the title with the existing title of the crime
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Left intentionally blank
            }
        });

        // the date button
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate(mCrime.getDateFormatted());
//        mDateButton.setEnabled(false); // the button is not clickable for now; will be changed in Chapter 13
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                // Tell the FragmentManager that the DatePickerFragment is linked to the CrimeFragment
                // This is needed when passing around data
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // the checkbox
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    // Get the date from the DatePicker and bind it to the crime
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date); // Set the date on the crime
            updateDate(mCrime.getDate().toString());
        }
    }

    // Set the date to the text button
    private void updateDate(String text) {
        mDateButton.setText(text); // Show in on the DateButton
    }


    // Create a bundle with the arguments that are connected to the CrimeFragment;
    // Has to happen after the Fragment gets created but Before it is added to the activity
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment(); // Create a new fragment using the self implemented abstract class SimpleFragment
        fragment.setArguments(args);
        return fragment;
    }

}
