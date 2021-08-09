package com.frogobox.board.model

import com.frogobox.board.util.SingleConst
import java.io.Serializable

/*
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
 */
class GameStatistics(n: Int) : Serializable {

    private var highestNumber: Long = 2

    var moves: Long = 0
    var timePlayed: Long = 0
    var record: Long = 0
    var n = 4
    var undo = 0
    var moves_l = 0
    var moves_r = 0
    var moves_t = 0
    var moves_d = 0

    val filename = SingleConst.Const.FILE_STATISTIC + n + SingleConst.Ext.TXT

    fun getHighestNumber(): Long {
        return highestNumber
    }

    fun setHighestNumber(highestNumber: Long) {
        if (this.highestNumber < highestNumber) this.highestNumber = highestNumber
    }

    fun addTimePlayed(timePlayed: Long) {
        this.timePlayed += timePlayed
    }

    fun resetTimePlayed(): Boolean {
        timePlayed = 0
        return true
    }

    fun addMoves(moves: Long) {
        this.moves += moves
    }

    fun undo() {
        undo++
    }

    fun moveL() {
        moves_l++
    }

    fun moveR() {
        moves_r++
    }

    fun moveT() {
        moves_t++
    }

    fun moveD() {
        moves_d++
    }

    override fun toString(): String {
        return "moves " + moves +
                " timePlayed " + timePlayed / 1000.0f +
                " highest Number " + highestNumber +
                " record" + record
    }

    init {
        this.n = n
    }

}