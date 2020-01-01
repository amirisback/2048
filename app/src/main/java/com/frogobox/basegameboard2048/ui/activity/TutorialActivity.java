package com.frogobox.basegameboard2048.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.frogobox.basegameboard2048.R;
import com.frogobox.basegameboard2048.util.helper.FirstLaunchManager;

public class TutorialActivity extends AppCompatActivity {

    private static final String TAG = TutorialActivity.class.getSimpleName();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private Button btnSkip, btnNext;
    private FirstLaunchManager firstLaunchManager;
    // layouts of all welcome sliders
    // add few more layouts if you want
    private int[] layouts = new int[]{
            R.layout.tutorial_slide1,
            R.layout.tutorial_slide2,
            R.layout.tutorial_slide3,
            R.layout.tutorial_slide4,
    };
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {


            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.okay));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        firstLaunchManager = new FirstLaunchManager(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        try {
            ImageView imageView = (ImageView) findViewById(R.id.image1);
            Glide.with(TutorialActivity.this).load(R.mipmap.ic_splash).into(imageView);//.into(imageView);//@mipmap/ic_splash).into(imageView);
        } catch (NullPointerException ne) {

        }

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int activeColor = ContextCompat.getColor(this, R.color.dot_light_screen);
        int inactiveColor = ContextCompat.getColor(this, R.color.dot_dark_screen);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(inactiveColor);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(activeColor);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        if (firstLaunchManager.isFirstTimeLaunch()) {
            Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            firstLaunchManager.setFirstTimeLaunch(false);
            startActivity(intent);
        }
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            ImageView imageView;
            switch (position) {
                case 0:
                    imageView = (ImageView) findViewById(R.id.image1);
                    Glide.with(TutorialActivity.this).load(R.mipmap.ic_splash).into(imageView);
                    break;
                case 1:
                    imageView = (ImageView) findViewById(R.id.image2);
                    if (PreferenceManager.getDefaultSharedPreferences(TutorialActivity.this).getString("pref_color", "1").equals("1"))
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_move_s).into(imageView);
                    else
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_move_o).into(imageView);
                    break;
                case 2:
                    imageView = (ImageView) findViewById(R.id.image3);
                    if (PreferenceManager.getDefaultSharedPreferences(TutorialActivity.this).getString("pref_color", "1").equals("1"))
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_swipe_s).into(imageView);
                    else
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_swipe_o).into(imageView);
                    break;
                case 3:
                    imageView = (ImageView) findViewById(R.id.image4);
                    if (PreferenceManager.getDefaultSharedPreferences(TutorialActivity.this).getString("pref_color", "1").equals("1"))
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_add_s).into(imageView);
                    else
                        Glide.with(TutorialActivity.this).load(R.drawable.tutorial_add_o).into(imageView);
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
}
