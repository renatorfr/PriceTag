package com.milionarios.pricetag.utils.interfaces;

import android.content.IntentSender;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;

import java.util.Map;

/**
 * Created by renatorfr on 06/07/15.
 */
public interface LocationObserver {
    public void locationUpdate(Location location);
    public void connectionFailed(ConnectionResult connectionResult);
    public void connectionFailed(IntentSender.SendIntentException exception);
    public void connectionSuspended(int i);
}


