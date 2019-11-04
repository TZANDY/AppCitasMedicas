package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.cibertec.pe.appcitmed.Entidades.CitaMedica;
import com.loopj.android.http.*;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaCita extends AppCompatActivity {

    public  String edtdni;
    private AsyncHttpClient cliente;
    private ListView lstcitas;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarcitamedica);



        lstcitas=(ListView)findViewById(R.id.lstcitas);
        cliente = new AsyncHttpClient();
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent i = this.getIntent();
        String vdni = i.getStringExtra("vdni");
        obtenerCitas(vdni);



    }
    /***AQUI TENGO QUE ENVIAR COMO PARAMETRO LA CLASE (PacienteAsegurado paciente)***/
    private void obtenerCitas(String asegurado_dni){
        String url ="https://bdproy.000webhostapp.com/obtenerDatosX.php?";

        /*Se reemplaza por paciente.getDni().toString*/
        String parametro= "asegurado_dni="+asegurado_dni;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarCitas(new String (responseBody));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    private void listarCitas(String respuesta){
        ArrayList<CitaMedica> lista = new ArrayList<>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                CitaMedica cm = new CitaMedica();
                cm.set_idcita(jsonArreglo.getJSONObject(i).getInt("id"));
                cm.set_asegurado_dni(jsonArreglo.getJSONObject(i).getString("asegurado_dni"));
                cm.set_especialista_dni(jsonArreglo.getJSONObject(i).getString("especialista_dni"));
                cm.set_fecha(jsonArreglo.getJSONObject(i).getString("fecha"));
                cm.set_estado(jsonArreglo.getJSONObject(i).getString("estado"));
                lista.add(cm);
            }
            ArrayAdapter<CitaMedica> adaptador=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
            lstcitas.setAdapter(adaptador);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
