package com.example.pieter_jan.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pieter_jan.criminalintent.database.CrimeBaseHelper;
import com.example.pieter_jan.criminalintent.database.CrimeCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.pieter_jan.criminalintent.database.CrimeDBSchema.CrimeTable;

/**
 * Created by pieter-jan on 11/15/2016.
 * A singleton class that holds all the crimes
 *
 * Most commented lines are code from before the introduction of the SQLite Database
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab; // A static variable (s)
//    private List<Crime> mCrimes;

    // the SQLite database

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //A private constructor so that get() can not be bypassed
    private CrimeLab(Context context){

        // create a SQLite database
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

//        mCrimes = new ArrayList<>();

//        // Populate the list with 100 boring crimes
//        for (int i = 0; i < 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime #" + i);
//            crime.setSolved(i % 2 == 0); // Every other one
//            mCrimes.add(crime);
//        }
    }

    // Returns the list of crimes
    public List<Crime> getCrimes(){

        List<Crime> crimes= new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close(); // important, otherwise you get errors and can crash the app
        }

        return crimes;

//        return mCrimes; // returns the list of crimes
    }

    // Find a crime by its id
    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?",
                new String[] {id.toString()}); // Find the right position in the dataset by using a WHERE clause

        try{
            if (cursor.getCount() == 0){
                return null; // there are no crimes
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close(); // again close the cursor to avoid nasty errors
        }

////        for (Crime crime : mCrimes) {
//            if(crime.getId().equals(id)){
//                return crime;
//            }
//        }
    }

    public void addCrime(Crime c){
//        mCrimes.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }


//    update a crime in the database
    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?", // this is a WHERE clause: specify which row has to be updated; the '?' makes it treat the String as an actual String, important when the string can contain SQL code -> SQL injection attack
                new String[] {uuidString});
    }

    public void deleteCrime(Crime c){

//        for (Crime crime : mCrimes){
//            if (crime.getId().equals(c.getId())){
//                mCrimes.remove(crime); // Maybe we have to iterate over the list to find the object? (We will see...)
//                break;
//            }
//        }
    }

    // Method to work with ContentValues
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    // Query the crimes
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,null, // GroupBy
                null, // having
                null  //Orderby
        );

        return new CrimeCursorWrapper(cursor);
    }



}
