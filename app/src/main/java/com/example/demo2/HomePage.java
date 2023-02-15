package com.example.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class HomePage extends AppCompatActivity {

    TextView textView;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FirebaseRemoteConfigSettings remoteConfigSettings;
    public String test, tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF6200EE")));

        textView = findViewById(R.id.textView);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.login_default_values);


            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener( this,new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean updated = task.getResult();

                                test = mFirebaseRemoteConfig.getString("uservarient");
                                tv = mFirebaseRemoteConfig.getString("activity_txt");

                                Log.e("TAG", "onComplete: FETCHED VARIENT is: "+ test+"  "+ test.getClass().getName()  );

                                Log.e("TAG", "Config params updated: " + updated);
                                if(test.compareTo("baseline")==0){
                                    getSupportActionBar().setBackgroundDrawable(
                                            new ColorDrawable(Color.parseColor("#FF000000")));
                                    textView.setText(tv);
                                    Log.e("TAG", "onComplete: USER VARIENT ---baseline" );

                                }
                                if(test.compareTo("basevarient")==0){
                                    getSupportActionBar().setBackgroundDrawable(
                                            new ColorDrawable(Color.parseColor("#FF3700B3")));
                                    textView.setText(tv);
                                    Log.e("TAG", "onComplete: USER VARIENT ---basevarient" );

                                }
                                if (test.compareTo("varient1")==0){
                                    getSupportActionBar().setBackgroundDrawable(
                                            new ColorDrawable(Color.parseColor("#D14242")));
                                    textView.setText(tv);
                                    Log.e("TAG", "onComplete: USER VARIENT varient 1 inner" );
                                }

                            } else {
                                Log.e("TAG", "onComplete: UNSCCCCCC" );
                            }
                            Log.e("TAG", "onComplete: exitg" );

                        }
                    });
        Log.e("TAG", "onComplete: exitg"+test );


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF6200EE")));
    }
}