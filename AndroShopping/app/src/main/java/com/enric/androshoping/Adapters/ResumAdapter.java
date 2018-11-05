package com.enric.androshoping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Tag;
import com.enric.androshoping.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by balbinavirgili on 15/05/15.
 */
public class ResumAdapter extends ArrayAdapter<Producte> {

    private Context context;
    private ArrayList<Producte> productes;

    public ResumAdapter(Context context, ArrayList<Producte> productes){

        super(context,0, productes);
        this.context=context;
        this.productes=productes;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Producte producte = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_compra, parent, false);
        }
        // Lookup view for data population
        TextView Name = (TextView) convertView.findViewById(R.id.txtNom);
        TextView Preu = (TextView) convertView.findViewById(R.id.txtPreu);

        Float preu_total = producte.precio*producte.quants;

        DecimalFormat form = new DecimalFormat("0.00");
        String formattedText=form.format(preu_total);
        Name.setText(producte.nombre);
        Preu.setText(formattedText+ " €");


        // Return the completed view to render on screen
        return convertView;
    }
}
