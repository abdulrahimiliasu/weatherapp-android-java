package com.example.weatherapp.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ActivitySettingsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    RadioButton celsiusRadioButton;
    RadioButton fahrenheitRadioButton;
    SharedPreferences sharedPref;
    RadioGroup metricRadioGroup;
    Button saveButton;
    CheckBox showLatLongCheckbox;
    int metric;
    boolean showLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        metric = sharedPref.getInt("metric",2131230826);
        showLatLong = sharedPref.getBoolean("show_lat_long",false);

        celsiusRadioButton = binding.celsiusRadioButton;
        fahrenheitRadioButton = binding.fahrenheitRadioButton;
        metricRadioGroup = binding.metricRadioGroup;
        saveButton = binding.saveButton;
        showLatLongCheckbox = binding.showLatLongCheckbox;

        metricRadioGroup.check(metric);
        showLatLongCheckbox.setChecked(showLatLong);
        metricRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> metric = i);
        showLatLongCheckbox.setOnCheckedChangeListener((compoundButton, b) -> showLatLong = b);

        saveButton.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("metric", metric);
            editor.putBoolean("show_lat_long",showLatLong);
            editor.apply();
            Snackbar.make(view, "Settings Saved successfully!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.success))
            .show();
        });

    }

}