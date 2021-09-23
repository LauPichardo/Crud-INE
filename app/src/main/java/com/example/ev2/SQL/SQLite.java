package com.example.ev2.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;

public class SQLite {

    private sql sql;
    private SQLiteDatabase db;

    public SQLite(Context context) {
        sql = new sql(context);
    }

    public void abrir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Log.i("SQLite",
                    "Se abre conexión a la base de datos" +
                            sql.getDatabaseName());
        }
        db = sql.getWritableDatabase();
    }

    public void cerrar() {
        Log.i("SQLite",
                "Se cierra conexión a la base de datos" +
                        sql.getDatabaseName());
        sql.close();
    }

    public boolean addregistroPaciente(
            String nom,
            String ap,
            String am,
            String calle,
            int numext,
            int numint,
            String colonia,
            int cp,
            String fechanacimiento,
            String sexo,
            String claveelector,
            String curp,
            int anoregistro,
            int nversion,
            String estado,
            String municipio,
            int seccion,
            String localidad,
            int anoemision,
            int vigencia,
            String image) {
        ContentValues cv = new ContentValues(); //Equivalente a putExtra
        cv.put("NOMBRE",nom);
        cv.put("AP",ap);
        cv.put("AM", am);
        cv.put("CALLE",calle);
        cv.put("NUMEXT",numext);
        cv.put("NUMINT", numint);
        cv.put("COLONIA", colonia);
        cv.put("CP",cp);
        cv.put("FECHANACIMIENTO",fechanacimiento);
        cv.put("SEXO", sexo);
        cv.put("CLAVEELECTOR", claveelector);
        cv.put("CURP", curp);
        cv.put("ANOREGISTRO",anoregistro);
        cv.put("NVERSION", nversion);
        cv.put("ESTADO", estado);
        cv.put("MUNICIPIO", municipio);
        cv.put("SECCION", seccion);
        cv.put("LOCALIDAD", localidad);
        cv.put("ANOEMISION",anoemision);
        cv.put("VIGENCIA", vigencia);
        cv.put("IMAGEN", image);
        cv.put("STATUS","SI");
        return (db.insert(
                "IFE",
                null, cv) != -1) ? true : false;
    }

    //Leer base de datos
    public Cursor getRegistro() {
        return db.rawQuery("SELECT * FROM IFE", null);
    }
    public Cursor getRegistroActivos() {
        return db.rawQuery("SELECT * FROM IFE WHERE STATUS='SI'", null);
    }
    public Cursor getRegistroFinados() {
        return db.rawQuery("SELECT * FROM IFE WHERE STATUS='NO'", null);
    }


    public ArrayList<String> getIFE(Cursor cursor) {
        ArrayList<String> listData = new ArrayList<>();
        String item = "";
        if (cursor.moveToFirst()) {
            do {
                item += "Nombre: [" + cursor.getString(0) + "]\r\n";
                item += "Apellido Paterno: [" + cursor.getString(1) + "]\r\n";
                item += "Apellido Materno: [" + cursor.getString(2) + "]\r\n";
                item += "Calle: [" + cursor.getString(3) + "]\r\n";
                item += "Número Exterior: [" + cursor.getString(4) + "]\r\n";
                item += "Número Interior: [" + cursor.getString(5) + "]\r\n";
                item += "Colonia: [" + cursor.getString(6) + "]\r\n";
                item += "Código Postal: [" + cursor.getString(7) + "]\r\n";
                item += "Fecha de Nacimiento: [" + cursor.getString(8) + "]\r\n";
                item += "Sexo: [" + cursor.getString(9) + "]\r\n";
                item += "Clave Elector: [" + cursor.getString(10) + "]\r\n";
                item += "CURP: [" + cursor.getString(11) + "]\r\n";
                item += "Año de registro: [" + cursor.getString(12) + "]\r\n";
                item += "Número de versión: [" + cursor.getString(13) + "]\r\n";
                item += "Estado: [" + cursor.getString(14) + "]\r\n";
                item += "Municipio: [" + cursor.getString(15) + "]\r\n";
                item += "Seccion: [" + cursor.getString(16) + "]\r\n";
                item += "Localidad: [" + cursor.getString(17) + "]\r\n";
                item += "Año de emisión: [" + cursor.getString(18) + "]\r\n";
                item += "Vigencia: [" + cursor.getString(19) + "]\r\n";
                listData.add(item); //LO AGREGAMOS AL ARRAYLIST
                item = ""; //LIMPIAMOS LA CADENA ITEM
            } while (cursor.moveToNext()); //MIENTRAS LA CONSULTA TENGA DATOS
        }
        return listData;
    }

    public ArrayList<String> getImagenes(Cursor cursor){
        ArrayList<String> listData = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                listData.add(cursor.getString(20));
            }while (cursor.moveToNext());
        }
        return listData;
    }

    public ArrayList<String> getID(Cursor cursor) {
        ArrayList<String> listData = new ArrayList<>();
        String item = "";
        if (cursor.moveToFirst()) {
            do {
                item += "ID: [" + cursor.getString(0) + "]\r\n";
                listData.add(item);
                item = "";
            } while (cursor.moveToNext());
        }
        return listData;
    }

    public int updatePaciente(
            String nom,
            String ap,
            String am,
            String calle,
            int numext,
            int numint,
            String colonia,
            int cp,
            String fechanacimiento,
            String sexo,
            String claveelector,
            String curpnuevo,
            String curpviejo,
            int anoregistro,
            int nversion,
            String estado,
            String municipio,
            int seccion,
            String localidad,
            int anoemision,
            int vigencia,
            String image
    ) {
        ContentValues cv = new ContentValues(); //Equivalente a putExtra
        cv.put("NOMBRE",nom);
        cv.put("AP",ap);
        cv.put("AM", am);
        cv.put("CALLE",calle);
        cv.put("NUMEXT",numext);
        cv.put("NUMINT", numint);
        cv.put("COLONIA", colonia);
        cv.put("CP",cp);
        cv.put("FECHANACIMIENTO",fechanacimiento);
        cv.put("SEXO", sexo);
        cv.put("CLAVEELECTOR", claveelector);
        cv.put("CURP", curpnuevo);
        cv.put("ANOREGISTRO",anoregistro);
        cv.put("NVERSION", nversion);
        cv.put("ESTADO", estado);
        cv.put("MUNICIPIO", municipio);
        cv.put("SECCION", seccion);
        cv.put("LOCALIDAD", localidad);
        cv.put("ANOEMISION",anoemision);
        cv.put("VIGENCIA", vigencia);
        cv.put("IMAGEN", image);
        cv.put("STATUS","SI");
        int cant = db.update(
                "IFE",
                cv,
                "CURP='" + curpviejo+"'",
                null);
        return cant;
    }

    public Cursor getCant(String curp) {
        return db.rawQuery(
                "SELECT * FROM IFE WHERE CURP= '" + curp +"'",
                null);
    }

    public Cursor getNombre(String nombre, String ap, String am) {
        return db.rawQuery(
                "SELECT * FROM IFE WHERE NOMBRE= '" + nombre +"' AND AP= '" + ap +"' AND AM= '" + am +"'",
                null);
    }

    public int Eliminar(String curp) {
        ContentValues cv = new ContentValues(); //Equivalente a putExtra
        cv.put("STATUS","NO");

        return db.update(
                "IFE",cv,
                "CURP='"+ curp+"'",
                null);
    }

    public Cursor getMunicipio(String municipio) {
        return db.rawQuery(
                "SELECT * FROM IFE WHERE MUNICIPIO= '" + municipio+"'",
                null);
    }
}
