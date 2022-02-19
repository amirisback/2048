package com.frogobox.board.mvvm.main

import android.content.SharedPreferences
import com.frogobox.board.R
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.frogobox.board.util.SingleConst
import android.os.Bundle
import android.view.LayoutInflater
import android.content.Intent
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.TaskStackBuilder
import com.frogobox.board.core.BaseBindingActivity
import com.frogobox.board.databinding.ActivityMainBinding
import com.frogobox.board.mvvm.game.GameActivity
import com.frogobox.board.mvvm.setting.SettingActivity
import com.frogobox.board.mvvm.stats.StatsActivity
import java.lang.Exception

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private var currentPage = 0
    private var editor: SharedPreferences.Editor? = null

    private val layouts = intArrayOf(
        R.layout.fragment_games_box,
        R.layout.fragment_games_box,
        R.layout.fragment_games_box,
        R.layout.fragment_games_box
    )

    private val gameResumeable = booleanArrayOf(
        false,
        false,
        false,
        false
    )


    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbars.toolbarMain)

        val directory = filesDir
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                Log.i("files", file.name)
                for (j in gameResumeable.indices) {
                    if (file.name == SingleConst.Const.FILE_STATE + (j + 4) + SingleConst.Ext.TXT) gameResumeable[j] =
                        true
                }
            }
        }

        addBottomDots(0)
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                setupShowAdsInterstitial()
                return true
            }
            R.id.toolbar_menu_stats -> {
                startActivity(Intent(this, StatsActivity::class.java))
                setupShowAdsInterstitial()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val preferences =
            applicationContext.getSharedPreferences(SingleConst.Pref.PREF_MY, MODE_PRIVATE)
        editor = preferences.edit()
        currentPage = preferences.getInt(SingleConst.Pref.PREF_CURRENT_PAGE, 0)
        binding.viewPager.currentItem = currentPage
        updateButtons(currentPage)
    }

    private fun setupViewPager() {

        val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainPager = MainPager(layoutInflater, layouts)
        binding.viewPager.apply {
            adapter = mainPager

            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    addBottomDots(position)
                    currentPage = position
                    editor!!.putInt(SingleConst.Pref.PREF_CURRENT_PAGE, currentPage)
                    editor!!.commit()
                    updateButtons(position)
                }

                override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
                override fun onPageScrollStateChanged(arg0: Int) {}
            })
        }

    }

    private fun addListener(b1: Button, b2: Button, n: Int) {
        b1.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra(SingleConst.Extra.EXTRA_N, n)
            intent.putExtra(SingleConst.Extra.EXTRA_POINTS, 0)
            intent.putExtra(SingleConst.Extra.EXTRA_NEW, true)
            intent.putExtra(
                SingleConst.Extra.EXTRA_FILENAME,
                SingleConst.Const.FILE_STATE + n + SingleConst.Ext.TXT
            )
            intent.putExtra(SingleConst.Extra.EXTRA_UNDO, false)
            createBackStack(intent)
            setupShowAdsInterstitial()
        }

        b2.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra(SingleConst.Extra.EXTRA_N, n)
            intent.putExtra(SingleConst.Extra.EXTRA_NEW, false)
            intent.putExtra(
                SingleConst.Extra.EXTRA_FILENAME,
                SingleConst.Const.FILE_STATE + n + SingleConst.Ext.TXT
            )
            intent.putExtra(SingleConst.Extra.EXTRA_UNDO, false)
            createBackStack(intent)
            setupShowAdsInterstitial()
        }
    }

    private fun createBackStack(intent: Intent) {
        val builder = TaskStackBuilder.create(this)
        builder.addNextIntentWithParentStack(intent)
        builder.startActivities()
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts.size)
        val activeColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val inactiveColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        binding.apply {

            dotsLayout.removeAllViews()
            for (i in dots.indices) {
                dots[i] = TextView(this@MainActivity)
                dots[i]!!.text = Html.fromHtml("&#8226;")
                dots[i]!!.textSize = 35f
                dots[i]!!.setTextColor(inactiveColor)
                dotsLayout.addView(dots[i])
            }
            if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(activeColor)
        }
    }

    fun updateButtons(position: Int) {
        val newGameButton = findViewById<Button>(R.id.button_newGame)
        val continueButton = findViewById<Button>(R.id.button_continueGame)
        try {
            if (gameResumeable[position]) continueButton.setBackgroundResource(R.drawable.background_button_active) else continueButton.setBackgroundResource(
                R.drawable.backgrorund_button_non_active
            )
            continueButton.isEnabled = gameResumeable[position]
        } catch (aie: Exception) {
            aie.printStackTrace()
        }
        addListener(newGameButton, continueButton, position + 4)
    }

}