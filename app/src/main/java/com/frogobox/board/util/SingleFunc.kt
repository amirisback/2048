package  com.frogobox.board.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import com.frogobox.board.BuildConfig
import com.frogobox.board.R
import com.frogobox.board.model.GameState
import com.frogobox.board.model.GameStatistics
import com.frogobox.board.mvvm.game.GameActivity
import com.frogobox.board.util.SingleConst.Dir.DIR_NAME
import com.frogobox.board.util.SingleConst.Dir.VIDEO_FILE_NAME
import com.frogobox.board.widget.Element
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


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

}