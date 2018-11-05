package com.enric.androshoping.Activitats;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.enric.androshoping.R;

public class MenuUser extends ActionBarActivity {

    private LinearLayout layUsuari;
    private LinearLayout layCompra;
    private LinearLayout layHistoric;
    private LinearLayout laySortida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_activity_menu_user);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        layUsuari = (LinearLayout) findViewById(R.id.layUsuari);
        layCompra = (LinearLayout) findViewById(R.id.layCompra);
        layHistoric = (LinearLayout) findViewById(R.id.layHistoric);
        laySortida = (LinearLayout) findViewById(R.id.laySortir);

        layUsuari.setClickable(true);
        layCompra.setClickable(true);
        layHistoric.setClickable(true);
        laySortida.setClickable(true);

        layUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), NewUser.class);
                i.putExtra("Tipus", "user");
                startActivity(i);

            }
        });

        layCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Comprar.class);
                startActivity(i);

            }
        });


        layHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), HistoricCompres.class);
                startActivity(i);

            }
        });


        laySortida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);

            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.compra5);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3CB371")));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_sortir) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
