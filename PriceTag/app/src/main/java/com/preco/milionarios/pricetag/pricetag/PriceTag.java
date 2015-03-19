package com.preco.milionarios.pricetag.pricetag;

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

import com.google.gson.Gson;
import com.preco.milionarios.pricetag.PlacesObjects.Place;
import com.preco.milionarios.pricetag.PlacesObjects.PlaceResult;
import com.preco.milionarios.pricetag.R;
import com.preco.milionarios.pricetag.utils.ParseHtml;

import java.util.ArrayList;
import java.util.List;


public class PriceTag extends Activity implements GetJson.GetJsonResponse, Localization.GetGPSResponse{

    private Button getLeitura;
    private TextView description;
    private String contents;
    private Button getPosition;
    private Button showList;
    private EditText latitude;
    private EditText longitude;
    private Context context = this;
    private Place places;
    private List<PlaceResult> placesResult = new ArrayList<PlaceResult>();
    private Location localNow;
    private PriceTag thisClass = this;



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
        description = (TextView) findViewById(R.id.resultado);
        getPosition = (Button) findViewById(R.id.btLocalizar);
        latitude = (EditText) findViewById(R.id.edLatitude);
        longitude = (EditText) findViewById(R.id.edLongitude);


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
                ItensListView.places = placesResult;
                startActivity(intent);
                finish();
            }
        });




        //listener para obter localização
        getPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Localization local = new Localization();
                local.setDelegate(thisClass);
                local.startGPS(getSystemService(Context.LOCATION_SERVICE), context);
            }
        });

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
        GetJson getJson = new GetJson(latitude.getText() + "," + longitude.getText(), null);
        getJson.setDelegate(this);
        getJson.execute(this);
    }

    public List<PlaceResult> loadPlaces(String jsonString) {
        Gson gson = new Gson();
        places = gson.fromJson(jsonString, new Place().getClass());
        //for(PlaceResult placeResult : places.getResult())
        //    Toast.makeText(this, "" + placeResult.getName() + " - " + placeResult.getGeometry().getLocationResult().getDistance(localNow), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "OK" , Toast.LENGTH_LONG).show();
        return places.getResult();
    }

    @Override
    public void getJsonResponse(String placesJson) {
        placesResult = this.loadPlaces(placesJson);
    }

    @Override
    public void getGPSResponse(Location location) {
        localNow = location;
        latitude.setText("" + location.getLatitude());
        longitude.setText("" + location.getLongitude());
        getPlace();

    }
}
