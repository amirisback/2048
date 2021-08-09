package com.frogobox.board.ui;

import android.content.Intent;
import android.os.Bundle;

import com.frogobox.board.base.BaseActivity;
import com.frogobox.board.util.FirstLaunchManager;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent;

        FirstLaunchManager firstStartPref = new FirstLaunchManager(this);

        if (firstStartPref.isFirstTimeLaunch()) {
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
