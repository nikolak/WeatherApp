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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.nikolak.weatherapp.ForecastIO.Currently;
import com.nikolak.weatherapp.ForecastIO.Day;
import com.nikolak.weatherapp.ForecastIO.Forecast;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity
        extends ActionBarActivity
        implements ConnectionCallbacks,
        OnConnectionFailedListener,
        SwipeRefreshLayout.OnRefreshListener {


    public static final String PREFS_NAME = "WeatherAppPrefs";
    public static final String TAG = "MainActivity";

    // Entry point to Google Play services
    protected GoogleApiClient mGoogleApiClient;

    // Geographical location from Play services
    protected Location playServicesLocation;

    // Location that's stored in preferences
    protected Location settingsLocation = new Location("");

    // Settings entry point
    private SharedPreferences settings;

    // Forecast entry point
    private Forecast forecast = new Forecast();

    // SwipeLayout

    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        settings = this.getSharedPreferences(PREFS_NAME, 0);
        buildGoogleApiClient();

    }

    // Check for forecast update
    // Handles initial checks, cache check etc and
    // decides whether to update the forecast and the UI or not

    public void checkForecastUpdate() {
        loadLocation();
        if (settingsLocation.getLatitude() == 0.0d ||
                settingsLocation.getLongitude() == 0.0d) {
            notifyFail("Unknown location.");
            return;
        }
        updateForecast();
    }

    // Handle saving and loading settings

    public void saveLocation(Double lat, Double lon) {
        long lastUpdateDate = System.currentTimeMillis() / 1000L;
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("latitude", lat.toString());
        prefEditor.putString("longitude", lon.toString());
        prefEditor.putLong("lastUpdate", lastUpdateDate);
        prefEditor.apply();
    }

    public void loadLocation() {
        String lat = settings.getString("latitude", null);
        String lon = settings.getString("longitude", null);
        if (lat == null || lon == null) {
            lat = "0.0";
            lon = "0.0";
        }

        settingsLocation.setLatitude(Double.valueOf(lat));
        settingsLocation.setLongitude(Double.valueOf(lon));
    }


    // Update forecast object

    public void updateForecast() {
        String lat = String.valueOf(settingsLocation.getLatitude());
        String lon = String.valueOf(settingsLocation.getLongitude());
        String lang = settings.getString("language", "en");
        FetchWeatherTask updater = new FetchWeatherTask();
        updater.execute(lat, lon, lang);
    }

    // Update app UI with new forecast values

    public void updateForecastUI() {
        Currently currently = forecast.currently;
        Day currentDay = forecast.daily.getDayData().get(0);
        String windSpeedUnit;
        if (forecast.flags.getUnits().equals("us")) {
            windSpeedUnit = "mph";
        } else {
            windSpeedUnit = "m/s";
        }
        String desc = currently.getSummary();
        String temp = Math.round(currently.getTemperature()) + "°";
        String apparent = Math.round(currently.getApparentTemperature()) + "°";
        String windSpeedValue = currently.getWindSpeed() + windSpeedUnit;
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

        ImageView currentIcon = (ImageView) findViewById(R.id.currentIcon);
        TextView summaryLabel = (TextView) findViewById(R.id.summaryLabel);
        TextView currentTemperature = (TextView) findViewById(R.id.currentTemperature);
        TextView apparentTemperature = (TextView) findViewById(R.id.apparentTemperature);
        TextView precipitation = (TextView) findViewById(R.id.precipitation);
        TextView windSpeed = (TextView) findViewById(R.id.windSpeed);
        TextView minTemperature = (TextView) findViewById(R.id.minTemperature);
        TextView maxTemperature = (TextView) findViewById(R.id.maxTemperature);

        TextView daySummary = (TextView) findViewById(R.id.daySummary);
        TextView weekSummary = (TextView) findViewById(R.id.weekSummary);
        currentIcon.setImageResource(icon);
        summaryLabel.setText(desc);

        currentTemperature.setText(temp);

        apparentTemperature.setText("Feels like: " + apparent);
        precipitation.setText(forecast.currently.getPrecipIntensity());
        windSpeed.setText(windSpeedValue);
        minTemperature.setText(minTemp);
        maxTemperature.setText(maxTemp);

        daySummary.setText(forecast.hourly.getSummary());
        weekSummary.setText(forecast.daily.getSummary());

        Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> address = null;
        try {
            address = geocoder.getFromLocation(
                    settingsLocation.getLatitude(),
                    settingsLocation.getLongitude(),
                    1);
        } catch (IOException e) {
            Log.e(TAG, settingsLocation.toString());
            e.printStackTrace();
        }

        if (address == null || address.size() <= 0) {
            Log.w(TAG, "Could not find a location name");
        } else {
            Address finalAddress = address.get(0);
            TextView locationLabel = (TextView) findViewById(R.id.locationLabel);
            locationLabel.setText(finalAddress.getLocality());
        }


    }

    public void notifyFail(String msg) {
        msg = msg != null ? msg : "Unknown error";

        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    // Google Play Location Services

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        playServicesLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (playServicesLocation != null) {
            Log.d("Latitude:", String.valueOf(playServicesLocation.getLatitude()));
            Log.d("Longitude", String.valueOf(playServicesLocation.getLongitude()));
            saveLocation(playServicesLocation.getLatitude(),
                    playServicesLocation.getLongitude());
            checkForecastUpdate();
        } else {
            Log.d(TAG, "Play services location is null");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        checkForecastUpdate();
        swipeLayout.setRefreshing(false);
    }

    // Fetch data from API
    public class FetchWeatherTask extends AsyncTask<String, Void, String> {
        private Boolean updated;

        @Override
        protected String doInBackground(String... params) {
            updated = false;
            try {
                String lat = params[0];
                String lon = params[1];
                String lang = params[2];
                updated = forecast.updateForecast(lat, lon, lang);
            } catch (JSONException e) {
                e.printStackTrace();
                updated = false;
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            if (updated) {
                updateForecastUI();
            } else {
                notifyFail("Getting data failed.");
            }
        }
    }

}
