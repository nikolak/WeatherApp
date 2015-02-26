package com.nikolak.weatherapp.ForecastIO;

import java.util.List;
import com.nikolak.weatherapp.ForecastIO.Day;

public class Daily {
    private List<Day> dayData;
    private String icon;
    private String summary;

    public List getData() {
        return this.dayData;
    }

    public void setData(List<Day> data) {
        this.dayData = data;
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
