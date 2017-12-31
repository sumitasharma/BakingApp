package com.example.sumitasharma.bakingapp;

import android.app.Application;

import timber.log.Timber;


public class TimberApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
