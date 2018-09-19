package com.netease.demo.abtest.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.netease.demo.abtest.R;

/**
 * Created by zyl06 on 2018/9/16.
 */
public class SecondActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    public static final String[] TAB_TEXTS = {
            TabType.Home.toString(),
            TabType.Discovery.toString(),
            TabType.ShoppingCart.toString(),
            TabType.UserPage.toString()
    };

    public static final int TAB_ICONS[] = {
            R.drawable.selector_icon_home,
            R.drawable.selector_icon_discovery,
            R.drawable.selector_icon_shopping_cart,
            R.drawable.selector_icon_person
    };
    private static final Class FRAGMENT_CLASSES[] = {
            HomeFragment.class,
            DiscoveryFragment.class,
            ShoppingCartFragment.class,
            UserPageFragment.class
    };


    FragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initContentView();
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    private void initContentView() {
        mFragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mFragmentTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realtabcontent);

        for (int i = 0; i < FRAGMENT_CLASSES.length; i++) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(TAB_TEXTS[i])
                    .setIndicator(getTabView(i));
            mFragmentTabHost.addTab(tabSpec, FRAGMENT_CLASSES[i], null);
            mFragmentTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
            mFragmentTabHost.getTabWidget().setBackgroundResource(android.R.color.transparent);//#271907


            View tabView = mFragmentTabHost.getTabWidget().getChildAt(i);
            tabView.setBackgroundResource(android.R.color.transparent);//#271907
            final int position = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentTabHost.setCurrentTab(position);
                }
            });
        }
        mFragmentTabHost.setOnTabChangedListener(this);
    }

    private View getTabView(int index) {
        View view = getLayoutInflater().inflate(R.layout.item_tabhost_view, null);

        TextView text = (TextView) view.findViewById(R.id.txt_tab_title);
        text.setText(TAB_TEXTS[index]);

        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_tab_icon);
        imgIcon.setImageResource(TAB_ICONS[index]);

        return view;
    }
}
