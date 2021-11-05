package com.equipo3.SIGEVA.dto;

import com.equipo3.SIGEVA.model.CupoSimple;

import java.util.ArrayList;
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

    public void setNumVacunas(int numVacunas) {
        this.numVacunas = numVacunas;
    }
}
