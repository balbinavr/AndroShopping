package com.enric.androshoping.Utils;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.enric.androshoping.R;

/**
 * Created by home on 04/06/15.
 */
public class MyDialog extends DialogFragment {

    private EditText mEditText;

    public MyDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container);
        getDialog().setTitle("Hello");

        return view;
    }
}