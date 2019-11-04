package com.cibertec.pe.appcitmed.Entidades;

public class Especialista {
    private int _idespecialista;
    private String _dni;
    private String _nombre;
    private String _apellido;
    private String _correo;
    private String _fnacimiento;
    private String _celular;
    private String _estadocivil;
    private String _tipo;
    private int _idespecialidad;
    private int _idhorario;

    public int get_idespecialista() {
        return _idespecialista;
    }

    public void set_idespecialista(int _idespecialista) {
        this._idespecialista = _idespecialista;
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

    public int get_idespecialidad() {
        return _idespecialidad;
    }

    public void set_idespecialidad(int _idespecialidad) {
        this._idespecialidad = _idespecialidad;
    }

    public int get_idhorario() {
        return _idhorario;
    }

    public void set_idhorario(int _idhorario) {
        this._idhorario = _idhorario;
    }

    @Override
    public String toString() {
        return "Dr. "+_nombre + " " +_apellido;
    }
}


