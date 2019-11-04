package com.cibertec.pe.appcitmed.Persistencia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class REEspecialistaRequest extends StringRequest {
    private static final String ruta="https://bdproy.000webhostapp.com/registroEspecialista.php";
    private Map<String,String> parametros;
    public REEspecialistaRequest(String dni, String nombre, String apellido, String correo, String fnacimiento, String celular, String estadocivil, String tipo,int idespecialidad,int idhorario, Response.Listener<String> listener){
        super(Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("dni",dni+"");
        parametros.put("nombre",nombre+"");
        parametros.put("apellido",apellido+"");
        parametros.put("correo",correo+"");
        parametros.put("fnacimiento",fnacimiento+"");
        parametros.put("celular",celular+"");
        parametros.put("estadocivil",estadocivil+"");
        parametros.put("tipo",tipo+"");
        parametros.put("idespecialidad",idespecialidad+"");
        parametros.put("idhorario",idhorario+"");
    }


    public Map<String, String> getParams() {
        return parametros;
    }
}
