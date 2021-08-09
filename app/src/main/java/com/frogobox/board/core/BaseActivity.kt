package  com.frogobox.board.core

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.frogobox.admob.ui.FrogoAdmobActivity
import com.frogobox.board.R
import kotlinx.android.synthetic.main.toolbar_main.*

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
open class BaseActivity : FrogoAdmobActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdmob()
    }

    private fun setupAdmob(){
        setPublisher()
        setBanner()
        setInterstitial()
    }

    private fun setPublisher() {
        setupAdsPublisher(getString(R.string.admob_publisher_id))
    }

    private fun setBanner() {
        setupAdsBanner(getString(R.string.admob_banner))
    }

    private fun setInterstitial() {
        setupAdsInterstitial(getString(R.string.admob_interstitial))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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

    protected fun setupToolbar() {
        setSupportActionBar(toolbar_main)
    }

}