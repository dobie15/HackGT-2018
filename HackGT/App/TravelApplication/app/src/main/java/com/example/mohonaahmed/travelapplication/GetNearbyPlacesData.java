package com.example.mohonaahmed.travelapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kelly on 10/20/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    String url;

    @Override
    protected String doInBackground(Object... params) {
        try {
            url = (String) params[0];
            DownloadURL downloadUrl = new DownloadURL();
            googlePlacesData = downloadUrl.readUrl(url);

        } catch (Exception e) {
            Log.d("GooglePlacesRead", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        List<HashMap<String, String>> nearbyPlaces = null;
        DataParser dataParser = new DataParser();
        nearbyPlaces =  dataParser.parse(result);
        GetNearbyPlaces(nearbyPlaces);
    }

    private void GetNearbyPlaces(List<HashMap<String, String>> nearbyPlaces) {
        for (int i = 0; i < nearbyPlaces.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlaces.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            //String keyword = topLabels.get(0)+topLabels.get(1)+opLabels.get(2);
            String placeID = googlePlace.get("placeid");
            String photoref = googlePlace.get("photoref");
        }
    }
}
