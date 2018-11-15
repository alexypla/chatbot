package org.ieszaidinvergeles.dam.chaterbot;

import android.provider.BaseColumns;

public class Contrato {


    public static final String SQL_CREATE_ENTRIES=
            "create table " + TablaContactos.TABLE_NAME + " (" +
                    TablaContactos._ID + " integer primary key autoincrement, " +
                    TablaContactos.COLUMN_NOMBRE + " text not null, " +
                    TablaContactos.COLUMN_MESSAGE + " text" +")";

    private static final String SQL_DELETE_ENTRIES =
            "drop table if exists " + TablaContactos.TABLE_NAME;

    private Contrato(){}

    public static class TablaContactos implements BaseColumns {
        public static final String TABLE_NAME = "contactos";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_MESSAGE = "message";

    }

    public static String getCreateTablaContactos(){
        return SQL_CREATE_ENTRIES;
    }

    public static String getRemoveTablaContactos(){
        return SQL_DELETE_ENTRIES;
    }
}