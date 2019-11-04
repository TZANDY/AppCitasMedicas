package com.cibertec.pe.appcitmed.Entidades;

public class Horario_Especialista {
    private int idhorario;
    private String turno;
    private String hora_entrada;
    private String hora_salida;
    private int sede_id;

    public Horario_Especialista(){}

    public int getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(int idhorario) {
        this.idhorario = idhorario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getSede_id() {
        return sede_id;
    }

    public void setSede_id(int sede_id) {
        this.sede_id = sede_id;
    }

    @Override
    public String toString() {
        return "" + turno + '\t' +
                " -> " +" I: " + hora_entrada +" hrs"+ '\t' +" <> "+
                " F: " + hora_salida +" hrs";
    }
}
