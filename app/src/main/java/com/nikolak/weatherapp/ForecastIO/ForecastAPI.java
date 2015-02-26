package com.nikolak.weatherapp.ForecastIO;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ForecastAPI {

    private static String API_BASE = "https://api.forecast.io";
    private static String API_KEY = "f1a122b49ed8d229604f818cd13ae21d";

    private String getAPIresponse(String apiURL) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httpget = new HttpGet(apiURL);
        httpget.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            return result;
        } catch (Exception e) {
            Log.e("URL Exception", e.toString());
            return null;
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                Log.e("URL Get", e.toString());
            }
        }
    }

    public JSONObject getDefault(String lat, String lon) {
        String forecastURL = API_BASE + "/forecast/" + API_KEY + "/" + lat + "," + lon+"?units=auto";
        Log.d("getDefault URL", forecastURL);
        if (lat == null || lon == null) {
            return null;
        }

        String apiResp = getAPIresponse(forecastURL);
        JSONObject APIJsonObject;
        try {
            APIJsonObject = new JSONObject(apiResp);
            return APIJsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

//        JSONObject currentObject;
//        String forecastUnits = "us";
//        try {
//            currentObject = jObject.getJSONObject("currently");
//            //forecastUnits = currentObject.getJSONArray("flags").getString("units");
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//        Log.d("currentObject", currentObject.toString());
//
//        Current currentForecast = new Current();
//
//        currentForecast.setUnits(forecastUnits);
//        try {
//            currentForecast.fromJson(currentObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return currentForecast;
    }
}
