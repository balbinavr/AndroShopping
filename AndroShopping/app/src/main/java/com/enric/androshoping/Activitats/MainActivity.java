package com.enric.androshoping.Activitats;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.widget.Toast;

import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnResult;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.WebServices.WSTask;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    public boolean finished;
    public WSTask task;
    public OnResult newActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        
        finished = false;
        newActivity = new OnResult() {
            @Override
            public void onOkResult() {
                Intent i = new Intent(MainActivity.this, LoginPage.class);
                startActivity(i);
                MainActivity.this.finish();
            }

            @Override
            public void onKoResult() {

            }
        };

        task = new WSTask(getApplicationContext(), newActivity, this);
        task.execute();

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                
                if(task.finished == false){
                    MainActivity.this.finished = true;
                }else{
                    newActivity.onOkResult();
                }
                
            }
        }, SPLASH_TIME_OUT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
