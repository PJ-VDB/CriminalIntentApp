package com.example.pieter_jan.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by pieter-jan on 11/15/2016.
 */



public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField; // EditText for the title
    private Button mDateButton; // Button that displays the date
    private CheckBox mSolvedCheckBox; // Checkbox that shows if the crime has been solved

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false); // second parameter is the view's parent;
        // third parameter tells the layout inflater whether to add the inflated view to the view's parent
        // we pass in flase because the view will be added in the activity's code

        // Also wire up widgets here
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
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
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
        String dateString = dateFormat.format(mCrime.getDate());
        mDateButton.setText(dateString);
        mDateButton.setEnabled(false); // the button is not clickable for now; will be changed in Chapter 13

        // the checkbox
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
