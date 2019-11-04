package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cibertec.pe.appcitmed.Entidades.Administrador;
import com.cibertec.pe.appcitmed.Entidades.PacienteAsegurado;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class MenuPrincipalAdministrador extends AppCompatActivity  {
    TextView tvdni;
    TextView tvnombre,tvapellido;
    Button btnREasegurado;
    Button btnREespecialista;
    Button btnOPEspecialista;
    private AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);
        cliente = new AsyncHttpClient();
        final String dni = getIntent().getStringExtra("dni");
        obtenerAdministrador(dni);
        tvdni = findViewById(R.id.tv_dni);
        tvnombre = findViewById(R.id.tv_nombre);
        tvapellido = findViewById(R.id.tv_apellido);



        btnREasegurado = findViewById(R.id.btn_registrar_aseg_re);
        btnREespecialista=findViewById(R.id.btn_registrar_esp_re);
        btnREasegurado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalAdministrador.this,REPacienteA.class);

//                String nom = tvnombre.getText().toString();
//                String ape = tvapellido.getText().toString();
                String dni = tvdni.getText().toString();
                //i.putExtra("nombre",nom);
                //i.putExtra("apellido",ape);
                i.putExtra("dni",dni);
                startActivity(i);
            }
        });

        btnREespecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String nom = tvnombre.getText().toString();
                String ape = tvapellido.getText().toString();
                String dni = tvdni.getText().toString();*/
                Intent i = new Intent(MenuPrincipalAdministrador.this,REEspecialista.class);
                //i.putExtra("nombre",nom);
                //i.putExtra("apellido",ape);
                i.putExtra("dni",dni);
                startActivity(i);
            }
        });
        btnOPEspecialista = findViewById(R.id.btn_op_esp);

        btnOPEspecialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipalAdministrador.this,OPEspecialista.class);
                i.putExtra("dni",dni);
                startActivity(i);
            }
        });


    }
    private void obtenerAdministrador(String dni){
        //progressBar.setVisibility(View.VISIBLE);
        String url ="https://bdproy.000webhostapp.com/obtenerAdministradorXDni.php?";
        String parametro= "usuario="+ dni;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarAdministrador(new String (responseBody));
                    //progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarAdministrador(String respuesta){
        Administrador adm = new Administrador();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                adm.setId(jsonArreglo.getJSONObject(i).getInt("id"));
                adm.setUsuario(jsonArreglo.getJSONObject(i).getString("usuario"));
                adm.setClave(jsonArreglo.getJSONObject(i).getString("clave"));
                adm.setTipo(jsonArreglo.getJSONObject(i).getString("tipo"));
                adm.setNombre(jsonArreglo.getJSONObject(i).getString("nombre"));
                adm.setApellido(jsonArreglo.getJSONObject(i).getString("apellido"));
                adm.setCorreo(jsonArreglo.getJSONObject(i).getString("correo"));

            }

            tvdni.setText(adm.getUsuario());
            tvnombre.setText(adm.getNombre().toUpperCase());
            tvapellido.setText(adm.getApellido().toUpperCase());
        }
        catch (Exception e){e.printStackTrace();}
    }

}
