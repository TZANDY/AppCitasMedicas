package com.cibertec.pe.appcitmed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Persistencia.LoginPacienteRequest;
import com.cibertec.pe.appcitmed.Persistencia.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPaciente extends AppCompatActivity{

    Button btnaceptar;
    ProgressBar progressBar;
    TextView tv_registrarse;
    EditText edt_dni,edt_clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_paciente);

        edt_dni=findViewById(R.id.edt_dni_aseg);
        edt_clave=findViewById(R.id.edt_clave_aseg);

        btnaceptar=findViewById(R.id.btn_acep_aseg);
        progressBar=findViewById(R.id.progressBar);
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = edt_dni.getText().toString().trim();
                String clave = edt_clave.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if(ok==true){
                                   String dni = jsonRespuesta.getString("usuario");
                                   String tipo = jsonRespuesta.getString("tipo");
                                   Intent i = new Intent(LoginPaciente.this,MenuPrincipal.class);
                                   i.putExtra("dni",dni);
                                   progressBar.setVisibility(View.GONE);

                                   LoginPaciente.this.startActivity(i);
                                   LoginPaciente.this.finish();
                                   Toast.makeText(LoginPaciente.this, "Bienvenido Paciente Asegurado", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginPaciente.this);
                                    alerta.setMessage("Error de Login").setNegativeButton("REINTENTAR",null).create().show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                            catch (JSONException e){
                                e.getMessage();
                            }
                        }
                    };

                    LoginPacienteRequest r = new LoginPacienteRequest(usuario,clave,respuesta);
                    RequestQueue cola = Volley.newRequestQueue(LoginPaciente.this);
                    cola.add(r);
                }

        });
        tv_registrarse=findViewById(R.id.tv_registrarAsegurado);
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPaciente.this,RegistroPaciente.class);
                startActivity(i);
            }
        });
    }


}
