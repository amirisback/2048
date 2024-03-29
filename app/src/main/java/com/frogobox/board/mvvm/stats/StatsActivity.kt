package com.frogobox.board.mvvm.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.frogobox.board.R
import com.frogobox.board.core.BaseBindingActivity
import com.frogobox.board.databinding.ActivityStatsBinding
import com.frogobox.board.util.SingleConst
import java.io.File

class StatsActivity : BaseBindingActivity<ActivityStatsBinding>() {

    private var TABNAMES = arrayOf("4 x 4", "5 x 5", "6 x 6", "7 x 7")

    private val layouts = intArrayOf(
        R.layout.fragment_stats,
        R.layout.fragment_stats,
        R.layout.fragment_stats,
        R.layout.fragment_stats
    )

    override fun setupViewBinding(): ActivityStatsBinding {
        return ActivityStatsBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupOnCreate(savedInstanceState: Bundle?) {
        setupDetailActivity("")
        setupViewPager()
        showAdBanner(binding.ads.adsBanner)
    }

    override fun setupDetailActivity(title: String) {
        super.setupDetailActivity(title)
        supportActionBar!!.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
        binding.apply {
            val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val mSectionsPagerAdapter =
                StatsPager(layoutInflater, layouts, TABNAMES, this@StatsActivity)
            viewpager.adapter = mSectionsPagerAdapter
            tabLayout.setupWithViewPager(viewpager)
        }
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