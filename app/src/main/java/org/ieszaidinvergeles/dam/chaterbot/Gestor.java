package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Gestor {
    private static final String LOG = "ZZT";
    private Ayudante ayudante;
    private SQLiteDatabase bd;

    public Gestor(Context context){
        this(context,true);
    }

    public Gestor(Context context, boolean write) {
        Log.v(LOG,"constructor gestor");
        this.ayudante = new Ayudante(context);
        if(write){
            bd = ayudante.getWritableDatabase();
        }else{
            bd = ayudante.getReadableDatabase();
        }
    }

    public void close(){
        ayudante.close();
    }

    public long insert(Contacto contacto){
        Log.v(LOG,"insert usuario");
        return bd.insert(Contrato.TablaContactos.TABLE_NAME, null, Utilidades.contentValues(contacto));
    }

    public int delete(Contacto contacto){
        /*String condicion = Contrato.TablaContactos._ID + " = ?";
        String[] argumentos = { contacto.getId() + "" };
        return bd.delete(Contrato.TablaContactos.TABLE_NAME, condicion, argumentos);*/
        return delete(contacto.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaContactos._ID + " = ?";
        String[] argumentos = { id + "" };
        return bd.delete(Contrato.TablaContactos.TABLE_NAME, condicion, argumentos);
    }

    public int update(Contacto contacto) {
        String condicion = Contrato.TablaContactos._ID + " = ?";
        String[] argumentos = { contacto.getId() + "" };
        return bd.update(Contrato.TablaContactos.TABLE_NAME, Utilidades.contentValues(contacto), condicion, argumentos);
    }

    // Hace un select
    private Cursor getCursor(){
        Cursor cursor = bd.query(
                Contrato.TablaContactos.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contrato.TablaContactos.COLUMN_NOMBRE + " desc",
                null
        );
        return cursor;
    }

    private Cursor getCursor(String condicion, String[] argumentos){
        Cursor cursor = bd.query(
                Contrato.TablaContactos.TABLE_NAME,
                null,
                condicion,
                argumentos,
                null,
                null,
                Contrato.TablaContactos.COLUMN_NOMBRE + " desc",
                null
        );
        return cursor;
    }

    private Contacto getRow(Cursor cursor){
        Contacto contacto = new Contacto();
        int posicionColumna = cursor.getColumnIndex(Contrato.TablaContactos._ID);
        int posicionNombre = cursor.getColumnIndex(Contrato.TablaContactos.COLUMN_NOMBRE);
        int posicionMensaje = cursor.getColumnIndex(Contrato.TablaContactos.COLUMN_MESSAGE);

        contacto.setId(cursor.getLong(posicionColumna));
        contacto.setNombre(cursor.getString(posicionNombre));
        contacto.setMessage(cursor.getString(posicionMensaje));

        return contacto;
    }

    public List<Contacto> getAll(){
        List<Contacto> contactos = new ArrayList<>();
        Cursor cursor = getCursor();
        Contacto contacto;

        while(cursor.moveToNext()){
            contacto = getRow(cursor);
            contactos.add(contacto);
        }
        return contactos;
    }

    public List<Contacto> getAll(String condicion){
        List<Contacto> contactos = new ArrayList<>();
        Cursor cursor = getCursor(condicion, null);
        Contacto contacto;

        while(cursor.moveToNext()){
            contacto = getRow(cursor);
            contactos.add(contacto);
        }
        return contactos;
    }

    public Contacto getContacto(long id){
        List<Contacto> contactos = getAll();
        Contacto contacto = null;

        for(Contacto c: contactos){
            if(c.getId() == id){
                contacto = c;
                break;
            }
        }

        return contacto;
    }

}

