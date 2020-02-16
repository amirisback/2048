package com.frogobox.basegameboard2048.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.frogobox.basegameboard2048.R;

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
public class TutorialPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private int[] layouts;

    public TutorialPagerAdapter(LayoutInflater layoutInflater, int[] layouts) {
        this.layoutInflater = layoutInflater;
        this.layouts = layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        ImageView imageView = view.findViewById(R.id.iv_tutorial);
        TextView tv_title = view.findViewById(R.id.tv_tutorial_title);
        TextView tv_subtitle = view.findViewById(R.id.tv_tutorial_subtitle);

        switch (position) {
            case 0:
                tv_title.setText(R.string.Tutorial_Titel);
                tv_subtitle.setText(R.string.Tutorial_Instruction);
                Glide.with(container.getContext()).load(R.drawable.ic_launcher).into(imageView);
                break;
            case 1:
                tv_title.setText(R.string.Tutorial_Titel_Move);
                tv_subtitle.setText(R.string.Tutorial_Move);
                Glide.with(container.getContext()).load(R.drawable.tutorial_move_o).into(imageView);
                break;
            case 2:
                tv_title.setText(R.string.Tutorial_Titel_Move);
                tv_subtitle.setText(R.string.Tutorial_Move);
                Glide.with(container.getContext()).load(R.drawable.tutorial_swipe_o).into(imageView);
                break;
            case 3:
                tv_title.setText(R.string.Tutorial_Titel_Add);
                tv_subtitle.setText(R.string.Tutorial_Add);
                Glide.with(container.getContext()).load(R.drawable.tutorial_add_o).into(imageView);
                break;
        }
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}