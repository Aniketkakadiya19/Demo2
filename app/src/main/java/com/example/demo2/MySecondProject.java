package com.example.demo2;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MySecondProject {
    private static FirebaseApp INSTANCE;
    private static FirebaseAnalytics inst;

    public static FirebaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getSecondProject(context);
        }
        return INSTANCE;
    }


    private static FirebaseApp getSecondProject(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("appproject2-51efa")
                .setApplicationId("1:679345531663:android:ad94f08837f90fe824ac7a")
                .setApiKey("AIzaSyBwYc8exoUdcx0BYwkParsJ4VJgpWMrjGA")
                // setDatabaseURL(...)
                // setStorageBucket(...)
                .build();

        FirebaseApp.initializeApp(context, options, "admin");
        return FirebaseApp.getInstance("admin");
    }


}
