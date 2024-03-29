package  com.frogobox.board.core

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.frogobox.admob.core.IFrogoAdInterstitial
import com.frogobox.admob.ui.FrogoAdmobActivity
import com.frogobox.board.R

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * ImplementationAdmob
 * Copyright (C) 27/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 *  com.frogobox.basegameboard2048.base
 *
 */
abstract class BaseActivity : FrogoAdmobActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showInterstitial(callback: IBaseFrogoAdInterstitial) {
        showAdInterstitial(getString(R.string.admob_interstitial),
            object : IFrogoAdInterstitial {
                override fun onAdDismissed(message: String) {
                    callback.onAdDismissed(message)
                }

                override fun onAdFailed(errorMessage: String) {
                    callback.onAdFailed(errorMessage)
                }

                override fun onAdLoaded(message: String) {
                }

                override fun onAdShowed(message: String) {
                }

            })
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected open fun setupDetailActivity(title: String) {
        setTitle(title)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_toolbar_back_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.colorBaseWhite
                )
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}