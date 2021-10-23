package com.equipo3.SIGEVA.model;

public class ConfiguracionCupos {

	private String uuid;
	private int duracionMinutos;
	private int numPacientesMaximo;

	public ConfiguracionCupos() {

	}

	public ConfiguracionCupos(String uuid, int duracionMinutos, int numPacientesMaximo) {
		this.uuid = uuid;
		this.duracionMinutos = duracionMinutos;
		this.numPacientesMaximo = numPacientesMaximo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public int getNumPacientesMaximo() {
		return numPacientesMaximo;
	}

	public void setNumPacientesMaximo(int numPacientesMaximo) {
		this.numPacientesMaximo = numPacientesMaximo;
	}

	@Override
	public String toString() {
		return "ConfiguracionCupos [uuid=" + uuid + ", duracionMinutos=" + duracionMinutos + ", numPacientesMaximo="
				+ numPacientesMaximo + "]";
	}

}
