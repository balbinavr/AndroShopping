package com.enric.androshoping.Fragments;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.enric.androshoping.R;

public class FirstStep extends Fragment {
    public EditText nom;
    public EditText descripcio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_step, container, false);

        nom = ((EditText)view.findViewById(R.id.first_nom));
        descripcio = ((EditText)view.findViewById(R.id.first_descripcio));

        return view;
    }
    
    public void reset(){
        try {
            nom.setText("");
            descripcio.setText("");
        }catch(NullPointerException e){}
    }

}
