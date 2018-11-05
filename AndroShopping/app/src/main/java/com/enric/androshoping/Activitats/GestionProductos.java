package com.enric.androshoping.Activitats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Adapters.ProducteAdapter;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;


public class GestionProductos extends ActionBarActivity {

    private ListView listview;
    public ArrayList<Producte> productes;
    public ProducteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_gestioProductes);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_productos);

        productes = new ArrayList<Producte>();
        
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ProducteAdapter(this, productes);
        listview.setAdapter(adapter);

        OnBackGruound bg = new OnBackGruound() {
            @Override
            public boolean onBackGround() {
                DAOProducte bbdd = new DAOProducte(getApplicationContext());
                productes.addAll(bbdd.selectAll());
                return true;
            }

            @Override
            public void onSucces() {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        };

        BBDDTask task = new BBDDTask(getApplicationContext(), bg);
        task.execute();

    }

    @Override
    protected void onResume() {

        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.compra5);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3CB371")));

        OnBackGruound bg = new OnBackGruound() {
            @Override
            public boolean onBackGround() {
                DAOProducte bbdd = new DAOProducte(getApplicationContext());
                productes.clear();
                productes.addAll(bbdd.selectAll());
                return true;
            }

            @Override
            public void onSucces() {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        };

        BBDDTask task = new BBDDTask(getApplicationContext(), bg);
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gestion_productos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.new_product) {
            Intent i = new Intent(getApplicationContext(), NewProduct.class);
            startActivity(i);
            return true;
        }

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
