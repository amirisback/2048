package com.frogobox.board.mvvm.setting

import android.os.Bundle
import com.frogobox.board.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.frogobox.board.core.BaseBindingActivity
import com.frogobox.board.databinding.ActivitySettingBinding
import com.frogobox.board.mvvm.main.AboutUsActivity
import com.frogobox.board.mvvm.tutorial.TutorialActivity

class SettingActivity : BaseBindingActivity<ActivitySettingBinding>() {

    override fun setupViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupDetailActivity("")
        setupShowAdsBanner(binding.ads.adsBanner)
    }

    override fun setupDetailActivity(title: String) {
        super.setupDetailActivity(title)
        supportActionBar!!.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.toolbar_menu_about) {
            startActivity(Intent(this, AboutUsActivity::class.java))
            return true
        } else if (id == R.id.toolbar_menu_help) {
            startActivity(Intent(this, TutorialActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}