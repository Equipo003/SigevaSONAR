package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.controller.Condicionamientos;
import com.equipo3.SIGEVA.exception.CupoCitasException;
import com.equipo3.SIGEVA.exception.NumVacunasInvalido;
import com.equipo3.SIGEVA.exception.PacienteYaVacunadoException;
import com.equipo3.SIGEVA.model.CupoSimple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacienteDTO extends UsuarioDTO{

    private boolean asignado;
    private List<CupoSimple> cuposAsignados;

    private boolean vacunado;
    private int numVacunas;

    public PacienteDTO() {
        super();
        this.asignado = false;
        this.cuposAsignados = new ArrayList<>();
        this.vacunado = false;
        this.numVacunas = 0;
    }

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }

    public List<CupoSimple> getCuposAsignados() {
        return cuposAsignados;
    }

    public void setCuposAsignados(List<CupoSimple> cuposAsignados) {
        this.cuposAsignados = cuposAsignados;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    public int getNumVacunas() {
        return numVacunas;
    }

    public void setNumVacunas(int numVacunas) throws PacienteYaVacunadoException, NumVacunasInvalido {
        if (numVacunas < 0 && Condicionamientos.control()) {
            throw new NumVacunasInvalido("El número de vacunas especificado es inválido.");

        } else if (numVacunas > 2 && Condicionamientos.control()) {
            throw new PacienteYaVacunadoException("El paciente ya estaba totalmente vacunado.");

        } else {
            this.numVacunas = numVacunas;
        }
    }

    public int getNumCitasPendientes() {
        int count = 0;
        Date hoy = new Date();
        for (int i = 0; i < cuposAsignados.size(); i++) {
            if (hoy.before(cuposAsignados.get(i).getFechaYHoraInicio())) {
                count++;
            }
        }
        return count;
    }

    public void asignarCupo(CupoSimple cupo) throws CupoCitasException {
        if (cuposAsignados.contains(cupo) && Condicionamientos.control()) {
            throw new CupoCitasException("Ya tenía este cupo asignado.");
        } else {
            cuposAsignados.add(cupo);
        }
    }

    @Override
    public String toString() {
        return "PacienteDTO{" + super.toString() +
                ", asignado=" + asignado +
                ", cuposAsignados=" + cuposAsignados +
                ", vacunado=" + vacunado +
                ", numVacunas=" + numVacunas +
                '}';
    }
}
