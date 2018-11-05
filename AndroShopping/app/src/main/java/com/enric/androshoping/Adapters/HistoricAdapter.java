package com.enric.androshoping.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enric.androshoping.Activitats.ViewProduct;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by balbinavirgili on 02/04/15.
 */
public class HistoricAdapter extends ArrayAdapter<Historic> {

    private Context context;
    private ArrayList<Historic> historic;
    private boolean showEmail;

    public HistoricAdapter(Context context, ArrayList<Historic> historic, boolean showEmail){

        super(context,0, historic);
        this.context=context;
        this.historic = historic;
        this.showEmail = showEmail;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Historic historic = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ventas, parent, false);
        }

        TextView data = (TextView)convertView.findViewById(R.id.HistorictxtData);
        TextView email = (TextView)convertView.findViewById(R.id.HistorictxtEmail);
        TextView preu = (TextView)convertView.findViewById(R.id.HistorictxtPreu);
        TextView producte = (TextView)convertView.findViewById(R.id.HistorictxtProducte);
        TextView quantitat = (TextView)convertView.findViewById(R.id.HistorictxtQuantitat);


        Date epoch = new Date(historic.date * 1000);
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");


        DecimalFormat form = new DecimalFormat("0.00");
        String formattedText=form.format(historic.preu * historic.nQuants);

        data.setText(sd.format(epoch));
        preu.setText("Precio total: " + formattedText + " €");
        producte.setText(historic.nom);
        quantitat.setText("Cantidad: " + Integer.toString(historic.nQuants));

        if(showEmail){
            DAOUsuari bbdd = new DAOUsuari(context);
            Usuari user = bbdd.selectID(historic.idUsuari).get(0);
            email.setText(user.email);
        }
        
        // Return the completed view to render on screen
        return convertView;
    }
}
