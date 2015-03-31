package com.preco.milionarios.pricetag.pricetag;


import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.preco.milionarios.pricetag.PlacesObjects.FacebookJson;
import com.preco.milionarios.pricetag.utils.WebserviceHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Created by dunha on 13/02/2015.
 */
public class GetJson extends AsyncTask<Context, Void, String> {

    private Context context = null;
    private GetJsonResponse delegate = null;
    private String coordenadas = null;
    private FacebookJson places;
    Gson gson = new Gson();


    public GetJson(String local) {
        this.coordenadas = local;


    }

    public void setDelegate(GetJsonResponse delegate) {
        this.delegate = delegate;
    }


    @Override
    protected String doInBackground(Context... params) {
        try {
            context = params[0];
            return getAllPlaces(coordenadas);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String response) {
        delegate.getJsonResponse(response);
    }

    private String getAllPlaces(String coordenadas) throws IOException {
        HttpResponse response;
        String url = "https://pricetagwebservice.herokuapp.com/json";
        Map<String, String> params = new HashMap<String, String>();
        params.put("location", coordenadas);

        response = WebserviceHelper.doGET(url, params);


        if (response.getStatusLine().getStatusCode() == 200) {
            return new BasicResponseHandler().handleResponse(response);
        }
        return null;

    }


    private String getUrl(String webserviceUrl, Map<String, String> params) {
        if (params == null) return webserviceUrl;

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");
        return webserviceUrl + "?" + paramsString;
    }

    interface GetJsonResponse {
        void getJsonResponse(String placesJson);
    }
}