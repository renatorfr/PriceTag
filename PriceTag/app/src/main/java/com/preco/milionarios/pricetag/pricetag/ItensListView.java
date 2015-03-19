package com.preco.milionarios.pricetag.pricetag;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.preco.milionarios.pricetag.PlacesObjects.PlaceResult;
import com.preco.milionarios.pricetag.R;

import java.util.List;

/**
 * Created by dunha on 18/03/2015.
 */
public class ItensListView extends ListActivity {
    static List<PlaceResult> places = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_places);
        ListView listView = (ListView) findViewById(R.id.list_item);


        ArrayAdapter<PlaceResult> placesAdapter = new ArrayAdapter<PlaceResult>(this, android.R.layout.simple_list_item_1, ItensListView.places);
        setListAdapter(placesAdapter);
    }
}