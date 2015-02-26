package com.nikolak.weatherapp.ForecastIO;



import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public Currently currently = new Currently();
    public Hourly hourly;
    public Daily daily;

    private ForecastAPI forecastAPI = new ForecastAPI();

    public Boolean updateForecast(String lat, String lon) throws JSONException {
        JSONObject response = forecastAPI.getDefault(lat, lon);
        if (response==null) {
            return false;
        }

        JSONObject currentlyJObject = response.getJSONObject("currently");
        this.currently.ConstructFromJson(currentlyJObject);
        return true;
    }


}
