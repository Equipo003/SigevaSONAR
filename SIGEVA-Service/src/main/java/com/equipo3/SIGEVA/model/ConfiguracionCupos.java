package com.equipo3.SIGEVA.model;

public class ConfiguracionCupos {

	private String uuid;
	private int duracionMinutos;
	private int numPacientesMaximo;

	public ConfiguracionCupos() {

	}

	public ConfiguracionCupos(String uuid, int duracionMinutos, int numPacientesMaximo) {
		/*
		 * SE PROPONE:
		 * 
		 * 1. Al momento de instanciar CentroSalud, en el propio constructor, llámese a
		 * la Dao de ConfiguracionCupos para leer y que nos devuelva ese objeto
		 * instanciado.
		 * 
		 * 2. Puesto que cada Cupo pertenece/depende de un Centro: dentro exactamente
		 * del método crearCentroSalud() del Controller, será exactamente cuando (y no
		 * antes) se creen los Cupos hasta 31/12/2021.
		 */
		this.uuid = uuid;
		this.duracionMinutos = 30;
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
