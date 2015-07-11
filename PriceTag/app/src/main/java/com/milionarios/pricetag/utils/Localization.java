package com.milionarios.pricetag.utils;


import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.milionarios.pricetag.utils.interfaces.LocationObserver;

import java.util.ArrayList;
import java.util.List;

public class Localization implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static Localization instance = null;
    private Context context;
    private Location mLastLocation;
    private Boolean mRequestingLocationUpdates;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private List<LocationObserver> observers = new ArrayList<LocationObserver>();


    private Localization() {

    }

    public static Localization getInstance(Context context) {
        if (instance == null) {
            instance = new Localization();
        }

        instance.start(context);

        return instance;
    }

    private void notifyObservers() {
        for (LocationObserver observer : observers) {
            observer.locationUpdate(mLastLocation);
        }
    }

    private void notifyObservers(ConnectionResult connectionResult) {
        for (LocationObserver observer : observers) {
            observer.connectionFailed(connectionResult);
        }
    }

    public void registerObserver(LocationObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(LocationObserver observer) {
        if (observer != null) {
            observers.remove(observer);
        }

        if (this.observers.isEmpty()) {
            stopLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        if (mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mRequestingLocationUpdates = false;
        }
    }

    private void start(Context context) {
        this.context = context;

        buildGoogleApiClient();
        createLocationRequest();
        mGoogleApiClient.connect();
        mRequestingLocationUpdates = false;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        notifyObservers();

        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        for (LocationObserver observer : observers) {
            observer.connectionSuspended(i);
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        notifyObservers(connectionResult);

        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult((Activity) context, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        notifyObservers();
    }
}