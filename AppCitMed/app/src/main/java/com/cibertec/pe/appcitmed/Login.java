package com.cibertec.pe.appcitmed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Persistencia.LoginRequest;
import com.cibertec.pe.appcitmed.Persistencia.RegistroCitaRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity{

    Button btnaceptar;
    ProgressBar progressBar;
    TextView tv_registrarse;
    EditText edt_dnip,edt_clavep;

    RadioButton rb1especialista,rb2paciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_dnip=(EditText)findViewById(R.id.edt_dnip);
        edt_clavep=(EditText)findViewById(R.id.edt_clavep);

        btnaceptar=findViewById(R.id.btn_aceptar);
        progressBar=findViewById(R.id.progressBar);



        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String midni = edt_dnip.getText().toString().trim();
                String miclave = edt_clavep.getText().toString().trim();


                progressBar.setVisibility(View.VISIBLE);
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespuesta = new JSONObject(response);
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if(ok==true){
                                    String dni = jsonRespuesta.getString("usuario");
                                    Intent i = new Intent(Login.this,MenuPrincipalAdministrador.class);
                                    i.putExtra("dni",dni);
                                    progressBar.setVisibility(View.GONE);
                                    Login.this.startActivity(i);
                                    Login.this.finish();
                                    Toast.makeText(Login.this, "Bienvenido ADMINISTRADOR", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setMessage("Error de Login").setNegativeButton("REINTENTAR",null).create().show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                            catch (JSONException e){
                                e.getMessage();
                            }
                        }
                };
                LoginRequest r = new LoginRequest(midni,miclave,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);
            }
        });
    }
}
