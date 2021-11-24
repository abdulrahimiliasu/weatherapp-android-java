package com.example.weatherapp.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeatherInfoCardAdapter;
import com.example.weatherapp.model.ParentLocation;
import com.example.weatherapp.model.Weather;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapp.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    RequestQueue queue;
    ProgressBar loading;
    AppBarLayout appBar;
    LinearLayout weatherDetails;

    TextView cityInCountry;
    TextView timezoneText;
    TextView sunriseText;
    TextView sunsetText;

    RecyclerView weatherInfoRecyclerView;
    WeatherInfoCardAdapter adapter;
    ArrayList<Weather> weather;

    SharedPreferences sharedPref;
    int metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        loading = binding.loadingProgress;
        appBar = binding.appBar;
        weatherInfoRecyclerView = binding.weatherInfoRecyclerView;
        weatherDetails = binding.weatherDetails;

        weather = new ArrayList<>();
        adapter = new WeatherInfoCardAdapter(this,weather);
        weatherInfoRecyclerView.setAdapter(adapter);
        weatherInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        metric = sharedPref.getInt("metric",0);

        cityInCountry = binding.cityInCountryTv;
        timezoneText = binding.timezoneTv;
        sunriseText = binding.sunriseTv;
        sunsetText = binding.sunsetTv;


        int woeid = getIntent().getIntExtra("woeid",0);
        queue = Volley.newRequestQueue(DetailActivity.this);

        String url = "https://www.metaweather.com/api/location/"+ woeid +"/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,response -> {
            loading.setVisibility(View.GONE);
            appBar.setVisibility(View.VISIBLE);
            weatherInfoRecyclerView.setVisibility(View.VISIBLE);
            weatherDetails.setVisibility(View.VISIBLE);
            try {
                Objects.requireNonNull(getSupportActionBar()).setTitle(response.getString("title"));
                JSONArray consWeather = new JSONArray(response.getString("consolidated_weather"));
                for (int i=0;i<consWeather.length();i++) {
                    JSONObject object = consWeather.getJSONObject(i);
                    Weather weatherToAdd = new Weather(Long.parseLong(object.getString("id")),
                            object.getString("weather_state_name"),
                            object.getString("weather_state_abbr"),
                            object.getString("wind_direction_compass"),
                            object.getString("created"),
                            object.getString("applicable_date"),
                            Float.parseFloat(object.getString("min_temp")),
                            Float.parseFloat(object.getString("max_temp")),
                            Float.parseFloat(object.getString("the_temp")),
                            Float.parseFloat(object.getString("wind_speed")),
                            Float.parseFloat(object.getString("wind_direction")),
                            Float.parseFloat(object.getString("air_pressure")),
                            Float.parseFloat(object.getString("humidity")),
                            Float.parseFloat(object.getString("visibility")),
                            Float.parseFloat(object.getString("predictability"))
                            );
                    weather.add(weatherToAdd);
                    adapter.notifyDataSetChanged();
                }

                JSONObject parent = new JSONObject(response.getString("parent"));
                ParentLocation parentLocation = new ParentLocation(parent.getString("title"),
                        parent.getString("location_type"),
                        Integer.parseInt(parent.getString("woeid")),
                        parent.getString("latt_long")
                );

                cityInCountry.setText(getString(R.string.city_in_country,response.getString("location_type"),parentLocation.getTitle()));
                timezoneText.setText(getString(R.string.timezone,response.getString("timezone")));
                sunriseText.setText(getString(R.string.sunrise,formatDate(response.getString("sun_rise"))[1]));
                sunsetText.setText(getString(R.string.sunset,formatDate(response.getString("sun_set"))[1]));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.e("Error !", "Failed to get data."+ error));
        queue.add(request);
    }

    /**
     * format date as a string of @link LocalDateTime.
     * @param date date as string
     * @return string array, first index:date, second index:time.
     */
    public String[] formatDate(String date){
        String[] arr = date.split("T");
        arr[1] = arr[1].split("\\.")[0];
        return arr;
    }
}