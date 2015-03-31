package com.preco.milionarios.pricetag.PlacesObjects;

import android.location.Location;
import java.util.List;


public class FacebookPlaces {


    private String category;
    private List<FaceBookCategoryPlaces> category_list;
    private FacebookLocation location;
    private String name;
    private String id;
    private String image;
    private double distancia;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<FaceBookCategoryPlaces> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<FaceBookCategoryPlaces> category_list) {
        this.category_list = category_list;
    }

    public FacebookLocation getLocation() {
        return location;
    }

    public void setLocation(FacebookLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public double getDistancia(double latitudeInicial, double longitudeInicial) {

        Location locationA = new Location("point A");

        locationA.setLatitude(latitudeInicial);
        locationA.setLongitude(longitudeInicial);

        Location locationB = new Location("point B");

        locationB.setLatitude(Double.parseDouble(location.getLatitude()));
        locationB.setLongitude(Double.parseDouble(location.getLongitude()));

        return locationA.distanceTo(locationB);


    }


}



