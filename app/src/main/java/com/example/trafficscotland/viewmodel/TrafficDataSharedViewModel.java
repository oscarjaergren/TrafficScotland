package com.example.trafficscotland.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trafficscotland.Models.TrafficData;
import com.example.trafficscotland.service.TrafficDataService;

import java.util.List;

public class TrafficDataSharedViewModel extends AndroidViewModel {

    MutableLiveData<List<TrafficData>> trafficLiveData;

    private final TrafficDataService trafficDataService =  new TrafficDataService();

    public TrafficDataSharedViewModel(Application application) {
        super(application);

        trafficLiveData = trafficDataService.getRepos();
        //loadTrafficData();
    }

    public void loadTrafficData(String url) {
        trafficDataService.loadTrafficData(url);
    }

    public LiveData<List<TrafficData>> getTrafficListObservable() {
        return trafficLiveData;
    }

    public MutableLiveData<List<TrafficData>> getLiveData() {
        return trafficLiveData;
    }
}
