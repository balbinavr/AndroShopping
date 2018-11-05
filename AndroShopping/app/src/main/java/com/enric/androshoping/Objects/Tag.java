package com.enric.androshoping.Objects;

import android.util.Log;

/**
 * Created by balbinavirgili on 15/05/15.
 */
public class Tag {
    public int id;
    public String nom;

    public Tag(int id, String nom) {
        Log.d("Inserim", "id recuperat " + id);
        this.id = id;
        this.nom = nom;
    }

    public Tag( String nom) {
        this.nom = nom;
    }
}
