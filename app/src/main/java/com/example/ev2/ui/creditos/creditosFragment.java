package com.example.ev2.ui.creditos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.ui.home.HomeViewModel;

public class creditosFragment extends Fragment {

    private creditosViewModel CreditosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreditosViewModel =
                ViewModelProviders.of(this).get(creditosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_creditos, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        CreditosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //  textView.setText(s);
            }
        });
        return root;
    }

}
