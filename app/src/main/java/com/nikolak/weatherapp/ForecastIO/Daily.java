package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Daily {
    private String summary;
    private String icon;
    private ArrayList<Day> dayData = new ArrayList<>();

    public String getSummary() {
        return this.summary;
    }

    public String getIcon() {
        return this.icon;
    }

    public ArrayList<Day> getDayData() {
        return this.dayData;
    }

    public void constructFromJson(JSONObject dailyJson) throws JSONException {
        this.summary = dailyJson.getString("summary");
        this.icon = dailyJson.getString("icon");

        JSONArray dayArray = dailyJson.getJSONArray("data");

        for (int i = 0; i < dayArray.length(); i++) {
            JSONObject dayJsonObject = dayArray.getJSONObject(i);
            Day dayInstance = new Day();
            dayInstance.constructFromJson(dayJsonObject);
            this.dayData.add(dayInstance);
        }
    }
}
