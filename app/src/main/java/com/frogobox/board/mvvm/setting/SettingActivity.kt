package com.frogobox.board.mvvm.setting

import android.content.Context
import com.frogobox.board.core.BaseActivity
import android.os.Bundle
import com.frogobox.board.R
import android.content.Intent
import android.content.res.Configuration
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import com.frogobox.board.mvvm.main.AboutUsActivity
import com.frogobox.board.mvvm.tutorial.TutorialActivity

class SettingActivity : BaseActivity() {

    companion object {
        private val sBindPreferenceSummaryToValueListener =
            Preference.OnPreferenceChangeListener { preference: Preference, value: Any ->
                val stringValue = value.toString()
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(stringValue)
                    preference.setSummary(
                        if (index >= 0) preference.entries[index] else null
                    )
                } else {
                    preference.summary = stringValue
                }
                true
            }

        private fun isXLargeTablet(context: Context): Boolean {
            return (context.resources.configuration.screenLayout
                    and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
            sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager.getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, "")
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setupDetailActivity("")
        setupShowAdsBanner(findViewById(R.id.ads_banner))
    }

    override fun setupDetailActivity(title: String) {
        super.setupDetailActivity(title)
        supportActionBar!!.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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

    private fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName || SettingFragment::class.java.name == fragmentName
    }

}