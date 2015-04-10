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

package com.nikolak.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolak.weatherapp.ForecastIO.Hour;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HourListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Hour> hourData;
    private static LayoutInflater inflater = null;

    public HourListAdapter(Context context, ArrayList<Hour> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.hourData = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hourData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return hourData.get(position);
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
            conView = inflater.inflate(R.layout.hour_list_item, null);
        TextView hourTime = (TextView) conView.findViewById(R.id.hour_time);
        ImageView hourIcon = (ImageView) conView.findViewById(R.id.hour_icon);
        TextView hourDesc = (TextView) conView.findViewById(R.id.hour_desc);
        TextView hourProbability = (TextView) conView.findViewById(R.id.hour_probability);
        TextView hourTemperature = (TextView) conView.findViewById(R.id.hour_temp);

        Hour hour = hourData.get(position);
        hourDesc.setText(hour.getSummary());
        return conView;
    }
}