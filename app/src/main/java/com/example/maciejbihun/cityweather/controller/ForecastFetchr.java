package com.example.maciejbihun.cityweather.controller;

import android.content.Context;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.constans.ApplicationConstants;
import com.example.maciejbihun.cityweather.model.CurrentWeather;
import com.example.maciejbihun.cityweather.model.DayWeather;
import com.example.maciejbihun.cityweather.model.HourlyWeather;
import com.example.maciejbihun.cityweather.model.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MaciekBihun on 2016-03-23.
 * This class manages downloading data from Forecast
 */
public class ForecastFetchr {

    private double latitude;
    private double longitude;
    private Context mContext;
    private String lastKnownWeather;
    private JSONObject jsonObject;

    public ForecastFetchr(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ForecastFetchr(Context mContext){

        this.mContext = mContext;
        //check weather data from forecast is already stored
        if(PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(ApplicationConstants.WEATHER_RESULT, null) == null){
            lastKnownWeather = null;
            Log.i(TAG, "there is no data yet");
        } else {
            //if weather is already stored, then get weather string from SharedPreferences.
            lastKnownWeather = PreferenceManager.getDefaultSharedPreferences(mContext)
                    .getString(ApplicationConstants.WEATHER_RESULT, null);
        }
        try {
            jsonObject = new JSONObject(lastKnownWeather);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ForecastFetchr(){

    }

    //url look: https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE
    public static final String TAG = "ForecastFetchr";
    private static final String API_KEY = "f069b3cc99e5c80041dd1f326e7a4237/";
    private static final String ENDPOINT = "https://api.forecast.io/forecast/";

    //gets bytes from specify url
    private byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        //open connection
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            Log.i(TAG, out + " ");
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    //creates full url from specify user location
    private String buildUrl(double latitude, double longitude) {
        return ENDPOINT + API_KEY + latitude + "," + longitude;
    }

    //converts bytes from specify url to String
    private String convertBytesToString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    //show current weather in Log
    public void showWeather() {
        String result = null;
        try {
            result = convertBytesToString(buildUrl(latitude, longitude));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, result);
    }

    //returns converted bytes into String
    public String getWeatherConditions() {
        String result = null;
        try {
            result = convertBytesToString(buildUrl(latitude, longitude));
            Log.i(TAG,result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<DayWeather> parseDailyWeather(JSONObject jsonBody)
            throws IOException, JSONException {

        List<DayWeather> dailyWeather = new ArrayList<>();
        JSONObject photosJsonObject = jsonBody.getJSONObject("daily");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("data");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            DayWeather item = new DayWeather();
            item.setTime(photoJsonObject.getLong("time"));
            item.setSummary(photoJsonObject.getString("summary"));
            item.setIcon(photoJsonObject.getString("icon"));

            double convertedTempMin = convertFarenheitToCelcjus(photoJsonObject.getDouble("temperatureMin"));
            double convertedTempMax = convertFarenheitToCelcjus(photoJsonObject.getDouble("temperatureMax"));

            item.setTempMin((int) convertedTempMin+""+(char) 0x00B0);
            item.setTempMax((int) convertedTempMax+""+(char) 0x00B0);

            item.setHumidity((int)photoJsonObject.getDouble("humidity")+"");
            item.setPressure((int) photoJsonObject.getDouble("pressure")+" hPa");

            dailyWeather.add(item);
        }
        return dailyWeather;
    }

    public List<HourlyWeather> parseHourlyWeather()
            throws IOException, JSONException {

        List<HourlyWeather> dailyWeather = new ArrayList<>();
        JSONObject photosJsonObject = jsonObject.getJSONObject("hourly");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("data");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject hourJsonObject = photoJsonArray.getJSONObject(i);

            HourlyWeather hourWeather = new HourlyWeather();
            hourWeather.setTime(Time.getFormattedTime(hourJsonObject.getLong("time")));
            hourWeather.setIcon(hourJsonObject.getString("icon"));

            hourWeather.setTemperature((int)hourJsonObject.getDouble("temperature")+""+(char) 0x00B0);

           /* double convertedTempMin = convertFarenheitToCelcjus(hourJsonObject.getDouble("temperatureMin"));
            double convertedTempMax = convertFarenheitToCelcjus(hourJsonObject.getDouble("temperatureMax"));*/


            dailyWeather.add(hourWeather);
        }
        return dailyWeather;
    }


    public CurrentWeather parseCurrentWeather(JSONObject jsonBody, Context mContext) throws JSONException {
        Resources res = mContext.getResources();
        JSONObject jsonObject = jsonBody.getJSONObject(res.getString(R.string.current_weather));
        double temp = jsonObject.getDouble(res.getString(R.string.temperature));
        String summary = jsonObject.getString(res.getString(R.string.summary));
        String icon = jsonObject.getString(res.getString(R.string.icon));
        long time = jsonObject.getLong(res.getString(R.string.time));
        double pressure = jsonObject.getDouble(res.getString(R.string.pressure));


        double convertedTemp = convertFarenheitToCelcjus(temp);

        CurrentWeather currentWeather = CurrentWeather.getInstance();
        currentWeather.setIcon(icon);
        currentWeather.setSummary(summary);
        currentWeather.setTime(time);
        currentWeather.setTemperature((int)convertedTemp+""+(char) 0x00B0);
        currentWeather.setPressure(pressure+""+" hPa");

        return currentWeather;
    }

    private double convertFarenheitToCelcjus(double farenTemp) {
        return ((farenTemp - 32) * 5) / 9;
    }
}
