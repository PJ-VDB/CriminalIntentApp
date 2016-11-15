package com.example.pieter_jan.criminalintent;

import java.util.UUID;

/**
 * Created by pieter-jan on 11/15/2016.
 */

public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
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
}
