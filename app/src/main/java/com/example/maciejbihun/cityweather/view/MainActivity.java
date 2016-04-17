package com.example.maciejbihun.cityweather.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.controller.UserLocation;
import com.example.maciejbihun.cityweather.controller.background_services.DownloadWeatherService;
import com.example.maciejbihun.cityweather.view.navigationDrawer.NavigationDrawer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //referencja do id głównego layoutu
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private NavigationDrawer navigationDrawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---------Initalize navigationDrawer instances-------------------------
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navigationDrawer = new NavigationDrawer(MainActivity.this,savedInstanceState, mDrawerLayout, mDrawerList);
        navigationDrawer.initializeNavigationDrawerItems();

        //----------Initialize ActionBar click listeners-----------------------------
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //----------Set view of the drawerlist in xml layout-----------------
        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        //------Download weather from Forecast and store it in SharedPreferences.------
        DownloadWeatherService.setServiceAlarm(MainActivity.this, true);


        //-------------Get user location and store it in SharedPreferences-------------
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                new UserLocation(MainActivity.this).setUserLocation();
            }
        });
        initializeViewPager();
    }

    public void initializeViewPager(){
        //initialize ViewPager
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.getCurrentItem();
        mViewPager.setOffscreenPageLimit(3);
    }

    //-------------Click drawerMenu items--------------------
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }


    //adapt fragments swipe view.
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //Depends on ViewPager position, load DayFragment with weather data
        //related with that day.
        @Override
        public Fragment getItem(int position) {
            return DayFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //return mCrimes.size();
            return 5;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
