package com.example.ev2.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql extends SQLiteOpenHelper {
    private static final String database = "pacientes";
    private static final int VERSION = 3;

    private final String tIFES = "CREATE TABLE IFE (" +
            "NOMBRE TEXT NOT NULL," +
            "AP TEXT NOT NULL," +
            "AM TEXT NOT NULL," +
            "CALLE TEXT NOT NULL," +
            "NUMEXT INTEGER NOT NULL," +
            "NUMINT INTEGER NOT NULL," +
            "COLONIA TEXT NOT NULL," +
            "CP INTEGER NOT NULL," +
            "FECHANACIMIENTO TEXT NOT NULL," +
            "SEXO TEXT NOT NULL," +
            "CLAVEELECTOR TEXT NOT NULL," +
            "CURP TEXT NOT NULL," +
            "ANOREGISTRO INTEGER NOT NULL," +
            "NVERSION INTEGER NOT NULL," +
            "ESTADO TEXT NOT NULL," +
            "MUNICIPIO TEXT NOT NULL," +
            "SECCION INTEGER NOT NULL," +
            "LOCALIDAD TEXT NOT NULL," +
            "ANOEMISION INTEGER NOT NULL," +
            "VIGENCIA INTEGER NOT NULL," +
            "IMAGEN TEXT NOT NULL," +
            "STATUS TEXT NOT NULL);";


    //Constructor
    public sql(Context context){
        super(context, database, null, VERSION);
    }

    //metodos heredados
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tIFES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS IFE");
            db.execSQL(tIFES);
        }
    }
}
