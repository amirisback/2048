package com.frogobox.board.mvvm.game;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseGameBoard2048
 * Copyright (C) 02/01/2020.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basegameboard2048.model
 *
 */

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameGesture implements View.OnTouchListener {

    private GestureDetector gestureDetector = null;
    private Context cxt = null;

    public GameGesture(Context context) {
        gestureDetector = new GestureDetector(context, new GameGestureListener());
        cxt = context;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean res = gestureDetector.onTouchEvent(event);
        return res;
    }

    private final class GameGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 0;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            result = onSwipeRight();
                        } else {
                            result = onSwipeLeft();
                        }
                    }else{
                        result = nichts();
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onSwipeBottom();
                        } else {
                            result = onSwipeTop();
                        }
                    }else{
                        result = nichts();
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public boolean onSwipeRight() {
        return false;
    }

    public boolean onSwipeLeft() {
        return false;
    }

    public boolean nichts(){
        return false;
    }

    public boolean onSwipeTop() {
        return false;
    }

    public boolean onSwipeBottom() {
        return false;
    }

}


