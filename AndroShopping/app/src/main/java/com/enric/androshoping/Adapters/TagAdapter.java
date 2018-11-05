package com.enric.androshoping.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.enric.androshoping.Activitats.ViewUser;
import com.enric.androshoping.Objects.Tag;
import com.enric.androshoping.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by balbinavirgili on 15/05/15.
 */
public class TagAdapter extends ArrayAdapter<Tag> {

    private Context context;
    private ArrayList<Tag> tags;

    public TagAdapter (Context context, ArrayList<Tag> tags){

        super(context,0, tags);
        this.context=context;
        this.tags=tags;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tag producte = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tag, parent, false);
        }
        // Lookup view for data population
        TextView Name = (TextView) convertView.findViewById(R.id.txtName);
        ImageButton buttonUp = (ImageButton) convertView.findViewById(R.id.btnButtonUP);
        ImageButton buttonDown = (ImageButton) convertView.findViewById(R.id.btnButtonDOWN);
        ImageButton buttonDelete = (ImageButton) convertView.findViewById(R.id.btnButtonDELETE);


        if (position <= 0) buttonUp.setVisibility(View.INVISIBLE);
        if (position >= tags.size() - 1) buttonDown.setVisibility(View.INVISIBLE);
        else buttonDown.setVisibility(View.VISIBLE);
        
        Name.setText(Integer.toString(position+1) + "-. "+ producte.nom);
        buttonUp.setTag(position);
        buttonDown.setTag(position);
        buttonDelete.setTag(position);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (position > 0)
                    Collections.swap(TagAdapter.this.tags, position, position - 1);
                TagAdapter.this.notifyDataSetChanged();
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (position < tags.size() - 1)
                    Collections.swap(TagAdapter.this.tags, position, position + 1);
                TagAdapter.this.notifyDataSetChanged();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();

                tags.remove(position);
                TagAdapter.this.notifyDataSetChanged();
            }
        });



        // Return the completed view to render on screen
        return convertView;
    }
}
