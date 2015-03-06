package com.preco.milionarios.pricetag.pricetag;

//importação das classes necessárias para o funcionamento do aplicativo

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;





public class Localization extends Activity {



    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(Object local, final EditText latitude, final EditText longitude, final AlertDialog.Builder alerta){
        LocationManager lManager = (LocationManager) local ;
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                latitude.setText("" + locat.getLatitude());
                longitude.setText("" + locat.getLongitude());
            }



            public void onStatusChanged(String provider, int status, Bundle extras) { }
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {

                 alerta.show();

            }
        };
        lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);

    }
}