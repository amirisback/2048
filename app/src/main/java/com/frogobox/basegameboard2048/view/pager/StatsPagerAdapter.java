package com.frogobox.basegameboard2048.view.pager;

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
public class StatsPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private int[] layouts;
    private String[] tabNames;
    private ContextWrapper contextWrapper;

    public StatsPagerAdapter(LayoutInflater layoutInflater, int[] layouts, String[] tabNames, ContextWrapper contextWrapper) {
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
        ImageView img = new ImageView(container.getContext());
        TextView highestNumber = new TextView(container.getContext());
        TextView timePlayed = new TextView(container.getContext());
        TextView undo = new TextView(container.getContext());
        TextView moves_L = new TextView(container.getContext());
        TextView moves_R = new TextView(container.getContext());
        TextView moves_T = new TextView(container.getContext());
        TextView moves_D = new TextView(container.getContext());
        TextView moves = new TextView(container.getContext());
        TextView tpm = new TextView(container.getContext());
        TextView rekord = new TextView(container.getContext());
        switch (position) {
            case 0:
                highestNumber = view.findViewById(R.id.highest_number1);
                timePlayed = view.findViewById(R.id.time_played1);
                undo = view.findViewById(R.id.undo_number1);
                moves_D = view.findViewById(R.id.moves_D1);
                moves_L = view.findViewById(R.id.moves_L1);
                moves_R = view.findViewById(R.id.moves_R1);
                moves_T = view.findViewById(R.id.moves_T1);
                moves = view.findViewById(R.id.moves_All1);
                tpm = view.findViewById(R.id.time_swipes1);
                rekord = view.findViewById(R.id.highest_score1);
                img = view.findViewById(R.id.stat_img1);
                Glide.with(container.getContext()).load(R.drawable.layout4x4_o).into(img);
                break;
            case 1:
                highestNumber = view.findViewById(R.id.highest_number2);
                timePlayed = view.findViewById(R.id.time_played2);
                undo = view.findViewById(R.id.undo_number2);
                moves_D = view.findViewById(R.id.moves_D2);
                moves_L = view.findViewById(R.id.moves_L2);
                moves_R = view.findViewById(R.id.moves_R2);
                moves_T = view.findViewById(R.id.moves_T2);
                moves = view.findViewById(R.id.moves_All2);
                tpm = view.findViewById(R.id.time_swipes2);
                rekord = view.findViewById(R.id.highest_score2);
                img = view.findViewById(R.id.stat_img2);
                Glide.with(container.getContext()).load(R.drawable.layout5x5_o).into(img);
                break;
            case 2:
                highestNumber = view.findViewById(R.id.highest_number3);
                timePlayed = view.findViewById(R.id.time_played3);
                undo = view.findViewById(R.id.undo_number3);
                moves_D = view.findViewById(R.id.moves_D3);
                moves_L = view.findViewById(R.id.moves_L3);
                moves_R = view.findViewById(R.id.moves_R3);
                moves_T = view.findViewById(R.id.moves_T3);
                moves = view.findViewById(R.id.moves_All3);
                tpm = view.findViewById(R.id.time_swipes3);
                rekord = view.findViewById(R.id.highest_score3);
                img = view.findViewById(R.id.stat_img3);
                Glide.with(container.getContext()).load(R.drawable.layout6x6_o).into(img);
                break;
            case 3:
                highestNumber = view.findViewById(R.id.highest_number4);
                timePlayed = view.findViewById(R.id.time_played4);
                undo = view.findViewById(R.id.undo_number4);
                moves_D = view.findViewById(R.id.moves_D4);
                moves_L = view.findViewById(R.id.moves_L4);
                moves_R = view.findViewById(R.id.moves_R4);
                moves_T = view.findViewById(R.id.moves_T4);
                moves = view.findViewById(R.id.moves_All4);
                tpm = view.findViewById(R.id.time_swipes4);
                rekord = view.findViewById(R.id.highest_score4);
                img = view.findViewById(R.id.stat_img4);
                Glide.with(container.getContext()).load(R.drawable.layout7x7_o).into(img);
                break;
        }
        GameStatistics gameStatistics = readStatisticsFromFile(position + 4);
        highestNumber.setText("" + gameStatistics.getHighestNumber());
        timePlayed.setText(formatMillis(gameStatistics.getTimePlayed()));
        undo.setText("" + gameStatistics.getUndo());
        moves_D.setText("" + gameStatistics.getMoves_d());
        moves_R.setText("" + gameStatistics.getMoves_r());
        moves_T.setText("" + gameStatistics.getMoves_t());
        moves_L.setText("" + gameStatistics.getMoves_l());
        moves.setText("" + gameStatistics.getMoves());
        if (gameStatistics.getMoves() != 0)
            tpm.setText("" + formatSmallMillis(gameStatistics.getTimePlayed() / gameStatistics.getMoves()));
        else
            tpm.setText("0");
        rekord.setText("" + gameStatistics.getRecord());


        return view;
    }

    public String formatSmallMillis(long timeInMillis) {
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

    public String formatMillis(long timeInMillis) {
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
            File file = new File(contextWrapper.getFilesDir(), FILE_STATISTIC + n + TXT);
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gS = (GameStatistics) in.readObject();
            in.close();
            fileIn.close();
        } catch (InvalidClassException ice) {
            File file = new File(contextWrapper.getFilesDir(), FILE_STATISTIC + n + TXT);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gS;
    }
}
