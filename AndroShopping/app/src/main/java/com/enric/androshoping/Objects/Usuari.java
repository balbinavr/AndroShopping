package com.enric.androshoping.Objects;


import java.sql.Date;

public class Usuari {
    public final static int HOME = 0;
    public final static int DONA = 1;

    public final static String ADMIN = "admin";
    public final static String USER = "user";

    public int id;
    public String nom;
    public int edat;
    public String email;
    public String contrasenya;
    public int genere;
    public String rol;
    public String foto;

    public Usuari() {
        super();
    }
    
    public Usuari(int id, String nom, int edat, String email, String contrasenya, int genere, String rol) {
        this.id = id;
        this.nom = nom;
        this.edat = edat;
        this.email = email;
        this.contrasenya = contrasenya;
        this.genere = genere;
        this.rol = rol;
    }


    public Usuari(String nom, int edat, String email, String contrasenya, int genere, String rol) {
        this.nom = nom;
        this.edat = edat;
        this.email = email;
        this.contrasenya = contrasenya;
        this.genere = genere;
        this.rol = rol;
    }
}
