package com.example.trafficscotland.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.trafficscotland.Models.TrafficData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrafficDetailsViewModel extends ViewModel implements OnMapReadyCallback {

    TrafficData trafficDataModel;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double[] parsedMapPos = getLatAndLong(trafficDataModel.getGeorss());
        LatLng mapPos = new LatLng(parsedMapPos[0], parsedMapPos[1]);
        googleMap.addMarker(new MarkerOptions().position(mapPos).title(trafficDataModel.getTitle()).visible(true));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mapPos, 14));
    }

    public double[] getLatAndLong(String latitudeLongitude) {
        String latitude = latitudeLongitude.substring(0, latitudeLongitude.indexOf(' '));
        String longitude = latitudeLongitude.substring(latitudeLongitude.indexOf(' '));
        return new double[]{Double.parseDouble(latitude), Double.parseDouble(longitude)};
    }

}