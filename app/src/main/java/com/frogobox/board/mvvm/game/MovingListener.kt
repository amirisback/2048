package com.frogobox.board.mvvm.game

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.LinearInterpolator
import com.frogobox.board.util.SingleConst
import com.frogobox.board.widget.Element

/*
 * Created by faisalamir on 09/08/21
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

class MovingListener(e: Element?, scale: Boolean) : AnimatorListenerAdapter() {

    var e: Element? = null
    var scalingFactor = 1.5f
    var scale = false
    private var scalingSpeed: Long = 100

    init {
        this.e = e
        scalingSpeed = SingleConst.Games.INIT_SCALINGSPEED
        scalingFactor = SingleConst.Games.INIT_SCALINGFACTOR
        this.scale = scale
    }

    override fun onAnimationCancel(animation: Animator) {
        super.onAnimationCancel(animation)
        animation.setupEndValues()
        if (e != null) e!!.drawItem()
    }

    override fun onAnimationPause(animation: Animator) {
        super.onAnimationPause(animation)
    }

    override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        if (e != null) {
            e!!.drawItem()
            if (scale) e!!.animate().scaleX(scalingFactor).scaleY(scalingFactor)
                .setDuration(scalingSpeed).setStartDelay(0).setInterpolator(LinearInterpolator())
                .setListener(ScalingListener(e)).start()
        }
    }

}