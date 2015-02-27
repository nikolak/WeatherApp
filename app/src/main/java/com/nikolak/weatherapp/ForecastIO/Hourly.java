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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Hourly {
    private ArrayList<Hour> hourData = new ArrayList<>();
    private String icon;
    private String summary;

    public String getIcon() {
        return this.icon;
    }

    public String getSummary() {
        return this.summary;
    }

    public ArrayList<Hour> getHourData() {
        return this.hourData;
    }

    public void constructFromJson(JSONObject hourlyJson) throws JSONException {
        this.summary = hourlyJson.getString("summary");
        this.icon = hourlyJson.getString("icon");

        JSONArray hourArray = hourlyJson.getJSONArray("data");

        for (int i = 0; i < hourArray.length(); i++) {
            JSONObject hourJsonObject = hourArray.getJSONObject(i);
            Hour hourInstance = new Hour();
            hourInstance.constrcutFromJson(hourJsonObject);
            this.hourData.add(hourInstance);
        }
    }
}