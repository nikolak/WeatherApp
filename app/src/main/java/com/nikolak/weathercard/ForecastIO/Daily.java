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
