package com.example.weatherapp.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Persistence {

    public static void addCity( Context context, City city){
        JSONArray citiesArray = getCities(context);
        try {
            JSONObject newCity = new JSONObject();
            newCity.put("title",city.title);
            newCity.put("type",city.type);
            newCity.put("woeid",city.woeId);
            newCity.put("position",city.position);
            citiesArray.put(newCity);
            writeToFile(context,citiesArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCity(Context context,int index){
            JSONArray citiesArray = getCities(context);
            citiesArray.remove(index);
            writeToFile(context,citiesArray.toString());
    }

    public static JSONArray getCities(Context context){
        String file = readFromFile(context);
        try{
            return new JSONArray(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public static ArrayList<City> getCitiesAsList(Context context){
        ArrayList<City> data = new ArrayList<>();
        try{
            JSONArray array = getCities(context);
            for (int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                City city = new City(obj.getString("title"),
                        obj.getString("type"),
                        Integer.parseInt(obj.getString("woeid")),
                        obj.getString("position"));
                data.add(city);
            }
        }catch (JSONException e){e.printStackTrace();}

        return data;
    }

    public static void writeToFile(Context context,String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("cities.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static String readFromFile(Context context) {

        String data = "";

        try {
            InputStream inputStream = context.openFileInput("cities.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Main Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Main Activity", "Can not read file: " + e.toString());
        }

        return data;
    }

}
