package com.equipo3.SIGEVA.dto;

import java.util.Objects;
import java.util.UUID;

public class VacunaDTO {
    private String id;
    private String nombre;
    private int diasEntreDosis;
    private int numDosis;

    public VacunaDTO(){
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

    public int getDiasEntreDosis() {
        return diasEntreDosis;
    }

    public void setDiasEntreDosis(int diasEntreDosis) {
        this.diasEntreDosis = diasEntreDosis;
    }

    public int getNumDosis() {
        return numDosis;
    }

    public void setNumDosis(int numDosis) {
        this.numDosis = numDosis;
    }

    @Override
    public String toString() {
        return "VacunaDTO{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", diasEntreDosis=" + diasEntreDosis +
                ", numDosis=" + numDosis +
                '}';
    }
}
