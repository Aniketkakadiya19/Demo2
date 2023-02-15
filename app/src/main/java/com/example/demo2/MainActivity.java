package com.example.demo2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, discardHitBtn, metricHitBtn,webViewBtn,demoHitBtn;
    Button twitterBtn, facebookBtn, emailBtn,crashButton;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FirebaseRemoteConfigSettings remoteConfigSettings;
    TextView textView;
    volatile String xx ="xx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("logging event," ,"onCreate: on create is called here" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("logging event," ,"onCreate: on create is called here" );

        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String insta_ID = task.getResult();
                    xx = insta_ID;
                    Log.e("App instance ID :AAAA",xx);
                }
            }
        });


//        ActionBar ac = getActionBar();
//        ac.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBB86FC")));
        getFirebaseCloudToken();



        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        HandleDynamicLinks();

//        firebaseAnalytics.setUserProperty("property1","value1");
//        firebaseAnalytics.setUserProperty("property2","value1");
//        firebaseAnalytics.setUserProperty("property3","value1");
//        firebaseAnalytics.setUserProperty("property4","value1");
//        firebaseAnalytics.setUserProperty("property5","value1");
//        firebaseAnalytics.setUserProperty("property6","value1");
//        firebaseAnalytics.setUserProperty("property7","value1");
//        firebaseAnalytics.setUserProperty("property8","value1");
//        firebaseAnalytics.setUserProperty("property9","value1");
//        firebaseAnalytics.setUserProperty("property10","value1");
//        firebaseAnalytics.setUserProperty("property11","value1");
//        firebaseAnalytics.setUserProperty("property12","value1");
//        firebaseAnalytics.setUserProperty("property13","value1");
//        firebaseAnalytics.setUserProperty("property14","value1");
//        firebaseAnalytics.setUserProperty("property15","value1");
//        firebaseAnalytics.setUserProperty("property16","value1");
//        firebaseAnalytics.setUserProperty("property17","value1");
//        firebaseAnalytics.setUserProperty("property18","value1");
//        firebaseAnalytics.setUserProperty("property19","value1");
//        firebaseAnalytics.setUserProperty("property20","value1");
//        firebaseAnalytics.setUserProperty("property21","value1");
//        firebaseAnalytics.setUserProperty("property22","value1");
//        firebaseAnalytics.setUserProperty("property23","value1");
//        firebaseAnalytics.setUserProperty("property24","value1");
//        firebaseAnalytics.setUserProperty("property25","value1");
//        firebaseAnalytics.setUserProperty("property26","value1");

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        twitterBtn = findViewById(R.id.twitter_btn);
        facebookBtn = findViewById(R.id.fb_btn);
        emailBtn = findViewById(R.id.email_btn);
        discardHitBtn = findViewById(R.id.discard_hit_btn);
        metricHitBtn = findViewById(R.id.metric_test_btn);
        textView = findViewById(R.id.textView);
        webViewBtn = findViewById(R.id.webview_btn);
        demoHitBtn = findViewById(R.id.demohit);
        crashButton = findViewById(R.id.crash_btn);

//        Bundle tesBundle = new Bundle();
//        tesBundle.putString("e_category", "Discard Hit");
//        tesBundle.putString("e_action", "Discard Button click");
//        firebaseAnalytics.logEvent("test_hit_event", tesBundle);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.login_default_values);

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String test = mFirebaseRemoteConfig.getString("uservarient");
                Log.d("TAG", "onComplete:FETCH MAIN ACTivitY: "+ test);
            }
        });

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                String test = mFirebaseRemoteConfig.getString("uservarient");
                Log.d("TAG", "onComplete: FETCH and ACTIVEATE MAIN ACTivitY: "+ test);
            }
        });
        checkIntent();

//
        Log.e("TAG", "onCreate: " );




        Log.e("App instance ID :AAAA",xx);
