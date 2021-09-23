package com.example.ev2.ui.slideshow;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.SQL.SQLite;

import java.io.File;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {
    EditText curp, nombre, ap, am, municipio;
    Button btnconsultaxcurp, btnconsultaxnombre, btnconsultapormunicipio;
    String g0,g1, g2, g3, g4,g5,g6,g7,g8,g9,g10,g11,g12,g13,g14,g15,g16,g17,g18,g19,g20,g21;
    public SQLite sqlite;
    ArrayList<String> reg;
    ArrayList<String> imagenes;



    private SlideshowViewModel slideshowViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        curp = root.findViewById(R.id.consultaporcurp);
        btnconsultaxcurp = root.findViewById(R.id.btn_consultaxcurp);
        nombre = root.findViewById(R.id.consultapornombre);
        ap = root.findViewById(R.id.consultaporapellidopaterno);
        am = root.findViewById(R.id.consultaporapellidomaterno);
        btnconsultaxnombre = root.findViewById(R.id.btn_consultaxnombre);
        municipio = root.findViewById(R.id.consultapormunicipio);
        btnconsultapormunicipio = root.findViewById(R.id.btn_consultaxmunicipio);
        final ListView l= (ListView) root.findViewById(R.id.lista) ;

        sqlite = new SQLite(getContext());
        sqlite.abrir();

        btnconsultaxcurp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!curp.getText().toString().equals(""))
                {
                    String cur = curp.getText().toString();
                    Cursor c = sqlite.getCant(cur); //14
                    int count = c.getCount();
                    if(count== 1)
                    {
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                        if(!curp.getText().toString().equals(""))
                        {
                            if(sqlite.getCant(curp.getText().toString()).getCount()==1)
                            {
                                Cursor cursor = sqlite.getCant(curp.getText().toString());
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        obtenerdatos(cursor);
                                    }while (cursor.moveToNext());
                                }
                            }
                        }
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ife, null);
                        ((TextView)dialogView.findViewById(R.id.dialogIFETvDatos)).setText(" Datos \n"
                                +"Nombre: "+"\n" +g0 +"\n"
                                +"Apellido Paterno: "+ "\n"+ g1 +"\n"
                                +"Apellido Materno: "+ "\n"+ g2 +"\n"
                                +"Calle: "+ "\n"+ g3 +"\n"
                                +"Num Ext: "+ g4 +"\n"
                                +"Num Int: "+ g5 +"\n"
                                +"Colonia: "+ "\n"+g6 +"\n"
                                +"CP: "+ g7 +"\n"
                                +"Fecha Nacimiento: "+ "\n"+g8 +"\n"
                                +"Sexo: "+ g9 +"\n"
                                +"Clave elector: "+ "\n"+g10 +"\n"
                                +"CURP: "+ "\n"+g11 +"\n"//64
                                +"Año de registro: "+ g12 +"\n"
                                +"Numero de versión: "+ g13 +"\n"
                                +"Estado: "+ "\n"+g14 +"\n"
                                +"Municipio: "+ "\n"+g15 +"\n"
                                +"Seccion: "+ "\n"+g16 +"\n"
                                +"Localidad: "+ "\n"+g17 +"\n"
                                +"Año de emisión: "+ "\n"+g18 +"\n"
                                +"Vigencia: "+g19 +"\n");
                        ImageView image = dialogView.findViewById(R.id.dialogIFEIVFoto);
                        cargarImagen(g20, image);
                        dialogo1.setTitle("Importante");
                        dialogo1.setView(dialogView);
                        dialogo1.show();
                    }
                }else
                {
                    Toast.makeText(getContext(),"Debe ingresar una curp", Toast.LENGTH_LONG);
                }
            }
        });

        btnconsultaxnombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().equals("") && !am.getText().toString().equals("") && !ap.getText().toString().equals(""))
                {
                    String n =nombre.getText().toString(), apma = am.getText().toString(), appa = ap.getText().toString();
                    Cursor c = sqlite.getNombre(n, appa,apma);
                    int count = c.getCount();
                    if(count== 1)
                    {
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                        if(!nombre.getText().toString().equals("") && !am.getText().toString().equals("") && !ap.getText().toString().equals(""))
                        {
                            if(sqlite.getNombre(n, appa,apma).getCount()==1)
                            {
                                Cursor cursor = sqlite.getNombre(n, appa,apma);
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        obtenerdatos(cursor);
                                    }while (cursor.moveToNext());
                                }
                            }
                        }
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ife, null);
                        ((TextView)dialogView.findViewById(R.id.dialogIFETvDatos)).setText(" Datos \n"
                                +"Nombre: "+"\n" +g0 +"\n"
                                +"Apellido Paterno: "+ "\n"+ g1 +"\n"
                                +"Apellido Materno: "+ "\n"+ g2 +"\n"
                                +"Calle: "+ "\n"+ g3 +"\n"
                                +"Num Ext: "+ g4 +"\n"
                                +"Num Int: "+ g5 +"\n"
                                +"Colonia: "+ "\n"+g6 +"\n"
                                +"CP: "+ g7 +"\n"
                                +"Fecha Nacimiento: "+ "\n"+g8 +"\n"
                                +"Sexo: "+ g9 +"\n"
                                +"Clave elector: "+ "\n"+g10 +"\n"
                                +"CURP: "+ "\n"+g11 +"\n"//64
                                +"Año de registro: "+ g12 +"\n"
                                +"Numero de versión: "+ g13 +"\n"
                                +"Estado: "+ "\n"+g14 +"\n"
                                +"Municipio: "+ "\n"+g15 +"\n"
                                +"Seccion: "+ "\n"+g16 +"\n"
                                +"Localidad: "+ "\n"+g17 +"\n"
                                +"Año de emisión: "+ "\n"+g18 +"\n"
                                +"Vigencia: "+g19 +"\n");
                        ImageView image = dialogView.findViewById(R.id.dialogIFEIVFoto);
                        cargarImagen(g20, image);
                        dialogo1.setTitle("Importante");
                        dialogo1.setView(dialogView);
                        dialogo1.show();
                    }else
                    {
                        Toast.makeText(getContext(), "No se encontro registro con ese nombre", Toast.LENGTH_LONG);
                    }

                }else
                {
                    Toast.makeText(getContext(),"Debe llenar todos los campos", Toast.LENGTH_LONG);
                }
            }
        });
        btnconsultapormunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!municipio.getText().toString().equals(""))
                {
                    String m = municipio.getText().toString();
                    Cursor cursor = sqlite.getMunicipio(m);
                    reg = sqlite.getIFE(cursor);
                    imagenes= sqlite.getImagenes(cursor);
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,reg);
                    l.setAdapter(adaptador);
                    l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ife,null);
                            ((TextView)dialogView.findViewById(R.id.dialogIFETvDatos)).setText(reg.get(i));
                            ImageView iVImagen=dialogView.findViewById(R.id.dialogIFEIVFoto);
                            cargarImagen(imagenes.get(i),iVImagen);
                            android.app.AlertDialog.Builder dialogo=new android.app.AlertDialog.Builder(getContext());
                            dialogo.setTitle("IFE");
                            dialogo.setView(dialogView);
                            dialogo.setPositiveButton("Aceptar",null);
                            dialogo.show();
                        }
                    });

                }
                else
                {
                    Toast.makeText(getContext(),"Ingrese un municipio", Toast.LENGTH_LONG);
                }
            }
        });



        return root;
    }

    public void obtenerdatos(Cursor cursor)
    {
        g0 = cursor.getString(0); //nombre
        g1 = cursor.getString(1); // ap
        g2 = cursor.getString(2); // am
        g3 = cursor.getString(3); //calle
        g4 = Integer.toString(cursor.getInt(4)); //numext
        g5 = Integer.toString(cursor.getInt(5)); //numint
        g6 = cursor.getString(6); //colonia
        g7 = Integer.toString(cursor.getInt(7)); //cp
        g8 = cursor.getString(8); //fecha de nacimiento
        g9 = cursor.getString(9); //sexo
        g10 = cursor.getString(10); //claveelector
        g11 = cursor.getString(11); //curp
        g12 = Integer.toString(cursor.getInt(12)); //anoregistro
        g13 = Integer.toString(cursor.getInt(13)); //nversion
        g14 = cursor.getString(14); //estado
        g15 = cursor.getString(15); // municipio
        g16 = Integer.toString(cursor.getInt(16)); // seccion
        g17 = cursor.getString(17); // localidad
        g18 = Integer.toString(cursor.getInt(18)); //anoemision
        g19 = Integer.toString(cursor.getInt(19)); //vigencia
        g20 = cursor.getString(20); // imagen
    }

    //cargar imagen
    public void cargarImagen(String imagen, ImageView iv){
        try{
            File filePhoto=new File(imagen);
            Uri uriPhoto = FileProvider.getUriForFile(getContext(),"com.example.ev2",filePhoto);
            iv.setImageURI(uriPhoto);
        }catch (Exception ex){
            Toast.makeText(getContext(), "Ocurrio un error al cargar la imagen", Toast.LENGTH_SHORT).show();
            Log.d("Cargar Imagen","Error al cargar imagen: "+imagen+"\nMensaje: "+ex.getMessage()+"\nCausa: "+ex.getCause());
        }
    }
}