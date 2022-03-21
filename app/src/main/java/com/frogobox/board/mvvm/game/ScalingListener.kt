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

class ScalingListener : AnimatorListenerAdapter {

    var e: Element? = null

    constructor() : super() {}

    constructor(e: Element?) : super() {
        this.e = e
    }

    override fun onAnimationCancel(animation: Animator) {
        super.onAnimationCancel(animation)
        animation.setupEndValues()
    }

    override fun onAnimationPause(animation: Animator) {
        super.onAnimationPause(animation)
    }

    override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        if (e != null) {
            e!!.animate().scaleX(1.0f).scaleY(1.0f).setDuration(SingleConst.Games.INIT_SCALINGSPEED)
                .setStartDelay(0).setInterpolator(LinearInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationCancel(animation: Animator) {
                        super.onAnimationCancel(animation)
                    }
                }).start()
        }
    }

}