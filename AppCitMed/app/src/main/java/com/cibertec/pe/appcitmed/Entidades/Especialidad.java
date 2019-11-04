package com.cibertec.pe.appcitmed.Entidades;

public class Especialidad {
    private String _codesp;
    private String _nomesp;


    public Especialidad(String _codesp, String _nomesp) {
        this._codesp = _codesp;
        this._nomesp = _nomesp;
    }

    public String get_codesp() {
        return _codesp;
    }

    public void set_codesp(String _codesp) {
        this._codesp = _codesp;
    }

    public String get_nomesp() {
        return _nomesp;
    }

    public void set_nomesp(String _nomesp) {
        this._nomesp = _nomesp;
    }
}
