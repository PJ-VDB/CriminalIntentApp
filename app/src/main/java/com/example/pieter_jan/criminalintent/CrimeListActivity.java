package com.example.pieter_jan.criminalintent;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by pieter-jan on 11/15/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {


    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "CrimeListFragment created.");
        return new CrimeListFragment();
    }
}
