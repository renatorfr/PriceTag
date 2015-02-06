package com.preco.milionarios.pricetag;

//importação das classes necessárias para o funcionamento do aplicativo
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;


public class Localization{

    private Double latitude;
    private Double longitude;

    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(Object local){
        LocationManager lManager = (LocationManager) local ;
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                updateView(locat);
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //startActivityForResult(intent, 1);
            }
        };
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);

    }


    //  Método que faz a atualização da tela para o usuário.
    public void updateView(Location locat){
        setLatitude(locat.getLatitude());
        setLongitude(locat.getLongitude());
    }

    public Double getLatitude() {
        return latitude;
    }

    private void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    private void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}