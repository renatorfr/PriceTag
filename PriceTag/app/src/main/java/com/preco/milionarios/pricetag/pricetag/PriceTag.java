package com.preco.milionarios.pricetag.pricetag;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.preco.milionarios.pricetag.R;
import com.preco.milionarios.pricetag.utils.ParseHtml;


public class PriceTag extends Activity {

    private Button getLeitura;
    private TextView resultado;
    private String contents;
    private Button getPosition;
    private EditText latitude;
    private EditText longitude;
    private AlertDialog.Builder alarme = new AlertDialog.Builder(this).setTitle("Atenção!").setNeutralButton("OK", null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_tag);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getLeitura = (Button) findViewById(R.id.getLeitura);
        resultado = (TextView) findViewById(R.id.resultado);

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


        //listenr para obter localização
        getPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Localization local = new Localization();
                Object contexto = getSystemService(Context.LOCATION_SERVICE);
                local.startGPS(contexto, latitude, longitude, alarme);
            }
        });

        //listener para obter json
        latitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                getPlace();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
                resultado.setText(ParseHtml.getString("http://cosmos.bluesoft.com.br/products/" + contents));
            } else if (resultCode == RESULT_CANCELED) {
                resultado.setText("Cancelado");
            }
        }
    }

    private void getPlace() {
        new Places(latitude.getText()).execute(this);
    }


}
