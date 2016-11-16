package com.example.pieter_jan.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment(); //using the self implemented abstract class SingleFragmentActivity
    }
}
