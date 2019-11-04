package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.pe.appcitmed.Entidades.PacienteAsegurado;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class MenuPrincipal extends AppCompatActivity {
    Button btnNuevaSolicitud;
    Button btnReferencias;
    TextView tvdni;
    TextView tvnombre,tvapellido;
    private AsyncHttpClient cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final String dni = getIntent().getStringExtra("dni");
        cliente = new AsyncHttpClient();
        obtenerAsegurado(dni);

        tvdni = findViewById(R.id.tv_dni);
        tvnombre = findViewById(R.id.tv_nombre);
        tvapellido = findViewById(R.id.tv_apellido);



        btnNuevaSolicitud = findViewById(R.id.btn_nuevasolicitud);
        btnNuevaSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipal.this,RegistroCita.class);
                i.putExtra("vvdni",dni);
                startActivity(i);
            }
        });
        btnReferencias=findViewById(R.id.btn_referencias);
        btnReferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipal.this,ListaCita.class);
                i.putExtra("vdni",dni);
                startActivity(i);
            }
        });




    }

    private void obtenerAsegurado(String dni){
        //progressBar.setVisibility(View.VISIBLE);
        String url ="https://bdproy.000webhostapp.com/obtenerAseguradoXDni.php?";
        String parametro= "dni="+ dni;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarAsegurado(new String (responseBody));
                    //progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarAsegurado(String respuesta){
        PacienteAsegurado pa = new PacienteAsegurado();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                pa.set_dni(jsonArreglo.getJSONObject(i).getString("dni"));
                pa.set_nombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                pa.set_apellido(jsonArreglo.getJSONObject(i).getString("apellido"));
                pa.set_correo(jsonArreglo.getJSONObject(i).getString("correo"));
                pa.set_fnacimiento(jsonArreglo.getJSONObject(i).getString("fnacimiento"));
                pa.set_celular(jsonArreglo.getJSONObject(i).getString("celular"));
                pa.set_estadocivil(jsonArreglo.getJSONObject(i).getString("estadocivil"));
                pa.set_tipo(jsonArreglo.getJSONObject(i).getString("tipo"));
            }

            tvdni.setText(pa.get_dni());
            tvnombre.setText(pa.get_nombre());
            tvapellido.setText(pa.get_apellido());


        }
        catch (Exception e){e.printStackTrace();}
    }
   }
