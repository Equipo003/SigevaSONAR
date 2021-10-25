package com.equipo3.SIGEVA.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class ConfiguracionCupos{

    private int duracionMinutos;
    private int numeroPacientes;
    private int duracionJornadaHoras;
    private int duracionJornadaMinutos;
    private String fechaInicio;


    public ConfiguracionCupos(){
    }

    public ConfiguracionCupos(int duracionMinutos, int numeroPacientes, int duracionJornadaHoras, int duracionJornadaMinutos, String fechaInicio){
        this.duracionMinutos = duracionMinutos;
        this.numeroPacientes = numeroPacientes;
        this.duracionJornadaHoras = duracionJornadaHoras;
        this.duracionJornadaMinutos = duracionJornadaMinutos;
        this.fechaInicio = fechaInicio;
    }



    public void setDuracionMinutos(int duracionMinutos){
        this.duracionMinutos = duracionMinutos;
    }

    public void setNumeroPacientes(int numeroPacientes){
        this.numeroPacientes = numeroPacientes;
    }

    public int getDuracionMinutos(){
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

    public Date getHoraFin(){
        Date fechaFin = new Date(this.fechaInicio);
        fechaFin.setHours(fechaFin.getHours()+this.duracionJornadaHoras);
        fechaFin.setMinutes(fechaFin.getMinutes()+this.duracionJornadaMinutos);

        return fechaFin;
    }
}