package com.enric.androshoping.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.Objects.Producte;

import java.util.ArrayList;

/**
 * Created by home on 26/05/15.
 */
public class DAOHistoric extends DAOBase<Historic> {


    public DAOHistoric(Context contexto) {
        super(contexto, "Historic");
    }

    @Override
    public Historic getFromCursor(Cursor cursor) {
        Historic historic = new Historic(cursor.getInt(cursor.getColumnIndex("id")),
                                         cursor.getInt(cursor.getColumnIndex("id_usuari")),
                                         cursor.getString(cursor.getColumnIndex("nombre")),
                                         cursor.getFloat(cursor.getColumnIndex("precio")),
                                         cursor.getInt(cursor.getColumnIndex("quantitat")),
                                         cursor.getInt(cursor.getColumnIndex("data"))
                                        );
        
        return historic;
    }

    public void insert(ArrayList<Producte> productes, int idUsuari){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            for(Producte producte: productes) {
                if(producte.selected) {
                    String sql = "INSERT OR IGNORE INTO Historic (id_usuari, nombre, precio, quantitat, data, deleted) VALUES ( ?, ?, ?, ?, ?, 0)";
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindAllArgsAsStrings(new
                            String[]{((Integer) idUsuari).toString(),
                            producte.nombre,
                            ((Float) producte.precio).toString(),
                            ((Integer) producte.quants).toString(),
                            Long.toString(System.currentTimeMillis()/1000)
                    });
                    statement.executeInsert();
                    statement.close();
                }
            }
            
            db.close();
        }
    }

    public ArrayList<Historic> selectMine(int idUsuari){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Historic> elements = new ArrayList<Historic>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM Historic WHERE deleted = 0 AND id_usuari = ? ORDER BY data DESC";
            Cursor cursor = db.rawQuery(sql, new String[]{
                                                    Integer.toString(idUsuari)
                                            });

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                Historic element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }

    public ArrayList<Historic> selectAll(){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Historic> elements = new ArrayList<Historic>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM Historic WHERE deleted = 0 ORDER BY data DESC";
            Cursor cursor = db.rawQuery(sql, new String[]{});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                Historic element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }
    
}
