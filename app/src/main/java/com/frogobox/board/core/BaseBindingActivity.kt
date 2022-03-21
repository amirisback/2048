package com.frogobox.board.core

import androidx.viewbinding.ViewBinding
import com.frogobox.admob.core.IFrogoAdInterstitial
import com.frogobox.admob.ui.FrogoSdkAdmobActivity
import com.frogobox.board.R

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
abstract class BaseBindingActivity<VB : ViewBinding> : FrogoSdkAdmobActivity<VB>() {

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

}