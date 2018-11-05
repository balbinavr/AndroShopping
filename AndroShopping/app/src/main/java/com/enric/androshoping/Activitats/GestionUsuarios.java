package com.enric.androshoping.Activitats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.enric.androshoping.Adapters.UserAdapter;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;


public class GestionUsuarios extends ActionBarActivity {

    public ArrayList<Usuari> persones;
    public ListView listview;
    public UserAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_gestioUsuaris);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        persones = new ArrayList<Usuari>();

        listview = (ListView) findViewById(R.id.listview);
        adapter = new UserAdapter(this, persones);
        listview.setAdapter(adapter);

        OnBackGruound bg = new OnBackGruound() {
            @Override
            public boolean onBackGround() {
                DAOUsuari bbdd = new DAOUsuari(getApplicationContext());
                persones.addAll(bbdd.selectAll());
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
                DAOUsuari bbdd = new DAOUsuari(getApplicationContext());
                persones.clear();
                persones.addAll(bbdd.selectAll());
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
        getMenuInflater().inflate(R.menu.menu_gestion_usuarios, menu);
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

        if (id == android.R.id.home) {
            finish();
            return true;
        }


        if (id == R.id.new_user){
            Intent i = new Intent(getApplicationContext(), NewUser.class);
            i.putExtra("Tipus", "admin");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
