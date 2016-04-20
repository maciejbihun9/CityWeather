package com.example.maciejbihun.cityweather.view.hourly_fragment_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maciejbihun.cityweather.R;
import com.example.maciejbihun.cityweather.controller.ForecastFetchr;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by MaciekBihun on 2016-04-17.
 */
public class HourlyListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.hourly_fragment_list, container, false);
        //Do RecyclerView dodajmey nasz adapter.
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.listRecyclerView);
        //wysłanie instancji interfejsu
        HourlyListAdapter mHourlyListAdapter = new HourlyListAdapter(getActivity());
        mRecyclerView.setAdapter(mHourlyListAdapter);
        //Ustalenie w jaki sposób bedzie adaptował poszczególne elementy z listy.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        return view;

    }
}
