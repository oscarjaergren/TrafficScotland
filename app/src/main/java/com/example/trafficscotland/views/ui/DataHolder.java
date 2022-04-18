/*
 Developer: Oscar Jargren
 Student ID: S1805227
*/


package com.example.trafficscotland.views.ui;

import com.example.trafficscotland.Models.TrafficData;

public class DataHolder {
    private TrafficData data;

    public TrafficData getData() {
        return data;
    }

    public void setData(TrafficData data) {
        this.data = data;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
