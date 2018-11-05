package com.enric.androshoping.Fragments;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.enric.androshoping.Activitats.Comprar;
import com.enric.androshoping.Adapters.CompraAdapter;
import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Compra;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;

public class FragmentComprar extends Fragment {

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comprar, container, false);

        EditText buscar = (EditText)view.findViewById(R.id.compra_busca_producte_input);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Comprar.adapter.setRestriction(s.toString());
                Log.d("RESTRICCIO", "Restriccio " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        return view;
    }

}
