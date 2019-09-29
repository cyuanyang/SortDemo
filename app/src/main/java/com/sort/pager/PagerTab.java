package com.sort.pager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sort.R;

import java.util.ArrayList;
import java.util.List;

public class PagerTab extends LinearLayout implements View.OnClickListener {

    private ViewPager mViewPage;
    private List<Tab> tabs = new ArrayList<>();
    private int currentTab = 0;

    public PagerTab(Context context) {
        super(context);
    }

    public PagerTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachViewPager(ViewPager viewPager){
        this.mViewPage = viewPager;
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter1) {
                if (viewPager.getAdapter() != null){
                    initTab();
                }
            }
        });
        this.mViewPage.addOnPageChangeListener(onPageChangeListener);
    }

    private void initTab(){
        PagerAdapter adapter = mViewPage.getAdapter();
        currentTab = mViewPage.getCurrentItem();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            Tab tab = new Tab(getContext());
            tab.setTag(i);
            tab.setTitle(adapter.getPageTitle(i));
            tab.setSelect(i == currentTab);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 120);
            lp.weight = 1;
            addView(tab, lp);
            tab.setOnClickListener(this);

            tabs.add(tab);
        }
    }

    private void setCurrentItemInteral(int pos){
        Tab cur = tabs.get(currentTab);
        cur.setSelect(false);
        currentTab = pos;
        Tab nextCur = tabs.get(currentTab);
        nextCur.setSelect(true);
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();
        mViewPage.setCurrentItem(index);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener =
            new ViewPager.SimpleOnPageChangeListener(){

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentItemInteral(position);
                }
            };


    class Tab extends RelativeLayout{

        private TextView titleView;
        private boolean select;

        public Tab(Context context) {
            super(context);
            init();
        }

        private void init(){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(R.layout.layout_tab, this);
            titleView = findViewById(R.id.textView);
        }

        public void setTitle(CharSequence title){
            titleView.setText(title);
        }

        public void setSelect(boolean select) {
            this.select = select;
            if (select){
                titleView.setTextColor(Color.BLUE);
            }else {
                titleView.setTextColor(Color.BLACK);
            }

        }
    }
}
