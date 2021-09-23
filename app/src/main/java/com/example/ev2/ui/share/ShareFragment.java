package com.example.ev2.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.SQL.SQLite;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    Button eliminar;
    EditText curp;
    SQLite sqlite;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        eliminar=root.findViewById(R.id.btn_eliminarcurp);
        curp=root.findViewById(R.id.edit_curp_eliminar);
        sqlite = new SQLite(getContext());
        sqlite.abrir();
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int cant=sqlite.Eliminar(curp.getText().toString());
                    if (cant== 1) {
                        Toast.makeText(getContext(), "Eliminado Correctamente"+cant, Toast.LENGTH_SHORT).show();
                        curp.setText("");
                    } else {
                        Toast.makeText(getContext(), "No se pudo eliminar"+cant, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex)
                {
                    Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        return root;
    }
}