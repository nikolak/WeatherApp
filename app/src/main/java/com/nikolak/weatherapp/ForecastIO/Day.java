package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONException;
import org.json.JSONObject;

public class Day {

    private Integer time;
    private String summary;
    private String icon;
    private Integer sunriseTime;
    private Integer sunsetTime;
    private Double moonPhase;
    private Double precipIntensity;
    private Double precipIntensityMax;
    private Integer precipIntensityMaxTime;
    private Double precipProbability;
    private String precipType;
    private Double temperatureMin;
    private Integer temperatureMinTime;
    private Double temperatureMax;
    private Integer temperatureMaxTime;
    private Double apparentTemperatureMin;
    private Integer apparentTemperatureMinTime;
    private Double apparentTemperatureMax;
    private Integer apparentTemperatureMaxTime;
    private Double dewPoint;
    private Double humidity;
    private Double windSpeed;
    private Integer windBearing;
    private Double cloudCover;
    private Double pressure;
    private Double ozone;

    private Double precipAccumulation;  // Not Always Present
    private Double visibility;          // Not Always Present

    public void constructFromJson(JSONObject dayJson) throws JSONException {


        this.time = dayJson.getInt("time");
        this.summary = dayJson.getString("summary");
        this.icon = dayJson.getString("icon");
        this.moonPhase = dayJson.getDouble("moonPhase");
        this.sunriseTime = dayJson.getInt("sunriseTime");
        this.sunsetTime = dayJson.getInt("sunsetTime");
        this.precipIntensity = dayJson.getDouble("precipIntensity");
        this.precipIntensityMax = dayJson.getDouble("precipIntensityMax");
        this.precipIntensityMaxTime = dayJson.getInt("precipIntensityMaxTime");
        this.precipProbability = dayJson.getDouble("precipProbability");
        this.precipType = dayJson.getString("precipType");
        this.temperatureMin = dayJson.getDouble("temperatureMin");
        this.temperatureMinTime = dayJson.getInt("temperatureMinTime");
        this.temperatureMax = dayJson.getDouble("temperatureMax");
        this.temperatureMaxTime = dayJson.getInt("temperatureMaxTime");
        this.apparentTemperatureMin = dayJson.getDouble("apparentTemperatureMin");
        this.apparentTemperatureMinTime = dayJson.getInt("apparentTemperatureMinTime");
        this.apparentTemperatureMax = dayJson.getDouble("apparentTemperatureMax");
        this.apparentTemperatureMaxTime = dayJson.getInt("apparentTemperatureMaxTime");
        this.dewPoint = dayJson.getDouble("dewPoint");
        this.humidity = dayJson.getDouble("humidity");
        this.windSpeed = dayJson.getDouble("windSpeed");
        this.windBearing = dayJson.getInt("windBearing");
        this.cloudCover = dayJson.getDouble("cloudCover");
        this.pressure = dayJson.getDouble("pressure");
        this.ozone = dayJson.getDouble("ozone");

        try {
            this.precipAccumulation = dayJson.getDouble("precipAccumulation");
        } catch (JSONException e) {
            this.precipAccumulation = null;
        }
        try {
            this.visibility = dayJson.getDouble("visibility");
        } catch (JSONException e) {
            this.visibility = null;
        }
    }
}
