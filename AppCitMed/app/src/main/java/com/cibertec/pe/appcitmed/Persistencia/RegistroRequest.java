package com.cibertec.pe.appcitmed.Persistencia;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {
    private static final String ruta="https://bdproy.000webhostapp.com/registroPersona.php";
    private Map<String,String> parametros;
    public RegistroRequest(String dni,String nombre, String apellido, String clave, String correo,String fnacimiento,String celular,String estadocivil,int tipo, Response.Listener<String> listener){
        super(Request.Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("dni",dni+"");
        parametros.put("nombre",nombre+"");
        parametros.put("apellido",apellido+"");
        parametros.put("clave",clave+"");
        parametros.put("correo",correo+"");
        parametros.put("fnacimiento",fnacimiento+"");
        parametros.put("celular",celular+"");
        parametros.put("estadocivil",estadocivil+"");
        parametros.put("tipo",tipo+"");
    }


    public Map<String, String> getParams() {
        return parametros;
    }
}
