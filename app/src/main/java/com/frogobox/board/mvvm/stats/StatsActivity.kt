package com.frogobox.board.mvvm.stats

import com.frogobox.board.core.BaseActivity
import com.frogobox.board.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.frogobox.board.util.SingleConst
import kotlinx.android.synthetic.main.activity_stats.*
import java.io.File
import java.lang.Exception

class StatsActivity : BaseActivity() {

    private var TABNAMES = arrayOf("4 x 4", "5 x 5", "6 x 6", "7 x 7")

    private val layouts = intArrayOf(
        R.layout.fragment_stats,
        R.layout.fragment_stats,
        R.layout.fragment_stats,
        R.layout.fragment_stats
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        setupDetailActivity("")
        setupViewPager()

        setupShowAdsBanner(findViewById(R.id.ads_banner))
    }

    override fun setupDetailActivity(title: String) {
        super.setupDetailActivity(title)
        supportActionBar!!.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_stats, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reset -> {
                resetGameStatistics()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager() {
        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mSectionsPagerAdapter = StatsPager(layoutInflater, layouts, TABNAMES, this)
        viewpager.adapter = mSectionsPagerAdapter
        tab_layout.setupWithViewPager(viewpager)
    }

    private fun resetGameStatistics() {
        for (n in 4..7) {
            try {
                val file =
                    File(filesDir, SingleConst.Const.FILE_STATISTIC + n + SingleConst.Ext.TXT)
                file.delete()
            } catch (e: Exception) {
            }
        }
        finish()
        startActivity(intent)
    }

}