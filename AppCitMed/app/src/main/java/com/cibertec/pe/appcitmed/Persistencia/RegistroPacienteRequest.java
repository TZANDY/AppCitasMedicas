package com.cibertec.pe.appcitmed.Persistencia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroPacienteRequest extends StringRequest {
    private static final String ruta="https://bdproy.000webhostapp.com/generarPaciente.php";
    private Map<String,String> parametros;
    public RegistroPacienteRequest(String usuario,String clave,String tipo, Response.Listener<String> listener){
        super(Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("usuario",usuario+"");
        parametros.put("clave",clave+"");
        parametros.put("tipo",tipo+"");
    }
    public Map<String, String> getParams() {
        return parametros;
    }
}
