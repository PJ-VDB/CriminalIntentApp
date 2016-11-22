package com.example.pieter_jan.criminalintent.database;

/**
 * Created by pieter-jan on 11/20/2016.
 * A class to define the String constants needed to describe the moving pieces of the table definition
 */

public class CrimeDBSchema {

    public static final class CrimeTable {

        // The name of the database
        public static final String NAME = "crimes";

        // The columns of the database
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            // new column in chapter 15
            public static final String SUSPECT = "suspect";
            public static final String CONTACT_ID = "contact_id";
        }
    }
}
