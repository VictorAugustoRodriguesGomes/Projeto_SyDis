package com.example.sydis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilUser extends AppCompatActivity {
    private String namePU;
    private String emailPU;

    private TextView textViewNomePerfil;
    private TextView textViewEmailPerfil;

    private Button BTNSairPerfil;
    private Button BTNPerfilVoltar;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        textViewNomePerfil = findViewById(R.id.textViewNomePerfil);
        textViewEmailPerfil = findViewById(R.id.textViewEmailPerfil);

        BTNSairPerfil = findViewById(R.id.BTNSairPerfil);
        BTNPerfilVoltar = findViewById(R.id.BTNPerfilVoltar);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        BTNPerfilVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
            }
        });

        BTNSairPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), FazerLogin.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            namePU = user.getDisplayName();
            emailPU = user.getEmail();
            textViewNomePerfil.setText(namePU);
            textViewEmailPerfil.setText(emailPU);
        } else {
            mAuth.signOut();
            startActivity(new Intent(this, FazerLogin.class));
        }
    }
}