package com.cibertec.pe.appcitmed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cibertec.pe.appcitmed.Entidades.CitaMedica;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaCitaXespecialista extends AppCompatActivity {

    private AsyncHttpClient cliente;
    private ListView lstcitas;
    ProgressBar progressBar;


    String dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarcitaespecialista);

        lstcitas=findViewById(R.id.lstcitas);
        cliente = new AsyncHttpClient();
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent i = this.getIntent();
        dni = i.getStringExtra("dni_especialista");
        obtenerCitas(dni);
    }

    private void obtenerCitas(String especialista_dni){
        String url ="https://bdproy.000webhostapp.com/obtenerCitasXespecialista.php?";

        String parametro= "especialista_dni="+especialista_dni;
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
        final ArrayList<CitaMedica> lista = new ArrayList<>();
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
            lstcitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final int cita_id =lista.get(position).get_idcita();
                    final String estado = "SUSPENDIDO";
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaCitaXespecialista.this);
                    builder.setTitle("");
                    builder.setMessage("¿Desea Suspender la cita?")
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActualizarEstadoCita(cita_id,estado);
                            obtenerCitas(dni);

                        }})
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Cancelo la operacion",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }})
                            .setCancelable(false)
                            .show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void ActualizarEstadoCita(int id,String estado) {

        String url = "https://bdproy.000webhostapp.com/actualizarEstadoCita.php?";
        String p1 = "id=" + id + "&";
        String p2 = "estado=" + estado;
        cliente.post(url + p1 + p2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ListaCitaXespecialista.this, "SE SUSPENDIO LA CITA", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ListaCitaXespecialista.this, "HUBO UN ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });

    }

}
