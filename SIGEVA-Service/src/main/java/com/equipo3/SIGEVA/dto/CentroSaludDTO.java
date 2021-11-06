package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.controller.Condicionamientos;
import com.equipo3.SIGEVA.model.Vacuna;

import java.util.UUID;

public class CentroSaludDTO {
	private String id;
    private String nombreCentro;
    private int numVacunasDisponibles;
    private String direccion;
    private Vacuna vacuna;

    public CentroSaludDTO() {
        this.id = UUID.randomUUID().toString();
        this.vacuna = new Vacuna("Pfizer", Condicionamientos.tiempoEntreDosis(), 2);
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

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    @Override
    public String toString() {
        return "CentroSaludDTO{" +
                "id='" + id + '\'' +
                ", nombreCentro='" + nombreCentro + '\'' +
                ", numVacunasDisponibles=" + numVacunasDisponibles +
                ", direccion='" + direccion + '\'' +
                ", vacuna=" + vacuna +
                '}';
    }
}
