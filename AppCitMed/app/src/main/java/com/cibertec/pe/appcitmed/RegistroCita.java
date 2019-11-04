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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cibertec.pe.appcitmed.Entidades.Especialista;
import com.cibertec.pe.appcitmed.Entidades.Horario_Especialista;
import com.cibertec.pe.appcitmed.Entidades.Sede;
import com.cibertec.pe.appcitmed.Persistencia.RegistroCitaRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class RegistroCita extends AppCompatActivity  implements View.OnClickListener{

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    public  final Calendar c = Calendar.getInstance();

    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    EditText edtHoraconsulta;
    EditText edtfechaconsulta;
    Button btnpagar;
    ImageButton ibObtenerFecha;
    ImageButton ibObtenerHora;
    ProgressBar progressBar,progressBar2;

    ListView lstespecialistas;
    ListView lstturnos;
    ListView lstsedes;

    private AsyncHttpClient cliente;


    RadioButton rbSede1,rbSede2,rbSede3,rbSede4,rbSede5;
    Spinner spnEspecialidad;
    public String especialista;
    public String sede;
    public String dni_esp;
    public int idhorario;
    public int idsede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_solicitud_uno);
        cliente = new AsyncHttpClient();


        spnEspecialidad=findViewById(R.id.spn_especialidad);
        progressBar=findViewById(R.id.progressBar);
        progressBar2=findViewById(R.id.progressBar2);

        edtfechaconsulta=findViewById(R.id.edt_fechaconsulta);
        edtHoraconsulta=findViewById(R.id.edt_horaconsulta);

        ibObtenerFecha=findViewById(R.id.ib_obtener_fecha);
        ibObtenerFecha.setOnClickListener(this);


        /**SELECCION DE DOCTOR - LISTA*******/
        lstespecialistas=findViewById(R.id.lst_especialistas);
        lstturnos=findViewById(R.id.lst_turno);
        lstsedes=findViewById(R.id.lst_sede);

        /****** SELECCION DE SEDE*/


        Intent i = this.getIntent();
        final String vdni = i.getStringExtra("vvdni");

        ArrayAdapter spnadapter = ArrayAdapter.createFromResource(this,R.array.spnrEspecialidad,R.layout.support_simple_spinner_dropdown_item);
        spnEspecialidad.setAdapter(spnadapter);
        spnEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                obtenerEspecialistasXespcialidad(position);
                //ValidarSedes(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        /*****************EVENTO DEL BOTON  [ P A G A R ] ****************/
        btnpagar = findViewById(R.id.btn_reservar_cita);
        btnpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String estado="REALIZADO";
                String fecha= edtfechaconsulta.getText().toString();
                //String dni_esp="20000004";

                progressBar.setVisibility(View.VISIBLE);

                if(spnEspecialidad.getSelectedItemPosition()!=0){
                    if (!fecha.isEmpty()){
                        Response.Listener<String> respuesta = new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonRespuesta = new JSONObject(response);
                                        boolean ok = jsonRespuesta.getBoolean("success");
                                        if(ok=true){
                                            Intent i = new Intent(RegistroCita.this,MenuPrincipal.class);
                                            /**##### MANTIENE LAS VARIABLES VIVAS  ####*/
                                            i.putExtra("dni",vdni);
                                            progressBar.setVisibility(View.GONE);
                                            RegistroCita.this.startActivity(i);
                                            RegistroCita.this.finish();
                                        }
                                        else{
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(RegistroCita.this);
                                            alerta.setMessage("ERROR AL GENERAR CITA").setNegativeButton("REINTENTAR",null).create().show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                    catch (JSONException e){
                                        e.getMessage();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            };
                        RegistroCitaRequest r = new RegistroCitaRequest(vdni,dni_esp,fecha,estado,respuesta);
                        RequestQueue cola = Volley.newRequestQueue(RegistroCita.this);
                        cola.add(r);
                        Toast.makeText(RegistroCita.this,"Realizado",Toast.LENGTH_LONG).show();
                    }
                    else{Toast.makeText(RegistroCita.this,"Ingrese la fecha",Toast.LENGTH_LONG).show();progressBar.setVisibility(View.GONE);}}
                    else{Toast.makeText(RegistroCita.this,"Seleccione una Especialidad",Toast.LENGTH_LONG).show();progressBar.setVisibility(View.GONE);
                }
            }
        });

        /*************************************TERMINA EL EVENTO DEL BOTON************************************/
    }

    /******     M   E   T   O   D   O   S    ********/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                edtfechaconsulta.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
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
            lstsedes.setAdapter(adaptador);
        }catch (JSONException e){e.printStackTrace();}
    }

    private void obtenerHorarioEspecialista(int codhorario){
        String url ="https://bdproy.000webhostapp.com/obtenerHorarioEspecialista.php?";
        String parametro= "idhorario="+ codhorario;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarHorarioEspecialista(new String (responseBody));
                    //progressBar2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }
    private void listarHorarioEspecialista(String respuesta){
        final ArrayList<Horario_Especialista> lista = new ArrayList<>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta) ;
            Horario_Especialista he = new Horario_Especialista();
            for (int i=0; i <jsonArreglo.length();i++){

                he.setTurno(jsonArreglo.getJSONObject(i).getString("turno"));
                he.setHora_entrada(jsonArreglo.getJSONObject(i).getString("hora_entrada"));
                he.setHora_salida(jsonArreglo.getJSONObject(i).getString("hora_salida"));
                he.setSede_id(jsonArreglo.getJSONObject(i).getInt("sede_id"));
                lista.add(he);
            }
            idsede=he.getSede_id();
            ArrayAdapter<Horario_Especialista> adaptador=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
            lstturnos.setAdapter(adaptador);
            obtenerSede(idsede);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void obtenerEspecialistasXespcialidad(int idespecialidad){
        progressBar2.setVisibility(View.VISIBLE);
        String url ="https://bdproy.000webhostapp.com/obtenerEspecialistaXEspecialidad.php?";
        String parametro= "idespecialidad="+ idespecialidad;
        cliente.post(url + parametro, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    listarEspecialistaXespecialidad(new String (responseBody));
                    progressBar2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    private void listarEspecialistaXespecialidad(String respuesta){
        final ArrayList<Especialista> lista = new ArrayList<>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++){
                Especialista esp = new Especialista();
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
                lista.add(esp);
            }
            ArrayAdapter<Especialista> adaptador=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
            lstespecialistas.setAdapter(adaptador);
            lstespecialistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //view.setBackgroundColor(Color.DKGRAY);

                        dni_esp = lista.get(position).get_dni().toString();
                        idhorario=lista.get(position).get_idhorario();
                        obtenerHorarioEspecialista(idhorario);


                }
            });

        }
        catch (Exception e){e.printStackTrace();}
    }




}
