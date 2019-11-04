package com.cibertec.pe.appcitmed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.pe.appcitmed.Entidades.Especialista;
import com.cibertec.pe.appcitmed.Entidades.Sede;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OPEspecialista extends AppCompatActivity {

    private AsyncHttpClient cliente;
    Button btnBuscarEsp;
    EditText edtDniEsp;
    TextView tvdniEsp;
    EditText edtNombreEsp;
    EditText edtApellidoEsp;
    EditText edtCorreoEsp;
    EditText edtFnacimientoEsp;
    EditText edtCelularEsp;
    Spinner spnEstadocivil;
    Spinner spnEspecialidad;
    Spinner spnHorario;
    ListView lstSede;

    int idEspecialista;
    public String estadocivil;
    public int idespecialidad;
    public int idhorario;
    int estado;

    Button btnActualizar;
    Button btnSalir;
    Button btnCancelar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_especialista);
        cliente = new AsyncHttpClient();
        final String dniv = getIntent().getStringExtra("dni");

        edtDniEsp=findViewById(R.id.edt_dni_espe);
        tvdniEsp = findViewById(R.id.tv_dni_espe);
        edtNombreEsp=findViewById(R.id.edt_nom_espe);
        edtApellidoEsp=findViewById(R.id.edt_ape_espe);
        edtCorreoEsp=findViewById(R.id.edt_correo_espe);
        edtFnacimientoEsp=findViewById(R.id.edt_fnacimiento_espe);
        edtCelularEsp=findViewById(R.id.edt_celular_espe);

        spnEstadocivil=findViewById(R.id.spn_ec_esp_op);
        spnEspecialidad=findViewById(R.id.spn_espcl_esp_op);
        spnHorario=findViewById(R.id.spn_horario_esp_op);



        spnHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        lstSede=findViewById(R.id.lst_sede_op);

        btnCancelar=findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OPEspecialista.this,MenuPrincipalAdministrador.class);
                i.putExtra("dni",dniv);
                startActivity(i);
            }
        });


        btnBuscarEsp=findViewById(R.id.btn_buscar_espe);
        btnBuscarEsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dniesp= edtDniEsp.getText().toString();
                obtenerEspecialistaXdni(dniesp);
                spnEstadocivil.setSelection(estado);
                spnEspecialidad.setSelection(idespecialidad);
                spnHorario.setSelection(idhorario);
            }
        });
        btnActualizar=findViewById(R.id.btn_actualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre=edtNombreEsp.getText().toString().trim();
                String apellido=edtApellidoEsp.getText().toString().trim();
                String correo=edtCorreoEsp.getText().toString();
                String fnacimiento=edtFnacimientoEsp.getText().toString().trim();
                String celular=edtCelularEsp.getText().toString().trim();
                String estadociv=spnEstadocivil.getSelectedItem().toString();
                int especialidadid = spnEspecialidad.getSelectedItemPosition();
                int horarioid = spnHorario.getSelectedItemPosition();

                if(!estadociv.equals(0)){
                    if(especialidadid!=0){
                        if(horarioid!=0){
                            ActualizarEspecialistaWork(idEspecialista,nombre,apellido,correo,fnacimiento,celular,estadociv,especialidadid,horarioid);
                            edtDniEsp.setText("");
                            tvdniEsp.setText("");
                            edtNombreEsp.setText("");
                            edtApellidoEsp.setText("");
                            edtCorreoEsp.setText("");
                            edtFnacimientoEsp.setText("");
                            edtCelularEsp.setText("");
                            spnEstadocivil.setSelection(0);
                            spnEspecialidad.setSelection(0);
                            spnHorario.setSelection(0);

                        }else{Toast.makeText(OPEspecialista.this, "Seleccione un Turno", Toast.LENGTH_LONG).show();}
                    }else{Toast.makeText(OPEspecialista.this, "Seleccione una Especialidad", Toast.LENGTH_LONG).show();}
                }else {Toast.makeText(OPEspecialista.this, "Seleccione el Estado Civil", Toast.LENGTH_LONG).show();}

            }
        });
        btnSalir=findViewById(R.id.btn_salir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OPEspecialista.this,Bienvenido.class);
                OPEspecialista.this.startActivity(i);
                OPEspecialista.this.finish();
            }
        });
    }
    public void AsignarEstado(String ec){
        if(ec.equals("Soltero")){
            estado=1;
        }
        if(ec.equals("Casado")){
            estado=2;
        }
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
            lstSede.setAdapter(adaptador);
        }catch (JSONException e){e.printStackTrace();}
    }
    private void ActualizarEspecialistaWork(int idespecialista,String nombre,String apellido,String correo,String fnacimiento,String celular,String estadociv,int especialidad,int horario){

        String url ="https://bdproy.000webhostapp.com/actualizarEspecialista.php?";

        String p1= "idespecialista="+ idespecialista+"&";
        String p2= "nombre="+ nombre+"&";
        String p3= "apellido="+ apellido+"&";
        String p4= "correo="+ correo+"&";
        String p5= "fnacimiento="+ fnacimiento+"&";
        String p6= "celular="+ celular+"&";
        String p7= "estadocivil="+ estadociv+"&";
        String p8= "idespecialidad="+ especialidad+"&";
        String p9= "idhorario="+ horario ;
        cliente.post(url + p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    try {
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(OPEspecialista.this, "ESPECIALISTA ACTUALIZADO", Toast.LENGTH_LONG).show();
                    /*Intent i = new Intent(OPEspecialista.this,MenuPrincipalAdministrador.class);
                    startActivity(i);*/
                }
                else {
                    Toast.makeText(OPEspecialista.this, "HUBO UN ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });


    }
    private void obtenerEspecialistaXdni(String dni){
        //progressBar.setVisibility(View.VISIBLE);
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
                esp.set_idespecialista(jsonArreglo.getJSONObject(i).getInt("idespecialista"));
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
            idEspecialista=esp.get_idespecialista();
            edtDniEsp.setText(esp.get_dni());
            tvdniEsp.setText(esp.get_dni());
            edtNombreEsp.setText(esp.get_nombre());
            edtApellidoEsp.setText(esp.get_apellido());
            edtCorreoEsp.setText(esp.get_correo());
            edtFnacimientoEsp.setText(esp.get_fnacimiento());
            edtCelularEsp.setText(esp.get_celular());

            estadocivil = esp.get_estadocivil();
            AsignarEstado(estadocivil);
            idespecialidad=esp.get_idespecialidad();
            idhorario =esp.get_idhorario();

        }
        catch (Exception e){e.printStackTrace();}
    }
}
