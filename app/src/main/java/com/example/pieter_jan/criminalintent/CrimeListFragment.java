package com.example.pieter_jan.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pieter-jan on 11/15/2016.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private Boolean mSubtitleVisible;

    // Save the subtitle option when rotating
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    // Chapter 10 Challenge
    private int savedPosition;
    private static final String SAVED_POSITION = "SAVED_POSITION";

    // Chapter 13 Challenge: empty view list
    private TextView mEmptyView;
    private Button mEmptyButton;
    private Callbacks mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Warns the fragmentmanager that there is an options menu to receive menu callbacks
        mSubtitleVisible = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);


        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            savedPosition = savedInstanceState.getInt(SAVED_POSITION);
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

//        updateUI(); // It will be called anyway in onResume, if it is called twice then the savedPosition is initialized to 0 -> wrong


        // The empty view

        mEmptyView = (TextView) view.findViewById(R.id.text_view_empty_list);
        mEmptyButton = (Button) view.findViewById(R.id.button_empty_list);
        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCrime();
            }
        });


        return view;
    }

    private void createNewCrime() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    // Preserve variables for screen rotations
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_POSITION, savedPosition);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater); // recommended to keep this here for convention
        inflater.inflate(R.menu.fragment_crime_list, menu);  // inflate the menu

        // Change the showsubtitle menu if the user clicks on it
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle); // the subtitle is visible so it should show "hide subtitle"
        } else {
            subtitleItem.setTitle(R.string.show_subtitle); // the subtitle is not visible so it should show "show subtitle"
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime(); // Create a new crime
                CrimeLab.get(getActivity()).addCrime(crime); // Add a crime to the database
//                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId()); // Create an intent for the CrimePagerActivity
//                startActivity(intent); // Give the intent

                updateUI();
                mCallbacks.onCrimeSelected(crime);

                return true; // action completed


            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        // Create and connect Adapter to RecyclerView
    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged(); // The whole dataset changes when an item is added or removed
//            mAdapter.notifyItemChanged(savedPosition); // This was to make the recyclerview more efficient,
// but it doesn't work when a crime is deleted, then the whole dataset changes :/
        }

        if (crimes.isEmpty()){
            mCrimeRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyButton.setVisibility(View.VISIBLE);
        } else {
            mCrimeRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            mEmptyButton.setVisibility(View.GONE);
        }

        updateSubtitle();
    }


    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount); // 1 Crime, 2 Crimes, ...
//        String subtitle = getString(R.string.subtitle_format, crimeCount); //generate subtitle string

        if (!mSubtitleVisible){ // Subtitle menu is not visible
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity(); // Activity that is hosting CrimeListFragment is cast to an AppCompatActivity
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


/////////////////////// Add the Callback interface needed for the tablet functionality
    // Required for hosting activities

    public interface Callbacks{
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////



    // An inner ViewHolder class
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mDateTextView;
        private TextView mTitleTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDateFormatted());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

            savedPosition = getAdapterPosition();

            // Start the CrimePagerActivity here
//            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
//            startActivity(intent);

            mCallbacks.onCrimeSelected(mCrime);

        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////



    // An inner Adapter class that connects the ViewHolders and the fragment
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }


        // Just create the ViewHolder
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        // Bind the ViewHolder to a Crime
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position); // Pull out a crime
            holder.bindCrime(crime); // Bind the data to the different views
        }



        // Return the amount of crimes in the list
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }


        // Swap out the crimes the adapter displays (needed with database)
        public void setCrimes(List<Crime> crimes){
            mCrimes = crimes;
        }

    }



}
