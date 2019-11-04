package com.cibertec.pe.appcitmed;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Persistencia.REPacienteARequest;
import com.cibertec.pe.appcitmed.Persistencia.RegistroRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class REPacienteA extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    public  final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

     EditText edtnombre;
     EditText edtapellido;
     EditText edtdni;
     EditText edtemail;
     Button btnregistrarse;
     EditText edtfnacimiento;
     Spinner spnestadocivil;
     EditText edtcelular;
     Button btnfnacimiento;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_paciente);

        edtnombre = findViewById(R.id.edt_nom_pac_re);
        edtapellido=findViewById(R.id.edt_ape_pac_re);
        edtdni=findViewById(R.id.edt_dni_pac_re);
        edtemail=findViewById(R.id.edt_email_pac_re);
        edtfnacimiento=findViewById(R.id.edt_fnac_pac_re);
        spnestadocivil=findViewById(R.id.spn_ec_pac_re);
        edtcelular=findViewById(R.id.edt_cell_pac_re);
        btnfnacimiento=findViewById(R.id.btn_fnacimiento);
        btnfnacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });


        Intent i = this.getIntent();
        final String nombrev = i.getStringExtra("nombre");
        final String apellidov=i.getStringExtra("apellido");
        final String dniv = getIntent().getStringExtra("dni");

        btnregistrarse=findViewById(R.id.btn_registrar_pac_re);
        btnregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dni = edtdni.getText().toString().trim();
                String nombre = edtnombre.getText().toString();
                String apellido = edtapellido.getText().toString();
                String correo=edtemail.getText().toString().trim();
                String fnacimiento=edtfnacimiento.getText().toString();
                String celular=edtcelular.getText().toString().trim();
                String estadocivil=spnestadocivil.getSelectedItem().toString();
                String tipo="P";

                if(!nombre.isEmpty()){
                    if(!apellido.isEmpty()){
                        if(!dni.isEmpty()){
                            if (!correo.isEmpty()){
                                if (!fnacimiento.isEmpty()){
                                    if (!celular.isEmpty()){
                                        if(spnestadocivil.getSelectedItemPosition()!=0){
                                            Response.Listener<String> respuesta = new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonRespuesta = new JSONObject(response);
                                                                    boolean ok = jsonRespuesta.getBoolean("success");
                                                                    if (ok = true) {
                                                                        Intent i = new Intent(REPacienteA.this, Bienvenido.class);
                                                                        i.putExtra("nombre",nombrev);
                                                                        i.putExtra("apellido",apellidov);
                                                                        i.putExtra("dni",dniv);
                                                                        REPacienteA.this.startActivity(i);
                                                                        REPacienteA.this.finish();}
                                                                    else {
                                                                        AlertDialog.Builder alerta = new AlertDialog.Builder(REPacienteA.this);
                                                                        alerta.setMessage("ERROR AL REGISTRAR").setNegativeButton("REINTENTAR", null).create().show();}}
                                                                catch (JSONException e) {e.getMessage();}
                                                            }};
                                                        REPacienteARequest r = new REPacienteARequest( dni,nombre, apellido, correo, fnacimiento,celular,estadocivil,tipo, respuesta);
                                                        RequestQueue cola = Volley.newRequestQueue(REPacienteA.this);
                                                        cola.add(r);
                                                        Toast.makeText(REPacienteA.this, "Realizado", Toast.LENGTH_LONG).show();}
                                                else {Toast.makeText(REPacienteA.this, "Seleccione un estado civil", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REPacienteA.this, "El campo Celular esta vacio", Toast.LENGTH_LONG).show();}}
                                        else{Toast.makeText(REPacienteA.this, "El campo Fecha de Nacimiento esta vacio", Toast.LENGTH_LONG).show();}}
                                    else{Toast.makeText(REPacienteA.this, "El campo Correo esta vacio", Toast.LENGTH_LONG).show();}}
                        else {Toast.makeText(REPacienteA.this, "El campo DNI esta vacio", Toast.LENGTH_LONG).show();}}
                    else{Toast.makeText(REPacienteA.this, "El campo Apellido esta vacio", Toast.LENGTH_LONG).show();}}
                else{Toast.makeText(REPacienteA.this, "El campo Nombre esta vacio", Toast.LENGTH_LONG).show();}
            }
        });

    }

    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                edtfnacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },anio, mes, dia);
        recogerFecha.show();
    }

}
