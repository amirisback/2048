package com.frogobox.basegameboard2048.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.frogobox.basegameboard2048.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Pref.PREF_COLOR;

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

public class Element extends androidx.appcompat.widget.AppCompatButton {
    public int number = 0;
    public int dNumber = 0;
    public int posX = 0;
    public int posY = 0;
    public int dPosX = 0;
    public int dPosY = 0;
    public boolean activated;
    public boolean animateMoving = false;
    public float textSize = 24;
    Context context;
    int color;
    private String settingColor;


    public Element(Context c) {
        super(c);
        context = c;
        setAllCaps(false);
        setTextSize(textSize);
        settingColor = PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_COLOR, "1");
        setBackgroundResource(R.drawable.background_game_brick);
        setupBackgroundTiles(); // SetupBackgroundTiles

    }

    public void drawItem() {
        dNumber = number;
        activated = (number != 0);
        if (number == 0) {
            setVisibility(View.INVISIBLE);
            setText("");
        } else {
            setText("" + number);
            if (getVisibility() != View.VISIBLE)
                setVisibility(View.VISIBLE);
        }
        setupGamesTiles(); // SetupGamesTiles
    }

    private int justGetColorId(int colorRes) {
        return context.getResources().getColor(colorRes);
    }

    private void setupBackgroundTiles() {
        if (settingColor.equals("1")) {
            // Reskin Background
            setColor(justGetColorId(R.color.tiles_empty));
        } else if (settingColor.equals("2")) {
            setColor(justGetColorId(R.color.tiles_empty));
        } else {
            setColor(justGetColorId(R.color.tiles_originial_empty));
        }
    }

    private String[] shuffleArrayString(String[] data) {
        ArrayList<String> shuffleTemp = new ArrayList<>(Arrays.asList(data).subList(0, data.length - 1));
        Collections.shuffle(shuffleTemp);
        String[] shuffleString = new String[shuffleTemp.size()];
        for (int i = 0; i < shuffleTemp.size() - 1; i++) {
            shuffleString[i] = shuffleTemp.get(i);
        }
        return shuffleString;
    }
    
    private void setupGamesTiles() {
        int[] textColor;
        int[] tilesColor = null;
        String[] linkImage = null;
        boolean isUsingImage = false;
        boolean isUsingUrl = false;
        TypedArray tilesImage = null;

        if (settingColor.equals("1")) {
            // Reskin Image
            tilesImage = context.getResources().obtainTypedArray(R.array.background_tiles_reskin);
            textColor = context.getResources().getIntArray(R.array.color_text_reskin);
            linkImage = context.getResources().getStringArray(R.array.background_image_url);
            isUsingImage = true;
            isUsingUrl = true;
        } else if (settingColor.equals("2")) {
            tilesColor = context.getResources().getIntArray(R.array.color_tiles_default);
            textColor = context.getResources().getIntArray(R.array.color_text_default);
        } else {
            tilesColor = context.getResources().getIntArray(R.array.color_tiles_original);
            textColor = context.getResources().getIntArray(R.array.color_text_original);
        }

        setupTiles(number, isUsingImage, isUsingUrl, textColor, tilesColor, tilesImage, linkImage);
    }

    @SuppressLint("ResourceType")
    private void setupTiles(int number, boolean usingImage, boolean usingUrl, int[] listTextColor, int[] listTilesBackground, TypedArray listBackgroundImage, String[] linkImage) {
        switch (number) {
            case 0:
                setTextColor(listTextColor[0]);
                if (usingImage) {
                    setColor(R.color.tiles_empty);
                } else {
                    setColor(listTilesBackground[1]);
                }
                break;
            case 2:
                setTextColor(listTextColor[1]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[1], listBackgroundImage.getResourceId(1, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(1, 0));
                    }
                } else {
                    setColor(listTilesBackground[1]);
                }
                break;
            case 4:
                setTextColor(listTextColor[2]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[2], listBackgroundImage.getResourceId(2, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(2, 0));
                    }
                } else {
                    setColor(listTilesBackground[2]);
                }
                break;
            case 8:
                setTextColor(listTextColor[3]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[3], listBackgroundImage.getResourceId(3, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(3, 0));
                    }
                } else {
                    setColor(listTilesBackground[3]);
                }
                break;
            case 16:
                setTextColor(listTextColor[4]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[4], listBackgroundImage.getResourceId(4, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(4, 0));
                    }
                } else {
                    setColor(listTilesBackground[4]);
                }
                break;
            case 32:
                setTextColor(listTextColor[5]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[5], listBackgroundImage.getResourceId(5, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(5, 0));
                    }
                } else {
                    setColor(listTilesBackground[5]);
                }
                break;
            case 64:
                setTextColor(listTextColor[6]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[6], listBackgroundImage.getResourceId(6, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(6, 0));
                    }
                } else {
                    setColor(listTilesBackground[6]);
                }
                break;
            case 128:
                setTextColor(listTextColor[7]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[7], listBackgroundImage.getResourceId(7, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(7, 0));
                    }
                } else {
                    setColor(listTilesBackground[7]);
                }
                break;
            case 256:
                setTextColor(listTextColor[8]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[8], listBackgroundImage.getResourceId(8, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(8, 0));
                    }
                } else {
                    setColor(listTilesBackground[8]);
                }
                break;
            case 512:
                setTextColor(listTextColor[9]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[9], listBackgroundImage.getResourceId(9, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(9, 0));
                    }
                } else {
                    setColor(listTilesBackground[9]);
                }
                break;
            case 1024:
                setTextColor(listTextColor[10]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[10], listBackgroundImage.getResourceId(10, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(10, 0));
                    }
                } else {
                    setColor(listTilesBackground[10]);
                }
                break;
            case 2048:
                setTextColor(listTextColor[11]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[11], listBackgroundImage.getResourceId(11, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(11, 0));
                    }
                } else {
                    setColor(listTilesBackground[11]);
                }
                break;
            case 4096:
                setTextColor(listTextColor[12]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[12], listBackgroundImage.getResourceId(12, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(12, 0));
                    }
                } else {
                    setColor(listTilesBackground[12]);
                }
                break;
            case 8192:
                setTextColor(listTextColor[13]);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[13], listBackgroundImage.getResourceId(13, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(13, 0));
                    }
                } else {
                    setColor(listTilesBackground[13]);
                }
                break;
            case 16384:
                setTextColor(listTextColor[14]);
                textSize = textSize * 0.8f;
                setTextSize(textSize);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[14], listBackgroundImage.getResourceId(14, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(14, 0));
                    }
                } else {
                    setColor(listTilesBackground[14]);
                }
                break;
            case 32768:
                setTextColor(listTextColor[15]);
                textSize = textSize * 0.8f;
                setTextSize(textSize);
                if (usingImage) {
                    if (usingUrl) {
                        setBackgroundFromUrl(linkImage[15], listBackgroundImage.getResourceId(15, 0));
                    } else {
                        setBackgroundImage(listBackgroundImage.getResourceId(15, 0));
                    }
                } else {
                    setColor(listTilesBackground[15]);
                }
                break;
        }
    }

    private void setBackgroundImage(int res) {
        setBackgroundResource(res);
    }

    // Important Function for request IMAGE
    private void setBackgroundFromUrl(String imageUrl, int resourceImage) {
        Glide.with(this).load(imageUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    setBackground(resource);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(context.getDrawable(resourceImage));
        }
    }

    private void setColor(int c) {
        color = c;
        Drawable background = getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(c);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(c);
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(c);
        }
    }

    public String toString() {
        return "number: " + number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int i) {
        number = i;
    }

    public void setDPosition(int i, int j) {
        dPosX = i;
        dPosY = j;
    }

    public int getdPosX() {
        return dPosX;
    }

    public int getdPosY() {
        return dPosY;
    }

    public int getdNumber() {
        return dNumber;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void updateFontSize() {
        textSize = (float) (getLayoutParams().width / 7.0);
        setTextSize(textSize);
    }

    public Element copy() {
        Element temp = new Element(context);
        temp.number = number;
        temp.dNumber = dNumber;
        temp.posX = posX;
        temp.posY = posY;
        temp.dPosX = dPosX;
        temp.dPosY = dPosY;
        temp.activated = activated;
        temp.animateMoving = animateMoving;
        temp.textSize = textSize;
        temp.color = color;
        temp.setTextSize(textSize);
        //temp.setBackgroundResource(backGroundResource);
        temp.setColor(color);
        temp.setVisibility(getVisibility());
        temp.setLayoutParams(getLayoutParams());
        return temp;
    }
}
