package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    List<String> topLabels;
    //Some url endpoint that you may have

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        GetNearbyPlacesData getRequest = new GetNearbyPlacesData();
       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();might need this IDK*/
        String result = "";
        String result2 = "";
        String keyword = topLabels.get(0);
        String keyword2 = topLabels.get(1);
        String keyword3 = topLabels.get(2);
        //Atlanta
        String myUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + "33.749" + "," + "-84.38798" + "&radius=17000" +
                "&keyword=" + keyword + keyword2 + keyword3 + "&key=YOUR_API_KEY";
        //Florence, Italy
        String myUrl2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + "43.769562" + "," + "11.255814" + "&radius=30000" +
                "&keyword=" + keyword + keyword2 + keyword3 + "&key=YOUR_API_KEY";

        try {
             result = getRequest.execute(myUrl).get();
             result2 = getRequest.execute(myUrl2).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
































