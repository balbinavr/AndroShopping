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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.Activitats.GestionProductos;
import com.enric.androshoping.Activitats.RegisterPage;
import com.enric.androshoping.Activitats.ViewProduct;
import com.enric.androshoping.Activitats.ViewUser;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by balbinavirgili on 02/04/15.
 */
public class ProducteAdapter extends ArrayAdapter<Producte> {

    private Context context;
    private ArrayList<Producte> productes;

    public ProducteAdapter (Context context, ArrayList<Producte> productes){

        super(context,0, productes);
        this.context=context;
        this.productes=productes;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Producte producte = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producte, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.txtName);
        TextView category = (TextView) convertView.findViewById(R.id.txtCategory);
        TextView prize = (TextView) convertView.findViewById(R.id.txtPrize);
        ImageView image = (ImageView) convertView.findViewById(R.id.imgvImagen);
        ImageButton button = (ImageButton) convertView.findViewById(R.id.btnButton);


        button.setClickable(true);
        convertView.setClickable(true);

        name.setText(producte.nombre);
        button.setTag(position);
        String tags = "";
        for(String tag : producte.tags){
            tags = tags + " #" + tag;
        }
        
        category.setText(tags);
        prize.setText(Float.toString(producte.precio) + "€");
        convertView.setTag(producte.id);

        // Pendent de fer la desacrrega de la foto
        // Image.setImageResource(producte.foto);
        Log.d("IMG", "Imatge: " + producte.foto + " id " + producte.id);
        Picasso.with(context)
                .load(producte.foto)
                .centerCrop()
                .resize(200, 200)
                .transform(new RoundedTransformation(200, 0))
                .into(image);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ViewProduct.class);
                int tag = (int)v.getTag();
                Log.d("TAG", "Id del producte " + tag);
                
                i.putExtra("producte", tag);
                context.startActivity(i);
            }
        });

        button.setTag(producte.id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int idProducte = (int) v.getTag();
                
                //dialog per confirmar
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.dialog_title);
                alertDialogBuilder.setMessage(R.string.dialog_text).setCancelable(false) .setPositiveButton(R.string.dialog_accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //int position = (int) v.getTag();

                        OnBackGruound bg = new OnBackGruound() {
                            @Override
                            public boolean onBackGround() {
                                DAOProducte bbdd = new DAOProducte(context);
                                bbdd.delete(idProducte);
                                return true;
                            }

                            @Override
                            public void onSucces() {
                                if(ProducteAdapter.this != null) {
                                    Log.d("DELETE", "Position " + position);
                                    ProducteAdapter.this.remove(ProducteAdapter.this.getItem(position));
                                    ProducteAdapter.this.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure() {

                            }
                        };

                        BBDDTask task = new BBDDTask(context, bg);
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

                //actualitzar la pantalla amb la informacio
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
