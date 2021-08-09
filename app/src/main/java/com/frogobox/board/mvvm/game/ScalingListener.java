package com.frogobox.board.mvvm.game;/*
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


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.LinearInterpolator;

import com.frogobox.board.model.Element;
import com.frogobox.board.util.SingleConst;

public class ScalingListener extends AnimatorListenerAdapter {
    Element e = null;

    public ScalingListener(Element e) {
        super();
        this.e = e;
    }

    public ScalingListener() {
        super();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
        animation.setupEndValues();
    }

    @Override
    public void onAnimationPause(Animator animation) {
        super.onAnimationPause(animation);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (e != null) {
            e.animate().scaleX(1.0f).scaleY(1.0f).setDuration(SingleConst.Games.INIT_SCALINGSPEED).setStartDelay(0).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }
            }).start();
        }

    }
}