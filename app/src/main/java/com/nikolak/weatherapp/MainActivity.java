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
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
import org.w3c.dom.Text;

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

    // Current data card elements' variables

    private ImageView currentIcon;
    private TextView currentDescription;
    private TextView currentFeelsLike;
    private TextView currentWind;
    private TextView currentHumidity;

    private TextView currentTemperature;
    private TextView currentLow;
    private TextView currentHigh;

    // Next day and next 7 days description card variables

    private TextView nextHourDesc;
    private TextView nextDayDesc;

    // List with 48 hour forecast data
    private ListView hourLIst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //swipeLayout.setOnRefreshListener(this);
        settings = this.getSharedPreferences(PREFS_NAME, 0);
        buildGoogleApiClient();

        ScrollView mainScroll = (ScrollView) findViewById(R.id.mainScrollView);

        hourLIst = (ListView) findViewById(R.id.two_day_list);
        hourLIst.setAdapter(new HourListAdapter(this,
                forecast.hourly.getHourData()));

        // Assign current weather card elements to variables

        currentIcon = (ImageView) findViewById(R.id.currentIcon);
        currentDescription = (TextView) findViewById(R.id.currentDescription);
        currentFeelsLike = (TextView) findViewById(R.id.currentFeelsLike);
        currentWind = (TextView) findViewById(R.id.currentWind);
        currentHumidity = (TextView) findViewById(R.id.currentHumidity);

        currentTemperature = (TextView) findViewById(R.id.currentTemperature);
        currentLow = (TextView) findViewById(R.id.currentLow);
        currentHigh = (TextView) findViewById(R.id.currentHigh);

        // Assign next hour/day card elements to variables

        nextHourDesc = (TextView) findViewById(R.id.nextHourDesc);
        nextDayDesc = (TextView) findViewById(R.id.nextDayDesc);

        // Add on touch listeners to allow scrolling of the lists inside scrollview
        mainScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.two_day_list).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });


        hourLIst.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
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
        //TODO: Remove fixed label names and use different element
        currentIcon.setImageResource(Utils.getIconFromValue(currentDay.getIcon()));
        currentDescription.setText(currently.getSummary());
        currentFeelsLike.setText("Feels like: " + Math.round(currently.getApparentTemperature()) + "°");
        currentWind.setText("Wind: " + currently.getWindSpeed() + windSpeedUnit);
        currentHumidity.setText("Humidity: " + currently.getHumidityPerc());
        currentTemperature.setText(Math.round(currently.getTemperature()) + "°");
        currentLow.setText("Low " + Math.round(currentDay.getTemperatureMin()) + "°"
                + " at " + Utils.getHour(currentDay.getTemperatureMinTime()));
        currentHigh.setText("Low " + Math.round(currentDay.getTemperatureMax()) + "°"
                + " at " + Utils.getHour(currentDay.getTemperatureMaxTime()));

        nextHourDesc.setText(forecast.hourly.getHourData().get(0).getSummary());
        nextDayDesc.setText(forecast.hourly.getSummary());

        ((BaseAdapter) hourLIst.getAdapter()).notifyDataSetChanged();
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
            Intent settingsIntetnt = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntetnt);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        checkForecastUpdate();
        swipeLayout.setRefreshing(false);
    }

    // Fetch hourData from API
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
