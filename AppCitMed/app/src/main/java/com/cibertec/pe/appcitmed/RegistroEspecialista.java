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
import com.cibertec.pe.appcitmed.Entidades.Especialista;
import com.cibertec.pe.appcitmed.Persistencia.RegistroEspecialistaRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistroEspecialista extends AppCompatActivity {
    Button btnbuscarespecialista;
    Button btnregistrar;
    EditText edtdniespecialista;
    EditText edtclave,edtclaverep;
    TextView tvdniespecialista,tvnomespecialista,tvapeespecialista;




    private AsyncHttpClient cliente;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_especialista);

        cliente = new AsyncHttpClient();

        tvdniespecialista = findViewById(R.id.tv_dni_esp);
        tvnomespecialista = findViewById(R.id.tv_nombre_esp);
        tvapeespecialista = findViewById(R.id.tv_apellido_esp);

        edtdniespecialista = findViewById(R.id.edt_dni_esp);
        edtclave = findViewById(R.id.edt_clv_esp);
        edtclaverep = findViewById(R.id.edt_repclv_esp);

        btnbuscarespecialista = findViewById(R.id.btn_buscar_esp);

        btnbuscarespecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtdniespecialista.length()!=8) {
                    Toast.makeText(RegistroEspecialista.this, "DNI incorrecto", Toast.LENGTH_LONG).show();
                    return;
                }
                String dniesp = edtdniespecialista.getText().toString();
                obtenerEspecialistaXdni(dniesp);
            }
        });

        btnregistrar = findViewById(R.id.btn_generar_especialista);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dniespecialista = tvdniespecialista.getText().toString();
                String clave = edtclave.getText().toString().trim();
                String claverep= edtclaverep.getText().toString().trim();

                if(!dniespecialista.isEmpty() && dniespecialista.length()==8){
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
                                                Intent i = new Intent(RegistroEspecialista.this, LoginEspecialista.class);
                                                RegistroEspecialista.this.startActivity(i);
                                                RegistroEspecialista.this.finish();}
                                            else {
                                                AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroEspecialista.this);
                                                alerta.setMessage("ERROR AL REGISTRARSE").setNegativeButton("REINTENTAR", null).create().show();}}
                                        catch (JSONException e) {e.getMessage();}
                                    }};

                                RegistroEspecialistaRequest r = new RegistroEspecialistaRequest( dniespecialista,clave,"E", respuesta);
                                RequestQueue cola = Volley.newRequestQueue(RegistroEspecialista.this);
                                cola.add(r);
                                Toast.makeText(RegistroEspecialista.this, "Realizado", Toast.LENGTH_LONG).show();

                            }else{Toast.makeText(RegistroEspecialista.this, "Claves diferentes,deben ser iguales", Toast.LENGTH_LONG).show();}
                        }else{Toast.makeText(RegistroEspecialista.this, "Campo Repetir Clave vacio", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(RegistroEspecialista.this, "Campo Clave vacio", Toast.LENGTH_LONG).show();}
                }else{Toast.makeText(RegistroEspecialista.this, "campo DNI erroneo", Toast.LENGTH_LONG).show();}
            }
        });
    }

    private void obtenerEspecialistaXdni(String dni){

        String url ="https://bdproy.000webhostapp.com/obtenerEspecialistaXDni.php?";
        String parametro= "dni="+ dni;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarEspecialistaXdni(new String (responseBody));
                    //progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarEspecialistaXdni(String respuesta){
        Especialista esp = new Especialista();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                esp.set_dni(jsonArreglo.getJSONObject(i).getString("dni"));
                esp.set_nombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                esp.set_apellido(jsonArreglo.getJSONObject(i).getString("apellido"));
                esp.set_correo(jsonArreglo.getJSONObject(i).getString("correo"));
                esp.set_fnacimiento(jsonArreglo.getJSONObject(i).getString("fnacimiento"));
                esp.set_celular(jsonArreglo.getJSONObject(i).getString("celular"));
                esp.set_estadocivil(jsonArreglo.getJSONObject(i).getString("estadocivil"));
                esp.set_tipo(jsonArreglo.getJSONObject(i).getString("tipo"));
                esp.set_idespecialidad(jsonArreglo.getJSONObject(i).getInt("idespecialidad"));
                esp.set_idhorario(jsonArreglo.getJSONObject(i).getInt("idhorario"));
            }
            tvdniespecialista.setText(esp.get_dni());
            tvnomespecialista.setText(esp.get_nombre());
            tvapeespecialista.setText(esp.get_apellido());


        }
        catch (Exception e){e.printStackTrace();}
    }


}
