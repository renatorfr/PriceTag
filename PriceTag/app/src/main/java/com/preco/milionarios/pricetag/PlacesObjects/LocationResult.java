package com.preco.milionarios.pricetag.PlacesObjects;

import android.location.Location;

/**
 * Created by dunha on 16/03/2015.
 */
public class LocationResult {
    private Double lat;
    private Double lng;


   public float getDistance(Location locat){
        Location dist = new Location("GPS");
        dist.setLatitude(lat);
        dist.setLongitude(lng);
        return locat.distanceTo(dist);
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
