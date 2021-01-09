package com.example.regions;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        getRegionId((String)intent.getExtras().get("region_name"));
        TextView type= findViewById(R.id.edtType);
//        TextView type= findViewById(R.id.edtType);
//        TextView type= findViewById(R.id.edtType);
        TextView name= findViewById(R.id.edtName);
        TextView desc= findViewById(R.id.edtDesc);
        TextView year= findViewById(R.id.edtYear);
        name.setText((String)intent.getExtras().get("region_name"));
        type.setText((String)intent.getExtras().get("region_pop"));
        desc.setText((String)intent.getExtras().get("region_lang"));
        year.setText((String)intent.getExtras().get("region_lat"));

        determineLocation((String)intent.getExtras().get("region_lat"),(String)intent.getExtras().get("region_lang"));

    }

    public void getRegionId(String name) {
        // Instantiate the RequestQueue.

        String url ="https://www.metaweather.com/api/location/search/?query=" + name;

//Here it's an array since it will return json array has name and value
        //[{"title":"London","location_type":"City","woeid":44418,"latt_long":"51.506321,-0.12714"}]//
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String cityID = "";
                        try {//take the first object in ithe returned array
                            JSONObject obj = response.getJSONObject(0);

                            cityID = obj.getString("woeid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getRegionWeather(cityID);

//                        edtInput.setText(cityID);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
    public void getRegionWeather(String Rid) {
        String url = "https://www.metaweather.com/api/location/" +Rid;

//here it will return an object contains array.

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String[] days;
                        try {
                            JSONArray array = response.getJSONArray("consolidated_weather");
                            days = new String[array.length()];
                            for(int i = 0; i<array.length(); i++){
                                JSONObject obj = array.getJSONObject(i);
                                String weatherDay = "";
                                weatherDay = "Day"+(i+1)+"\nstate: " + obj.getString("weather_state_name") +
                                        "\n, date: " + obj.getString("applicable_date") +
                                        "\n, min: " + obj.getString("min_temp") +
                                        ", max: " + obj.getString("max_temp");
                                days[i] = weatherDay;
                            }

                           ListView lst = findViewById(R.id.lst);
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1,
                                            days);
                            lst.setAdapter(itemsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

public void determineLocation(String lat,String lang){
TextView type= findViewById(R.id.edtType);
    Geocoder geocoder = new Geocoder(this);
    try {
        List<Address> address = geocoder.getFromLocation(Double.parseDouble(lat),
                Double.parseDouble(lang), 1);
       type.setText(address.get(0).getAddressLine(0));
    } catch (Exception e) {
        type.setText("Cannot get Street Address!");
    }
}
}
