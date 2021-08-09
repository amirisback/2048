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
        setupBackgroundTiles() // SetupBackgroundTiles
    }

    fun drawItem() {
        dNumber = number
        activateds = number != 0
        if (number == 0) {
            visibility = INVISIBLE
            text = ""
        } else {
            text = "" + number
            if (visibility != VISIBLE) visibility = VISIBLE
        }
        setupGamesTiles() // SetupGamesTiles
    }

    private fun justGetColorId(colorRes: Int): Int {
        return context.resources.getColor(colorRes)
    }

    private fun setupBackgroundTiles() {
        if (settingColor == "1") {
            setColor(justGetColorId(R.color.tiles_originial_empty))
        } else if (settingColor == "2") {
            setColor(justGetColorId(R.color.tiles_empty))
        } else {
            setColor(justGetColorId(R.color.tiles_originial_empty))
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
                setTextColor(listTextColor[1])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![1],
                            listBackgroundImage!!.getResourceId(1, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(1, 0))
                    }
                } else {
                    setColor(listTilesBackground!![1])
                }
            }
            4 -> {
                setTextColor(listTextColor[2])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![2],
                            listBackgroundImage!!.getResourceId(2, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(2, 0))
                    }
                } else {
                    setColor(listTilesBackground!![2])
                }
            }
            8 -> {
                setTextColor(listTextColor[3])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![3],
                            listBackgroundImage!!.getResourceId(3, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(3, 0))
                    }
                } else {
                    setColor(listTilesBackground!![3])
                }
            }
            16 -> {
                setTextColor(listTextColor[4])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![4],
                            listBackgroundImage!!.getResourceId(4, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(4, 0))
                    }
                } else {
                    setColor(listTilesBackground!![4])
                }
            }
            32 -> {
                setTextColor(listTextColor[5])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![5],
                            listBackgroundImage!!.getResourceId(5, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(5, 0))
                    }
                } else {
                    setColor(listTilesBackground!![5])
                }
            }
            64 -> {
                setTextColor(listTextColor[6])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![6],
                            listBackgroundImage!!.getResourceId(6, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(6, 0))
                    }
                } else {
                    setColor(listTilesBackground!![6])
                }
            }
            128 -> {
                setTextColor(listTextColor[7])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![7],
                            listBackgroundImage!!.getResourceId(7, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(7, 0))
                    }
                } else {
                    setColor(listTilesBackground!![7])
                }
            }
            256 -> {
                setTextColor(listTextColor[8])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![8],
                            listBackgroundImage!!.getResourceId(8, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(8, 0))
                    }
                } else {
                    setColor(listTilesBackground!![8])
                }
            }
            512 -> {
                setTextColor(listTextColor[9])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![9],
                            listBackgroundImage!!.getResourceId(9, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(9, 0))
                    }
                } else {
                    setColor(listTilesBackground!![9])
                }
            }
            1024 -> {
                setTextColor(listTextColor[10])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![10],
                            listBackgroundImage!!.getResourceId(10, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(10, 0))
                    }
                } else {
                    setColor(listTilesBackground!![10])
                }
            }
            2048 -> {
                setTextColor(listTextColor[11])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![11],
                            listBackgroundImage!!.getResourceId(11, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(11, 0))
                    }
                } else {
                    setColor(listTilesBackground!![11])
                }
            }
            4096 -> {
                setTextColor(listTextColor[12])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![12],
                            listBackgroundImage!!.getResourceId(12, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(12, 0))
                    }
                } else {
                    setColor(listTilesBackground!![12])
                }
            }
            8192 -> {
                setTextColor(listTextColor[13])
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![13],
                            listBackgroundImage!!.getResourceId(13, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(13, 0))
                    }
                } else {
                    setColor(listTilesBackground!![13])
                }
            }
            16384 -> {
                setTextColor(listTextColor[14])
                textSized *= 0.8f
                textSize = textSized
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![14],
                            listBackgroundImage!!.getResourceId(14, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(14, 0))
                    }
                } else {
                    setColor(listTilesBackground!![14])
                }
            }
            32768 -> {
                setTextColor(listTextColor[15])
                textSized *= 0.8f
                textSize = textSized
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(
                            linkImage!![15],
                            listBackgroundImage!!.getResourceId(15, 0)
                        )
                    } else {
                        setBackgroundImage(listBackgroundImage!!.getResourceId(15, 0))
                    }
                } else {
                    setColor(listTilesBackground!![15])
                }
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
        val background = background
        if (background is ShapeDrawable) {
            background.paint.color = c
        } else if (background is GradientDrawable) {
            background.setColor(c)
        } else if (background is ColorDrawable) {
            background.color = c
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