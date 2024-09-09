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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CriarLogin extends AppCompatActivity {

    private Button BTNFazerLogin;
    private Button BTNCriarLogin;

    private EditText inputNome2;
    private EditText inputEmail2;
    private EditText inputSenha2;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String userNome;
    private String userEmail;
    private String userSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        inputNome2 = findViewById(R.id.inputNome2);
        inputEmail2 = findViewById(R.id.inputEmail2);
        inputSenha2 = findViewById(R.id.inputSenha2);

        BTNFazerLogin = findViewById(R.id.BTNFazerLogin);
        BTNCriarLogin = findViewById(R.id.BTNCriarLogin);

        BTNFazerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FazerLogin.class);
                startActivity(intent);
            }
        });

        BTNCriarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNome = inputNome2.getText().toString().trim();
                userEmail = inputEmail2.getText().toString().trim();
                userSenha = inputSenha2.getText().toString().trim();

                if (userNome.isEmpty() || userEmail.isEmpty() || userSenha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos !", Toast.LENGTH_SHORT).show();
                } else {
                    if (userSenha.length() < 8 || userSenha.length() > 11) {
                        Toast.makeText(getApplicationContext(), "Sua senha deve conter de 8 a 11 caracteres", Toast.LENGTH_SHORT).show();
                    } else {
                        criarLoginMetodo(userNome, userEmail, userSenha);
                    }
                }
            }
        });
    }

    private void criarLoginMetodo(String userNome, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AtualizarUserMetodo(userNome, email, password);
                            Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "E-mail inválido ou existente !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void AtualizarUserMetodo(String userNome, String emailUser, String senhaUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userNome)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addUser(userNome, emailUser, senhaUser);
                            Toast.makeText(getApplicationContext(), "Aguarde...", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Menu.class));
                        }
                    }
                });
    }

    private void addUser(String userNome, String emailUser, String senhaUser) {
        String uidl = gerarUUID();
        User luser = new User();
        luser.setNomeUser(userNome);
        luser.setEmailUser(emailUser);
        luser.setSenhaUser(senhaUser);
        luser.setUidUser(uidl);
        mDatabase.child("Perfil_Usuario").child(uidl).setValue(luser);
    }

    public String gerarUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
        }
    }
}