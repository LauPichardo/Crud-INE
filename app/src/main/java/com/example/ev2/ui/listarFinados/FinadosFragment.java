package com.example.ev2.ui.listarFinados;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.SQL.SQLite;

import java.io.File;
import java.util.ArrayList;

public class FinadosFragment extends Fragment {

    private finadosViewModel finadosViewModel;
    ArrayList<String> reg;
    ArrayList<String> imagenes;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        finadosViewModel =
                ViewModelProviders.of(this).get(finadosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finados, container, false);

        finadosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        ListView l= root.findViewById(R.id.lista) ;
        SQLite sqlite;

        //base de datos
        sqlite = new SQLite(getContext());
        sqlite.abrir();
        Cursor cursor= sqlite.getRegistroFinados();
        reg= sqlite.getIFE(cursor);
        imagenes = sqlite.getImagenes(cursor);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,reg);
        l.setAdapter(adaptador);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ife,null);
                ((TextView)dialogView.findViewById(R.id.dialogIFETvDatos)).setText(reg.get(i));
                ImageView iVImagen=dialogView.findViewById(R.id.dialogIFEIVFoto);
                cargarImagen(imagenes.get(i),iVImagen);
                AlertDialog.Builder dialogo=new AlertDialog.Builder(getContext());
                dialogo.setTitle("Los finados");
                dialogo.setView(dialogView);
                View.OnClickListener vista;
                dialogo.setPositiveButton("Aceptar",null);
                dialogo.show();
            }
        });
        return root;
    }

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
