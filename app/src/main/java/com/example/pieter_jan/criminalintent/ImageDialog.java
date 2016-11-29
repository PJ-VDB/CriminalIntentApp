package com.example.pieter_jan.criminalintent;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.example.pieter_jan.criminalintent.PictureUtils.getScaledBitmap;

/**
 * Created by pieter-jan on 11/29/2016.
 */

public class ImageDialog extends DialogFragment {

    private ImageView mImageView;
    private static String mImagePath;
    private static Context mContext;

    public static void getImage(String path, Context context){
        mImagePath = path;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_image, container,false);
        mImageView = (ImageView) view.findViewById(R.id.image_zoom);

        Bitmap bitmap = getScaledBitmap(mImagePath, getActivity());
        mImageView.setImageBitmap(bitmap);
        return view;
    }
}
