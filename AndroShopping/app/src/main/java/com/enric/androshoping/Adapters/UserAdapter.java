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

import com.enric.androshoping.Activitats.ViewUser;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;

/**
 * Created by balbinavirgili on 02/04/15.
 */
public class UserAdapter extends ArrayAdapter<Usuari> {

    private Context context;
    private ArrayList<Usuari> usuaris;

    public UserAdapter(Context context, ArrayList<Usuari> usuaris){

        super(context,0, usuaris);
        this.context=context;
        this.usuaris=usuaris;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Usuari usuari = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuari, parent, false);
        }
        // Lookup view for data population
        TextView Email = (TextView) convertView.findViewById(R.id.txtUserEmail);
        TextView Edat = (TextView) convertView.findViewById(R.id.txtUserAge);
        TextView Sexe = (TextView) convertView.findViewById(R.id.txtUserGender);
        ImageView Image = (ImageView) convertView.findViewById(R.id.imgvUserImagen);
        ImageButton button = (ImageButton) convertView.findViewById(R.id.btnUserDelete);


        button.setClickable(true);
        convertView.setClickable(true);

        Email.setText(usuari.email);
        button.setTag(usuari.id);
        Edat.setText(((Integer)usuari.edat).toString());
        convertView.setTag(usuari.id);
        switch (usuari.genere){
            case 1:
                Sexe.setText("Mujer");
                break;
            case 0:
                Sexe.setText("Hombre");
                break;
            default:
                Sexe.setText("Mujer");
                break;
        }
        Image.setTag(position);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int idUsuari = (int)v.getTag();

                Intent i = new Intent(context, ViewUser.class);
                i.putExtra("usuari", idUsuari);
                context.startActivity(i);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int idUsuari = (int)v.getTag();
                
                //dialog per confirmar
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.dialog_title);
                alertDialogBuilder.setMessage(R.string.dialog_text).setCancelable(false) .setPositiveButton(R.string.dialog_accept,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        OnBackGruound bg = new OnBackGruound() {
                            @Override
                            public boolean onBackGround() {
                                DAOUsuari bbdd = new DAOUsuari(context);
                                bbdd.delete(idUsuari);
                                Log.d("USER DELETE","Usuari " + idUsuari);
                                
                                return true;
                            }

                            @Override
                            public void onSucces() {
                                if(UserAdapter.this != null) {
                                    Log.d("DELETE", "Position " + position);
                                    UserAdapter.this.remove(UserAdapter.this.getItem(position));
                                    UserAdapter.this.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure() {

                            }
                        };

                        BBDDTask task = new BBDDTask(context, bg);
                        task.execute();

                    }
                }) .setNegativeButton(R.string.dialog_cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, do nothing
                    dialog.cancel(); }
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
