package com.frogobox.basegameboard2048.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.frogobox.basegameboard2048.util.helper.FirstLaunchManager;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent;

        FirstLaunchManager firstStartPref = new FirstLaunchManager(this);

        if(firstStartPref.isFirstTimeLaunch()) {
            firstStartPref.initFirstTimeLaunch();
            mainIntent = new Intent(this, TutorialActivity.class);
        } else {
            mainIntent = new Intent(this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        startActivity(mainIntent);
        finish();
    }

}
