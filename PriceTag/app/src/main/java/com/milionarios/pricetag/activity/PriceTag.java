package com.milionarios.pricetag.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.milionarios.pricetag.R;
import com.milionarios.pricetag.domain.MyPlacesJson;
import com.milionarios.pricetag.utils.GetJson;
import com.milionarios.pricetag.utils.ParseHtml;


public class PriceTag extends Activity implements GetJson.GetJsonResponse, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static Location localNow;
    private Button getLeitura;
    private TextView description;
    private String contents;
    private Button getPosition;
    private Button showList;
    private EditText latitude;
    private EditText longitude;
    private Context context = this;
    private MyPlacesJson places;
    private PriceTag thisClass = this;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Boolean mRequestingLocationUpdates;
    private LocationRequest mLocationRequest;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save hierarchy state
        super.onSaveInstanceState(outState);

        outState.putString(getString(R.string.activity_price_tag_descricao_label), description.getText().toString());
        outState.putString(getString(R.string.activity_price_tag_latitude_label), latitude.getText().toString());
        outState.putString(getString(R.string.activity_price_tag_longitude_label), longitude.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Save hierarchy state
        super.onRestoreInstanceState(savedInstanceState);

        description.setText(savedInstanceState.getString(getString(R.string.activity_price_tag_descricao_label)));
        latitude.setText(savedInstanceState.getString(getString(R.string.activity_price_tag_latitude_label)));
        longitude.setText(savedInstanceState.getString(getString(R.string.activity_price_tag_longitude_label)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_tag);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getLeitura = (Button) findViewById(R.id.getLeitura);
        showList = (Button) findViewById(R.id.showList);
        description = (TextView) findViewById(R.id.txtDescricao);
        getPosition = (Button) findViewById(R.id.btLocalizar);
        latitude = (EditText) findViewById(R.id.edLatitude);
        longitude = (EditText) findViewById(R.id.edLongitude);


        showList.requestFocus();
        showList.setEnabled(false);


        // listener para ler codigo de barras
        getLeitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                startActivityForResult(intent, 0);
            }
        });

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ItensListView.class);

                ItensListView.places = places.getMyplaces();
                startActivity(intent);


            }
        });

        buildGoogleApiClient();
        createLocationRequest();
        mGoogleApiClient.connect();
        mRequestingLocationUpdates = false;

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_price_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //requisição da descrição do produto
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                description.setText(ParseHtml.getString("http://cosmos.bluesoft.com.br/products/" + contents, "h1"));
            } else if (resultCode == RESULT_CANCELED) {
                description.setText("Cancelado");
            }
        }
    }

    private void getPlace() {
        GetJson getJson = new GetJson(latitude.getText().toString(), longitude.getText().toString());
        getJson.setDelegate(this);
        getJson.execute(this);
    }

    @Override
    public void getJsonResponse(String placesJson) {
        placesJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(placesJson));
        Gson gson = new Gson();
        places = gson.fromJson(placesJson, MyPlacesJson.class);
        showList.setEnabled(true);

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        updateLocationUI();

        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "GPS Suspenso", Toast.LENGTH_LONG);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "GPS Falhou", Toast.LENGTH_LONG);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateLocationUI();
    }

    private void updateLocationUI() {
        Toast.makeText(this, "GPS Atualizou", Toast.LENGTH_LONG);
        if (mLastLocation != null) {
            showList.setEnabled(true);
            latitude.setText(String.valueOf(mLastLocation.getLatitude()));
            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }
}
