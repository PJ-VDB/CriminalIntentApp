package com.example.pieter_jan.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pieter-jan on 11/15/2016.
 * A singleton class that holds all the crimes
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab; // A static variable (s)
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //A private constructor so that get() can not be bypassed
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();

        // Populate the list with 100 boring crimes
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(crime);
        }
    }

    // Returns the list of crimes
    public List<Crime> getCrimes(){
        return mCrimes; // returns the list of crimes
    }

    // Find a crime by its id
    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes) {
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }



}
