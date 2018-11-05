package com.enric.androshoping.BBDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by home on 17/03/15.
 */
public abstract class DAOBase<T> extends SQLiteOpenHelper {

    private final String SQL_CREATE_PRODUCTES = "CREATE TABLE Productes ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "id_remot INTEGER, " +
                                                                            "nombre VARCHAR(255), " +
                                                                            "descripcion VARCHAR(512), " +
                                                                            "precio REAL, " +
                                                                            "activo INTEGER, " +
                                                                            "stock INTEGER, " +
                                                                            "data INTEGER, " +
                                                                            "foto VARCHAR(512), " +
                                                                            "deleted INTEGER" +
                                                                       ")";

    private final String SQL_CREATE_HISTORIC = "CREATE TABLE Historic ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                            "id_usuari INTEGER," +
                                                                            "nombre VARCHAR(255), " +
                                                                            "precio REAL, " +
                                                                            "quantitat INTEGER, " +
                                                                            "data INTEGER," +
                                                                            "deleted INTEGER," +
                                                                            "FOREIGN KEY (id_usuari) REFERENCES Usuaris(id)" +
                                                                        ")";

    private final String SQL_CREATE_USUARIS = "CREATE TABLE Usuaris ( " +
                                                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                        "nom VARCHAR(255), " +
                                                                        "edat INTEGER, " +
                                                                        "email VARCHAR(300) UNIQUE, " +
                                                                        "contrasenya VARCHAR(200), " +
                                                                        "genere INTEGER, " +
                                                                        "rol VARCHAR(200), " +
                                                                        "deleted INTEGER" +
                                                                   ")";
    private final String SQL_INSERT_USUARIS = "INSERT OR IGNORE INTO Usuaris (nom, edat, email, contrasenya, genere, rol, deleted) " +
                                              "VALUES ( 'Enric', 20, 'admin@salleurl.edu', 'qwerty123', 1, 'admin', 0)," +
                                                     "( 'Balbina', 20, 'user@salleurl.edu', 'qwerty123', 2, 'user', 0)";

    private final String SQL_CREATE_TAGS = "CREATE TABLE Tags ( " +
                                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                "nom VARCHAR(255),"+
                                                                "deleted INTEGER" +
                                                            ")";

    private final String SQL_CREATE_REL_PROD_TAG = "CREATE TABLE RelTagProd (" +
                                                                                "id_prod INTEGER ," +
                                                                                "id_tag INTEGER ," +
                                                                                "deleted INTEGER," +
                                                                                "FOREIGN KEY (id_prod) REFERENCES Productes(id)," +
                                                                                "FOREIGN KEY (id_tag) REFERENCES Tags(id)" +
                                                                            ")";

    private final String SQL_CREATE_AUTO_LOGIN = "CREATE TABLE AutoLogin (" +
                                                                            "id_usuari INTEGER," +
                                                                            "deleted INTEGER," +
                                                                            "FOREIGN KEY (id_usuari) REFERENCES Usuaris(id)" +
                                                                        ")";

    private final String SQL_CREATE_UPDATE = "CREATE TABLE Actualitza (" +
            "deleted INTEGER," +
            "dia INTEGER" +
            ")";



    private static final String dbName = "appDB.db";
    private static final int VERSION = 6;
    private String taula;
    
    

    public DAOBase(Context contexto, String taula) {
        super(contexto, dbName, null, VERSION);
        this.taula = taula;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(SQL_CREATE_PRODUCTES);
        db.execSQL(SQL_CREATE_HISTORIC);

        db.execSQL(SQL_CREATE_USUARIS);
        db.execSQL(SQL_INSERT_USUARIS);
        db.execSQL(SQL_CREATE_TAGS);
        db.execSQL(SQL_CREATE_REL_PROD_TAG);
        db.execSQL(SQL_CREATE_AUTO_LOGIN);
        db.execSQL(SQL_CREATE_UPDATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS Productes");
        db.execSQL("DROP TABLE IF EXISTS Usuaris");
        db.execSQL("DROP TABLE IF EXISTS Tags");
        db.execSQL("DROP TABLE IF EXISTS RelTagProd");
        db.execSQL("DROP TABLE IF EXISTS AutoLogin");
        db.execSQL("DROP TABLE IF EXISTS Historic");
        db.execSQL("DROP TABLE IF EXISTS Update");

        db.execSQL(SQL_CREATE_PRODUCTES);
        db.execSQL(SQL_CREATE_HISTORIC);
        db.execSQL(SQL_CREATE_USUARIS);
        db.execSQL(SQL_INSERT_USUARIS);
        db.execSQL(SQL_CREATE_TAGS);
        db.execSQL(SQL_CREATE_REL_PROD_TAG);
        db.execSQL(SQL_CREATE_AUTO_LOGIN);
        db.execSQL(SQL_CREATE_UPDATE);
    }
    
    public ArrayList<T> selectAll(){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<T> elements = new ArrayList<T>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM " + taula + " WHERE deleted = 0";
            Cursor cursor = db.rawQuery(sql, new String[]{});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                T element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }

    public ArrayList<T> selectID(int id){
        SQLiteDatabase db = super.getReadableDatabase();
        ArrayList<T> elements = new ArrayList<T>();

        //Si hemos abierto correctamente la base de datos
        if(db != null){

            String sql = "SELECT * FROM " + taula + " WHERE id = " +  id + " AND deleted = 0";
            Cursor cursor = db.rawQuery(sql, new String[]{});

            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                T element = getFromCursor(cursor);
                elements.add(element);
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return elements;
    }
    
    public abstract T getFromCursor(Cursor c);
    
}
