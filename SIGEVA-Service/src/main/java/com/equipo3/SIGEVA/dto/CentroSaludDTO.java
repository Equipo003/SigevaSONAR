package com.equipo3.SIGEVA.dto;

import java.util.UUID;

public class CentroSaludDTO {
	private String id;
    private String nombreCentro;
    private int numVacunasDisponibles;
    private String direccion;
    private VacunaDTO vacuna;

    public CentroSaludDTO() {
        this.id = UUID.randomUUID().toString();
    }

    public CentroSaludDTO(String nombreCentro, String direccion, int numVacunasDisponibles) {
        this.id = UUID.randomUUID().toString();
        this.nombreCentro = nombreCentro;
        this.numVacunasDisponibles = numVacunasDisponibles;
        this.direccion = direccion;
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

    public VacunaDTO getVacuna() {
        return vacuna;
    }

    public void setVacuna(VacunaDTO vacunaDTO) {
        this.vacuna = vacunaDTO;
    }

    @Override
    public String toString() {
        return "CentroSaludDTO{" +
                "id='" + id + '\'' +
                ", nombreCentro='" + nombreCentro + '\'' +
                ", numVacunasDisponibles=" + numVacunasDisponibles +
                ", direccion='" + direccion + '\'' +
                ", vacuna=" + vacuna.toString() +
                '}';
    }
}
