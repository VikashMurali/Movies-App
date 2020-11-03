package com.example.movies.viewmodel;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class CardViewItemViewModel {
    private String name;
    private Bitmap bitmap;
    private Resources resources;

    public CardViewItemViewModel(String name, Bitmap bitmap, Resources resources) {
        this.name = name;
        this.bitmap = bitmap;
        this.resources = resources;
    }

    public String getName(){
        return name;
    }

    public BitmapDrawable getBitmap(){
        return new BitmapDrawable(resources, bitmap);
    }
}
