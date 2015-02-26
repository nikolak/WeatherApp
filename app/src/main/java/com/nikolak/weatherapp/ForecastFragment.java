package com.nikolak.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nikolak.weatherapp.ForecastIO.Current;
import com.nikolak.weatherapp.ForecastIO.ForecastAPI;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class ForecastFragment extends Fragment {// implements LocationListener{

    protected String latitude, longitude;
    private SharedPreferences settings;
    private View rootView;

    public ImageView currentIcon;

    public TextView currentSummary;

    public TextView currentTemperature;
    public TextView currentApparentTemp;
    public TextView currentPerception;
    public TextView currentWindSpeed;
    public TextView currentMinTemp;
    public TextView currentMaxTemp;
//    private ForecastAPI forecastAPI;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        updateForecast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        currentIcon = (ImageView) rootView.findViewById(R.id.currentIcon);

        currentSummary = (TextView) rootView.findViewById(R.id.summaryLabel);

        currentTemperature =  (TextView) rootView.findViewById(R.id.currentTemperature);
        currentApparentTemp = (TextView) rootView.findViewById(R.id.apparentTemperature);
        currentPerception = (TextView)  rootView.findViewById(R.id.perception);
        currentWindSpeed = (TextView) rootView.findViewById(R.id.windSpeed);
        currentMinTemp = (TextView) rootView.findViewById(R.id.minTemperature);
        currentMaxTemp = (TextView) rootView.findViewById(R.id.maxTemperature);

        return rootView;
    }

    public String[] getLocation(){
        return null;
    }

    public void saveLocation(){

    }

    public void updateForecast(){
        latitude = settings.getString("latitude", null);
        longitude = settings.getString("longitude", null);
        FetchWeatherTask updater = new FetchWeatherTask();
        updater.execute();
    }

    public void updateForecastUI(Current current){
        String desc = current.getSummary();
        String temp = Math.round(current.getTemperature())+"°";
        String apparent = Math.round(current.getApparentTemperature())+"°";
        String perception = "0.0mm";
        String windSpeed = current.getWindSpeed()+"mph";
        String minTemp = "-5°";
        String maxTemp = "5°";
        Integer icon;
        switch (current.getIcon()){
            case "clear-day":
                icon = R.drawable.clearday;
                break;
            case  "clear-night":
                icon = R.drawable.clearnight;
                break;
            case "rain":
                icon = R.drawable.rain;
                break;
            case "snow":
                icon = R.drawable.snow;
                break;
            case "sleet":
                icon = R.drawable.sleet;
                break;
            case "wind":
                icon = R.drawable.wind;
                break;
            case "fog":
                icon = R.drawable.fog;
                break;
            case "cloudy":
                icon = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                icon = R.drawable.partlycloudyday;
                break;
            case "partly-cloudy-night":
                icon = R.drawable.partlycloudynight;
                break;
            default:
                //TODO: Add default icon
                icon = R.drawable.fog;

        }
        currentIcon.setImageResource(icon);
        currentSummary.setText(desc);

        currentTemperature.setText(temp);

        currentApparentTemp.setText("Feels like: "+apparent);
        currentPerception.setText(perception);
        currentWindSpeed.setText(windSpeed);
        currentMinTemp.setText(minTemp);
        currentMaxTemp.setText(maxTemp);
    }


    public class FetchWeatherTask extends AsyncTask<String, Void, String> {

        private Current current;
        @Override
        protected String doInBackground(String... params) {
            ForecastAPI f = new ForecastAPI();
            current = f.getCurrent(latitude,longitude);
            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            if(current != null){
                updateForecastUI(current);
            }
        }
    }
}