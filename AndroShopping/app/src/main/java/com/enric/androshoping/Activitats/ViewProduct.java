package com.enric.androshoping.Activitats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ViewProduct extends ActionBarActivity {

    private DAOProducte bbdd;
    private TextView nombre;
    private TextView descripcion;
    private ImageView foto;
    private TextView precio;
    private TextView stock;
    private TextView tags;
    private ArrayList<Producte> productes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_viewProduct);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        bbdd = new DAOProducte(getApplicationContext());
        int id = getIntent().getExtras().getInt("producte");

        productes = bbdd.selectID(id);

        nombre = (TextView) findViewById(R.id.txtNom);
        descripcion = (TextView) findViewById(R.id.txtDescripcio);
        foto = (ImageView) findViewById(R.id.imvFoto);
        precio = (TextView) findViewById(R.id.txtPreu);
        stock = (TextView) findViewById(R.id.txtStock);
        tags = (TextView) findViewById(R.id.txtTags);


        nombre.setText(nombre.getText()+productes.get(0).nombre);
        descripcion.setText(descripcion.getText()+productes.get(0).descripcion);
        precio.setText(precio.getText()+Float.toString(productes.get(0).precio)+" €");
        stock.setText(stock.getText()+Integer.toString(productes.get(0).stock)+" unidades");
        Log.d("IMG", "Imatge: " + productes.get(0).foto);
        Picasso.with(getApplicationContext())
                .load(productes.get(0).foto).resize(400,400).centerInside()
                .into(foto);

        String tags = "\n\t";
        int i = 1;
        for(String tag : productes.get(0).tags){
            tags = tags + i + ". #" + tag + "\n\t";
            i++;
        }

        this.tags.setText(this.tags.getText() + tags);


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
        getMenuInflater().inflate(R.menu.menu_view_product, menu);
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
        
        if(id == R.id.action_remove){

            //dialog per confirmar
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewProduct.this);
            alertDialogBuilder.setTitle(R.string.dialog_title);
            alertDialogBuilder.setMessage(R.string.dialog_text).setCancelable(false) .setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //int position = (int) v.getTag();

                    OnBackGruound bg = new OnBackGruound() {
                        @Override
                        public boolean onBackGround() {
                            DAOProducte bbdd = new DAOProducte(getApplicationContext());
                            bbdd.delete(productes.get(0).id);
                            return true;
                        }

                        @Override
                        public void onSucces() {
                            finish();
                        }

                        @Override
                        public void onFailure() {

                        }
                    };

                    BBDDTask task = new BBDDTask(getApplicationContext(), bg);
                    task.execute();

                }
            }) .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, do nothing
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create(); // show it
            alertDialog.show();
            
            return true;
        }

        if ( id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
