package com.frogobox.basegameboard2048.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.frogobox.basegameboard2048.R;
import com.frogobox.basegameboard2048.base.ui.BaseActivity;
import com.frogobox.basegameboard2048.view.pager.StatsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;


public class StatsActivity extends BaseActivity {

    String[] TABNAMES = {"4x4", "5x5", "6x6", "7x7"};
    private int[] layouts = new int[]{
            R.layout.fragment_stats1,
            R.layout.fragment_stats2,
            R.layout.fragment_stats3,
            R.layout.fragment_stats4,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setupDetailActivity("");
        getSupportActionBar().setElevation(0f);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        StatsPagerAdapter mSectionsPagerAdapter = new StatsPagerAdapter(layoutInflater, layouts, TABNAMES, this);

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);
        //tabLayout.setTabTextColors(Color.WHITE,Color.YELLOW);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        //getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
        //return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_reset:
                //    SaveLoadStatistics.resetStats(this);
                //    mSectionsPagerAdapter.refresh(this);

                resetGameStatistics();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetGameStatistics() {
        for (int n = 4; n <= 7; n++) {
            try {
                File file = new File(getFilesDir(), "statistics" + n + ".txt");
                file.delete();
            } catch (Exception e) {

            }
        }
        finish();
        startActivity(getIntent());
    }

}