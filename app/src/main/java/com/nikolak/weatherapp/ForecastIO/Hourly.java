package com.nikolak.weatherapp.ForecastIO;

import java.util.List;
import com.nikolak.weatherapp.ForecastIO.Hour;

public class Hourly {
    private List<Hour> hourData;
    private String icon;
    private String summary;

    public List getData() {
        return this.hourData;
    }

    public void setData(List data) {
        this.hourData = data;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}