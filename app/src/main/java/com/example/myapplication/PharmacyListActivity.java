package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// PharmacyListActivity.java
public class PharmacyListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PharmacyAdapter pharmacyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);

        recyclerView = findViewById(R.id.recyclerViewPharmacyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pharmacyAdapter = new PharmacyAdapter();
        recyclerView.setAdapter(pharmacyAdapter);

        // Make a Volley request to fetch pharmacy data
        fetchDataFromWebService();
    }

    private void fetchDataFromWebService() {
        String url = "https://api.collectapi.com/health/dutyPharmacy?ilce=%C3%87ankaya&il=Ankara";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Set up the headers for the request
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "apikey your_token");
        headers.put("content-type", "application/json");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Handle the JSON response and parse it into Pharmacy objects
                    List<Pharmacy> pharmacyList = parseJsonResponse(response);
                    pharmacyAdapter.updateData(pharmacyList);
                },
                error -> {
                    // Handle errors
                    Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Pass the headers to the request
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    private List<Pharmacy> parseJsonResponse(JSONObject response) {
        List<Pharmacy> pharmacyList = new ArrayList<>();
        try {
            JSONArray resultArray = response.getJSONArray("result");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject pharmacyJson = resultArray.getJSONObject(i);
                String name = pharmacyJson.getString("name");
                String address = pharmacyJson.getString("address");
                Pharmacy pharmacy = new Pharmacy(name, address);
                pharmacyList.add(pharmacy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pharmacyList;
    }
}
