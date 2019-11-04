package com.cibertec.pe.appcitmed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Entidades.PacienteAsegurado;
import com.cibertec.pe.appcitmed.Entidades.UsuarioEspecialista;
import com.cibertec.pe.appcitmed.Entidades.UsuarioPaciente;
import com.cibertec.pe.appcitmed.Persistencia.RegistroPacienteRequest;
import com.cibertec.pe.appcitmed.Persistencia.RegistroRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistroPaciente  extends AppCompatActivity {

    Button btnbuscarpaciente;
    Button btnregistrar;
    EditText edtdniasegurado;
    EditText edtclave,edtclaverep;
    TextView tvdniasegurado,tvnomasegurado,tvapeasegurado;
    private AsyncHttpClient cliente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_asegurado);

        edtdniasegurado=findViewById(R.id.edt_dniasegurado);
        edtclave = findViewById(R.id.edt_clv);
        edtclaverep=findViewById(R.id.edt_repclv);

        tvdniasegurado=findViewById(R.id.tv_dniasegurado);
        tvnomasegurado=findViewById(R.id.tv_nombreasegurado);
        tvapeasegurado=findViewById(R.id.tv_apellidoasegurado);

        cliente = new AsyncHttpClient();
        btnbuscarpaciente=findViewById(R.id.btn_buscarasegurado);
        btnbuscarpaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dniasegurado = edtdniasegurado.getText().toString();
                obtenerAseguradoXdni(dniasegurado);
            }
        });
        btnregistrar=findViewById(R.id.btn_registrar_asegurado);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clave = edtclave.getText().toString().trim();
                String claverep= edtclaverep.getText().toString().trim();
                String dniasegurado = tvdniasegurado.getText().toString();
                String tipo = "P";
                if(!dniasegurado.isEmpty()&& dniasegurado.length()==8){
                    if(!clave.isEmpty()){
                        if(!claverep.isEmpty()){
                            if (clave.equals(claverep)){
                                Response.Listener<String> respuesta = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonRespuesta = new JSONObject(response);
                                            boolean ok = jsonRespuesta.getBoolean("success");
                                            if (ok = true) {
                                                Intent i = new Intent(RegistroPaciente.this, LoginPaciente.class);
                                                RegistroPaciente.this.startActivity(i);
                                                RegistroPaciente.this.finish();}
                                            else {
                                                AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroPaciente.this);
                                                alerta.setMessage("ERROR AL REGISTRARSE").setNegativeButton("REINTENTAR", null).create().show();}}
                                        catch (JSONException e) {e.getMessage();}
                                    }};

                                RegistroPacienteRequest r = new RegistroPacienteRequest( dniasegurado,clave, tipo, respuesta);
                                RequestQueue cola = Volley.newRequestQueue(RegistroPaciente.this);
                                cola.add(r);
                                Toast.makeText(RegistroPaciente.this, "Realizado", Toast.LENGTH_LONG).show();
                            }else{Toast.makeText(RegistroPaciente.this, "Claves diferentes,deben ser iguales", Toast.LENGTH_LONG).show();}
                        }else{Toast.makeText(RegistroPaciente.this, "Campo Repetir Clave vacio", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(RegistroPaciente.this, "Campo Clave vacio", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(RegistroPaciente.this, "campo DNI erroneo", Toast.LENGTH_LONG).show();}
            }
        });
    }
    private void obtenerAseguradoXdni(String dni){
        //progressBar.setVisibility(View.VISIBLE);
        String url ="https://bdproy.000webhostapp.com/obtenerAseguradoXDni.php?";
        String parametro= "dni="+ dni;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarAseguradoXdni(new String (responseBody));
                    //progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarAseguradoXdni(String respuesta){
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
            tvdniasegurado.setText(pa.get_dni());
            tvnomasegurado.setText(pa.get_nombre());
            tvapeasegurado.setText(pa.get_apellido());


        }
        catch (Exception e){e.printStackTrace();}
    }



}
