package com.example.sydis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FazerLogin extends AppCompatActivity {

    private Button BTNTelaCiarConta;
    private Button BTNRealizarLogin;

    private EditText inputEmail;
    private EditText inputSenha;

    private TextView esqueceuSenha;

    private String userEmail;
    private String userSenha;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private boolean cliqueDuplo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_login);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        BTNTelaCiarConta = findViewById(R.id.BTNTelaCiarConta);
        BTNRealizarLogin = findViewById(R.id.BTNRealizarLogin);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);

        esqueceuSenha = findViewById(R.id.esqueceuSenha);

        BTNRealizarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = inputEmail.getText().toString().trim();
                userSenha = inputSenha.getText().toString().trim();

                if (userEmail.isEmpty() || userSenha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos !", Toast.LENGTH_SHORT).show();
                } else {
                    if (userSenha.length() < 8 || userSenha.length() > 11) {
                        Toast.makeText(getApplicationContext(), "Sua senha deve conter de 8 a 11 caracteres", Toast.LENGTH_SHORT).show();
                    } else {
                        realizarLoginMetodo(userEmail, userSenha);
                    }
                }
            }
        });

        BTNTelaCiarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CriarLogin.class);
                startActivity(intent);
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecuperarSenha.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            mAuth.signOut();
        } else {
        }
    }

    @Override
    public void onBackPressed() {
        if (cliqueDuplo) {
            finishAffinity();
        }

        cliqueDuplo = true;

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cliqueDuplo = false;
            }
        }, 3000);
        Toast.makeText(getApplicationContext(), "Pressione 2 vezes para sair do aplicativo !", Toast.LENGTH_SHORT).show();
    }

    private void realizarLoginMetodo(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Aguarde...", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Menu.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Login e/ou senha inválidos !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}