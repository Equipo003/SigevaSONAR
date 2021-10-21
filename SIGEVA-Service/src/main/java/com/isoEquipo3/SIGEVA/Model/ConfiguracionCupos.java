package com.isoEquipo3.SIGEVA.Model;

public class ConfiguracionCupos{

    private int duracionMinutos;
    private int numeroPacientes;


    public ConfiguracionCupos(){
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
}