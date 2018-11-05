package com.enric.androshoping.Activitats;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.BBDD.DAOHistoric;
import com.enric.androshoping.Objects.Compra;
import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Singelton.Session;
import com.enric.androshoping.Adapters.ResumAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ConfirmarCompra extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_confirmar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_compra);
        Double sumTotal = 0.0;

        ArrayList<Producte> productes = new ArrayList<Producte>();
        for(Producte p : Comprar.productes){
            if(p.selected){
                productes.add(p);
                sumTotal= sumTotal + (p.precio*p.quants);
            }
        }

        ResumAdapter adapter = new ResumAdapter(this, productes);

        TextView total = (TextView) findViewById(R.id.txtTotal);
        ListView vista = (ListView)findViewById(R.id.confirma_comprar_listview);
        vista.setAdapter(adapter);

        Button accept = ((Button)findViewById(R.id.confirma_comprar_accept));
        Button cancel = ((Button)findViewById(R.id.confirma_comprar_cancel));


        DecimalFormat form = new DecimalFormat("0.00");
        String formattedText=form.format(sumTotal);
        total.setText("Total: "+formattedText+ " €");
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ACCEPTA-COMPRA", "Entra a l'acceptar compra");
                DAOHistoric bbdd = new DAOHistoric(getApplicationContext());
                bbdd.insert(Comprar.productes, Session.usuari.id);
                
                ArrayList<Historic> historic = bbdd.selectAll();
                Toast.makeText(getApplicationContext(), "COMPRA REALIZADA EXITOSAMENTE!", Toast.LENGTH_SHORT).show();

                Intent i;
                if(Session.usuari.rol == Usuari.ADMIN)
                    i = new Intent(getApplicationContext(), MenuAdmin.class);
                else
                    i = new Intent(getApplicationContext(), MenuUser.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirmar_compra, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
