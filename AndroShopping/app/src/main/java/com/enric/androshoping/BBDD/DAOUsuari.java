package com.enric.androshoping.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.enric.androshoping.Objects.Historic;
import com.enric.androshoping.Objects.Usuari;

import java.util.ArrayList;

/**
 * Created by home on 26/05/15.
 */
public class DAOUsuari extends DAOBase<Usuari> {

    private Context context;
    
    public DAOUsuari(Context contexto) {
        super(contexto, "Usuaris");
        this.context = contexto;
    }

    @Override
    public Usuari getFromCursor(Cursor cursor) {
        Usuari usuari = new Usuari(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("nom")),
                cursor.getInt(cursor.getColumnIndex("edat")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("contrasenya")),
                cursor.getInt(cursor.getColumnIndex("genere")),
                cursor.getString(cursor.getColumnIndex("rol"))
                );

        Log.d("USER", "User " + usuari.nom + " Deleted " + cursor.getInt(cursor.getColumnIndex("deleted")));
        return usuari;
    }
    
    private int getAutoLoginFromCursor(Cursor cursor){
        return cursor.getInt(cursor.getColumnIndex("id_usuari"));
    }

    public long insert(Usuari usuari){
        SQLiteDatabase db = super.getWritableDatabase();
        long id = -1;
        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="INSERT OR IGNORE INTO Usuaris (nom, edat, email, contrasenya, genere, rol, deleted) VALUES ( ?, ?, ?, ?, ?, ?, 0)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                        String[]{usuari.nom,
                        ((Integer)usuari.edat).toString(),
                        usuari.email,
                        usuari.contrasenya,
                        ((Integer)usuari.genere).toString(),
                        usuari.rol
            });
            id = statement.executeInsert();
            statement.close();

        }
        return id;
    }

    /**
     * *
     * @param email
     * @param password
     * @return null if user is not correct
     */
    public Usuari autenticaUsuari(String email, String password){
        SQLiteDatabase db = super.getReadableDatabase();
        Usuari usuari = null;
        
        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM Usuaris WHERE deleted = 0 AND email = ? AND contrasenya = ? LIMIT 1";
            Cursor cursor = db.rawQuery(sql, new String[]{email, password});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                usuari = this.getFromCursor(cursor);
                Log.d("Autentica", "Usuari " + usuari.nom + " Contra " + usuari.contrasenya);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return usuari;
    }


    /**
     * * @return null if user is not correct
     */
    public Usuari getAutoLoginUser(){
        SQLiteDatabase db = super.getReadableDatabase();
        Usuari usuari = null;

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM AutoLogin LIMIT 1";
            Cursor cursor = db.rawQuery(sql, new String[]{});

            cursor.moveToFirst();

            if(cursor.isAfterLast() == false) {
                int idUsuari = this.getAutoLoginFromCursor(cursor);
                usuari = this.selectID(idUsuari).get(0);
            }

            cursor.close();
            db.close();
        }
        return usuari;
    }

    public void delete(int id){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="UPDATE Usuaris SET deleted = 1 WHERE id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{((Integer)id).toString()
            });
            statement.executeInsert();
            statement.close();
        }
    }

    public void update(Usuari usuari){
        SQLiteDatabase db = super.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql="UPDATE Usuaris SET nom = ?, edat = ?, email = ?, contrasenya = ?, genere = ? WHERE id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindAllArgsAsStrings(new
                    String[]{
                    usuari.nom,
                    ((Integer)usuari.edat).toString(),
                    usuari.email,
                    usuari.contrasenya,
                    ((Integer)usuari.genere).toString(),
                    ((Integer)usuari.id).toString()
            });
            statement.executeInsert();
            statement.close();
        }
    }

    public ArrayList<Historic> selectAllHistoric(int idUser){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<Historic> elements = new ArrayList<Historic>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM Historic WHERE id_usuari = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{Integer.toString(idUser)});

            cursor.moveToFirst();
            DAOHistoric bbdd = new DAOHistoric(this.context);
            while (cursor.isAfterLast() == false) {
                Historic element = bbdd.getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }
    
}
