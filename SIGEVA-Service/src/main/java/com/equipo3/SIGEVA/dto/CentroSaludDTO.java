package com.equipo3.SIGEVA.dto;

import java.util.UUID;

public class CentroSaludDTO {
	private String id;
    private String nombreCentro;
    private int numVacunasDisponibles;
    private String direccion;

    public CentroSaludDTO() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public int getNumVacunasDisponibles() {
        return numVacunasDisponibles;
    }

    public void setNumVacunasDisponibles(int numVacunasDisponibles) {
        this.numVacunasDisponibles = numVacunasDisponibles;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
