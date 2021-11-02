package com.equipo3.SIGEVA.model;

public class RolDTO {

    String nombre;

    public RolDTO(String nombre) {
        this.nombre = nombre;
    }

    public RolDTO(Rol rol){
        this.nombre = rol.getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
