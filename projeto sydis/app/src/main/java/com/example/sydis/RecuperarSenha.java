package com.example.sydis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecuperarSenha extends AppCompatActivity {

    private EditText inputEmail3;

    private Button BTNRecuperarSenha;
    private Button BTNFazerLogin3;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        inputEmail3 = findViewById(R.id.inputEmail3);

        BTNRecuperarSenha = findViewById(R.id.BTNRecuperarSenha);
        BTNFazerLogin3 = findViewById(R.id.BTNFazerLogin3);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        BTNRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarSenhaMetodo(inputEmail3.getText().toString().trim());
            }
        });

        BTNFazerLogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FazerLogin.class));
            }
        });
    }

    private void recuperarSenhaMetodo(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Confira sua caixa de mensagem no seu e-mail para alterar a senha.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}