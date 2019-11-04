package com.cibertec.pe.appcitmed.Persistencia;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String ruta="https://bdproy.000webhostapp.com/loginAdministrador.php";
    private Map<String,String> parametros;
    public LoginRequest(String dni,String clave, Response.Listener<String> listener){
        super(Request.Method.POST,ruta,listener,null);
        parametros = new HashMap<>();
        parametros.put("usuario",dni+"");
        parametros.put("clave",clave+"");

    }

    public Map<String, String> getParams() {
        return parametros;
    }

}
