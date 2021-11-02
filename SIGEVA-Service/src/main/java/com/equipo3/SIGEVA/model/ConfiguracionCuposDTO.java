package com.equipo3.SIGEVA.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfiguracionCuposDTO {
    private int duracionMinutos;
    private int numeroPacientes;
    private int duracionJornadaHoras;
    private int duracionJornadaMinutos;
    private String fechaInicio;

    public ConfiguracionCuposDTO(int duracionMinutos, int numeroPacientes, int duracionJornadaHoras, int duracionJornadaMinutos, String fechaInicio) {
        this.duracionMinutos = duracionMinutos;
        this.numeroPacientes = numeroPacientes;
        this.duracionJornadaHoras = duracionJornadaHoras;
        this.duracionJornadaMinutos = duracionJornadaMinutos;
        this.fechaInicio = fechaInicio;
    }

    public ConfiguracionCuposDTO(ConfiguracionCupos configuracionCupos){
        this.duracionMinutos = configuracionCupos.getDuracionMinutos();
        this.numeroPacientes = configuracionCupos.getNumeroPacientes();
        this.duracionJornadaHoras = configuracionCupos.getDuracionJornadaHoras();
        this.duracionMinutos = configuracionCupos.getDuracionMinutos();
        this.fechaInicio = configuracionCupos.getFechaInicio();
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getNumeroPacientes() {
        return numeroPacientes;
    }

    public void setNumeroPacientes(int numeroPacientes) {
        this.numeroPacientes = numeroPacientes;
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

    public Date getFechaInicioAsDate() {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            return formateador.parse(fechaInicio);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public Date getHoraFin() {
        Date fechaFin = getFechaInicioAsDate();
        fechaFin.setHours(fechaFin.getHours() + this.duracionJornadaHoras);
        fechaFin.setMinutes(fechaFin.getMinutes() + this.duracionJornadaMinutos);

        return fechaFin;
    }
}
