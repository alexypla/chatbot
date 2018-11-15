package org.ieszaidinvergeles.dam.chaterbot;

import android.content.ContentValues;

public class Utilidades {

    public static ContentValues contentValues(Contacto contacto){
        ContentValues cv = new ContentValues();

        //cv.put(Contrato.TablaContactos._ID, contacto.getId());
        cv.put(Contrato.TablaContactos.COLUMN_NOMBRE, contacto.getNombre());
        cv.put(Contrato.TablaContactos.COLUMN_MESSAGE, contacto.getMessage());



        return cv;
    }

}