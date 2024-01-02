package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Covid17Activity extends AppCompatActivity {

    private static final String TAG = "Covid17Activity";
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        fetchData();
    }

    private void fetchData() {
        String url = "https://covid-19-statistics.p.rapidapi.com/regions";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data: " + error.getMessage());
                        dataTextView.setText("Error fetching data");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", "dd619cea18msh46e20fd7ca93fbcp1466fajsne3800c86dac1");
                headers.put("X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void handleResponse(JSONObject jsonObject) {
        try {
            JSONArray regionsArray = jsonObject.getJSONArray("data");

            StringBuilder dataStringBuilder = new StringBuilder();
            for (int i = 0; i < regionsArray.length(); i++) {
                JSONObject regionObject = regionsArray.getJSONObject(i);
                String regionName = regionObject.getString("region");
                int confirmedCases = regionObject.getInt("confirmed");
                int deaths = regionObject.getInt("deaths");

                dataStringBuilder.append("Region: ").append(regionName)
                        .append("\nConfirmed Cases: ").append(confirmedCases)
                        .append("\nDeaths: ").append(deaths).append("\n\n");
            }

            dataTextView.setText(dataStringBuilder.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data: " + e.getMessage());
            dataTextView.setText("Error parsing data");
        }
    }
}
