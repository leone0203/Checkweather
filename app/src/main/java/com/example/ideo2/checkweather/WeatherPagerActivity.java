package com.example.ideo2.checkweather;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
/**
 * Obsluga wszystkich fragmentow do viewpager
 */
public class WeatherPagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 7;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pager);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);



        Intent i = getIntent();
        String source = i.getStringExtra("fragment");
        if (source.matches("fragment1")) mPager.setCurrentItem(0);
        if (source.matches("fragment2")) mPager.setCurrentItem(1);
        if (source.matches("fragment3")) mPager.setCurrentItem(2);
        if (source.matches("fragment4")) mPager.setCurrentItem(3);
        if (source.matches("fragment5")) mPager.setCurrentItem(4);
        if (source.matches("fragment6")) mPager.setCurrentItem(5);
        if (source.matches("fragment7")) mPager.setCurrentItem(6);

    }

    private static class ScreenSlidePageAdapter extends FragmentStatePagerAdapter {



        public ScreenSlidePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new ScreenSlidePageFragment();
            }
            if (position == 1) {
                return new ScreenSlidePageFragment2();
            }
            if (position == 2) {
                return new ScreenSlidePageFragment3();
            }
            if (position == 3) {
                return new ScreenSlidePageFragment4();
            }
            if (position == 4) {
                return new ScreenSlidePageFragment5();
            }
            if (position == 5) {
                return new ScreenSlidePageFragment6();
            }
            if (position == 6) {
                return new ScreenSlidePageFragment7();
            }

            return null;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }



}
