package com.example.ev2.ui.salir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.ui.send.SendViewModel;

import java.util.ArrayList;

public class salirFragment extends Fragment {

    private salirViewModel salirViewModel;

    ImageButton salir;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salirViewModel =
                ViewModelProviders.of(this).get(salirViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salir, container, false);

        salirViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        salir= root.findViewById(R.id.imageButton);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(Intent.ACTION_MAIN);
                inte.addCategory(Intent.CATEGORY_HOME);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inte);
            }
        });

        return root;
    }
}
