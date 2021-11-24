package com.example.weatherapp.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ActivityAddCityBinding;
import com.example.weatherapp.model.City;
import com.example.weatherapp.model.Persistence;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class AddCityActivity extends AppCompatActivity {

    ListView searchListView;
    EditText searchTextField;
    ProgressBar searchProgressBar;
    ArrayAdapter<String> listAdapter;
    ArrayList<City> searchedCities = new ArrayList<>();
    ArrayList<String> searchedCityTitles = new ArrayList<>();
    RequestQueue queue;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAddCityBinding binding = ActivityAddCityBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_city);
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(AddCityActivity.this);

        searchListView = binding.searchListView;
        listAdapter = new ArrayAdapter<>(this, R.layout.search_list_item,searchedCityTitles);
        searchListView.setAdapter(listAdapter);
        searchProgressBar = binding.searchProgressBar;
        searchTextField = binding.searchCityTextfield;
        searchButton = binding.searchButton;

        searchListView.setOnItemClickListener((adapterView, view, i, l) -> {
            City clickedCity = searchedCities.get(i);
            Log.d("DEBUG","Clicked:" + clickedCity + " Saving to File...");
            Persistence.addCity(this,clickedCity);
            Snackbar.make(view, clickedCity.getTitle() + " was added successfully!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.success))
                    .show();
        });

        searchButton.setOnClickListener(view -> {
            String searchedCity = searchTextField.getText().toString();
            searchListView.setVisibility(View.GONE);

            if (searchedCity.equals("")){
                Snackbar.make(view, "Please Fill in search bar !", Snackbar.LENGTH_LONG)
                        .setAction("Success", null).show();
                return;
            }
            searchedCities.clear();
            searchedCityTitles.clear();
            String url = "https://www.metaweather.com/api/location/search/?query="+searchedCity;
            searchProgressBar.setVisibility(View.VISIBLE);

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                    null, response -> {
                searchProgressBar.setVisibility(View.GONE);
                searchListView.setVisibility(View.VISIBLE);
                try{
                    for(int i =0; i < response.length(); i++  ){
                        JSONObject item = response.getJSONObject(i);
                        String title = item.getString("title");
                        String type = item.getString("location_type");
                        String woeid = item.getString("woeid");
                        String position = item.getString("latt_long");
                        City newCityToAdd = new City(title, type,Integer.parseInt(woeid),position);
                        searchedCities.add(newCityToAdd);
                        searchedCityTitles.add(newCityToAdd.getTitle());
                    }
                    listAdapter.notifyDataSetChanged();
                    System.out.println(searchedCities);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }, error -> {
                searchProgressBar.setVisibility(View.GONE);
                Log.e("Searched Result Error!", "Failed to get data."+ error);
            });
            queue.add(jsonObjectRequest);
        });
    }

}