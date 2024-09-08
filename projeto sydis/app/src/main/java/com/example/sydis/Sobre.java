package com.example.sydis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sobre extends AppCompatActivity {
    private Button BTNVoltarSobre;

    private Button BTNLinkCurriculum;

    private Button BTNLinkPortfolio;

    private Button BTNLinkLinkedIn;

    private Button BTNLinkGitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        BTNVoltarSobre = findViewById(R.id.BTNVoltarSobre);

        BTNLinkCurriculum = findViewById(R.id.BTNLinkCurriculum);
        BTNLinkPortfolio = findViewById(R.id.BTNLinkPortfolio);
        BTNLinkLinkedIn = findViewById(R.id.BTNLinkLinkedIn);
        BTNLinkGitHub = findViewById(R.id.BTNLinkGitHub);

        BTNLinkCurriculum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Aguarde... Abrindo Link.", Toast.LENGTH_LONG).show();
                String urlCurriculum = "https://github.com/VictorAugustoRodriguesGomes/VictorAugustoRodriguesGomes/blob/main/src/doc/Victor%20Augusto%20Rodrigues%20Gomes%20-%20curr%C3%ADculo.pdf";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlCurriculum));
                startActivity(intent);
            }
        });

        BTNLinkPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aguarde... Abrindo Link.", Toast.LENGTH_LONG).show();
                String urlPortfolio = "https://victor-augusto-dev.firebaseapp.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlPortfolio));
                startActivity(intent);
            }
        });

        BTNLinkLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aguarde... Abrindo Link.", Toast.LENGTH_LONG).show();
                String urlLinkedIn = "https://www.linkedin.com/in/victor-augusto-desenvolvedor/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlLinkedIn));
                startActivity(intent);
            }
        });

        BTNLinkGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aguarde... Abrindo Link.", Toast.LENGTH_LONG).show();
                String urlGitHub = "https://github.com/VictorAugustoRodriguesGomes";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlGitHub));
                startActivity(intent);
            }
        });

        BTNVoltarSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menu.class));
            }
        });
    }
}