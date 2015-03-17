package com.preco.milionarios.pricetag.pricetag;


import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.Toast;

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
    private String latitude = null;


    public GetJson(Editable latitude) {
        this.latitude = latitude.toString();

    }

    public void setDelegate(GetJsonResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Context... params) {
        try {
            context = params[0];
            return this.getPlaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
        delegate.getJsonResponse(response);
    }

    private String getPlaces() throws IOException {

        String url = "https://maps.googleapis.com/maps/api/place/search/json";
        Map<String, String> params = new HashMap<String, String>();

        //params.put("location", "37.787930,-122.4074990");
        params.put("location", latitude);
        params.put("radius", "300");
        params.put("key", "AIzaSyA3mNfzyHZ4K4pviZStxXCCQBemWoXgkBg");

        HttpResponse response = WebserviceHelper.doGET(url, params);

        if (response.getStatusLine().getStatusCode() == 200) {
            return new BasicResponseHandler().handleResponse(response);
        }

        return " NOTHING";
    }

    interface GetJsonResponse{
        void getJsonResponse(String placesJson);
    }
}