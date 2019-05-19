package com.example.dagger2example;

import android.util.Log;

import javax.inject.Inject;

public class Remote {

 public static final String TAG = "Cars";

 @Inject
    Remote(){

 }

 public void setListener(Cars car){
     Log.d(TAG,"Remote connected....");
 }

}
