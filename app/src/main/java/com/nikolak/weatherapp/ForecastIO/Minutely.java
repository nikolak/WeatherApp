package com.nikolak.weatherapp.ForecastIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Minutely {
    private String summary;
    private String icon;

    private ArrayList<Minute> minuteData = new ArrayList<>();

    public String getSummary(){
        return this.summary;
    }

    public String getIcon(){
        return this.icon;
    }

    public ArrayList<Minute> getMinuteData(){
        return this.minuteData;
    }

    public void constructFromJson(JSONObject minutelyJson) throws JSONException{
        this.summary = minutelyJson.getString("summary");
        this.icon = minutelyJson.getString("icon");

        JSONArray minuteArray = minutelyJson.getJSONArray("data");

        for (int i = 0; i < minuteArray.length(); i++) {
            JSONObject minuteJsonObject = minuteArray.getJSONObject(i);
            Minute minuteInstance = new Minute();
            minuteInstance.constrcutFromJson(minuteJsonObject);
            this.minuteData.add(minuteInstance);
        }
    }


}
