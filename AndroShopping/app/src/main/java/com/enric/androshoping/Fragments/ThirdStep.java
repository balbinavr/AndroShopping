package com.enric.androshoping.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.enric.androshoping.Objects.Tag;
import com.enric.androshoping.Adapters.TagAdapter;
import com.enric.androshoping.R;

import java.util.ArrayList;

public class ThirdStep extends Fragment {

    private ListView listview;
    public static ArrayList<Tag> tags = new ArrayList<Tag>();;
    private TagAdapter adapter;
    
    
    public void reset(){
        try {
            tags.clear();
        }catch (NullPointerException e){}
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_third_step, container, false);


        listview = (ListView) view.findViewById(R.id.listview);
        adapter = new TagAdapter(getActivity(), tags);
        listview.setAdapter(adapter);
        
        Button insertTag = (Button)view.findViewById(R.id.new_product_insertar_tag);
        
        insertTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNom = (EditText)view.findViewById(R.id.edtNombre);
                String tag = ((EditText)view.findViewById(R.id.edtNombre)).getText().toString();
                if (tag.equals("")){
                    edtNom.setError("Empty Tag");
                }else {
                    tags.add(new Tag(-1, tag));
                    adapter.notifyDataSetChanged();
                    edtNom.setText("");
                }
            }
        });
        
        return view;
    }



}
