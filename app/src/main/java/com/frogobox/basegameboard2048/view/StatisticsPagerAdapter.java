package com.frogobox.basegameboard2048.view;

import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.frogobox.basegameboard2048.R;
import com.frogobox.basegameboard2048.model.GameStatistics;
import com.frogobox.basegameboard2048.util.helper.ConstHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Const.FILE_STATISTIC;
import static com.frogobox.basegameboard2048.util.helper.ConstHelper.Ext.TXT;

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
public class StatisticsPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private int[] layouts;
    private String[] tabNames;
    private ContextWrapper contextWrapper;

    public StatisticsPagerAdapter(LayoutInflater layoutInflater, int[] layouts, String[] tabNames, ContextWrapper contextWrapper) {
        this.layoutInflater = layoutInflater;
        this.layouts = layouts;
        this.tabNames = tabNames;
        this.contextWrapper = contextWrapper;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        TextView highestNumber = view.findViewById(R.id.highest_number4);
        TextView timePlayed = view.findViewById(R.id.time_played4);
        TextView undo = view.findViewById(R.id.undo_number4);
        TextView moves = view.findViewById(R.id.moves_All4);
        TextView tpm = view.findViewById(R.id.time_swipes4);
        TextView rekord = view.findViewById(R.id.highest_score4);
        ImageView img = view.findViewById(R.id.stat_img4);
        switch (position) {
            case 0:
                Glide.with(container.getContext()).load(R.drawable.layout4x4_o).into(img);
                break;
            case 1:
                Glide.with(container.getContext()).load(R.drawable.layout5x5_o).into(img);
                break;
            case 2:
                Glide.with(container.getContext()).load(R.drawable.layout6x6_o).into(img);
                break;
            case 3:
                Glide.with(container.getContext()).load(R.drawable.layout7x7_o).into(img);
                break;
        }
        GameStatistics gameStatistics = readStatisticsFromFile(position + 4);
        highestNumber.setText("" + gameStatistics.getHighestNumber());
        timePlayed.setText(formatMillis(gameStatistics.getTimePlayed()));
        undo.setText("" + gameStatistics.getUndo());
        moves.setText("" + gameStatistics.getMoves());
        if (gameStatistics.getMoves() != 0)
            tpm.setText("" + formatSmallMillis(gameStatistics.getTimePlayed() / gameStatistics.getMoves()));
        else
            tpm.setText("0");
        rekord.setText("" + gameStatistics.getRecord());

        return view;
    }

    private String formatSmallMillis(long timeInMillis) {
        String sign = "";
        if (timeInMillis < 0) {
            sign = "-";
            timeInMillis = Math.abs(timeInMillis);
        }
        Double seconds = new Double(((double) timeInMillis) / (double) TimeUnit.SECONDS.toMillis(1));
        StringBuilder sb = new StringBuilder(",##0.00");
        DecimalFormat df = new DecimalFormat(sb.toString());
        df.setRoundingMode(RoundingMode.HALF_UP);
        final StringBuilder formatted = new StringBuilder(20);
        formatted.append(sign);
        formatted.append(df.format(seconds));
        formatted.append(" s");
        return formatted.toString();
    }

    private String formatMillis(long timeInMillis) {
        String sign = "";
        if (timeInMillis < 0) {
            sign = "-";
            timeInMillis = Math.abs(timeInMillis);
        }
        Double seconds = new Double(((double) timeInMillis) / (double) TimeUnit.HOURS.toMillis(1));
        StringBuilder sb = new StringBuilder(",##0.00");
        DecimalFormat df = new DecimalFormat(sb.toString());
        df.setRoundingMode(RoundingMode.HALF_UP);
        final StringBuilder formatted = new StringBuilder(20);
        formatted.append(sign);
        formatted.append(df.format(seconds));
        formatted.append(" h");
        return formatted.toString();
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

    private GameStatistics readStatisticsFromFile(int n) {
        GameStatistics gS = new GameStatistics(n);
        try {
            File file = new File(contextWrapper.getFilesDir(), ConstHelper.Const.FILE_STATISTIC + n + ConstHelper.Ext.TXT);
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gS = (GameStatistics) in.readObject();
            in.close();
            fileIn.close();
        } catch (InvalidClassException ice) {
            File file = new File(contextWrapper.getFilesDir(), ConstHelper.Const.FILE_STATISTIC + n + ConstHelper.Ext.TXT);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gS;
    }
}
