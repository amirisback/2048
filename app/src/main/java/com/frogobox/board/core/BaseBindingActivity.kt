package com.frogobox.board.core

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.frogobox.admob.core.FrogoAdmob
import com.frogobox.board.R
import com.frogobox.sdk.FrogoActivity
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

    private fun setupAdmob() {
        setPublisher()
        setBanner()
        setInterstitial()
    }

    private fun setPublisher() {
        FrogoAdmob.setupAppID(getString(R.string.admob_publisher_id))
        FrogoAdmob.App.setupApp(this)
    }

    private fun setBanner() {
        FrogoAdmob.setupBannerAdUnitID(getString(R.string.admob_banner))
    }

    private fun setInterstitial() {
        FrogoAdmob.setupInterstialAdUnitID(getString(R.string.admob_interstitial))
        FrogoAdmob.Interstitial.setupInterstitial(this)
    }

    protected fun setupShowAdsBanner(mAdView: AdView) {
        FrogoAdmob.Banner.showBanner(mAdView)
    }

    protected fun setupShowAdsInterstitial() {
        FrogoAdmob.Interstitial.showInterstitial(this)
    }

}