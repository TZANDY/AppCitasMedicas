package com.cibertec.pe.appcitmed.Entidades;

public class PacienteAsegurado {
    private int _idasegurado;
    private String _dni;
    private String _nombre;
    private String _apellido;
    private String _correo;
    private String _fnacimiento;
    private String _celular;
    private String _estadocivil;
    private String _tipo;

    public PacienteAsegurado(){}

    public int get_idasegurado() {
        return _idasegurado;
    }

    public void set_idasegurado(int _idasegurado) {
        this._idasegurado = _idasegurado;
    }

    public String get_dni() {
        return _dni;
    }

    public void set_dni(String _dni) {
        this._dni = _dni;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_apellido() {
        return _apellido;
    }

    public void set_apellido(String _apellido) {
        this._apellido = _apellido;
    }

    public String get_correo() {
        return _correo;
    }

    public void set_correo(String _correo) {
        this._correo = _correo;
    }

    public String get_fnacimiento() {
        return _fnacimiento;
    }

    public void set_fnacimiento(String _fnacimiento) {
        this._fnacimiento = _fnacimiento;
    }

    public String get_celular() {
        return _celular;
    }

    public void set_celular(String _celular) {
        this._celular = _celular;
    }

    public String get_estadocivil() {
        return _estadocivil;
    }

    public void set_estadocivil(String _estadocivil) {
        this._estadocivil = _estadocivil;
    }

    public String get_tipo() {
        return _tipo;
    }

    public void set_tipo(String _tipo) {
        this._tipo = _tipo;
    }

    public String toString(){
        return _nombre+"\t"+_apellido+"\t"+_dni;
    }
}
