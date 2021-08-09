package com.frogobox.board.model

import com.frogobox.board.widget.Element
import java.io.Serializable
import java.lang.StringBuilder

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

class GameState : Serializable {

    @JvmField
    var n = 4

    @JvmField
    var points = 0

    @JvmField
    var last_points = 0

    @JvmField
    var numbers: IntArray

    @JvmField
    var undo = false

    private lateinit var last_numbers: IntArray

    constructor(size: Int) {
        numbers = IntArray(size * size)
    }

    constructor(e: Array<IntArray>) {
        var length = 1
        for (ints in e) {
            if (ints.size > length) length = ints.size
        }
        n = e.size
        numbers = IntArray(e.size * e.size)
        var c = 0
        for (ints in e) {
            for (anInt in ints) {
                numbers[c++] = anInt
            }
        }
        last_numbers = numbers
    }

    constructor(e: Array<Array<Element>>, e2: Array<Array<Element>>) {
        var length = 1
        for (elements in e) {
            if (elements.size > length) length = elements.size
        }
        n = e.size
        numbers = IntArray(e.size * e.size)
        var c = 0
        for (elements in e) {
            for (element in elements) {
                numbers[c++] = element.number
            }
        }
        length = 1
        for (elements in e2) {
            if (elements.size > length) length = elements.size
        }
        last_numbers = IntArray(e2.size * e2.size)
        c = 0
        for (elements in e2) {
            for (element in elements) {
                last_numbers[c++] = element.number
            }
        }
    }

    fun getNumber(i: Int, j: Int): Int {
        try {
            return numbers[i * n + j]
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        return 0
    }

    fun getLastNumber(i: Int, j: Int): Int {
        try {
            return last_numbers[i * n + j]
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        return 0
    }

    override fun toString(): String {
        val result = StringBuilder("numbers: ")
        for (i in numbers) {
            result.append(i).append(" ")
        }
        result.append(", n: ").append(n)
        result.append(", points: ").append(points)
        result.append(", undo: ").append(undo)
        return result.toString()
    }
}