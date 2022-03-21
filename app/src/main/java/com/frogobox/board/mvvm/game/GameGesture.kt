package com.frogobox.board.mvvm.game

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

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
open class GameGesture(context: Context?) : OnTouchListener {

    companion object {
        private const val SWIPE_THRESHOLD = 50
        private const val SWIPE_VELOCITY_THRESHOLD = 0
    }

    private var gestureDetector: GestureDetector? = null
    private var cxt: Context? = null

    init {
        gestureDetector = GestureDetector(context, GameGestureListener())
        cxt = context
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    private inner class GameGestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                result = if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > Companion.SWIPE_THRESHOLD && abs(velocityX) > Companion.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    } else {
                        nichts()
                    }
                } else {
                    if (abs(diffY) > Companion.SWIPE_THRESHOLD && abs(velocityY) > Companion.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                    } else {
                        nichts()
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

    }

    open fun onSwipeRight(): Boolean {
        return false
    }

    open fun onSwipeLeft(): Boolean {
        return false
    }

    open fun nichts(): Boolean {
        return false
    }

    open fun onSwipeTop(): Boolean {
        return false
    }

    open fun onSwipeBottom(): Boolean {
        return false
    }

}