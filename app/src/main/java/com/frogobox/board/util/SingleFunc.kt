package  com.frogobox.board.util

import android.content.Context
import com.frogobox.board.model.GameState
import com.frogobox.board.model.GameStatistics
import com.frogobox.board.mvvm.game.GameCallback
import com.frogobox.board.widget.Element
import java.io.*
import java.lang.Exception


/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.helper
 *
 */
object SingleFunc {

    fun saveStatisticsToFile(context: Context, gS: GameStatistics) {
        try {
            val file = File(context.filesDir, gS.filename)
            val fileOut = FileOutputStream(file)
            val out = ObjectOutputStream(fileOut)
            out.writeObject(gS)
            out.close()
            fileOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun deleteStateFile(context: Context, fileName: String): Boolean {
        try {
            val directory: File = context.filesDir
            val f = File(directory, fileName)
            return f.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun saveStateToFile(context: Context, nS: GameState, fileName: String, saveState: Boolean) {
        if (saveState) try {
            val file = File(context.filesDir, fileName)
            val fileOut = FileOutputStream(file)
            val out = ObjectOutputStream(fileOut)
            out.writeObject(nS)
            out.close()
            fileOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readStatisticsFromFile(context: Context, n: Int): GameStatistics {
        var gS = GameStatistics(n)
        try {
            val file: File =
                File(context.filesDir, SingleConst.Const.FILE_STATISTIC + n + SingleConst.Ext.TXT)
            val fileIn = FileInputStream(file)
            val `in` = ObjectInputStream(fileIn)
            gS = `in`.readObject() as GameStatistics
            `in`.close()
            fileIn.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gS
    }

    fun readStateFromFile(
        context: Context,
        fileName: String,
        n: Int,
        callback: GameCallback.GameStateCallback
    ): GameState {
        var nS = GameState(n)
        try {
            val file = File(context.filesDir, fileName)
            val fileIn = FileInputStream(file)
            val `in` = ObjectInputStream(fileIn)
            nS = `in`.readObject() as GameState
            var emptyField = true
            for (i in nS.numbers.indices) {
                if (nS.numbers[i] > 0) {
                    emptyField = false
                    break
                }
            }
            if (emptyField || nS.n != n) {
                nS = GameState(n)
                callback.setupNewGame()
            }
            `in`.close()
            fileIn.close()
        } catch (e: Exception) {
            callback.setupNewGame()
            e.printStackTrace()
        }
        return nS
    }

    fun deepCopy(e: Array<Array<Element>>): Array<Array<Element?>?> {
        val r: Array<Array<Element?>?> = arrayOfNulls(e.size)
        for (i in r.indices) {
            r[i] = arrayOfNulls(e[i].size)
            for (j in r[i]!!.indices) {
                r[i]?.set(j, e[i][j].copy())
            }
        }
        return r
    }

    fun drawAllElements(e: Array<Array<Element>>) {
        for (i in e) {
            for (j in i) {
                j.drawItem()
            }
        }
    }

    fun updateHighestNumber(
        elements: Array<Array<Element>>,
        score: Int,
        callback: GameCallback.GameScoreCallback
    ) {
        for (element in elements) {
            for (value in element) {
                if (score < value.number) {
                    callback.setupHighScore(value.number)
                }
            }
        }
    }
}