package com.example.maciejbihun.cityweather.view.navigationDrawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;


import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.controller.ForecastFetchr;
import com.example.maciejbihun.cityweather.model.HourlyWeather;
import com.example.maciejbihun.cityweather.view.DayFragment;
import com.example.maciejbihun.cityweather.view.hourly_fragment_list.HourlyListAdapter;
import com.example.maciejbihun.cityweather.view.hourly_fragment_list.HourlyListFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MaciekBihun on 2016-04-15.
 */
public class NavigationDrawer {

    private static final String TAG = NavigationDrawer.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;
    private HourlyListAdapter adapter;


    private Activity parentActivity;
    private Bundle savedInstanceState;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerViewList;

    public NavigationDrawer(final Activity parentActivity,Bundle savedInstanceState,
                            DrawerLayout mDrawerLayout, RecyclerView recyclerViewList){
        this.parentActivity = parentActivity;
        this.savedInstanceState = savedInstanceState;
        this.mDrawerLayout = mDrawerLayout;
        this.recyclerViewList = recyclerViewList;
    }


    public void initializeNavigationDrawerItems(){
        adapter = new HourlyListAdapter(parentActivity);
        recyclerViewList.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parentActivity);
        recyclerViewList.setLayoutManager(layoutManager);

        // enabling action bar app icon and behaving it as toggle button


        mDrawerToggle = new ActionBarDrawerToggle(parentActivity, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                parentActivity.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                parentActivity.invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
        }
        //recyclerViewList.setOnItemClickListener(new SlideMenuClickListener());
    }

}

