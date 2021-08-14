package com.frogobox.board.mvvm.tutorial

import com.frogobox.board.R
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.text.Html
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import com.frogobox.board.core.BaseBindingActivity
import com.frogobox.board.databinding.ActivityTutorialBinding
import com.frogobox.board.mvvm.main.MainActivity
import kotlinx.android.synthetic.main.fragment_tutorial_slide.*

class TutorialActivity : BaseBindingActivity<ActivityTutorialBinding>() {

    private val tutorialController by lazy {
        TutorialController(this)
    }

    private val layouts = intArrayOf(
        R.layout.fragment_tutorial_slide,
        R.layout.fragment_tutorial_slide,
        R.layout.fragment_tutorial_slide,
        R.layout.fragment_tutorial_slide
    )

    override fun setupViewBinding(): ActivityTutorialBinding {
        return ActivityTutorialBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        binding.apply {
            changeStatusBarColor()
            addBottomDots(0)

            setupViewPager()

            btnSkip.setOnClickListener {
                launchHomeScreen()
            }

            btnNext.setOnClickListener {
                val current = binding.viewPager.currentItem + 1
                if (current < layouts.size) {
                    viewPager.currentItem = current
                } else {
                    launchHomeScreen()
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.apply {

            val layoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val tutorialPager = TutorialPager(layoutInflater, layouts)

            viewPager.adapter = tutorialPager
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    addBottomDots(position)
                    // changing the next button text 'NEXT' / 'GOT IT'
                    if (position == layouts.size - 1) {
                        // last page. make button text to GOT IT
                        btnNext.text = getString(R.string.okay)
                        btnSkip.visibility = View.GONE
                    } else {
                        // still pages are left
                        btnNext.text = getString(R.string.next)
                        btnSkip!!.visibility = View.VISIBLE
                    }
                }

                override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
                override fun onPageScrollStateChanged(arg0: Int) {}
            })
        }
    }

    private fun addBottomDots(currentPage: Int) {
        binding.apply {
            val dots = arrayOfNulls<TextView>(layouts.size)
            val activeColor = ContextCompat.getColor(this@TutorialActivity, R.color.dot_light_screen)
            val inactiveColor = ContextCompat.getColor(this@TutorialActivity, R.color.dot_dark_screen)
            dotsLayout.removeAllViews()
            for (i in dots.indices) {
                dots[i] = TextView(this@TutorialActivity)
                dots[i]!!.text = Html.fromHtml("&#8226;")
                dots[i]!!.textSize = 35f
                dots[i]!!.setTextColor(inactiveColor)
                dotsLayout.addView(dots[i])
            }
            if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(activeColor)
        }
    }

    private fun launchHomeScreen() {
        if (tutorialController.isFirstTimeLaunch) {
            val intent = Intent(this@TutorialActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            tutorialController.isFirstTimeLaunch = false
            startActivity(intent)
        }
        finish()
    }

    private fun changeStatusBarColor() {
        val windowS = window
        windowS.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        windowS.statusBarColor = Color.TRANSPARENT
        windowS.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

}