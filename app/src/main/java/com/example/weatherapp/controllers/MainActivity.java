package com.example.weatherapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeatherCardAdapter;
import com.example.weatherapp.model.City;
import com.example.weatherapp.model.Persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    RecyclerView cardRecyclerView;
    WeatherCardAdapter adapter;
    ArrayList<City> cityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.cities);

        cityArrayList = Persistence.getCitiesAsList(this);

        cardRecyclerView = binding.weatherCardRv;
        adapter = new WeatherCardAdapter(this,cityArrayList);
        cardRecyclerView.setAdapter(adapter);
        cardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void fabOnClick(View view){
        Intent searchIntent = new Intent(this,AddCityActivity.class);
        startActivity(searchIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cityArrayList = Persistence.getCitiesAsList(this);
        adapter = new WeatherCardAdapter(this,cityArrayList);
        cardRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this,SettingsActivity.class);
        if (id == R.id.action_settings) {
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}