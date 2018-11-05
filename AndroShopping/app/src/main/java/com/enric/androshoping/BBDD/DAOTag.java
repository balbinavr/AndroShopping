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
public class DAOTag extends DAOBase<Tag> {


    public DAOTag(Context contexto) {
        super(contexto, "Tags");
    }

    @Override
    public Tag getFromCursor(Cursor cursor) {
        Tag tag = new Tag(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString( cursor.getColumnIndex("nom")));
        return tag;
    }

    public void insert(long producte, String tag){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="INSERT OR IGNORE INTO Tags (nom, deleted) VALUES ( ?, 0 )";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{tag});
            long idTag = statement.executeInsert();
            statement.close();

            Log.d("INS_TAG", "id Inserit" + idTag);
            
            Log.d("Relacio", "Producte " + producte + " tag " + idTag);
            
            sql="INSERT OR IGNORE INTO RelTagProd (id_prod, id_tag, deleted) VALUES ( ?, ?, 0 )";
            SQLiteStatement statement2 = db.compileStatement(sql);
            statement2.bindAllArgsAsStrings(new
                    String[]{((Long)producte).toString(), ((Long)idTag).toString()});
            statement2.executeInsert();
            statement2.close();
        }
    }
    
    public ArrayList<Tag> selectTagsFromProducte(int idProducte){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Tag> elements = new ArrayList<Tag>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT t.* FROM RelTagProd AS r, Tags AS t WHERE t.id = r.id_tag AND r.id_prod = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{((Integer)idProducte).toString()});

            cursor.moveToFirst();
            Log.d("Relacio", "Relacions ");
            while (cursor.isAfterLast() == false) {
                Tag element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }


}
