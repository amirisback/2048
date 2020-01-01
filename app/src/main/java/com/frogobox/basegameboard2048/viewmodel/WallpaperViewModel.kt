package com.frogobox.basegameboard2048.viewmodel

import android.app.Application
import com.frogobox.basegameboard2048.base.util.BaseViewModel
import com.frogobox.basegameboard2048.model.Wallpaper
import com.frogobox.basegameboard2048.source.FrogoDataRepository
import com.frogobox.basegameboard2048.util.SingleLiveEvent

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseWallpaperApp
 * Copyright (C) 22/12/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basegameboard2048.viewmodel
 *
 */
class WallpaperViewModel (
    private val context: Application,
    private val repository: FrogoDataRepository
) :
    BaseViewModel(context) {

    var wallpaperListLive = SingleLiveEvent<List<Wallpaper>>()

}
