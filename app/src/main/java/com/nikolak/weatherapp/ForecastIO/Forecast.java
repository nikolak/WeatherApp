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

package com.nikolak.weatherapp.ForecastIO;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public Currently currently = new Currently();
    public Minutely minutely = new Minutely();
    public Hourly hourly = new Hourly();
    public Daily daily = new Daily();
    public Flags flags = new Flags();

    private ForecastAPI forecastAPI = new ForecastAPI();

    public Boolean updateForecast(String lat, String lon, String lang) throws JSONException {
        JSONObject response;
        try{
            response = forecastAPI.getDefault(lat, lon, lang);
        } catch (NullPointerException e){
            response = null;
            return false;
        }

        JSONObject currentlyJObject = response.getJSONObject("currently");
        this.currently.ConstructFromJson(currentlyJObject);

        try {
            JSONObject minutelyJObject = response.getJSONObject("minutely");
            if (minutelyJObject != null) {
                minutely.constructFromJson(minutelyJObject);
            } else {
                minutely = null;
            }
        } catch (JSONException e) {
            Log.d("Json", "Minutely not available");
        }
        try {
            JSONObject hourlyJObject = response.getJSONObject("hourly");
            this.hourly.constructFromJson(hourlyJObject);
        } catch (JSONException e){
            Log.d("Hourly exception", e.toString());
        }

        JSONObject dailyObject = response.getJSONObject("daily");
        this.daily.constructFromJson(dailyObject);

        JSONObject flagsObject = response.getJSONObject("flags");
        this.flags.constructFromJson(flagsObject);
        return true;
    }


}
