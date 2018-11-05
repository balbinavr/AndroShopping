package com.enric.androshoping.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.Activitats.Comprar;
import com.enric.androshoping.Activitats.ViewProduct;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by balbinavirgili on 02/04/15.
 */
public class CompraAdapter extends ArrayAdapter<Producte> {

    private Context context;
    public ArrayList<Producte> productes;
    private TextView data;
    private TextView preu;
    private TextView prod;
    private Spinner spinner;
    private CheckBox check;
    private String restriction;
    private boolean isRestricted;
    private static int HEIGHT;

    public CompraAdapter(Context context, ArrayList<Producte> productes){
        super(context,0, productes);
        this.context=context;
        this.productes=productes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Producte producte = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_compras, parent, false);
            HEIGHT = convertView.getHeight();
        }
        
        boolean tagTrobat = false;
        
        for(String t : producte.tags){
            if(isRestricted && t.contains(restriction)){
                tagTrobat = true;
                convertView.setBackgroundColor(Color.YELLOW);
                break;
            }
        }
        
        if(tagTrobat){
            convertView.setBackgroundColor(Color.YELLOW);
        }else{
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        
        if(isRestricted && !(producte.nombre.contains(restriction) || tagTrobat)){
            convertView.setVisibility(View.GONE);
            convertView.getLayoutParams().height = 1;
            return convertView;
        }else{
            convertView.getLayoutParams().height = HEIGHT;
            convertView.setVisibility(View.VISIBLE);
        }

        data = (TextView) convertView.findViewById(R.id.edtData_hora);
        preu = (TextView) convertView.findViewById(R.id.edtPreu);
        prod = (TextView) convertView.findViewById(R.id.edtProd);
        spinner = (Spinner) convertView.findViewById(R.id.spinner_compras);
        check = (CheckBox) convertView.findViewById(R.id.checkbox);

        check.setTag(producte.id);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int id = (int) buttonView.getTag();
                for(int i = 0; i < productes.size(); i++){
                    if(productes.get(i).id == id) {
                        Log.d("IGUALS", "Hem trobat dues ids iguals");
                        productes.get(i).selected = isChecked;
                    }
                }
            }
        });
        check.setChecked(producte.selected);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Log.d("DATA", "Data " + producte.data * 1000);
        Date dia = new Date((long)producte.data * 1000);
        
        Log.d("DATA", "Dia " + dia.getTime());
        
        data.setText(df.format(dia));
        preu.setText("Precio total: " + Float.toString(producte.precio)+"€");
        prod.setText(producte.nombre);
        convertView.setTag(producte.id);
        String tags = "";
        for(String tag : producte.tags){
            tags = tags + " #" + tag;
        }

        ArrayList a = new ArrayList<String>();
        for ( int i=1;i<=producte.stock&&i<=10; i++){
            a.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ADAPTER", "Item: " + position);
                producte.quants = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
    
    public void setRestriction(String restriction){
        this.restriction = restriction;
        this.isRestricted = !(restriction.isEmpty());
        if(isRestricted){
            Log.d("RESTRICCIO", "Estem Restringin");
        }
        this.notifyDataSetChanged();
    }
}
