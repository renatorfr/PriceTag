package com.milionarios.pricetag.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.milionarios.pricetag.R;
import com.milionarios.pricetag.domain.MyPlaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;



/**
 * Created by dunha on 19/03/2015.
 */
public class ListaAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    Handler handler = new Handler();
    private List<MyPlaces> places = null;


    public ListaAdapter(Context context, List<MyPlaces> places) {
        this.places = places;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return places.size();
    }

    public MyPlaces getItem(int position) {

        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final MyPlaces place = places.get(position);
        view = mInflater.inflate(R.layout.itens_list, null);


        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        getImagem(imageView, place.getIcon());

        TextView textViewNome = (TextView) view.findViewById(R.id.text_view_nome);
        textViewNome.setText(place.getName());

        TextView textViewStreet = (TextView) view.findViewById(R.id.text_view_street);
        textViewStreet.setText(place.getEndereco());

        TextView textViewDist = (TextView) view.findViewById(R.id.text_view_dist);
        DecimalFormat df;
        String textoDistancia;

        if (place.getDistance() < 1) {
            df = new DecimalFormat("#,##0");
            textoDistancia = df.format(place.getDistance() * 1000) + "m";
        }
        else {
            df = new DecimalFormat("#,##0.00");
            textoDistancia = df.format(place.getDistance()) + "Km";
        }
        textViewDist.setText(textoDistancia);
        return view;
    }


    public void getImagem(final ImageView imgv, final String urlimg) {
        new Thread() {

            public void run() {


                Bitmap img = null;
                try {
                    URL url = new URL(urlimg);
                    HttpURLConnection conec = (HttpURLConnection) url.openConnection();
                    InputStream is = conec.getInputStream();
                    img = BitmapFactory.decodeStream(is);

                } catch (IOException e) {
                }
                final Bitmap imgaux = img;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imgv.setImageBitmap(imgaux);


                    }
                });
            }

        }.start();
    }

}