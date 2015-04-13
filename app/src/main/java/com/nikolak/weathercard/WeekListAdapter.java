// Copyright 2015 Nikola Kovacevic <nikolak@outlook.com>
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

package com.nikolak.weathercard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nikolak.weathercard.ForecastIO.Day;

import java.util.ArrayList;

public class WeekListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Day> dayData;
    private static LayoutInflater inflater = null;

    public WeekListAdapter(Context context, ArrayList<Day> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.dayData = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View conView = convertView;
        if (conView == null)
            conView = inflater.inflate(R.layout.day_list_item, null);

        TextView dayName = (TextView) conView.findViewById(R.id.dayName);
        TextView dayDesc = (TextView) conView.findViewById(R.id.dayDesc);
        TextView dayLow = (TextView) conView.findViewById(R.id.dayLow);
        TextView dayHigh = (TextView) conView.findViewById(R.id.dayHigh);

        Day day = dayData.get(position);

        dayName.setText(Utils.getDay(day.getTime()));
        dayDesc.setText(day.getSummary());
        dayLow.setText(Math.round(day.getTemperatureMin())+"°");
        dayHigh.setText(Math.round(day.getTemperatureMax())+"°");

        return conView;
    }

}
