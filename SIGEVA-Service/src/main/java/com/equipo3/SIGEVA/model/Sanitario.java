package com.equipo3.SIGEVA.model;

import java.util.UUID;

public class Sanitario extends Usuario{
    public Sanitario() {
        super();
        this.setIdUsuario(UUID.randomUUID().toString());
    }
}
