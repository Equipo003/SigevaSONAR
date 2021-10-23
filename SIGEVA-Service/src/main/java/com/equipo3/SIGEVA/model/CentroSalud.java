package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CentroSalud {

	@Id
	private String idCentroSalud;

	@Field
	private String nombreCentro;

	@Field
	private int numVacunasDisponibles;

	@Field
	private ConfiguracionCupos configuracionCupos;

	@Field
	private Vacuna vacuna;

	@Field
	private String direccion;

	public CentroSalud() {

	}

	public CentroSalud(String nombreCentro, int numVacunasDisponibles, ConfiguracionCupos configuracionCupos,
			Vacuna vacuna, String direccion) {
		this.idCentroSalud = UUID.randomUUID().toString();
		this.nombreCentro = nombreCentro;
		this.numVacunasDisponibles = numVacunasDisponibles;
		this.configuracionCupos = configuracionCupos;
		this.vacuna = vacuna;
		this.direccion = direccion;
	}

	public String getIdCentroSalud() {
		return idCentroSalud;
	}

	public void setIdCentroSalud(String idCentroSalud) {
		this.idCentroSalud = idCentroSalud;
	}

	public String getNombreCentro() {
		return nombreCentro;
	}

	public void setNombreCentro(String nombreCentro) {
		this.nombreCentro = nombreCentro;
	}

	public int getNumVacunasDisponibles() {
		return numVacunasDisponibles;
	}

	public void setNumVacunasDisponibles(int numVacunasDisponibles) {
		this.numVacunasDisponibles = numVacunasDisponibles;
	}

	public ConfiguracionCupos getConfiguracionCupos() {
		return configuracionCupos;
	}

	public void setConfiguracionCupos(ConfiguracionCupos configuracionCupos) {
		this.configuracionCupos = configuracionCupos;
	}

	public Vacuna getVacuna() {
		return vacuna;
	}

	public void setVacuna(Vacuna vacuna) {
		this.vacuna = vacuna;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "CentroSalud [idCentroSalud=" + idCentroSalud + ", nombreCentro=" + nombreCentro
				+ ", numVacunasDisponibles=" + numVacunasDisponibles + ", configuracionCupos=" + configuracionCupos
				+ ", vacuna=" + vacuna + ", direccion=" + direccion + "]";
	}

}
