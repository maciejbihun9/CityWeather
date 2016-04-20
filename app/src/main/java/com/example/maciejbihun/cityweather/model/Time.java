package com.example.maciejbihun.cityweather.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by MaciekBihun on 2016-04-20.
 */
public class Time {

    public static String getFormattedTime(long time) {

        long timing = System.currentTimeMillis() - time;

        Locale locale = Locale.getDefault();
        Calendar mInstance = GregorianCalendar.getInstance(locale);

        DateFormat formatter = SimpleDateFormat.getInstance();
        String finalTime = formatter.format(timing);

        return finalTime;
    }
}
