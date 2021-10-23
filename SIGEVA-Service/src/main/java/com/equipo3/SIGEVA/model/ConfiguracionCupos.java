package com.equipo3.SIGEVA.model;

import java.util.Date;

public class ConfiguracionCupos {

	private int duracionMinutos;
	private int numeroPacientes;

	private Date diaInicio;
	private Date diaFin;

	private Date horaInicio;
	private Date horaFin;

	public ConfiguracionCupos(int duracionMinutos, int numeroPacientes, Date diaInicio, Date diaFin, Date horaInicio,
			Date horaFin) {
		this.duracionMinutos = duracionMinutos;
		this.numeroPacientes = numeroPacientes;
		this.diaInicio = diaInicio;
		this.diaFin = diaFin;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
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

	public Date getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Date diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Date getDiaFin() {
		return diaFin;
	}

	public void setDiaFin(Date diaFin) {
		this.diaFin = diaFin;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	@Override
	public String toString() {
		return "ConfiguracionCupos [duracionMinutos=" + duracionMinutos + ", numeroPacientes=" + numeroPacientes
				+ ", diaInicio=" + diaInicio + ", diaFin=" + diaFin + ", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + "]";
	}

}
