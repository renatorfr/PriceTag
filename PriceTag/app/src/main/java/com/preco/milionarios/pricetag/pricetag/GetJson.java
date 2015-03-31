package com.preco.milionarios.pricetag.pricetag;


import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.preco.milionarios.pricetag.PlacesObjects.MyPlaces;
import com.preco.milionarios.pricetag.PlacesObjects.MyPlacesJson;
import com.preco.milionarios.pricetag.utils.WebserviceHelper;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dunha on 13/02/2015.
 */
public class GetJson extends AsyncTask<Context, Void, String> {

    private Context context = null;
    private GetJsonResponse delegate = null;
    private String lat;
    private String lng;

    private MyPlacesJson places;
    Gson gson = new Gson();


    public GetJson(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setDelegate(GetJsonResponse delegate) {
        this.delegate = delegate;
    }


    @Override
    protected String doInBackground(Context... params) {
        try {
            context = params[0];
            return getAllPlaces(lat, lng);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String response) {
        delegate.getJsonResponse(response);
    }

    private String getAllPlaces(String lat, String lng) throws IOException {
        HttpResponse response;
        String url = "https://pricetagwebservice.herokuapp.com/json";
        Map<String, String> params = new HashMap<String, String>();
        params.put("lat", lat);
        params.put("lng", lng);

        response = WebserviceHelper.doGET(url, params);


        if (response.getStatusLine().getStatusCode() == 200) {
            return new BasicResponseHandler().handleResponse(response);
        }
        return null;

    }

    interface GetJsonResponse {
        void getJsonResponse(String placesJson);
    }
}