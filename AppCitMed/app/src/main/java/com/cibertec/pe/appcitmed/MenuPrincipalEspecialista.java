package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.pe.appcitmed.Entidades.Especialista;
import com.cibertec.pe.appcitmed.Entidades.PacienteAsegurado;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class MenuPrincipalEspecialista extends AppCompatActivity  {
    TextView tvdni;
    TextView tvnombre,tvapellido;
    Button btnVerCitas;
    private AsyncHttpClient cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cliente = new AsyncHttpClient();

        final String dni_especialista = getIntent().getStringExtra("dni");
        obtenerEspecialista(dni_especialista);
        setContentView(R.layout.activity_menu_especialista);

        tvdni = findViewById(R.id.tv_dni);
        tvnombre = findViewById(R.id.tv_nombre);
        tvapellido = findViewById(R.id.tv_apellido);

        btnVerCitas=findViewById(R.id.btn_vercitas);
        btnVerCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalEspecialista.this,ListaCitaXespecialista.class);
                i.putExtra("dni_especialista",dni_especialista);
                startActivity(i);
            }
        });


    }

    private void obtenerEspecialista(String dni_especialista){

        String url ="https://bdproy.000webhostapp.com/obtenerEspecialistaXDni.php?";
        String parametro= "dni="+ dni_especialista;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarEspecialista(new String (responseBody));
                    //progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarEspecialista(String respuesta){
        Especialista es = new Especialista();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                es.set_idespecialista(jsonArreglo.getJSONObject(i).getInt("idespecialista"));
                es.set_dni(jsonArreglo.getJSONObject(i).getString("dni"));
                es.set_nombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                es.set_apellido(jsonArreglo.getJSONObject(i).getString("apellido"));
                es.set_correo(jsonArreglo.getJSONObject(i).getString("correo"));
                es.set_fnacimiento(jsonArreglo.getJSONObject(i).getString("fnacimiento"));
                es.set_celular(jsonArreglo.getJSONObject(i).getString("celular"));
                es.set_estadocivil(jsonArreglo.getJSONObject(i).getString("estadocivil"));
                es.set_tipo(jsonArreglo.getJSONObject(i).getString("tipo"));
                es.set_idespecialidad(jsonArreglo.getJSONObject(i).getInt("idespecialidad"));
                es.set_idhorario(jsonArreglo.getJSONObject(i).getInt("idhorario"));
            }
            tvdni.setText(es.get_dni());
            tvnombre.setText(es.get_nombre());
            tvapellido.setText(es.get_apellido());
        }
        catch (Exception e){e.printStackTrace();}
    }


}
