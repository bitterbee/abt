package com.netease.demo.abtest.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.demo.abtest.R;
import com.netease.libs.abtestbase.ABLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/9/16.
 */

public class UserPageFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private List<View> mPageViews = new ArrayList<>(6);
    private String[] mTabContents = {"推荐", "新品", "居家", "床品", "厨房", "饮食"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userpage, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.addOnTabSelectedListener(this);

        mPageViews.clear();
        for (String tabContent : mTabContents) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(tabContent);
            mTabLayout.addTab(tab);

            View tabView = getLayoutInflater().inflate(R.layout.tab_view, null);
            ((TextView) tabView.findViewById(R.id.text_view)).setText(tabContent);
            mPageViews.add(tabView);
        }

        mViewPager = (ViewPager) view.findViewById(R.id.vp_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mTabContents.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mPageViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTabContents[position];
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ABLog.i("tab selected " + tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
