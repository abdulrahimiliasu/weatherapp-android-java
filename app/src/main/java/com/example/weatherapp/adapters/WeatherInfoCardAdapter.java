package com.example.weatherapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.Weather;

import java.util.ArrayList;

public class WeatherInfoCardAdapter extends RecyclerView.Adapter<WeatherInfoCardAdapter.InfoCardViewHolder>{

    private final ArrayList<Weather> weatherArrayList;
    private final LayoutInflater layoutInflater;
    private final Context context;
    SharedPreferences sharedPref;
    int metric;

    public WeatherInfoCardAdapter(Context context, ArrayList<Weather> arrayList){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.weatherArrayList = arrayList;
        sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        metric = sharedPref.getInt("metric",0);
    }

    @NonNull
    @Override
    public InfoCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = layoutInflater.inflate(R.layout.weather_info_card,parent,false);
        return new InfoCardViewHolder(card, this);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoCardViewHolder holder, int position) {
        Weather currentWeather = weatherArrayList.get(position);

        String temp = Math.round(currentWeather.getTemp()) + context.getString(R.string.deg_celsius);
        String high = Math.round(currentWeather.getMaxTemp()) + context.getString(R.string.deg_celsius);
        String low  = Math.round(currentWeather.getMinTemp()) + context.getString(R.string.deg_celsius);

        if (metric == 2131230905){
            temp = getFahrenheitValue(currentWeather.getTemp());
            high = getFahrenheitValue(currentWeather.getMaxTemp());
            low = getFahrenheitValue(currentWeather.getMinTemp());
        }

        int img = getImage(currentWeather.getStateAbbreviation());
        holder.imageView.setImageResource(img);

        if (position == 0)
            holder.dateText.setText(context.getString(R.string.today));
        else if (position == 1)
            holder.dateText.setText(context.getString(R.string.tomorrow));
        else
            holder.dateText.setText(currentWeather.getApplicableDate());

        holder.tempText.setText(temp);
        holder.stateText.setText(context.getString(R.string.weather_state, currentWeather.getState()));
        holder.highText.setText(context.getString(R.string.weather_max,high));
        holder.lowText.setText(context.getString(R.string.weather_min, low));
        holder.humidityText.setText(context.getString(R.string.weather_humidity,Math.round(currentWeather.getHumidity())));
        holder.airPressureText.setText(context.getString(R.string.weather_air_pressure,Math.round(currentWeather.getAirPressure())));
        holder.visibilityText.setText(context.getString(R.string.weather_visibility,Math.round(currentWeather.getVisibility())));
        holder.windDirectionText.setText(context.getString(R.string.weather_wind_direction,Math.round(currentWeather.getWindDirection())));
    }

    private String getFahrenheitValue(float tempInCelsius){
        return Math.round((tempInCelsius * 9/5) + 32 )+ context.getString(R.string.deg_fahrenheit);
    }

    private int getImage(String stateAbbreviation){
        switch (stateAbbreviation){
            case "hc":
                return R.drawable.ic_hc;
            case "t":
                return R.drawable.ic_t;
            case "sn":
                return R.drawable.ic_sn;
            case "sl":
                return R.drawable.ic_sl;
            case "lr":
                return R.drawable.ic_lr;
            case "hr":
                return R.drawable.ic_hr;
            case "h":
                return R.drawable.ic_h;
            case "s":
                return R.drawable.ic_s;
            case "lc":
                return R.drawable.ic_lc;
            case "c":
                return R.drawable.ic_c;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }

    static class InfoCardViewHolder extends RecyclerView.ViewHolder{

        public final TextView dateText;
        public final TextView tempText;
        public final ImageView imageView;
        public final TextView stateText;
        public final TextView highText;
        public final TextView lowText;
        public final TextView humidityText;
        public final TextView airPressureText;
        public final TextView visibilityText;
        public final TextView windDirectionText;

        final WeatherInfoCardAdapter adapter;

        public InfoCardViewHolder(View itemView, WeatherInfoCardAdapter adapter) {
            super(itemView);
            dateText = itemView.findViewById(R.id.date_tv);
            tempText = itemView.findViewById(R.id.temp_tv);
            imageView = itemView.findViewById(R.id.temp_iv);
            stateText = itemView.findViewById(R.id.state_tv);
            highText = itemView.findViewById(R.id.high_tv);
            lowText = itemView.findViewById(R.id.low_tv);
            humidityText = itemView.findViewById(R.id.humidity_tv);
            airPressureText = itemView.findViewById(R.id.air_press_tv);
            visibilityText = itemView.findViewById(R.id.visibility_tv);
            windDirectionText = itemView.findViewById(R.id.wind_dir_tv);
            this.adapter = adapter;

        }
    }
}
