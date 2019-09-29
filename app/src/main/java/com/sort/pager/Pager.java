package com.sort.pager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.sort.R;

import java.util.ArrayList;
import java.util.List;

public class Pager extends LinearLayout {

    private ViewPager mViewPager;
    private MainPagerAdapter mAdapter;
    private PagerTab mTab;
    private List<TabInfo> tabInfos;


    public Pager(Context context) {
        super(context);
        init();
    }

    public Pager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.layout_pager, this);

        mViewPager = findViewById(R.id.viewPager);
        mTab = findViewById(R.id.pagerTab);

        mTab.attachViewPager(mViewPager);

        initViewPager();
    }

    private FragmentManager getSupportFragmentManager(){
        if (getContext() instanceof FragmentActivity){
            return ((FragmentActivity) getContext()).getSupportFragmentManager();
        }else {
            throw new IllegalStateException("容器 activity 没有继承 FragmentActivity");
        }
    }

    private void initViewPager(){

        tabInfos = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            tabInfos.add(TabInfo.create(""+i));
        }

        mAdapter = new MainPagerAdapter(getSupportFragmentManager(), tabInfos);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * main pager adapter
     */
    static class MainPagerAdapter extends FragmentPagerAdapter {

        private List<TabInfo> tabInfos;

        public MainPagerAdapter(FragmentManager fm, List<TabInfo> tabInfos) {
            super(fm);
            this.tabInfos = tabInfos;
        }

        @Override
        public Fragment getItem(int i) {
            return MainFragment.newInstance();
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabInfos.get(position).name;
        }
    }

    static class TabInfo{
        String name;

        public static TabInfo create(String name){
            TabInfo tabInfo = new TabInfo();
            tabInfo.name = name;
            return tabInfo;
        }
    }

}
