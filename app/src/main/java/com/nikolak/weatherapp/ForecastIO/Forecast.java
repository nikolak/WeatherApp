package com.nikolak.weatherapp.ForecastIO;



import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public Currently currently = new Currently();
    public Minutely minutely = new Minutely();
    public Hourly hourly = new Hourly();
    public Daily daily = new Daily();

    private ForecastAPI forecastAPI = new ForecastAPI();

    public Boolean updateForecast(String lat, String lon) throws JSONException {
        JSONObject response = forecastAPI.getDefault(lat, lon);
        if (response==null) {
            return false;
        }

        JSONObject currentlyJObject = response.getJSONObject("currently");
        this.currently.ConstructFromJson(currentlyJObject);

        try{
            JSONObject minutelyJObject = response.getJSONObject("minutely");
            if (minutelyJObject!=null){
                minutely.constructFromJson(minutelyJObject);
            } else{
                minutely = null;
            }
        } catch (JSONException e){
            Log.d("Json", "Minutely not available");
        }

        JSONObject hourlyJObject = response.getJSONObject("hourly");
        this.hourly.constructFromJson(hourlyJObject);
        return true;
    }


}
