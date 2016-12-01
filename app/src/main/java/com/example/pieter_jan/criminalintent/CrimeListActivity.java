package com.example.pieter_jan.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by pieter-jan on 11/15/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{


    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "CrimeListFragment created.");
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        // if statement to see which layout was inflated: tells if it is a phone or a tablet
        if (findViewById(R.id.detail_fragment_container) == null) { // no detail fragment container so on a phone: start CrimePagerActivity
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else { // on a tablet so put a CrimeFragment in detail_fragment_container
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
