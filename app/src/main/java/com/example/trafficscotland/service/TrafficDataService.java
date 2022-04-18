package com.example.trafficscotland.service;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.trafficscotland.Models.TrafficData;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrafficDataService {

    private final MutableLiveData<List<TrafficData>> trafficData = new MutableLiveData<>();

    // do async operation to fetch users
    public void loadTrafficData(String url) {

        urlSource = url;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        executorService.submit(this::downloadAndParseTrafficData);
    }

    public MutableLiveData<List<TrafficData>> getRepos(){
        return trafficData;
    }

    String urlSource = "";



    private InputStream downloadTrafficData() {

        URL url = null;
        try {
            url = new URL(urlSource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        InputStream in = null;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return in;

    }

    private void downloadAndParseTrafficData() {

        Log.e("Info", "in run");

        InputStream rawTrafficData = downloadTrafficData();

        XmlTrafficDataParser parser = new XmlTrafficDataParser();

        Log.e("Info", "Finished Parsing XML");

        ArrayList<TrafficData> parsed = parser.parse(rawTrafficData);

        trafficData.postValue(parsed);
    }
}