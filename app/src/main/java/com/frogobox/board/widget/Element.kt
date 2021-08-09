package com.frogobox.board.widget

import androidx.appcompat.widget.AppCompatButton
import com.frogobox.board.R
import android.content.res.TypedArray
import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import android.os.Build
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import com.bumptech.glide.request.transition.Transition
import com.frogobox.board.util.SingleConst.Pref.PREF_COLOR
import java.util.*

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
 * com.frogobox.basegameboard2048.model
 */
class Element(context: Context) : AppCompatButton(context) {

    @JvmField
    var number = 0

    @JvmField
    var dNumber = 0

    @JvmField
    var posX = 0

    @JvmField
    var posY = 0

    @JvmField
    var dPosX = 0

    @JvmField
    var dPosY = 0

    @JvmField
    var animateMoving = false

    var activateds = false
    var textSized = 24f

    private val settingColor: String?
    private var color = 0

    init {
        isAllCaps = false
        textSize = textSized
        settingColor = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_COLOR, "1")
        setBackgroundResource(R.drawable.background_game_brick)
        setupBackgroundTiles()
    }

    fun drawItem() {
        dNumber = number
        activateds = number != 0
        if (number == 0) {
            visibility = INVISIBLE
            text = ""
        } else {
            text = number.toString()
            if (visibility != VISIBLE) visibility = VISIBLE
        }
        setupGamesTiles()
    }

    private fun justGetColorId(colorRes: Int): Int {
        return context.resources.getColor(colorRes)
    }

    private fun setupBackgroundTiles() {
        when (settingColor) {
            "1" -> {
                setColor(justGetColorId(R.color.tiles_originial_empty))
            }
            "2" -> {
                setColor(justGetColorId(R.color.tiles_empty))
            }
            else -> {
                setColor(justGetColorId(R.color.tiles_originial_empty))
            }
        }
    }

    private fun shuffleArrayString(data: Array<String>): Array<String?> {
        val shuffleTemp = ArrayList(mutableListOf(*data).subList(0, data.size - 1))
        shuffleTemp.shuffle()
        val shuffleString = arrayOfNulls<String>(shuffleTemp.size)
        for (i in 0 until shuffleTemp.size - 1) {
            shuffleString[i] = shuffleTemp[i]
        }
        return shuffleString
    }

    private fun setupGamesTiles() {
        val textColor: IntArray
        var tilesColor: IntArray? = null
        var linkImage: Array<String>? = null
        var isUsingImage = false
        var isUsingUrl = false
        var tilesImage: TypedArray? = null

        when (settingColor) {
            "1" -> {
                // Reskin Image
                tilesImage = context.resources.obtainTypedArray(R.array.background_tiles_reskin)
                textColor = context.resources.getIntArray(R.array.color_text_reskin)
                linkImage = context.resources.getStringArray(R.array.background_image_url)
                isUsingImage = true
                isUsingUrl = true
            }
            "2" -> {
                tilesColor = context.resources.getIntArray(R.array.color_tiles_default)
                textColor = context.resources.getIntArray(R.array.color_text_default)
            }
            else -> {
                tilesColor = context.resources.getIntArray(R.array.color_tiles_original)
                textColor = context.resources.getIntArray(R.array.color_text_original)
            }
        }
        setupTiles(number, isUsingImage, isUsingUrl, textColor, tilesColor, tilesImage, linkImage)
    }

    private fun setTiles(
        number: Int,
        usingImage: Boolean,
        usingUrl: Boolean,
        listTextColor: IntArray,
        listTilesBackground: IntArray?,
        listBackgroundImage: TypedArray?,
        linkImage: Array<String>?
    ) {
        setTextColor(listTextColor[number])
        if (usingImage) {
            if (usingUrl) {
                setBackgroundFromUrl(
                    linkImage!![number],
                    listBackgroundImage!!.getResourceId(number, 0)
                )
            } else {
                setBackgroundImage(listBackgroundImage!!.getResourceId(number, 0))
            }
        } else {
            setColor(listTilesBackground!![number])
        }
    }

    @SuppressLint("ResourceType")
    private fun setupTiles(
        number: Int,
        usingImage: Boolean,
        usingUrl: Boolean,
        listTextColor: IntArray,
        listTilesBackground: IntArray?,
        listBackgroundImage: TypedArray?,
        linkImage: Array<String>?
    ) {
        when (number) {
            0 -> {
                setTextColor(listTextColor[0])
                if (usingImage) {
                    setColor(R.color.tiles_empty)
                } else {
                    setColor(listTilesBackground!![1])
                }
            }
            2 -> {
                setTiles(
                    1,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            4 -> {
                setTiles(
                    2,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            8 -> {
                setTiles(
                    3,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            16 -> {
                setTiles(
                    4,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            32 -> {
                setTiles(
                    5,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            64 -> {
                setTiles(
                    6,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            128 -> {
                setTiles(
                    7,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            256 -> {
                setTiles(
                    8,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            512 -> {
                setTiles(
                    9,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            1024 -> {
                setTiles(
                    10,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            2048 -> {
                setTiles(
                    11,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            4096 -> {
                setTiles(
                    12,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            8192 -> {
                setTiles(
                    13,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
            }
            16384 -> {
                setTiles(
                    14,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
                textSized *= 0.8f
                textSize = textSized
            }
            32768 -> {
                setTiles(
                    15,
                    usingImage,
                    usingUrl,
                    listTextColor,
                    listTilesBackground,
                    listBackgroundImage,
                    linkImage
                )
                textSized *= 0.8f
                textSize = textSized
            }
        }
    }

    private fun setBackgroundImage(res: Int) {
        setBackgroundResource(res)
    }

    // Important Function for request IMAGE
    private fun setBackgroundFromUrl(imageUrl: String, resourceImage: Int) {
        Glide.with(this).load(imageUrl).into(object : SimpleTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                background = resource
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foreground = context.resources.getDrawable(resourceImage)
        }
    }

    private fun setColor(c: Int) {
        color = c
        when (val mBackground = background) {
            is ShapeDrawable -> {
                mBackground.paint.color = c
            }
            is GradientDrawable -> {
                mBackground.setColor(c)
            }
            is ColorDrawable -> {
                mBackground.color = c
            }
        }
    }

    override fun toString(): String {
        return "number: $number"
    }

    fun getNumber(): Int {
        return number
    }

    fun setNumber(i: Int) {
        number = i
    }

    fun setDPosition(i: Int, j: Int) {
        dPosX = i
        dPosY = j
    }

    fun getdPosX(): Int {
        return dPosX
    }

    fun getdPosY(): Int {
        return dPosY
    }

    fun getdNumber(): Int {
        return dNumber
    }

    fun updateFontSize() {
        textSized = (layoutParams.width / 7.0).toFloat()
        textSize = textSized
    }

    fun copy(): Element {
        val temp = Element(context)
        temp.number = number
        temp.dNumber = dNumber
        temp.posX = posX
        temp.posY = posY
        temp.dPosX = dPosX
        temp.dPosY = dPosY
        temp.activateds = activateds
        temp.animateMoving = animateMoving
        temp.textSized = textSized
        temp.color = color
        temp.textSize = textSized
        temp.setColor(color)
        temp.visibility = visibility
        temp.layoutParams = layoutParams
        return temp
    }

}