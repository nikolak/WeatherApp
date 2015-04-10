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

public class Utils {

    public static Integer getIconFromValue(String iconValue) {
        Integer icon;
        switch (iconValue) {
            case "clear-day":
                icon = R.drawable.clearday;
                break;
            case "clear-night":
                icon = R.drawable.clearnight;
                break;
            case "rain":
                icon = R.drawable.rain;
                break;
            case "snow":
                icon = R.drawable.snow;
                break;
            case "sleet":
                icon = R.drawable.sleet;
                break;
            case "wind":
                icon = R.drawable.wind;
                break;
            case "fog":
                icon = R.drawable.fog;
                break;
            case "cloudy":
                icon = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                icon = R.drawable.partlycloudyday;
                break;
            case "partly-cloudy-night":
                icon = R.drawable.partlycloudynight;
                break;
            default:
                //TODO: Add default icon
                icon = R.drawable.fog;

        }
        return icon;
    }
}
