package com.enric.androshoping.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.enric.androshoping.Objects.Tag;

import java.util.ArrayList;

/**
 * Created by home on 26/05/15.
 */
public class DAODate extends DAOBase<String> {


    public DAODate(Context contexto) {
        super(contexto, "Actualitza");
    }

    @Override
    public String getFromCursor(Cursor cursor) {
        Log.d("EPOCH", "Des del cursor " + cursor.getString(cursor.getColumnIndex("dia")));
        return cursor.getString(cursor.getColumnIndex("dia"));
    }

    public void insert(int date){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="DELETE FROM Actualitza";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{});
            statement.executeUpdateDelete();
            statement.close();

            sql="INSERT OR IGNORE INTO Actualitza (dia, deleted) VALUES ( ? , 0 )";
            SQLiteStatement statement2 = db.compileStatement(sql);
            statement2.bindAllArgsAsStrings(new
                    String[]{Long.toString(date)});
            statement2.executeInsert();
            statement2.close();
            
            Log.d("EPOCH", "Fem l'insert " + date);
            
            db.close();
        }
    }

}
