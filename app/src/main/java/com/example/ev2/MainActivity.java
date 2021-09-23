package com.example.ev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView nombre, contraseña;
        Button BtnAceptar, BtnCancelar;
        nombre = findViewById(R.id.altaNombre);
        contraseña = findViewById(R.id.LoginPass);
        BtnAceptar = findViewById(R.id.BtnAceptarMain);
        BtnCancelar = findViewById(R.id.BtnCancelarMain);
        BtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=  Toast.makeText(getApplicationContext(), "Ádios", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

        });
        BtnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if ((nombre.getText().toString().equals("DESMovil") && contraseña.getText().toString().equals("titulo2020A"))){
                Intent intent = new Intent(v.getContext(), BarraNavegacion.class);
                //intent.putExtra("TipoUsuario", "Administrador");
                startActivity(intent);
                //}else if((nombre.getText().toString().equals("desmovil") && contraseña.getText().toString().equals("comple2020A"))){
                //  Intent intent = new Intent(v.getContext(), BarraNavegacion.class);
                //intent.putExtra("TipoUsuario", "Consultor");
                //startActivity(intent);
                //}else{
                //   Toast toast= Toast.makeText(MainActivity.this, "Usuario inválido", Toast.LENGTH_SHORT);
                //  toast.show();
                //}
            }
        });
    }

    public void aceptar(View v){

        Intent inte= new Intent(this, BarraNavegacion.class);
    }


}
