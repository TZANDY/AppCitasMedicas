package com.cibertec.pe.appcitmed.Persistencia;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroCitaRequest extends StringRequest {
    private static final String ruta="https://bdproy.000webhostapp.com/registroCita2.php";
    private Map<String,String> parametros;
    public RegistroCitaRequest(String asegurado_dni, String especialista_dni, String fecha,String estado, Response.Listener<String> listener){
        super(Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("aseguradodni",asegurado_dni+"");
        parametros.put("especialistadni",especialista_dni+"");
        parametros.put("fecha",fecha+"");
        parametros.put("estado",estado+"");
    }

    public Map<String, String> getParams() {
        return parametros;
    }
}
