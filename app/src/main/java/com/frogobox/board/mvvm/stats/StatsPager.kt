package com.frogobox.board.mvvm.stats

import android.view.LayoutInflater
import android.content.ContextWrapper
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.frogobox.board.R
import com.bumptech.glide.Glide
import com.frogobox.board.model.GameStatistics
import com.frogobox.board.util.SingleConst
import java.io.File
import java.io.FileInputStream
import java.io.InvalidClassException
import java.io.ObjectInputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.math.abs

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseGameBoard2048
 * Copyright (C) 03/01/2020.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basegameboard2048.view.pager
 */

class StatsPager(
    private val layoutInflater: LayoutInflater,
    private val layouts: IntArray,
    private val tabNames: Array<String>,
    private val contextWrapper: ContextWrapper
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(layouts[position], container, false)
        container.addView(view)

        val highestNumber = view.findViewById<TextView>(R.id.highest_number4)
        val timePlayed = view.findViewById<TextView>(R.id.time_played4)
        val undo = view.findViewById<TextView>(R.id.undo_number4)
        val moves = view.findViewById<TextView>(R.id.moves_All4)
        val tpm = view.findViewById<TextView>(R.id.time_swipes4)
        val record = view.findViewById<TextView>(R.id.highest_score4)
        val img = view.findViewById<ImageView>(R.id.stat_img4)

        when (position) {
            0 -> Glide.with(container.context).load(R.drawable.layout4x4_o).into(img)
            1 -> Glide.with(container.context).load(R.drawable.layout5x5_o).into(img)
            2 -> Glide.with(container.context).load(R.drawable.layout6x6_o).into(img)
            3 -> Glide.with(container.context).load(R.drawable.layout7x7_o).into(img)
        }

        val gameStatistics = readStatisticsFromFile(position + 4)
        highestNumber.text = "${gameStatistics.getHighestNumber()}"
        timePlayed.text = formatMillis(gameStatistics.timePlayed)
        undo.text = "${gameStatistics.undo}"
        moves.text = "${gameStatistics.moves}"

        if (gameStatistics.moves != 0L) {
            val tempText = formatSmallMillis(gameStatistics.timePlayed / gameStatistics.moves)
            tpm.text = tempText
        } else {
            tpm.text = "0"
        }

        record.text = "${gameStatistics.record}"
        return view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabNames[position]
    }

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    private fun readStatisticsFromFile(n: Int): GameStatistics {
        var gS = GameStatistics(n)
        try {
            val file = File(
                contextWrapper.filesDir,
                SingleConst.Const.FILE_STATISTIC + n + SingleConst.Ext.TXT
            )
            val fileIn = FileInputStream(file)
            val `in` = ObjectInputStream(fileIn)
            gS = `in`.readObject() as GameStatistics
            `in`.close()
            fileIn.close()
        } catch (ice: InvalidClassException) {
            val file = File(
                contextWrapper.filesDir,
                SingleConst.Const.FILE_STATISTIC + n + SingleConst.Ext.TXT
            )
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gS
    }

    private fun formatSmallMillis(timeInMilli: Long): String {
        var timeInMillis = timeInMilli
        var sign = ""
        if (timeInMillis < 0) {
            sign = "-"
            timeInMillis = abs(timeInMillis)
        }

        val seconds = timeInMillis.toDouble() / TimeUnit.SECONDS.toMillis(1).toDouble()
        val sb = StringBuilder(",##0.00")
        val df = DecimalFormat(sb.toString())
        val formatted = StringBuilder(20)

        df.roundingMode = RoundingMode.HALF_UP
        formatted.append(sign)
        formatted.append(df.format(seconds))
        formatted.append(" s")

        return formatted.toString()
    }

    private fun formatMillis(timeInMilli: Long): String {
        var timeInMillis = timeInMilli
        var sign = ""
        if (timeInMillis < 0) {
            sign = "-"
            timeInMillis = abs(timeInMillis)
        }

        val seconds = timeInMillis.toDouble() / TimeUnit.HOURS.toMillis(1).toDouble()
        val sb = StringBuilder(",##0.00")
        val df = DecimalFormat(sb.toString())
        val formatted = StringBuilder(20)

        df.roundingMode = RoundingMode.HALF_UP
        formatted.append(sign)
        formatted.append(df.format(seconds))
        formatted.append(" h")

        return formatted.toString()
    }

}