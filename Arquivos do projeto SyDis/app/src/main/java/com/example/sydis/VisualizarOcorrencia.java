package com.example.sydis;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisualizarOcorrencia extends AppCompatActivity {
    // outra coisa
    private final List<Ocorrencia> ocorenciaLista = new ArrayList<Ocorrencia>();
    Ocorrencia ocorrenciaSelecionada;
    private ListView listaVPesquisa;
    private EditText editPalavra;
    private Intent passarDados;
    // outra coisa
    private FirebaseUser userAuth;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayAdapter<Ocorrencia> arrayAdapterOcorencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_ocorrencia);

        editPalavra = findViewById(R.id.editPalavra);
        listaVPesquisa = findViewById(R.id.listaVPesquisa);

        FirebaseApp.initializeApp(VisualizarOcorrencia.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();

        eventoEdit();

        listaVPesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ocorrenciaSelecionada = (Ocorrencia) listaVPesquisa.getItemAtPosition(i);

                passarDados = new Intent(getApplicationContext(), VisualizarOcorrenciaDetalhada.class);
                passarDados.putExtra("UIDOcorrencia", ocorrenciaSelecionada.getuUIDOcorrencia());
                startActivity(passarDados);
            }
        });
    }

    private void eventoEdit() {
        editPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String palavra = editPalavra.getText().toString().trim();
                pesquisarPalavra(palavra);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        pesquisarPalavra("");
    }

    public void pesquisarPalavra(String palavra) {
        Query query;
        if (palavra.equals("")) {
            query = databaseReference.child("ocorrencia").orderByChild("nomeOcorrencia");
        } else {
            query = databaseReference.child("ocorrencia").orderByChild("nomeOcorrencia").startAt(palavra).endAt(palavra + "\uf8ff");
        }

        ocorenciaLista.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Ocorrencia o = dataSnapshot.getValue(Ocorrencia.class);
                    ocorenciaLista.add(o);
                }
                arrayAdapterOcorencia = new ArrayAdapter<Ocorrencia>(VisualizarOcorrencia.this, android.R.layout.simple_list_item_1, ocorenciaLista);
                listaVPesquisa.setAdapter(arrayAdapterOcorencia);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}