package com.cibertec.pe.appcitmed.Entidades;

public class Sede {
    private int idsede;
    private String distrito;
    private String direccion;

    public Sede() {
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return " "+ distrito + '\t'+
                " -> " + direccion;
    }
}
