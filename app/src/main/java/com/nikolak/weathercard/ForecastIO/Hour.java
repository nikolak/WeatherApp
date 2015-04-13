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

package com.nikolak.weathercard.ForecastIO;

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

    public String getProbabilityPerc() {
        Double percentage = this.precipProbability * 100;
        return String.valueOf(percentage.intValue()) + "%";
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

    public void constrcutFromJson(JSONObject hourJson) throws JSONException {
        this.time = hourJson.getInt("time");
        this.summary = hourJson.getString("summary");
        this.icon = hourJson.getString("icon");
        this.precipIntensity = hourJson.getDouble("precipIntensity");
        this.precipProbability = hourJson.getDouble("precipProbability");
        this.temperature = hourJson.getDouble("temperature");
        this.apparentTemperature = hourJson.getDouble("apparentTemperature");
        this.dewPoint = hourJson.getDouble("dewPoint");
        this.humidity = hourJson.getDouble("humidity");
        this.windSpeed = hourJson.getDouble("windSpeed");
        this.windBearing = hourJson.getInt("windBearing");
        this.cloudCover = hourJson.getDouble("cloudCover");
        this.pressure = hourJson.getDouble("pressure");
        this.ozone = hourJson.getDouble("ozone");
    }
}
