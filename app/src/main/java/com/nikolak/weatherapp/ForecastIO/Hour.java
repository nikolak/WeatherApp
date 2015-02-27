package com.nikolak.weatherapp.ForecastIO;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Hour {

    private Integer time;
    private String summary;
    private String icon;
    private Double precipIntensity;
    private Double precipProbability;
    private Double temperature;
    private Double apparentTemperature;
    private Double dewPoint;
    private Double humidity;
    private Double windSpeed;
    private Integer windBearing;
    private Double cloudCover;
    private Double pressure;
    private Double ozone;

    public Integer getTime() {
        return this.time;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getIcon() {
        return this.icon;
    }

    public Double getPrecipIntensity() {
        return this.precipIntensity;
    }

    public Double getPrecipProbability() {
        return this.precipProbability;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public Double getApparentTemperature() {
        return this.apparentTemperature;
    }

    public Double getDewPoint() {
        return this.dewPoint;
    }

    public Double getHumidity() {
        return this.humidity;
    }

    public Double getWindSpeed() {
        return this.windSpeed;
    }

    public Integer getWindBearing() {
        return this.windBearing;
    }

    public Double getCloudCover() {
        return this.cloudCover;
    }

    public Double getPressure() {
        return this.pressure;
    }

    public Double getOzone() {
        return this.ozone;
    }

    public void constrcutFromJson(JSONObject hourData) throws JSONException {
        this.time = hourData.getInt("time");
        this.summary = hourData.getString("summary");
        this.icon = hourData.getString("icon");
        this.precipIntensity = hourData.getDouble("precipIntensity");
        this.precipProbability = hourData.getDouble("precipProbability");
        this.temperature = hourData.getDouble("temperature");
        this.apparentTemperature = hourData.getDouble("apparentTemperature");
        this.dewPoint = hourData.getDouble("dewPoint");
        this.humidity = hourData.getDouble("humidity");
        this.windSpeed = hourData.getDouble("windSpeed");
        this.windBearing = hourData.getInt("windBearing");
        this.cloudCover = hourData.getDouble("cloudCover");
        this.pressure = hourData.getDouble("pressure");
        this.ozone = hourData.getDouble("ozone");
        Log.d("Hour", "Constructed: " + time.toString());
    }
}
