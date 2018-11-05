package com.enric.androshoping.Activitats;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.enric.androshoping.Adapters.HistoricAdapter;
import com.enric.androshoping.BBDD.DAOHistoric;
import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.R;
import com.enric.androshoping.Singelton.Session;

import java.util.ArrayList;


public class HistoricCompres extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_historico);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_compres);

        ListView list = (ListView)findViewById(R.id.historic_list);

        DAOHistoric bbdd = new DAOHistoric(getApplicationContext());
        ArrayList<Historic> historic = bbdd.selectMine(Session.usuari.id);
        HistoricAdapter adapter = new HistoricAdapter(HistoricCompres.this, historic, false);
        list.setAdapter(adapter);
        list.setClickable(false);
        
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
        getMenuInflater().inflate(R.menu.menu_historic_compres, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
