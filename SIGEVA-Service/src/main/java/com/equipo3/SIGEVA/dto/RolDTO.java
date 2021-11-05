package com.equipo3.SIGEVA.dto;

import java.util.UUID;

public class RolDTO {

    String id;
    String nombre;

    public RolDTO() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
