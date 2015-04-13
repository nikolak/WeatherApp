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

    public Integer getTime() {
        return this.time;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getIcon() {
        return this.icon;
    }

    public Double getMoonPhase() {
        return this.moonPhase;
    }

    public Integer getSunriseTime() {
        return this.sunriseTime;
    }

    public Integer getSunsetTime() {
        return this.sunsetTime;
    }

    public Double getPrecipIntensity() {
        return this.precipIntensity;
    }

    public Double getPrecipIntensityMax() {
        return this.precipIntensityMax;
    }

    public Integer getPrecipIntensityMaxTime() {
        return this.precipIntensityMaxTime;
    }

    public Double getPrecipProbability() {
        return this.precipProbability;
    }

    public String getPrecipType() {
        return this.precipType;
    }

    public Double getTemperatureMin() {
        return this.temperatureMin;
    }

    public Integer getTemperatureMinTime() {
        return this.temperatureMinTime;
    }

    public Double getTemperatureMax() {
        return this.temperatureMax;
    }

    public Integer getTemperatureMaxTime() {
        return this.temperatureMaxTime;
    }

    public Double getApparentTemperatureMin() {
        return this.apparentTemperatureMin;
    }

    public Integer getApparentTemperatureMinTime() {
        return this.apparentTemperatureMinTime;
    }

    public Double getApparentTemperatureMax() {
        return this.apparentTemperatureMax;
    }

    public Integer getApparentTemperatureMaxTime() {
        return this.apparentTemperatureMaxTime;
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

    public Double getPrecipAccumulation() {
        return this.precipAccumulation;
    }

    public Double getVisibility() {
        return this.visibility;
    }

    public void constructFromJson(JSONObject dayJson) throws JSONException {

        this.time = dayJson.getInt("time");
        this.summary = dayJson.getString("summary");
        this.icon = dayJson.getString("icon");
        this.moonPhase = dayJson.getDouble("moonPhase");
        this.sunriseTime = dayJson.getInt("sunriseTime");
        this.sunsetTime = dayJson.getInt("sunsetTime");
        this.precipIntensity = dayJson.getDouble("precipIntensity");
        this.precipIntensityMax = dayJson.getDouble("precipIntensityMax");
        this.precipProbability = dayJson.getDouble("precipProbability");
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

        this.cloudCover = dayJson.getDouble("cloudCover");
        this.pressure = dayJson.getDouble("pressure");
        this.ozone = dayJson.getDouble("ozone");

        if (this.windSpeed.equals(0.0)) {
            this.windBearing = null;
        } else {
            this.windBearing = dayJson.getInt("windBearing");
        }

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
        if (precipIntensity == 0) {
            this.precipType = "N/A";
        } else {
            this.precipType = dayJson.getString("precipType");
        }
        try {
            this.precipIntensityMaxTime = dayJson.getInt("precipIntensityMaxTime");
        } catch (JSONException e) {
            this.precipIntensityMaxTime = 0;
        }
    }
}
