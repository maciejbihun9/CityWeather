package com.example.maciejbihun.cityweather.view.hourly_fragment_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.constans.Icon;
import com.example.maciejbihun.cityweather.controller.ForecastFetchr;
import com.example.maciejbihun.cityweather.model.HourlyWeather;

import org.json.JSONException;
import java.util.List;

import java.io.IOException;

/**
 * Created by MaciekBihun on 2016-04-02.
 */
public class HourlyListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<HourlyWeather> hourlyList;
    public HourlyListAdapter(Context mContext) {

        this.mContext = mContext;
        //---------Get CurrentWeather from SharedPreferences--------------------
        ForecastFetchr currentWeather = new ForecastFetchr(mContext);
        try {
            hourlyList = currentWeather.parseHourlyWeather();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Dodajemy elementy do widoku z pamięci telefonu
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView weatherIcon;
        private TextView hourText;
        private TextView hourTemp;

        public ListViewHolder(View itemView) {
            super(itemView);
            //Inicjalizacja poszczególnego elementu z listy.
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            hourTemp = (TextView) itemView.findViewById(R.id.hour_temp);
            hourText = (TextView) itemView.findViewById(R.id.hour_text);
            //nasłuchiwanie kliknięć bierzącego obiektu.
            /*temView.setOnClickListener(this);*/
        }

        public void bindView(int position){
            //W zależności od pozycji w liście nadaje odpowiednie wartości elementom.
            hourText.setText(hourlyList.get(position).getTime());
            weatherIcon.setImageResource(Icon.getIconId(hourlyList.get(position).getIcon()));
            hourTemp.setText(hourlyList.get(position).getTemperature());
        }

        /*@Override
        public void onClick(View v) {
            mListener.onListrecipeSelected(mIndex);
        }*/
    }
}
