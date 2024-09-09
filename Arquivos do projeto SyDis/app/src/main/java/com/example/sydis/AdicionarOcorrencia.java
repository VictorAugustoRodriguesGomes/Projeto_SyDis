package com.example.sydis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AdicionarOcorrencia extends AppCompatActivity {

    private Button BTNADDOcorrencia;
    private Button BTNADDvoltar;
    private Button BTNBuscar;

    private ImageView ivFoto;

    private ProgressBar progressBar;

    private EditText inputNomeOcorrencia;
    private EditText inputDescricaoOcorrencia;
    private EditText inputLocalOcorrencia;

    private String nome;
    private String descricao;
    private String local;
    private String id;
    private String user;
    private String uidFoto;
    private int verificarfoto = 0;

    private FirebaseUser userAuth;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_ocorrencia);

        mAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        BTNADDOcorrencia = findViewById(R.id.BTNADDOcorrencia);
        BTNADDvoltar = findViewById(R.id.BTNADDvoltar);

        ivFoto = findViewById(R.id.ivFoto);
        BTNBuscar = findViewById(R.id.btnBuscar);
        progressBar = findViewById(R.id.progressBar);

        ivFoto.setVisibility(View.GONE);

        inputNomeOcorrencia = findViewById(R.id.inputNomeOcorrencia);
        inputDescricaoOcorrencia = findViewById(R.id.inputDescricaoOcorrencia);
        inputLocalOcorrencia = findViewById(R.id.inputLocalOcorrencia);

        BTNADDOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = inputNomeOcorrencia.getText().toString().trim();
                descricao = inputDescricaoOcorrencia.getText().toString().trim();
                local = inputLocalOcorrencia.getText().toString().trim();

                if (nome.isEmpty() || descricao.isEmpty() || local.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos !", Toast.LENGTH_SHORT).show();
                } else if (verificarfoto == 0) {
                    Toast.makeText(getApplicationContext(), "Selecione uma imagem !", Toast.LENGTH_SHORT).show();
                } else {
                    id = gerarUUID();
                    salvar(nome, descricao, local, id, user);
                }
            }
        });

        BTNBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto();
            }
        });

        BTNADDvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
            }
        });
    }

    private void selecionarFoto() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SelecionarFoto"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            ivFoto.setImageURI(uri);
            ivFoto.setVisibility(View.VISIBLE);
            BTNBuscar.setVisibility(View.GONE);
            verificarfoto = 1;
        }
    }

    private String enviarFoto() {
        Bitmap bitmap = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imagem = byteArrayOutputStream.toByteArray();

        uidFoto = "ImagemOcorrência/" + gerarUUID() + ".jpeg";
        StorageReference imgRef = mStorageRef.child(uidFoto);
        UploadTask uploadTask = imgRef.putBytes(imagem);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Aguarde...", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressBar.setProgress((int) progress);
            }
        });
        return uidFoto;
    }

    private void salvar(String nomeOcorrencia, String descricaoOcorrencia, String localOcorrencia, String uUIDOcorrencia, String userOcorrencia) {
        String UrlImgOcorrencia = enviarFoto();

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setuUIDOcorrencia(uUIDOcorrencia);
        ocorrencia.setNomeOcorrencia(nomeOcorrencia);
        ocorrencia.setDescricaoOcorrencia(descricaoOcorrencia);
        ocorrencia.setLocalOcorrencia(localOcorrencia);
        ocorrencia.setUserOcorrencia(userOcorrencia);
        ocorrencia.setUrlImgOcorrencia(UrlImgOcorrencia);

        mDatabase.child("ocorrencia").child(uUIDOcorrencia).setValue(ocorrencia);
        Toast.makeText(getApplicationContext(), "Ocorrência realizada com sucesso", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Menu.class));
    }

    private String gerarUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userAuth != null) {
            user = userAuth.getDisplayName();
        } else {
            mAuth.signOut();
            startActivity(new Intent(this, FazerLogin.class));
        }
    }
}