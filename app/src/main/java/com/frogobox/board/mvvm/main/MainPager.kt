package com.frogobox.board.mvvm.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.frogobox.board.R

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseGameBoard2048
 * Copyright (C) 02/01/2020.
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
class MainPager(
    private val layoutInflater: LayoutInflater,
    private val layouts: IntArray
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(layouts[position], container, false)

        container.addView(view)

        val imageView = view.findViewById<ImageView>(R.id.main_menu_img)
        val textView = view.findViewById<TextView>(R.id.tv_size_box)

        when (position) {
            0 -> {
                textView.text = "4 x 4"
                Glide.with(container.context).load(R.drawable.layout4x4_o).into(imageView)
            }
            1 -> {
                textView.text = "5 x 5"
                Glide.with(container.context).load(R.drawable.layout5x5_o).into(imageView)
            }
            2 -> {
                textView.text = "6 x 6"
                Glide.with(container.context).load(R.drawable.layout6x6_o).into(imageView)
            }
            3 -> {
                textView.text = "7 x 7"
                Glide.with(container.context).load(R.drawable.layout7x7_o).into(imageView)
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
}