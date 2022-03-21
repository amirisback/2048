package com.frogobox.board.mvvm.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.frogobox.board.R
import com.frogobox.board.model.Tutorial

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
class TutorialPager(
    private val layoutInflater: LayoutInflater,
    private val layouts: IntArray
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(layouts[position], container, false)
        container.addView(view)

        val imageView = view.findViewById<ImageView>(R.id.iv_tutorial)
        val tv_title = view.findViewById<TextView>(R.id.tv_tutorial_title)
        val tv_subtitle = view.findViewById<TextView>(R.id.tv_tutorial_subtitle)

        val tutorial = setupData(container.context)

        when (position) {
            0 -> {
                setupUI(container.context, tutorial[0], tv_title, tv_subtitle, imageView)
            }
            1 -> {
                setupUI(container.context, tutorial[1], tv_title, tv_subtitle, imageView)
            }
            2 -> {
                setupUI(container.context, tutorial[2], tv_title, tv_subtitle, imageView)
            }
            3 -> {
                setupUI(container.context, tutorial[3], tv_title, tv_subtitle, imageView)
            }
        }
        return view
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

    private fun setupUI(
        context: Context,
        data: Tutorial,
        tvTitle: TextView,
        tvSubtitle: TextView,
        image: ImageView
    ) {
        tvTitle.text = data.title
        tvSubtitle.text = data.subTitle
        Glide.with(context).load(data.imageRes).into(image)
    }

    private fun setupData(context: Context): MutableList<Tutorial> {
        val data = mutableListOf<Tutorial>()
        data.add(
            Tutorial(
                context.getString(R.string.Tutorial_Titel),
                context.getString(R.string.Tutorial_Instruction),
                R.drawable.ic_launcher
            )
        )
        data.add(
            Tutorial(
                context.getString(R.string.Tutorial_Titel_Move),
                context.getString(R.string.Tutorial_Move),
                R.drawable.tutorial_move_o
            )
        )
        data.add(
            Tutorial(
                context.getString(R.string.Tutorial_Titel_Move),
                context.getString(R.string.Tutorial_Move),
                R.drawable.tutorial_swipe_o
            )
        )
        data.add(
            Tutorial(
                context.getString(R.string.Tutorial_Titel_Add),
                context.getString(R.string.Tutorial_Add),
                R.drawable.tutorial_add_o
            )
        )
        return data
    }

}