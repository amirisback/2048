package com.frogobox.board.mvvm.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.frogobox.board.R;

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
public class MainPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private int[] layouts;

    public MainPagerAdapter(LayoutInflater layoutInflater, int[] layouts) {
        this.layoutInflater = layoutInflater;
        this.layouts = layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        ImageView imageView = view.findViewById(R.id.main_menu_img);;
        TextView textView = view.findViewById(R.id.tv_size_box);
        switch (position) {
            case 0:
                textView.setText("4 x 4");
                Glide.with(container.getContext()).load(R.drawable.layout4x4_o).into(imageView);
                break;
            case 1:
                textView.setText("5 x 5");
                Glide.with(container.getContext()).load(R.drawable.layout5x5_o).into(imageView);
                break;
            case 2:
                textView.setText("6 x 6");
                Glide.with(container.getContext()).load(R.drawable.layout6x6_o).into(imageView);
                break;
            case 3:
                textView.setText("7 x 7");
                Glide.with(container.getContext()).load(R.drawable.layout7x7_o).into(imageView);
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
