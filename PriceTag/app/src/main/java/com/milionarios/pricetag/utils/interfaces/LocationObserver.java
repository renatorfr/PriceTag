package com.milionarios.pricetag.utils.interfaces;

import android.location.Location;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by renatorfr on 06/07/15.
 */
public interface LocationObserver {
    public void locationUpdate(Location location);
    public void connectionFailed(ConnectionResult connectionResult);
    public void connectionSuspended(int i);
}


