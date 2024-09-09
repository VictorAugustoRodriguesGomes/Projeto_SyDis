package com.example.sydis;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VisualizarComentarioDetalhada extends AppCompatActivity {

    private TextView seComentComentario;
    private TextView seDataComentario;
    private TextView seuserComentario;

    private FirebaseUser userAuth;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private String recuperarDasdosBancoRef;
    private String ComentarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_comentario_detalhada);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        Bundle extras = getIntent().getExtras();
        recuperarDasdosBancoRef = extras.getString("BancoRef");
        ComentarioRef = extras.getString("ComentarioRef");

        seComentComentario = findViewById(R.id.setComentComentario);
        seDataComentario = findViewById(R.id.setDataComentario);
        seuserComentario = findViewById(R.id.setuserComentario);

        mDatabase.child(recuperarDasdosBancoRef).child(ComentarioRef).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                } else {
                    Comentario co = task.getResult().getValue(Comentario.class);
                    seComentComentario.setText(co.getComentComentario());
                    seDataComentario.setText(co.getDataComentario());
                    seuserComentario.setText(co.getUserComentario());
                }
            }
        });
    }
}