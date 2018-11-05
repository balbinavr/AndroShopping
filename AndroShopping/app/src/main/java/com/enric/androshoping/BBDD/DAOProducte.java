package com.enric.androshoping.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Tag;

import java.util.ArrayList;

/**
 * Created by home on 26/05/15.
 */
public class DAOProducte extends DAOBase<Producte> {
    private Context context;

    public DAOProducte(Context contexto) {
        super(contexto, "Productes");
        context = contexto;
    }

    @Override
    public Producte getFromCursor(Cursor cursor) {
        Producte producte = new Producte(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("id_remot")),
                cursor.getString(cursor.getColumnIndex("nombre")),
                cursor.getString(cursor.getColumnIndex("descripcion")),
                cursor.getFloat(cursor.getColumnIndex("precio")),
                cursor.getInt(cursor.getColumnIndex("stock")),
                cursor.getString(cursor.getColumnIndex("foto")),
                cursor.getInt(cursor.getColumnIndex("data"))
                );

        int activo = cursor.getInt(cursor.getColumnIndex("activo"));
       
        producte.activo = (activo == 1);
        
        
        DAOTag bbdd = new DAOTag(context);
        ArrayList<Tag> tags = bbdd.selectTagsFromProducte(producte.id);

        producte.tags = new String[tags.size()];
        int i = 0;
        for(Tag tag : tags){
            Log.d("TAGS", "Tag " + tag.nom + " id " + tag.id);
            producte.tags[i++] = tag.nom;
        }
        
        return producte;
    }

    public void insert(Producte producte){
        if(producte.idRemot >= 0){
            deleteExisting(producte.idRemot);
        }
        
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="INSERT OR IGNORE INTO Productes (id_remot, nombre, descripcion, precio, activo, stock, foto, data, deleted) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, 0)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{((Integer)producte.idRemot).toString(),
                                producte.nombre, 
                                producte.descripcion, 
                                ((Float)producte.precio).toString(), 
                                (producte.activo)? "1":"0",
                                ((Integer)producte.stock).toString(),
                                producte.foto,
                                Long.toString(System.currentTimeMillis() / 1000)
            });
            long id = statement.executeInsert();
            statement.close();
            db.close();
            
            DAOTag bbddTag = new DAOTag(context);

            for(String tag : producte.tags) {
                bbddTag.insert(id, tag);
            }
        }

    }
    
    public void delete(int id){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="UPDATE Productes SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{((Integer)id).toString()
            });
            statement.executeInsert();
            statement.close();
            db.close();
        }
    }
    
    public void deleteExisting(int idRemot){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Producte> elements = new ArrayList<Producte>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="DELETE FROM Productes WHERE id_remot = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{((Integer)idRemot).toString()
            });
            statement.executeInsert();
            statement.close();
            
            db.close();
        }
        
    }

    public ArrayList<Producte> selectAllCompres(){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Producte> elements = new ArrayList<Producte>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM Productes WHERE deleted = 0 AND stock > 0 AND activo = 1 ORDER BY data DESC";
            Cursor cursor = db.rawQuery(sql, new String[]{});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                Producte element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }
    
}
