package com.preco.milionarios.pricetag.pricetag;

//importação das classes necessárias para o funcionamento do aplicativo

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;





public class Localization{




    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(Object local, final EditText latitude, final EditText longitude, final AlertDialog.Builder alerta){
        final LocationManager lManager = (LocationManager) local ;
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                if( locat != null ) {
                    latitude.setText(locat.getLatitude() + "," + locat.getLongitude());
                    longitude.setText("" + locat.getLongitude());

                    lManager.removeUpdates(this);

                }
            }
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                alerta.setMessage("Status Alterado " + arg0);
                alerta.show();
            }
            @Override
            public void onProviderEnabled(String arg0) {
                alerta.setMessage("Provider Ativo " + arg0);
                alerta.show();

            }
            @Override
            public void onProviderDisabled(String arg0) {
                alerta.setMessage("Provider Desativado " + arg0);
                alerta.show();
            }
        };
        lManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, lListener, null);


    }

}