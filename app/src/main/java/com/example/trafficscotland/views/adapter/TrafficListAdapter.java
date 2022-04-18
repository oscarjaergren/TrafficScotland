package com.example.trafficscotland.views.adapter;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trafficscotland.Models.TrafficData;
import com.example.trafficscotland.R;
import com.example.trafficscotland.views.callback.TrafficClickCallback;

import java.util.List;
import java.util.Objects;

public class TrafficListAdapter extends RecyclerView.Adapter<TrafficListAdapter.TrafficListAdapterViewHolder> implements TrafficClickCallback {
    List<? extends TrafficData> trafficDataList;

    @Nullable
    private final TrafficClickCallback trafficClickCallback;

    public TrafficListAdapter(@Nullable TrafficClickCallback trafficClickCallback) {
        this.trafficClickCallback = trafficClickCallback;
    }


    @NonNull
    @Override
    public TrafficListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.traffic_data_list_view, parent, false);
        return new TrafficListAdapterViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull TrafficListAdapterViewHolder holder, int position) {

        TrafficData trafficData = trafficDataList.get(position);

        ((TrafficListAdapterViewHolder) holder).textView_title.setText(trafficData.getTitle());

        Long roadWorkLengthLong = trafficData.getRoadWorkLength();
        if(roadWorkLengthLong != null){
            int roadWorkLength = Math.toIntExact(roadWorkLengthLong);
            ((TrafficListAdapterViewHolder) holder).road_length.setText(String.valueOf(roadWorkLength));

            if (roadWorkLength > 100)
                ((TrafficListAdapterViewHolder) holder).road_length.setTextColor(Color.parseColor("#FF0000"));;
        }
    }

    @Override
    public int getItemCount() {
        if (trafficDataList == null) return 0;
        return trafficDataList.size();
    }

    public void setTrafficDataList(List<TrafficData> trafficDataList) {
        this.trafficDataList = trafficDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(TrafficData trafficData) {

    }


    public class TrafficListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_title;

        TextView road_length;


        public TrafficListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            textView_title = itemView.findViewById(R.id.textView_title);
            road_length = itemView.findViewById(R.id.roadLength);

        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();

            TrafficData trafficData = trafficDataList.get(position);

            if (trafficClickCallback != null) {
                trafficClickCallback.onClick(trafficData);
            }
            Log.d("TAG", "Clicked!");
        }
    }
}