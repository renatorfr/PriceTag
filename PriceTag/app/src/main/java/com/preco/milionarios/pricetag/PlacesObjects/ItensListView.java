package com.preco.milionarios.pricetag.PlacesObjects;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;
import android.os.Bundle;


import android.widget.ListView;
import android.widget.Toast;


import com.preco.milionarios.pricetag.R;


import java.util.List;

/**
 * Created by dunha on 19/03/2015.
 */
public class ItensListView extends Activity implements OnItemClickListener{

    public static List<PlaceResult> places;
    private ListaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_places);

        ListView listView = (ListView) findViewById(R.id.list);

        adapter = new ListaAdapter(this,  places);
        listView.setAdapter(adapter);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Pega o item que foi selecionado.
        PlaceResult item = adapter.getItem(position);
        //Demostração
        Toast.makeText(this, "Você Clicou em: " + item.getName(), Toast.LENGTH_LONG).show();
    }
}