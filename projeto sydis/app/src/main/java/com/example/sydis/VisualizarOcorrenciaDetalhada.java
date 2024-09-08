package com.example.sydis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class VisualizarOcorrenciaDetalhada extends AppCompatActivity {

    // outra coisa
    private final List<Comentario> comentarioLista = new ArrayList<Comentario>();
    Comentario comentarioSelecionada;

    private TextView nome;
    private TextView descricao;
    private TextView local;
    private TextView userOcorrencia;

    // outra coisa
    private FirebaseUser userAuth;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    // outra coisa
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String recuperarDasdos;
    private String urlIMG;

    // outra coisa
    private ImageView downloadIMG;

    // outra coisa
    private Button BTNComentario;
    private EditText inputComentario;
    private String inputC;
    private String user;
    private String dateTime;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String dataCompleta;
    private String idC;
    // outra coisa
    private ListView listaVComentario;
    private ArrayAdapter<Comentario> arrayAdapterComentario;
    // outra coisa
    private Intent passarDados2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_ocorrencia_detalhada);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Bundle extras = getIntent().getExtras();
        recuperarDasdos = extras.getString("UIDOcorrencia");

        nome = findViewById(R.id.setNome);
        descricao = findViewById(R.id.setDescricao);
        local = findViewById(R.id.setLocal);
        userOcorrencia = findViewById(R.id.setUser);

        downloadIMG = findViewById(R.id.downloadIMG);

        mDatabase.child("ocorrencia").child(recuperarDasdos).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                } else {
                    Ocorrencia o = task.getResult().getValue(Ocorrencia.class);

                    nome.setText(o.getNomeOcorrencia());
                    descricao.setText(o.getDescricaoOcorrencia());
                    local.setText(o.getLocalOcorrencia());
                    userOcorrencia.setText(o.getUserOcorrencia());
                    urlIMG = o.getUrlImgOcorrencia();

                    StorageReference imgRef = mStorageRef.child(urlIMG);
                    final long MEGABYTE = 1024 * 1024;
                    imgRef.getBytes(MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            downloadIMG.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(getApplicationContext(), "falha imga", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // outra coisa
        BTNComentario = findViewById(R.id.BTNComentario);
        inputComentario = findViewById(R.id.inputComentario);

        BTNComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputC = inputComentario.getText().toString().trim();
                if (inputC.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha o campo !", Toast.LENGTH_SHORT).show();
                } else {
                    enviarComentario(inputC);

                }
            }
        });

        // outra coisa
        listaVComentario = findViewById(R.id.listaVComentario);
        visualizarLista();

        listaVComentario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                comentarioSelecionada = (Comentario) listaVComentario.getItemAtPosition(i);

                passarDados2 = new Intent(getApplicationContext(), VisualizarComentarioDetalhada.class);
                passarDados2.putExtra("BancoRef", "comentario-" + recuperarDasdos);
                passarDados2.putExtra("ComentarioRef", comentarioSelecionada.getIdComentario());
                startActivity(passarDados2);
            }
        });
    }

    private void visualizarLista() {
        Query query;
        query = databaseReference.child("comentario-" + recuperarDasdos).orderByChild("comentComentario");

        comentarioLista.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comentarioLista.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Comentario comenta = dataSnapshot.getValue(Comentario.class);
                    comentarioLista.add(comenta);
                }
                arrayAdapterComentario = new ArrayAdapter<Comentario>(VisualizarOcorrenciaDetalhada.this, android.R.layout.simple_list_item_1, comentarioLista);
                listaVComentario.setAdapter(arrayAdapterComentario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void enviarComentario(String inputC) {
        dataCompleta = obterData();
        idC = gerarUUID();

        inputComentario.setText("");

        Comentario comen = new Comentario();
        comen.setComentComentario(inputC);
        comen.setIdComentario(idC);
        comen.setDataComentario(dataCompleta);
        comen.setUserComentario(user);

        mDatabase.child("comentario-" + recuperarDasdos).child(idC).setValue(comen);
        Toast.makeText(getApplicationContext(), "Comentario realizada com sucesso", Toast.LENGTH_SHORT).show();

        comentarioLista.clear();
        visualizarLista();
    }

    private String gerarUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    private String obterData() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime());
        return dateTime;
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