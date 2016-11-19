package com.example.pieter_jan.criminalintent;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by pieter-jan on 11/15/2016.
 */

public class Crime {

    private UUID mId; //a random unique identifier for the crime
    private String mTitle; //The tile of the crime
    private Date mDate; //The date a crime occured
    private boolean mSolved; //Has the crime been solved
    private Context context;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getDateFormatted() {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
        String dateString = dateFormat.format(mDate);
        return dateString;
    }

    public String getTimeFormatted(){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(mDate);
        return time;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
