package com.cibertec.pe.appcitmed;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Entidades.Sede;
import com.cibertec.pe.appcitmed.Persistencia.REEspecialistaRequest;
import com.cibertec.pe.appcitmed.Persistencia.RegistroRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class REEspecialista extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    public  final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    private AsyncHttpClient cliente;
    private EditText edtnombre;
    private EditText edtapellido;
    private EditText edtdni;
    private EditText edtemail;
    private Button btnregistrarse;
    private EditText edtfnacimiento;
    private Spinner spnestadocivil;
    private Spinner spnhorario;
    private Spinner spnespecialidad;
    private EditText edtcelular;
    private ImageButton btnfnacimiento;
    private ListView lstsede;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_especialista);
        cliente = new AsyncHttpClient();

        edtnombre = findViewById(R.id.edt_nom_esp_re);
        edtapellido=findViewById(R.id.edt_ape_esp_re);
        edtdni=findViewById(R.id.edt_dni_re);
        edtemail=findViewById(R.id.edt_email_esp_re);
        edtfnacimiento=findViewById(R.id.edt_fnacimiento_espe_re);
        spnestadocivil=findViewById(R.id.spn_ec_esp_re);
        spnespecialidad=findViewById(R.id.spn_epcld_esp_re);
        spnhorario=findViewById(R.id.spn_horario_esp_re);
        edtcelular=findViewById(R.id.edt_cell_esp_re);
        lstsede=findViewById(R.id.lst_sede_re);

        spnhorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sedeid;
                if(position==1||position==2||position==3){
                    sedeid=1;
                    obtenerSede(sedeid);
                }
                if(position==4||position==5||position==6){
                    sedeid=2;
                    obtenerSede(sedeid);
                }
                if(position==7||position==8||position==9){
                    sedeid=3;
                    obtenerSede(sedeid);
                }
                if(position==10||position==11||position==12){
                    sedeid=4;
                    obtenerSede(sedeid);
                }
                if(position==13||position==14||position==15){
                    sedeid=5;
                    obtenerSede(sedeid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnfnacimiento=findViewById(R.id.ib_fnac_re);
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

        btnregistrarse=findViewById(R.id.btn_registrarseR);
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
                String tipo="E";
                int horario_id=spnhorario.getSelectedItemPosition();
                int especialidad_id=spnespecialidad.getSelectedItemPosition();

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
                                                            Intent i = new Intent(REEspecialista.this, MenuPrincipalAdministrador.class);
                                                            i.putExtra("nombre",nombrev);
                                                            i.putExtra("apellido",apellidov);
                                                            i.putExtra("dni",dniv);
                                                            REEspecialista.this.startActivity(i);
                                                            REEspecialista.this.finish();
                                                        }else {
                                                            AlertDialog.Builder alerta = new AlertDialog.Builder(REEspecialista.this);
                                                            alerta.setMessage("ERROR AL REGISTRARSE").setNegativeButton("REINTENTAR", null).create().show();}}

                                                            catch (JSONException e) {e.getMessage();}
                                                }};
                                            REEspecialistaRequest r = new REEspecialistaRequest( dni,nombre, apellido, correo, fnacimiento,celular,estadocivil,tipo,horario_id,especialidad_id , respuesta);
                                            RequestQueue cola = Volley.newRequestQueue(REEspecialista.this);
                                            cola.add(r);
                                            Toast.makeText(REEspecialista.this, "Realizado", Toast.LENGTH_LONG).show();}
                                            else {Toast.makeText(REEspecialista.this, "Seleccione un estado civil", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REEspecialista.this, "El campo Celular esta vacio", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REEspecialista.this, "El campo Fecha de Nacimiento esta vacio", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REEspecialista.this, "El campo Correo esta vacio", Toast.LENGTH_LONG).show();}}
                                            else {Toast.makeText(REEspecialista.this, "El campo DNI esta vacio", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REEspecialista.this, "El campo Apellido esta vacio", Toast.LENGTH_LONG).show();}}
                                            else{Toast.makeText(REEspecialista.this, "El campo Nombre esta vacio", Toast.LENGTH_LONG).show();}
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

    private void obtenerSede(int codsede){
        String url ="https://bdproy.000webhostapp.com/obtenerSede.php?";
        String parametro= "idsede="+ codsede;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarSede(new String (responseBody));
                    //progressBar2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }
    private void listarSede(String respuesta){
        final ArrayList<Sede> lista = new ArrayList<>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta) ;
            for (int i=0; i <jsonArreglo.length();i++){
                Sede s = new Sede();
                s.setDistrito(jsonArreglo.getJSONObject(i).getString("distrito"));
                s.setDireccion(jsonArreglo.getJSONObject(i).getString("direccion"));
                lista.add(s);
            }
            ArrayAdapter<Sede> adaptador=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
            lstsede.setAdapter(adaptador);
        }catch (JSONException e){e.printStackTrace();}
    }

}
