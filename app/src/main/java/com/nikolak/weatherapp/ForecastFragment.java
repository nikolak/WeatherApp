//    Copyright 2015 Nikola Kovacevic <nikolak@outlook.com>
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.

package com.nikolak.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nikolak.weatherapp.ForecastIO.Currently;
import com.nikolak.weatherapp.ForecastIO.Day;
import com.nikolak.weatherapp.ForecastIO.Forecast;

import org.json.JSONException;
import org.w3c.dom.Text;

/**
 * Encapsulates fetching the forecast and displaying it as a {@link ListView} layout.
 */
public class ForecastFragment extends Fragment {// implements LocationListener{

    public ImageView currentIcon;
    public TextView currentSummary;
    public TextView currentTemperature;
    public TextView currentApparentTemp;
    public TextView currentPerception;
    public TextView currentWindSpeed;
    public TextView currentMinTemp;
    public TextView currentMaxTemp;
    public TextView daySummary;
    public TextView weekSummary;


    protected String latitude, longitude;
    private SharedPreferences settings;
    private View rootView;
    private Forecast forecast = new Forecast();

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        updateForecast();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        currentIcon = (ImageView) rootView.findViewById(R.id.currentIcon);

        currentSummary = (TextView) rootView.findViewById(R.id.summaryLabel);

        currentTemperature = (TextView) rootView.findViewById(R.id.currentTemperature);
        currentApparentTemp = (TextView) rootView.findViewById(R.id.apparentTemperature);
        currentPerception = (TextView) rootView.findViewById(R.id.perception);
        currentWindSpeed = (TextView) rootView.findViewById(R.id.windSpeed);
        currentMinTemp = (TextView) rootView.findViewById(R.id.minTemperature);
        currentMaxTemp = (TextView) rootView.findViewById(R.id.maxTemperature);

        daySummary = (TextView) rootView.findViewById(R.id.daySummary);
        weekSummary = (TextView) rootView.findViewById(R.id.weekSummary);

        return rootView;
    }

    public String[] getLocation() {
        return null;
    }

    public void saveLocation() {

    }

    public void updateForecast() {
        latitude = settings.getString("latitude", null);
        longitude = settings.getString("longitude", null);
        FetchWeatherTask updater = new FetchWeatherTask();
        updater.execute();
    }

    public void updateForecastUI() {
        Currently currently = forecast.currently;
        Day currentDay = forecast.daily.getDayData().get(0);
        String windSpeedUnit = null;
        if (forecast.flags.getUnits().equals("us")) {
            windSpeedUnit = "mph";
        } else {
            windSpeedUnit = "m/s";
        }
        String desc = currently.getSummary();
        String temp = Math.round(currently.getTemperature()) + "°";
        String apparent = Math.round(currently.getApparentTemperature()) + "°";
        String perception = forecast.currently.getPrecipIntensity().toString();
        String windSpeed = currently.getWindSpeed() + windSpeedUnit;
        String minTemp = Math.round(currentDay.getTemperatureMin()) + "°";
        String maxTemp = Math.round(currentDay.getTemperatureMax()) + "°";
        Integer icon;
        switch (currently.getIcon()) {
            case "clear-day":
                icon = R.drawable.clearday;
                break;
            case "clear-night":
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

        currentApparentTemp.setText("Feels like: " + apparent);
        currentPerception.setText(perception);
        currentWindSpeed.setText(windSpeed);
        currentMinTemp.setText(minTemp);
        currentMaxTemp.setText(maxTemp);

        daySummary.setText(forecast.hourly.getSummary());
        weekSummary.setText(forecast.daily.getSummary());
    }


    public class FetchWeatherTask extends AsyncTask<String, Void, String> {
        private Boolean updated;

        @Override
        protected String doInBackground(String... params) {
            updated = false;
            try {
                String lat = params[0];
                String lon = params[0];
                String lang = "en";
                if(params.length==3) {
                    lang = params[3];
                }

                updated = forecast.updateForecast(lat, lon, lang);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            if (updated) {
                updateForecastUI();
            }
        }
    }
}