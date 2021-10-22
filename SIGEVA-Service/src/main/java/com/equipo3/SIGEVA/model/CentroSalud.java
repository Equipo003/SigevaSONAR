package com.equipo3.SIGEVA.model;

import java.util.Date;

public class CentroSalud {

	private String uuid;
	private int numVacunasDisponibles;
	private Vacuna vacuna;
	private ConfiguracionCupos configuracionCupos;
	private Date horaApertura;
	private Date horaCierre;
	private String direccion;

	public CentroSalud() {

	}

	public CentroSalud(String uuid, int numVacunasDisponibles, Vacuna vacuna, ConfiguracionCupos configuracionCupos,
			Date horaApertura, Date horaCierre, String direccion) {
		this.uuid = uuid;
		this.numVacunasDisponibles = numVacunasDisponibles;
		this.vacuna = vacuna;
		this.configuracionCupos = configuracionCupos;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.direccion = direccion;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}

	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		this.numVacunasDisponibles = numVacunasDisponibles;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public ConfiguracionCupos getConfiguracionCupos() {
		return configuracionCupos;
	}

	public void setConfiguracionCupos(ConfiguracionCupos configuracionCupos) {
		this.configuracionCupos = configuracionCupos;
	}

	public Date getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "CentroSalud [uuid=" + uuid + ", numVacunasDisponibles=" + numVacunasDisponibles + ", vacuna=" + vacuna
				+ ", configuracionCupos=" + configuracionCupos + ", horaApertura=" + horaApertura + ", horaCierre="
				+ horaCierre + ", direccion=" + direccion + "]";
	}

}
