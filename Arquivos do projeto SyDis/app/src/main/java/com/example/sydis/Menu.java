package com.example.sydis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity {

    Handler handler;

    private LinearLayout BTNADD;
    private LinearLayout BTNLista;
    private LinearLayout BTNPerfil;
    private LinearLayout BTNSobre;
    private LinearLayout BTNSair;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private boolean cliqueDuplo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        BTNADD = findViewById(R.id.BTNADD);
        BTNLista = findViewById(R.id.BTNLista);
        BTNPerfil = findViewById(R.id.BTNPerfil);
        BTNSobre = findViewById(R.id.BTNSobre);
        BTNSair = findViewById(R.id.BTNSair);

        BTNADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdicionarOcorrencia.class));
            }

        });

        BTNLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), VisualizarOcorrencia.class));
            }

        });

        BTNPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PerfilUser.class));
            }

        });

        BTNSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Sobre.class));
            }
        });

        BTNSair.setOnClickListener(new View.OnClickListener() {
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
        } else {
            mAuth.signOut();
            startActivity(new Intent(this, FazerLogin.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (cliqueDuplo) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), FazerLogin.class));
        }
        cliqueDuplo = true;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cliqueDuplo = false;
            }
        }, 3000);
        Toast.makeText(getApplicationContext(), "Pressione 2 vezes para Logout !", Toast.LENGTH_SHORT).show();
    }
}