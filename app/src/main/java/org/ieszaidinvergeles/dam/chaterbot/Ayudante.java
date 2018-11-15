package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class Ayudante extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contactos.db";
    private static final String LOG = "ZZT";

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(LOG,"constructor ayudante");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.v(LOG,"onCreaate BD");
        db.execSQL(Contrato.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion == 1 && newVersion == 2){
            makeMigration_1_2(db);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2){
            makeMigration_1_2(db);
        }
    }

    private void makeMigration_1_2(SQLiteDatabase db){
        // Cambiar el nombre de la tabla a antigua
        String sql = "ALTER TABLE "+ Contrato.TablaContactos.TABLE_NAME +" RENAME TO oldContactos";
        db.execSQL(sql);

        // Se crea la nueva tabla con la nueva versión
        onCreate(db);

        // Guardar los datos de una tabla a la otra
        sql = "INSERT INTO " + Contrato.TablaContactos.TABLE_NAME + " (" +
                Contrato.TablaContactos._ID+"," +
                Contrato.TablaContactos.COLUMN_NOMBRE+"," +
                Contrato.TablaContactos.COLUMN_MESSAGE+")" +
                " SELECT "+
                Contrato.TablaContactos._ID+"," +
                Contrato.TablaContactos.COLUMN_NOMBRE+"," +
                Contrato.TablaContactos.COLUMN_MESSAGE+
                " FROM oldContactos";
        db.execSQL(sql);

        // Borrar la tabla antigua
        sql = "DROP TABLE oldContactos";
        db.execSQL(sql);

    }



}
