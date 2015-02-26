package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONException;
import org.json.JSONObject;

public class Currently {
    private Integer time;
    private String summary;
    private String icon;
    private Double precipIntensity;
    private Double precipProbability;
    private String precipType;
    private Double temperature;
    private Double apparentTemperature;
    private Double dewPoint;
    private Double humidity;
    private Double windSpeed;
    private Integer windBearing;
    private Double cloudCover;
    private Double pressure;
    private Double ozone;

    public Double getApparentTemperature() {
        return this.apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Double getCloudCover() {
        return this.cloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Double getDewPoint() {
        return this.dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getHumidity() {
        return this.humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getOzone() {
        return this.ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public Double getPrecipIntensity() {
        return this.precipIntensity;
    }

    public void setPrecipIntensity(Double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Double getPrecipProbability() {
        return this.precipProbability;
    }

    public void setPrecipProbability(Double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return this.precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public Double getPressure() {
        return this.pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getWindBearing() {
        return this.windBearing;
    }

    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    public Double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void ConstructFromJson(JSONObject data) throws JSONException {
        setTime(data.getInt("time"));
        setSummary(data.getString("summary"));
        setIcon(data.getString("icon"));
        setPrecipIntensity(data.getDouble("precipIntensity"));
        setTemperature(data.getDouble("temperature"));
        setApparentTemperature(data.getDouble("apparentTemperature"));
        setDewPoint(data.getDouble("dewPoint"));
        setHumidity(data.getDouble("humidity"));
        setWindSpeed(data.getDouble("windSpeed"));
        setWindBearing(data.getInt("windBearing"));
        setCloudCover(data.getDouble("cloudCover"));
        setPressure(data.getDouble("pressure"));
        setOzone(data.getDouble("ozone"));
    }
}
