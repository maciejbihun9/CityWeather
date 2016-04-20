package com.example.maciejbihun.cityweather.view.navigationDrawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.view.DayFragment;
import com.example.maciejbihun.cityweather.view.hourly_fragment_list.HourlyListFragment;

import java.util.ArrayList;

/**
 * Created by MaciekBihun on 2016-04-15.
 */
public class NavigationDrawer {

    public static final String LIST_FRAGMENT = "list_fragment";
    public static final String TEST_FRAGMENT = "test_fragment";
    public static final String HOURLY_LIST_FRAGMENT = "hourly_list_fragmnet";

    private static final String TAG = NavigationDrawer.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;


    private Activity parentActivity;
    private Bundle savedInstanceState;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    public NavigationDrawer(final Activity parentActivity,Bundle savedInstanceState,
                            DrawerLayout mDrawerLayout, ListView mDrawerList){
        this.parentActivity = parentActivity;
        this.savedInstanceState = savedInstanceState;
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mDrawerList;
    }


    public void initializeNavigationDrawerItems(){
        //mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = parentActivity.getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = parentActivity.getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);



        *//*navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));*//*
        // Find People
       *//* navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));*//*


        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(parentActivity.getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

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
            displayView(0);
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    //adapt fragments swipe view.
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return DayFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //return mCrimes.size();
            return 5;
        }
    }

    private void displayView(int position) {

        FragmentManager fragmentManager = parentActivity.getFragmentManager();
        switch (position) {
            case 0:

                if (fragmentManager.findFragmentByTag(TEST_FRAGMENT) != null) {
                    Fragment fr = fragmentManager.findFragmentByTag(TEST_FRAGMENT);
                    fragmentManager.beginTransaction().detach(fr);
                }
                break;
            case 1:
                Fragment hourlyListFragment = new HourlyListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, hourlyListFragment, HOURLY_LIST_FRAGMENT).commit();
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 2:



                break;


        }

    }

}

