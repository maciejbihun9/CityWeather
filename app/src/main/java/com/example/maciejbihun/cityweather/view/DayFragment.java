package com.example.maciejbihun.cityweather.view;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.constans.Icon;
import com.example.maciejbihun.cityweather.controller.ForecastFetchr;
import com.example.maciejbihun.cityweather.constans.ApplicationConstants;
import com.example.maciejbihun.cityweather.model.CurrentWeather;
import com.example.maciejbihun.cityweather.model.DayWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by MaciekBihun on 2016-03-23.
 */

/**
 * Provides the entry point to Google Play services.
 */

public class DayFragment extends Fragment {

    private static final String TAG = "DayFragment";
    private static final String PAGER_POSITION = "pager_position";

    //widgets
    private TextView temperature;
    private TextView summary;
    private TextView time;
    private ImageView icon;
    private ImageView cityImage;
    private TextView pressure;
    private TextView city;

    private View fragmentView;
    private String lastKnownWeather;



    //returns new DayFragment with position in ViewPager
    public static DayFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putSerializable(PAGER_POSITION, position);

        DayFragment fragment = new DayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check weather data from forecast is already stored
        if(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(ApplicationConstants.WEATHER_RESULT, null) == null){
            lastKnownWeather = null;
            Log.i(TAG, "there is no data yet");
        } else {
            //if weather is already stored, then get weather string from SharedPreferences.
            lastKnownWeather = PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(ApplicationConstants.WEATHER_RESULT, null);
        }
    }


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.day_fragment_layout, container, false);

        //get int position from ViewPager
        int pagerPosition = (int)getArguments().getSerializable(PAGER_POSITION);

        //must be first
        initializeWidgets(fragmentView);
        try {
            //If lastWeather is null then do not make changes in layout/
            if(lastKnownWeather == null)
                return fragmentView;
            else
            updateFragmentLayout(lastKnownWeather, pagerPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fragmentView;
    }

    //initialize all widgets
    private void initializeWidgets(View mView){
        //set values to widgets
        temperature = (TextView) mView.findViewById(R.id.temperature);
        summary = (TextView) mView.findViewById(R.id.summary);
        icon = (ImageView) mView.findViewById(R.id.icon);
        pressure = (TextView) mView.findViewById(R.id.pressure);
        time = (TextView) mView.findViewById(R.id.time);
        city = (TextView) mView.findViewById(R.id.city);
        cityImage = (ImageView) mView.findViewById(R.id.city_photo);

    }

    //update all fragments with data depends on viewPager position
    private void updateFragmentLayout(String weatherConditions, int position) throws JSONException, IOException {

        //fragment with currentWeather
        ForecastFetchr weather = new ForecastFetchr();
        JSONObject jsonObject = new JSONObject(weatherConditions);

        //for currentWeather
        if(position == 0){
            CurrentWeather currentWeather = weather.parseCurrentWeather(jsonObject, getContext());
            time.setText(getFormattedTime(currentWeather.getTime()));
            temperature.setText(currentWeather.getTemperature());
            summary.setText(currentWeather.getSummary());
            pressure.setText(currentWeather.getPressure());

            //set weather icon
            int imageIcon = Icon.getIconId(currentWeather.getIcon());
            icon.setImageResource(imageIcon);

        } else {
            //for later days
            List <DayWeather> weatherList = weather.parseDailyWeather(jsonObject);
            int positionInList = position - 1;

            time.setText(getFormattedTime(weatherList.get(positionInList).getTime()));
            summary.setText(weatherList.get(positionInList).getSummary());
            pressure.setText(weatherList.get(positionInList).getPressure());
            temperature.setText(weatherList.get(positionInList).getTempMax());

            //set weather icon
            int imageIcon = Icon.getIconId(weatherList.get(positionInList).getIcon());
            icon.setImageResource(imageIcon);


        }
    }

    public String getFormattedTime(long time) {

        long timing = System.currentTimeMillis() - time;
        DateFormat formatter = SimpleDateFormat.getInstance();
        String finalTime = formatter.format(timing);

        return finalTime;
    }

}
