package com.equipo3.SIGEVA.model;

public class CentroSaludDTO {
    private String nombreCentro;
    private int numVacunasDisponibles;
    private String direccion;

    public CentroSaludDTO(String nombreCentro, int numVacunasDisponibles, String direccion) {
        this.nombreCentro = nombreCentro;
        this.numVacunasDisponibles = numVacunasDisponibles;
        this.direccion = direccion;
    }

    public CentroSaludDTO(CentroSalud centroSalud){
        this.nombreCentro = centroSalud.getNombreCentro();
        this.numVacunasDisponibles = centroSalud.getNumVacunasDisponibles();
        this.direccion = centroSalud.getDireccion();
    }

    public CentroSaludDTO() {

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
