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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Currently {
    private Integer time;
    private String summary;
    private String icon;
    private Double precipIntensity;
    private Double precipProbability;
    private Double nearestStormDistance; //todo implement
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

    public Double getDewPoint() {
        return this.dewPoint;
    }

    public Double getHumidity() {
        return this.humidity;
    }

    public String getHumidityPerc() {
        Double percentage = this.humidity * 100;
        return String.valueOf(percentage.intValue())+"%";
    }

    public String getIcon() {
        return this.icon;
    }

    public Double getOzone() {
        return this.ozone;
    }

    public String getPrecipIntensity() {
        return new DecimalFormat("#.##").format(this.precipIntensity);
    }

    public Double getPrecipProbability() {
        return this.precipProbability;
    }

    public Double getnearestStormDistance() {
        return this.nearestStormDistance;
    }

    public Double getPressure() {
        return this.pressure;
    }

    public String getSummary() {
        return this.summary;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public Integer getTime() {
        return this.time;
    }

    public Integer getWindBearing() {
        return this.windBearing;
    }

    public Double getWindSpeed() {
        return this.windSpeed;
    }

    public void ConstructFromJson(JSONObject data) throws JSONException {
        this.time = data.getInt("time");
        this.summary = data.getString("summary");
        this.icon = data.getString("icon");
        this.precipProbability = data.getDouble("precipProbability");
        this.precipIntensity = data.getDouble("precipIntensity");
        this.temperature = data.getDouble("temperature");
        this.apparentTemperature = data.getDouble("apparentTemperature");
        this.dewPoint = data.getDouble("dewPoint");
        this.humidity = data.getDouble("humidity");
        this.windSpeed = data.getDouble("windSpeed");
        this.windBearing = data.getInt("windBearing");
        this.cloudCover = data.getDouble("cloudCover");
        this.pressure = data.getDouble("pressure");
        this.ozone = data.getDouble("ozone");
        try {
            this.nearestStormDistance = data.getDouble("nearestStormDistance");
        } catch (JSONException e) {
            this.nearestStormDistance = null;
        }
    }
}
