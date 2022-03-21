package com.frogobox.board.core


/*
 * Created by faisalamir on 22/03/22
 * 2048
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2022 Frogobox Media Inc.      
 * All rights reserved
 *
 */

interface IBaseFrogoAdInterstitial {

    fun onAdDismissed(message: String)

    fun onAdFailed(errorMessage: String)

}