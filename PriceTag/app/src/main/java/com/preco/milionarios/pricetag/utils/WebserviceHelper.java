package com.preco.milionarios.pricetag.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by dunha on 25/02/2015.
 */
public class WebserviceHelper {

    private static final String LOG_TAG = "peloton_webservice_helper";

    public static HttpResponse doPOST(String webserviceUrl, Map<String, Object> paramsPost) {
        HttpPost httpPost = new HttpPost(webserviceUrl);

        httpPost.setHeader("content-type", "application/json");
        JSONObject data = new JSONObject();
        try {
            for (Entry<String, Object> entry : paramsPost.entrySet()) {
                data.put(entry.getKey(), entry.getValue());
            }

            StringEntity entity = null;
            entity = new StringEntity(data.toString());
            httpPost.setEntity(entity);

            HttpClient httpClient = new DefaultHttpClient();
            return httpClient.execute(httpPost);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }

    public static HttpResponse doGET(String webserviceUrl, Map<String, String> params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(getGETRequestUrl(webserviceUrl, params));
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }

    public static String getGETRequestUrl(String webserviceUrl, Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        for (Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");

        return webserviceUrl + "?" + paramsString;
    }
}