//
        FirebaseInstallations.getInstance().getToken(/* forceRefresh */true)
                .addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.d("Installations", "Installation auth token: " + task.getResult().getToken());
                        } else {
                            Log.e("Installations", "Unable to get Installation auth token");
                        }
                    }
                });

        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });

        demoHitBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"InvalidAnalyticsName", "Range"})
            @Override
            public void onClick(View view) {
                Bundle b1 = new Bundle();
                b1.putString("btn_label", button2.getText().toString());
//                b1.putString("event_parameter_nameevent_parameter_namestt","value exist1");
//                b1.putString("event_is_working_not_sdsdsdsdsdsafasfafadsfaf","value exist2");
//                b1.putString("event_parameter_nameevent_parameter_nam","parameter key in limit");
//                b1.putString("demo_exceed","lets dancelets dancelets dancelets dancelets dsancelets dancelets dancelets dancelets dancelets danceing in the dark");
                b1.putString("param1","value1");
                b1.putString("param2","value1");
                b1.putString("param3","value1");
                b1.putString("param4","value1");
                b1.putString("param5","value1");
                b1.putString("param6","value1");
                b1.putString("param7","value1");
                b1.putString("param8","value1");
               b1.putString("param9","value1");
                b1.putString("param10","value1");
                b1.putString("param11","value1");
                b1.putString("param12","value1");
                b1.putString("param13","value1");
                b1.putString("param14","value1");
                b1.putString("param15","value1");
                b1.putString("param16","value1");
                b1.putString("param17","value1");
                b1.putString("param18","value1");
                b1.putString("param19","value1");
                b1.putString("param20","value1");
                b1.putString("param21","value1");
                b1.putString("param22","value1");
                b1.putString("param23","value1");
                b1.putString("param24","value1");
                b1.putString("param25","value1");
//                b1.putString("param26","value1");
//                firebaseAnalytics.logEvent("length_of_parameter_value", b1);
                firebaseAnalytics.logEvent("demo_event",b1);
//                firebaseAnalytics.setUserProperty("set_demo_user_property","Aagar tu muje london me nahi milti to amstradam main milti, amstradam main nahi milti to paris me milti");



            }
        });

        webViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(i);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, button1.getText().toString() + "txt", Toast.LENGTH_LONG).show();

                Bundle b1 = new Bundle();
                b1.putString("btn_label", button1.getText().toString());
                firebaseAnalytics.logEvent("button_one_click", b1);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b1 = new Bundle();
                b1.putString("btn_label", button2.getText().toString());
                firebaseAnalytics.logEvent("button_two_click", b1);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b1 = new Bundle();
                b1.putString("btn_label", button3.getText().toString());
                firebaseAnalytics.logEvent("button_three_click", b1);


            }
        });

        discardHitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle discardBundle = new Bundle();
                discardBundle.putString("e_category", "Discard Hit");
                discardBundle.putString("e_action", "Discard Button click");

                for (int i = 0; i < 60; i++) {
                    firebaseAnalytics.logEvent("discard_hit_event", discardBundle);
                }
            }
        });
        metricHitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendNotification();
                }
                Bundle strBundle = new Bundle();
                strBundle.putString("e_category", "String Metrics");
                strBundle.putString("e_action", "String Metric btn click");
                strBundle.putString("str_metric", "Hi");
                strBundle.putString("str_int_metric", "1");
                strBundle.putInt("click_count", 1);
                firebaseAnalytics.logEvent("send_custom_metric", strBundle);
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle fbBundle = new Bundle();
                fbBundle.putString("eCategory","Button Test");
                fbBundle.putString("eAction","Twitter Button Clicked");
                fbBundle.putString("etype","twitter");
                firebaseAnalytics.setUserProperty("login_type", "Twitter");
                firebaseAnalytics.logEvent("fb_btn", fbBundle);

                Intent ti = new Intent(MainActivity.this, HomePage.class);
                ti.putExtra("btn","tibtn");
                startActivity(ti);
            }
        });
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent fbi = new Intent(MainActivity.this, HomePage.class);
                Bundle fbBundle = new Bundle();
                fbBundle.putString("eCategory","Button Test");
                fbBundle.putString("eAction","Facebook Button Clicked");
                fbBundle.putString("etype","facebook");
                firebaseAnalytics.setUserProperty("login_type", "facebook");

                firebaseAnalytics.logEvent("fb_btn", fbBundle);

                startActivity(fbi);
            }
        });
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle fbBundle = new Bundle();
                fbBundle.putString("eCategory","Button Test");
                fbBundle.putString("eAction","Email Button Clicked");
                fbBundle.putString("etype","email");
                firebaseAnalytics.setUserProperty("login_type", "Email");

                firebaseAnalytics.logEvent("fb_btn", fbBundle);
                Intent ei = new Intent(MainActivity.this, HomePage.class);
                ei.putExtra("btn","emailbtn");
                startActivity(ei);
            }
        });

    }

    private void HandleDynamicLinks() {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {

                            // get deep link url
                            deepLink = pendingDynamicLinkData.getLink();
                            Log.e("DeepLink", String.valueOf(deepLink));

                            // get utm parameters
                            Bundle bundle =pendingDynamicLinkData.getUtmParameters();

                            String utm_source = null, utm_medium = null, utm_campaign = null;
                            if (bundle.containsKey("utm_source")) {
                                utm_source = bundle.getString("utm_source");
                            }
                            if (bundle.containsKey("utm_medium")) {
                                utm_medium = bundle.getString("utm_medium");
                            }
                            if (bundle.containsKey("utm_campaign")) {
                                utm_campaign = bundle.getString("utm_campaign");
                            }

                            // pass custom event of dynamic link
                            Bundle params = new Bundle();
                            params.putString("eventCategory", "dynamic link campaign");
                            params.putString("eventAction", "campaign link");
                            params.putString("utm_source", utm_source);
                            params.putString("utm_medium", utm_medium);
                            params.putString("utm_campaign", utm_campaign);
                            mFirebaseAnalytics.logEvent("dynamic_link_event", params);


                        }
                        // Use the deep link
                        if (deepLink != null) {
                            Log.d("TAGDDD", "Deep link: " + deepLink.toString());
                        } else {
                            Log.d("TAGDDD", "No deep link found");
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "getDynamicLink:onFailure", e);
                    }
                });


    }

    private void getInstanceId() {
    }

    private void checkIntent() {

        if(getIntent()!=null && getIntent().hasExtra("key")){
            Log.d("TAGGG", "checkIntent: notificaation Condition true");
            Intent i = new Intent(MainActivity.this,HomePage.class);
            startActivity(i);
        }
        Log.d("TAGGG", "checkIntent: notificaation Condition FALSE");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification() {
        NotificationChannel channel = new NotificationChannel("notification","notification_name",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"notification")
                .setContentTitle("Title HERE")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentText("This is notification text")
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Hey, Nasty user! There is falana dhinkana offer running in our campaign. " +
                        "please check out our products before it gets out of stock"));


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }

    private void getFirebaseCloudToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("TAGGG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("TAGGG", token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    public void loginClick(View view) {
//        Intent i = new Intent(MainActivity.this, HomePage.class);
//        startActivity(i);
//    }
}