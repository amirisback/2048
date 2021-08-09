package com.frogobox.board.mvvm.game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.LinearInterpolator;

import com.frogobox.board.model.Element;
import com.frogobox.board.util.SingleConst;

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

public class MovingListener extends AnimatorListenerAdapter {
    Element e = null;
    long SCALINGSPEED = SingleConst.Games.INIT_SCALINGSPEED;
    float scalingFactor = SingleConst.Games.INIT_SCALINGFACTOR;
    boolean scale = false;

    public MovingListener(Element e, boolean scale) {
        super();
        this.e = e;
        this.scale = scale;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
        animation.setupEndValues();
        if (e != null)
            e.drawItem();
    }

    @Override
    public void onAnimationPause(Animator animation) {
        super.onAnimationPause(animation);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        if (e != null) {
            e.drawItem();
            if (scale)
                e.animate().scaleX(scalingFactor).scaleY(scalingFactor).setDuration(SCALINGSPEED).setStartDelay(0).setInterpolator(new LinearInterpolator()).setListener(new ScalingListener(e)).start();
        }

    }
}
