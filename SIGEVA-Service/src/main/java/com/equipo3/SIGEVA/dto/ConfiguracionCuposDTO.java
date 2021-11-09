package com.equipo3.SIGEVA.dto;

import java.util.UUID;

public class ConfiguracionCuposDTO {

    private String id;
    private int duracionMinutos;
    private int numeroPacientes;
    private int duracionJornadaHoras;
    private int duracionJornadaMinutos;
    private String fechaInicio;

    public ConfiguracionCuposDTO() {
        this.id = UUID.randomUUID().toString();
    }

    public ConfiguracionCuposDTO(int duracionMinutos, int numeroPacientes, int duracionJornadaHoras,
                                 int duracionJornadaMinutos, String fechaInicio) {
        this.id = UUID.randomUUID().toString();
        this.duracionMinutos = duracionMinutos;
        this.numeroPacientes = numeroPacientes;
        this.duracionJornadaHoras = duracionJornadaHoras;
        this.duracionJornadaMinutos = duracionJornadaMinutos;
        this.fechaInicio = fechaInicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public void setNumeroPacientes(int numeroPacientes) {
        this.numeroPacientes = numeroPacientes;
    }

    public int getDuracionMinutos() {
        return this.duracionMinutos;
    }

    public int getNumeroPacientes() {
        return numeroPacientes;
    }

    public int getDuracionJornadaHoras() {
        return duracionJornadaHoras;
    }

    public void setDuracionJornadaHoras(int duracionJornadaHoras) {
        this.duracionJornadaHoras = duracionJornadaHoras;
    }

    public int getDuracionJornadaMinutos() {
        return duracionJornadaMinutos;
    }

    public void setDuracionJornadaMinutos(int duracionJornadaMinutos) {
        this.duracionJornadaMinutos = duracionJornadaMinutos;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return "ConfiguracionCuposDTO{" +
                "duracionMinutos=" + duracionMinutos +
                ", numeroPacientes=" + numeroPacientes +
                ", duracionJornadaHoras=" + duracionJornadaHoras +
                ", duracionJornadaMinutos=" + duracionJornadaMinutos +
                ", fechaInicio='" + fechaInicio + '\'' +
                '}';
    }
}
