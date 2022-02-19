package com.frogobox.board.core

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.frogobox.admob.core.FrogoAdmob
import com.frogobox.board.R
import com.frogobox.sdk.core.FrogoActivity
import com.google.android.gms.ads.AdView

/*
 * Created by faisalamir on 14/08/21
 * 2048
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2021 FrogoBox Inc.      
 * All rights reserved
 *
 */
abstract class BaseBindingActivity<VB : ViewBinding> : FrogoActivity<VB>() {

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
        FrogoAdmob.setupPublisherID(getString(R.string.admob_publisher_id))
        FrogoAdmob.Publisher.setupPublisher(this)
    }

    private fun setBanner() {
        FrogoAdmob.setupBannerAdUnitID(getString(R.string.admob_banner))
    }

    private fun setInterstitial() {
        FrogoAdmob.setupInterstialAdUnitID(getString(R.string.admob_interstitial))
        FrogoAdmob.Interstitial.setupInterstitial(this)
    }

    protected fun setupShowAdsBanner(mAdView: AdView) {
        FrogoAdmob.Banner.setupBanner(mAdView)
        FrogoAdmob.Banner.showBanner(mAdView)
    }

    protected fun setupShowAdsInterstitial() {
        FrogoAdmob.Interstitial.showInterstitial(this)
    }

    override fun setupDetailActivity(title: String) {
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