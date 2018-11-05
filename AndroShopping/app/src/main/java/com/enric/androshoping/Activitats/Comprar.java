package com.enric.androshoping.Activitats;



import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.Adapters.CompraAdapter;
import com.enric.androshoping.Adapters.ProducteAdapter;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Fragments.FirstStep;
import com.enric.androshoping.Fragments.FragmentComprar;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Compra;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;
import com.enric.androshoping.Utils.MyDialog;

import java.util.ArrayList;


public class Comprar extends ActionBarActivity {

    private ListView listview;
    public static ArrayList<Producte> productes;
    public static CompraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_buy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

        productes = new ArrayList<Producte>();

        listview = (ListView) findViewById(R.id.listview_compra);
        adapter = new CompraAdapter(Comprar.this, productes);
        listview.setAdapter(adapter);

        OnBackGruound bg = new OnBackGruound() {
            @Override
            public boolean onBackGround() {
                DAOProducte bbdd = new DAOProducte(getApplicationContext());
                productes.addAll(bbdd.selectAllCompres());
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comprar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Comprar.productes = adapter.productes;
            int c = 0;
            for(Producte p : Comprar.productes){
                CheckBox checkBox = (CheckBox)adapter.getView(c, null, (ListView) findViewById(R.id.listview_compra)).findViewById(R.id.checkbox);
                if(checkBox.isChecked()){
                    p.selected = true;
                    Log.d("Quants", "Quants: " + p.quants);
                }
                c++;
            }
            
            Intent i = new Intent(getApplicationContext(), ConfirmarCompra.class);
            startActivity(i);
            
            return true;
        }
        
        if(id == R.id.action_buscar){
            Log.d("FRAGMENT COMPRA", "Entra a lo del fragment de la compra");
            FragmentComprar fragmentComprar = new FragmentComprar();
            
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_compres, fragmentComprar);
            fragmentTransaction.commit();
        }

        if ( id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
