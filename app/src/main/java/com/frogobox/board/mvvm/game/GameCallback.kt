package com.frogobox.board.mvvm.game

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
interface GameCallback {

    interface GameScoreCallback {
        fun setupHighScore(highScore: Int)
    }

    interface GameStateCallback {
        fun setupNewGame()
    }

}