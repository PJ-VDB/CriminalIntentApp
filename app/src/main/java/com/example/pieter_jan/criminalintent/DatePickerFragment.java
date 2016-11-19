package com.example.pieter_jan.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pieter-jan on 11/19/2016.
 */

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.example.pieter_jan.criminalintent.date";
    private Calendar mCalendar;

    private DatePicker mDatePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the date argument from the crime object, passed to the instance in CrimeFragment
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        // Create a calendar and initialize it with the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);

        // Get the information in int's from the calendar object
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Inflate the datepicker view
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        // Initialize the DatePicker with the correct date, obtained from the calendar object
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year,month,day, null);

        // Build an AlertDialog with a tile and one OK button, and the date picker
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        int hour   = mCalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = mCalendar.get(Calendar.MINUTE);
                        int second = mCalendar.get(Calendar.SECOND);

                        Date date = new GregorianCalendar(year, month, day, hour, minute, second).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // A private method that creates an intent, puts the date on it as an extra, and then calls CrimeFragment.onActivityResult(...)
    private void sendResult(int resultCode, Date date){
        // Check for existance(?)
        if (getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

}
