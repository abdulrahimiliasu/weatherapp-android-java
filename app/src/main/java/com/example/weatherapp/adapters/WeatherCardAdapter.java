package com.example.weatherapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.controllers.DetailActivity;
import com.example.weatherapp.model.City;
import com.example.weatherapp.model.Persistence;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WeatherCardAdapter extends RecyclerView.Adapter<WeatherCardAdapter.CardViewHolder>{

    private final ArrayList<City> cityArrayList;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final boolean showLatitudeAndLongitude;

    public WeatherCardAdapter(Context context, ArrayList<City> arrayList){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.cityArrayList = arrayList;
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        showLatitudeAndLongitude = sharedPref.getBoolean("show_lat_long",true);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = layoutInflater.inflate(R.layout.weather_card,parent,false);
        return new CardViewHolder(card,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        City currentCity = cityArrayList.get(position);
        holder.titleTextView.setText(currentCity.getTitle());
        holder.typeTextView.setText(currentCity.getType());

        if (showLatitudeAndLongitude){
            String lat = "lat: " + Math.round(Float.parseFloat(currentCity.getPosition().split(",")[0]));
            String lon = "long: " + Math.round(Float.parseFloat(currentCity.getPosition().split(",")[1]));
            String pos = lat+", "+lon;
            holder.positionTextView.setText(pos);
            holder.positionTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        public final TextView titleTextView;
        public final TextView typeTextView;
        public final TextView positionTextView;
        public final ImageView deleteButton;

        final WeatherCardAdapter adapter;

        public CardViewHolder(View itemView, WeatherCardAdapter adapter) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            deleteButton = itemView.findViewById(R.id.deleteCityButton);
            this.adapter = adapter;

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                int woeid = cityArrayList.get(position).getWoeId();
                Intent goToDetails = new Intent(context, DetailActivity.class);
                goToDetails.putExtra("woeid",woeid);
                context.startActivity(goToDetails);
            });

            deleteButton.setOnClickListener(view -> {
                int position =  getAdapterPosition();
                cityArrayList.remove(position);
                Persistence.deleteCity(context,position);
                adapter.notifyItemRemoved(position);
                Snackbar.make(view, "City Deleted", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(context.getResources().getColor(R.color.error))
                        .show();
            });
        }
    }
}
