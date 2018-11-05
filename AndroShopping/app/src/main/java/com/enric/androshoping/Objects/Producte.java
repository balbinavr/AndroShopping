package com.enric.androshoping.Objects;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.enric.androshoping.BBDD.DAOBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by home on 12/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producte{
    @JsonIgnoreProperties public int id;
    @JsonProperty("id") public int idRemot;
    @JsonProperty("nombre") public String nombre;
    @JsonProperty("descripcion") public String descripcion;
    @JsonProperty("precio") public float precio;
    @JsonProperty("activo") public boolean activo;
    @JsonProperty("stock") public int stock;
    @JsonProperty("foto") public String foto;
    @JsonProperty("tags") public String[] tags;
    @JsonIgnoreProperties public boolean selected;
    @JsonIgnoreProperties public int quants;
    @JsonIgnoreProperties public int data;

    public Producte() {
        super();
    }

    public Producte(int id, int idRemot, String nombre, String descripcion, float precio,  int stock, String foto, int data) {
        this.id = id;
        this.idRemot = idRemot;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.foto = foto;
        activo = false;
        quants = 0;
        selected = false;
        this.data = data;
    }


}
