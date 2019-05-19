package com.example.dagger2example;

import android.util.Log;

import javax.inject.Inject;

public class Cars {

    private static final String TAG = "Car";
    @Inject
    Engine engine;
    private Wheels wheels;

    @Inject
    public Cars(Wheels wheels) {
        this.wheels = wheels;
    }

    @Inject
    public void enableRemote(Remote remote) {
        remote.setListener(this);
    }

    public void drive() {
        engine.start();
        Log.d(TAG, "driving....");
    }

}
