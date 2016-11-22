package com.example.pieter_jan.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pieter_jan.criminalintent.database.CrimeDBSchema.CrimeTable;

/**
 * Created by pieter-jan on 11/20/2016.
 * Initialize the Database, the steps that need to be taken are:
 * 1. Check to see if the database already exists
 * 2. If it does not, create it and create the tables and initial data it needs.
 * 3. If it does, open it up and see what version of your CimeDBSchema it has
 * 4. If it is an old version, run code to upgrade it to a newer version
 */



public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1; // the version of the database; needs to be updated if database is changed!
    private static final String DATABASE_NAME = "crimeBase.db"; // the location of the database

    public CrimeBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" + " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
