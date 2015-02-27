package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hourly {
    private ArrayList<Hour> hourData = new ArrayList<>();
    private String icon;
    private String summary;

    public List getData() {
        return this.hourData;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getSummary() {
        return this.summary;
    }


    public void constructFromJson(JSONObject hourlyData) throws JSONException {
        this.summary = hourlyData.getString("summary");
        this.icon = hourlyData.getString("icon");

        JSONArray hourArray = hourlyData.getJSONArray("data");

        for (int i = 0; i < hourArray.length(); i++) {
            JSONObject hourJsonObject = hourArray.getJSONObject(i);
            Hour hourInstance = new Hour();
            hourInstance.constrcutFromJson(hourJsonObject);
            this.hourData.add(hourInstance);
        }
    }
}