package com.frogobox.board.mvvm.main

import android.content.Intent
import android.os.Bundle
import com.frogobox.board.core.BaseActivity
import com.frogobox.board.mvvm.tutorial.TutorialActivity
import com.frogobox.board.mvvm.tutorial.TutorialController

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainIntent: Intent
        val firstStartPref = TutorialController(this)

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