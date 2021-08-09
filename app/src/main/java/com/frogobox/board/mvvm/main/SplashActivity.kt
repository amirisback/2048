package com.frogobox.board.mvvm.main

import com.frogobox.board.core.BaseActivity
import android.os.Bundle
import android.content.Intent
import com.frogobox.board.util.FirstLaunchManager
import com.frogobox.board.mvvm.tutorial.TutorialActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainIntent: Intent
        val firstStartPref = FirstLaunchManager(this)

        if (firstStartPref.isFirstTimeLaunch) {
            firstStartPref.initFirstTimeLaunch()
            mainIntent = Intent(this, TutorialActivity::class.java)
        } else {
            mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        startActivity(mainIntent)
        finish()
    }
}