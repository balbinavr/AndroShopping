package com.enric.androshoping.Activitats;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Singelton.Session;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;


public class ViewUser extends ActionBarActivity {

    DAOUsuari bbdd;
    private ArrayList<Usuari> usuaris;
    private TextView txtEmail;
    private TextView txtNom;
    private TextView txtEdad;
    private TextView txtCompras;
    private TextView txtTotal;
    private RadioGroup radioGroup;
    private RadioButton radioButton_hom;
    private RadioButton radioButton_muj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTitle(R.string.title_viewUser);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        bbdd = new DAOUsuari(getApplicationContext());
        int id = getIntent().getExtras().getInt("usuari");

        usuaris = bbdd.selectID(id);

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtNom = (TextView) findViewById(R.id.txtNom);
        txtEdad = (TextView) findViewById(R.id.txtEdad);
        txtCompras = (TextView) findViewById(R.id.txtCompras);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton_hom = (RadioButton) findViewById(R.id.radiobutton_hom);
        radioButton_muj = (RadioButton) findViewById(R.id.radiobutton_muj);

        txtEmail.setText(txtEmail.getText()+usuaris.get(0).email);
        txtNom.setText(txtNom.getText() + usuaris.get(0).nom);
        txtEdad.setText(txtEdad.getText()+Integer.toString(usuaris.get(0).edat)+" años");


        OnBackGruound bg = new OnBackGruound() {
            @Override
            public boolean onBackGround() {
                DAOUsuari bbdd = new DAOUsuari(ViewUser.this);
                ArrayList<Historic> historic = bbdd.selectAllHistoric(usuaris.get(0).id);
                this.data = new Object[2];
                float preu = 0;
                int quantitat = 0;
                Log.d("Compres", "Compres:");
                for(Historic h : historic){
                    Log.d("Compres", "Preu " + h.preu + " Quantitat " + h.nQuants);
                    preu = preu + (h.nQuants * h.preu);
                    quantitat = quantitat + h.nQuants;
                }
                this.data[1] = preu;
                this.data[0] = quantitat;

                return true;
            }

            @Override
            public void onSucces() {
                txtCompras.setText(txtCompras.getText()+Integer.toString((int)this.data[0]));
                txtTotal.setText(txtTotal.getText()+Float.toString((float)this.data[1])+" €");
            }

            @Override
            public void onFailure() {

            }
        };

        BBDDTask task = new BBDDTask(getApplicationContext(), bg);
        task.execute();
        
        
        //txtCompras.setText(txtCompras.getText()+Integer.toString(usuaris.get(0).compres));
        //txtTotal.setText(txtTotal.getText()+Float.toString(usuaris.get(0).preu)+" €");
        
        switch (usuaris.get(0).genere){
            case 1:
                radioButton_muj.setChecked(true);
                break;
            case 0:
                radioButton_hom.setChecked(true);
                break;
            default:
                radioButton_muj.setChecked(true);
                break;
        }



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
        getMenuInflater().inflate(R.menu.menu_view_user, menu);
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

        if ( id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
