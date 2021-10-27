package com.equipo3.SIGEVA.model;

import java.util.UUID;

public class Paciente extends Usuario{

    private int numVacunas;

    public Paciente() {
        super();
        this.setIdUsuario(UUID.randomUUID().toString());
        this.setNumVacunas(0);
    }

    public int getNumVacunas() {
        return numVacunas;
    }

    public void setNumVacunas(int numVacunas) {
        this.numVacunas = numVacunas;
    }
}
