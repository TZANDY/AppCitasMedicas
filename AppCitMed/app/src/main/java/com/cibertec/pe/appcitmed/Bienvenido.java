package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Bienvenido extends AppCompatActivity {
    Button btnpaciente;
    Button btnespecialista;
    Button btnadministrador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        btnpaciente=findViewById(R.id.btn_paciente);
        btnpaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bienvenido.this,LoginPaciente.class);
                startActivity(i);

            }
        });
        btnespecialista=findViewById(R.id.btn_especialista);
        btnespecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bienvenido.this,LoginEspecialista.class);
                startActivity(i);

            }
        });
        btnadministrador=findViewById(R.id.btn_administrador);
        btnadministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bienvenido.this,Login.class);
                startActivity(i);
            }
        });

    }
}
