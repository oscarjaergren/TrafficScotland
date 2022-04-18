package com.example.trafficscotland.views.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficscotland.DateHelper;
import com.example.trafficscotland.Models.TrafficData;
import com.example.trafficscotland.R;
import com.example.trafficscotland.viewmodel.TrafficDataSharedViewModel;
import com.example.trafficscotland.views.adapter.TrafficListAdapter;
import com.example.trafficscotland.views.callback.TrafficClickCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private ProgressBar progressBar;
    private TrafficDataSharedViewModel sharedViewModel;

    RecyclerView recyclerView;
    TrafficListAdapter trafficAdapter;



    BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.list);
        trafficAdapter = new TrafficListAdapter(projectClickCallback);
        recyclerView.setAdapter(trafficAdapter);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        sharedViewModel = new ViewModelProvider(getViewModelStore(),
                getDefaultViewModelProviderFactory()).get(TrafficDataSharedViewModel.class);

        sharedViewModel.getTrafficListObservable().observe(this, trafficListObserver);

        // Set Home selected
        bottomNavigation.setSelectedItemId(R.id.current_roadworks_button);
        final String currentRoadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
        sharedViewModel.loadTrafficData(currentRoadworksUrl);

        // Perform item selected listener
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {

            progressBar.setVisibility(View.VISIBLE);

            switch (item.getItemId()) {
                case R.id.current_roadworks_button:
                    sharedViewModel.loadTrafficData(currentRoadworksUrl);
                    return true;
                case R.id.planned_roadworks_button:
                    final String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
                    sharedViewModel.loadTrafficData(plannedRoadworksUrl);
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.current_incidents_button:
                    final String currentIncidentsUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
                    sharedViewModel.loadTrafficData(currentIncidentsUrl);
                    overridePendingTransition(1, 0);
                    return true;
            }
            return false;
        });


        EditText userInput = findViewById(R.id.userInput);

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog StartTime = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            List<TrafficData> list = sharedViewModel.getTrafficListObservable().getValue();

            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(year, monthOfYear, dayOfMonth);
            Date date = tempCalendar.getTime();

            List<TrafficData> filteredList = list.stream()
                    .filter(dates -> dates.getStartDate().getDay() == date.getDay())
                    .collect(Collectors.toList());

            if (filteredList.stream().count() != list.stream().count())
            trafficAdapter.setTrafficDataList(filteredList);


        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        RadioButton dateSelector = findViewById(R.id.date_select);

        dateSelector.setOnClickListener((View.OnClickListener) v -> {
            StartTime.show();
        });

    }


    Observer<List<TrafficData>> trafficListObserver = new Observer<List<TrafficData>>() {
        @Override
        public void onChanged(List<TrafficData> trafficList) {
            trafficAdapter.setTrafficDataList(trafficList);
            progressBar.setVisibility(View.GONE);
        }
    };

    public void openTrafficDetails() {
        Intent intent = new Intent(getApplicationContext(), TrafficDetailsActivity.class);
        startActivity(intent);
    }

    private final TrafficClickCallback projectClickCallback = trafficData -> {
        DataHolder.getInstance().setData(trafficData);
        openTrafficDetails();
    };
}
