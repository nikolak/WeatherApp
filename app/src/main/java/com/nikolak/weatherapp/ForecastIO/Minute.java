package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Minute {

    private Integer time;
    private Double precipIntensity;
    private Double precipProbability;

    public Integer getTime(){return this.time;}
    public Double getPrecipIntensity(){return this.precipIntensity;}
    public Double getPrecipProbability(){return this.precipProbability;}

    public void constrcutFromJson(JSONObject minutelyJson) throws JSONException{
        this.time = minutelyJson.getInt("time");
        this.precipIntensity = minutelyJson.getDouble("precipIntensity");
        this.precipProbability = minutelyJson.getDouble("precipProbability");
    }
}

