package com.cibertec.pe.appcitmed.Entidades;

public class CitaMedica {

    private int _idcita;
    private String _asegurado_dni;
    private String _especialista_dni;
    private String _fecha;
    private String _estado;

    public int get_idcita() {
        return _idcita;
    }

    public void set_idcita(int _idcita) {
        this._idcita = _idcita;
    }

    public String get_asegurado_dni() {
        return _asegurado_dni;
    }

    public void set_asegurado_dni(String _asegurado_dni) {
        this._asegurado_dni = _asegurado_dni;
    }

    public String get_especialista_dni() {
        return _especialista_dni;
    }

    public void set_especialista_dni(String _especialista_dni) {
        this._especialista_dni = _especialista_dni;
    }

    public String get_fecha() {
        return _fecha;
    }

    public void set_fecha(String _fecha) {
        this._fecha = _fecha;
    }


    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }


    public CitaMedica() {
    }

    @Override
    public String toString() {
        return "\n"+
                "  CODIGO DE CITA:  "+ "\t"+ _idcita +"\n"+
                "  FECHA:  "+ "\t"+_fecha +"\n"+
                "  DNI-DOCTOR:  "+ "\t"+_especialista_dni +"\n"+
                "  DNI-PACIENTE:  "+ "\t"+_asegurado_dni +"\n"+
                "  ESTADO:  "+ "\t"+_estado + "\n"+
                "* * *" + "\n";
    }

}

