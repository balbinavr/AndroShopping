package com.enric.androshoping.WebServices;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.enric.androshoping.Activitats.MainActivity;
import com.enric.androshoping.BBDD.DAODate;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.Interfaces.OnResult;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by home on 12/05/15.
 */
public class WSTask extends AsyncTask<Void, Integer, Boolean> {

    private String jsonResul;
    private Context context;
    private List<Producte> productes;
    private OnResult listener;
    public boolean finished;
    private MainActivity pare;
    private DAOProducte bbdd;

    public WSTask(Context context, OnResult listener, MainActivity pare){
        this.context = context;
        this.listener = listener;
        this.pare = pare;
        finished = false;
    }
    
    
    protected Boolean doInBackground(Void... params){
        Log.d("JSON", "AsiynkTask");
        WSBase webService = new WSBase();
        bbdd = new DAOProducte(context);


        switch(checkDate(webService)) {
            case 1:
                return getProducts(webService);
            case 2:
                return true;
            default:
                return false;
        }
        
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Boolean result) {

        if(result) {
            //Inserim els productes a la base de dades, no ho fem des de un nou AsincTask per no haver-ne
            // de crear un de nou innecessari i tindre un cost major
            for (Producte producte : productes) {
                bbdd.insert(producte);
            }

            if(pare.finished){
                listener.onOkResult();
            }else{
                this.finished = true;
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(pare);

            builder.setMessage(R.string.diallog_no_connection)
                    .setTitle(R.string.dialog_no_connection_title);
            // Add the buttons
            builder.setPositiveButton(R.string.dialog_no_connection_retry, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    WSTask retry = new WSTask(context, listener, pare);
                    retry.execute();
                }
            });
            builder.setNegativeButton(R.string.dialog_no_connection_continue, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    listener.onOkResult();
                }
            });

            builder.create().show();
        }

    }

    protected void onPreExecute(){
        Log.d("JSON", "Començo a executar");
        
    }

    private int checkDate(WSBase webService){
        int result = -1;
        boolean succes = false;
        int nIntents = 0;


        while(!succes) {
            try {
                jsonResul = webService.get("http://www.v2msoft.com/curso-android/ws/last_update.php?force_update=1");

                ObjectMapper mapper = new ObjectMapper();

                JsonNode rootNode = mapper.readTree(jsonResul);
                JsonNode idNode = rootNode.path("last_update");
                
                String date = mapper.treeToValue(idNode, String.class);
                Log.d("JSON", "Aquesta es la data que retorna " + date);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date update = df.parse(date);

                Log.d("JSON", "Arribem a la data");
                
                DAODate bbdd = new DAODate(context);
                ArrayList<String> dates = bbdd.selectAll();
                int epoch = Integer.parseInt((dates.size() > 0)?dates.get(0): "0");
                int epochWS = (int) (update.getTime() /1000);
                
                Log.d("EPOCH", "WS " + epochWS);
                Log.d("EPOCH", "propi " + epoch);

                if(epochWS > epoch){
                    bbdd.insert(epochWS);
                    succes = true;
                    result = 1;    
                    Log.d("UPDATE", "Ara es l'hora");
                }else{
                    succes = true;
                    result = 2;
                }
                

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("JSON", "Ha petat");
                nIntents++;
                if(nIntents > 3){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

                if(nIntents > 5){
                    succes = true;
                    result = -1;
                }
            } catch (ParseException e) {
                result = -1;
                succes = true;
            }
        }

        return result;
    }
    
    private boolean getProducts(WSBase webService){
        boolean result = false;
        boolean succes = false;
        int nIntents = 0;


        while(!succes) {
            try {
                jsonResul = webService.get("http://www.v2msoft.com/curso-android/ws/lista_productos.php");

                ObjectMapper mapper = new ObjectMapper();

                this.productes = mapper.readValue(
                        jsonResul,
                        mapper.getTypeFactory().constructCollectionType(
                                List.class, Producte.class));

                succes = true;
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("JSON", "Ha petat");
                nIntents++;
                if(nIntents > 3){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

                if(nIntents > 5){
                    succes = true;
                    result = false;
                }
            }
        }

        return result;
    }

}
