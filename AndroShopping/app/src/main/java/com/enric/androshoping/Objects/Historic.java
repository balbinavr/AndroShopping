package com.enric.androshoping.Objects;

/**
 * Created by home on 30/05/15.
 */
public class Historic {
    public int id;
    public int idUsuari;
    public String nom;
    public float preu;
    public int nQuants;
    public long date;


    public Historic(int id, int idUsuari, String nom, float preu, int nQuants, long date) {
        this.id = id;
        this.idUsuari = idUsuari;
        this.nom = nom;
        this.preu = preu;
        this.nQuants = nQuants;
        this.date = date;
    }
}
