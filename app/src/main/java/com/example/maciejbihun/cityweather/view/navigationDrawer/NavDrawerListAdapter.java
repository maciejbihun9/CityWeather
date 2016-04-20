package com.example.maciejbihun.cityweather.view.navigationDrawer;

/**
 * Created by MaciekBihun on 2016-04-15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.model.HourlyWeather;

import java.util.ArrayList;

/**
 * Created by MaciekBihun on 2016-04-15.
 * Class adapts every single item in DrawerList
 */

public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<HourlyWeather> weatherItems;

    private ImageView weatherIcon;
    private TextView hourText;
    private TextView hourTemp;

    public NavDrawerListAdapter(Context context, List<HourlyWeather> weatherItems){
        this.context = context;
        this.weatherItems = weatherItems;
    }

    @Override
    public int getCount() {
        return weatherItems.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.hour_item, null);
        }

        weatherIcon = (ImageView) convertView.findViewById(R.id.weather_icon);
        hourTemp = (TextView) convertView.findViewById(R.id.hour_temp);
        hourText = (TextView) convertView.findViewById(R.id.hour_text);



        // displaying count
        // check whether it set visible or not
        /*if(navDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }*/

        return convertView;
    }

}
