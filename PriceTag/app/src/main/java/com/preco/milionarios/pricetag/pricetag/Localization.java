package com.preco.milionarios.pricetag.pricetag;

//importação das classes necessárias para o funcionamento do aplicativo

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class Localization {

    private GetGPSResponse delegate = null;

    public void setDelegate(GetGPSResponse delegate) {
        this.delegate = delegate;
    }


    //Método que faz a leitura de fato dos valores recebidos do GPS
    public void startGPS(Object local, final Context context) {
        final LocationManager lManager = (LocationManager) local;
        //final AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        //alerta.setTitle("Atenção!");
        //alerta.setNeutralButton("OK", null);
        LocationListener lListener = new LocationListener() {
            public void onLocationChanged(Location locat) {
                if (locat != null) {
                    delegate.getGPSResponse(locat);
                    lManager.removeUpdates(this);
                }
            }

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                //alerta.setMessage("Status Alterado " + arg0);
                //alerta.show();
            }

            @Override
            public void onProviderEnabled(String arg0) {
                //alerta.setMessage("Provider Ativo " + arg0);
                //alerta.show();

            }

            @Override
            public void onProviderDisabled(String arg0) {
                //alerta.setMessage("Provider Desativado " + arg0);
                //alerta.show();
            }
        };
        lManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, lListener, null);


    }

    interface GetGPSResponse {
        void getGPSResponse(Location location);
    }


}