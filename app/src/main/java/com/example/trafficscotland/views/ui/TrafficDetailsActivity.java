/*
 Developer: Oscar Jargren
 Student ID: S1805227
*/


package com.example.trafficscotland.views.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.trafficscotland.Models.TrafficData;
import com.example.trafficscotland.R;
import com.example.trafficscotland.viewmodel.TrafficDataSharedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrafficDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    TrafficData trafficData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_details);

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        TrafficDataSharedViewModel sharedViewModel = viewModelProvider.get(TrafficDataSharedViewModel.class);

         trafficData = DataHolder.getInstance().getData();
        Log.e("Info", "Finished Parsing XML");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        TextView title = findViewById(R.id.trafficDetailsActivity_title);
        TextView description = findViewById(R.id.trafficDetailsActivity_description);

        TextView author = findViewById(R.id.trafficDetailsActivity_author);
        String authorText = trafficData.getAuthor();
        if(authorText == "")
        {
            authorText = "No content";
        }
        author.setText(authorText);

        TextView comments = findViewById(R.id.trafficDetailsActivity_comments);
        String comment = trafficData.getComments();
        if(comment == "")
        {
            comment = "No content";
        }
        comments.setText(comment);

        TextView link = findViewById(R.id.trafficDetailsActivity_link);
        TextView rssgeo = findViewById(R.id.trafficDetailsActivity_georss);
        TextView pubDate = findViewById(R.id.trafficDetailsActivity_pub_date);

        //TextView startDate = findViewById(R.id.traffic_act_start_date);
        //TextView endDate = findViewById(R.id.traffic_act_end_date);
        //startDate.setText(trafficDataModel.getStartDateAsString());
        //endDate.setText(trafficDataModel.getEndDateAsString());

        //TextView roadworksLn = findViewById(R.id.traffic_act_road_ln);
        //roadworksLn.setText(trafficDataModel.getRoadworksLength()+""); // convert to string

        title.setText(trafficData.getTitle());
        Log.v("TrafficActivity", trafficData.getTitle());
        description.setText(trafficData.getDescription());

        rssgeo.setText(trafficData.getGeorss());

        link.setText(trafficData.getLink());
        rssgeo.setText(trafficData.getGeorss());
        pubDate.setText(trafficData.getPubDate());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String georss = trafficData.getGeorss();

        if(georss == null) return;

        double[] parsedMapPos = getLatAndLong(georss);
        LatLng mapPos = new LatLng(parsedMapPos[0], parsedMapPos[1]);
        googleMap.addMarker(new MarkerOptions().position(mapPos).title(trafficData.getTitle()).visible(true));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mapPos, 14));
    }

    public double[] getLatAndLong(String latitudeLongitude) {
        String latitude = latitudeLongitude.substring(0, latitudeLongitude.indexOf(' '));
        String longitude = latitudeLongitude.substring(latitudeLongitude.indexOf(' '));
        return new double[]{Double.parseDouble(latitude), Double.parseDouble(longitude)};
    }
}

