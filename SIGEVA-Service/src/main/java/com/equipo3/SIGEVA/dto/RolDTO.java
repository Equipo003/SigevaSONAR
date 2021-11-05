package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.model.Rol;

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
