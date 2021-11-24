package com.equipo3.SIGEVA.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/***
 * Entidad Cupo.
 * Clase que representará el cupo al que estarán asociados otras entidades para vacunar.
 * 
 * 
 * @author Equipo3
 *
 */

@Document
public class Cupo implements Comparable<Cupo> {

	@Id
	private String uuidCupo;

	@Field
	private String uuidCentroSalud;

	@Field
	private Date fechaYHoraInicio;

	@Field
	private int tamanoActual;

	public Cupo() {
		this.uuidCupo = UUID.randomUUID().toString();
	}

	public Cupo(String uuidCentroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = UUID.randomUUID().toString();
		this.uuidCentroSalud = uuidCentroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	public Cupo(String uuidCupo, String uuidCentroSalud, Date fechaYHoraInicio, int tamanoActual) {
		this.uuidCupo = uuidCupo;
		this.uuidCentroSalud = uuidCentroSalud;
		this.fechaYHoraInicio = fechaYHoraInicio;
		this.tamanoActual = tamanoActual;
	}

	public String getUuidCupo() {
		return uuidCupo;
	}

	public void setUuidCupo(String uuidCupo) {
		this.uuidCupo = uuidCupo;
	}

	public String getUuidCentroSalud() {
		return uuidCentroSalud;
	}

	public void setUuidCentroSalud(String uuidCentroSalud) {
		this.uuidCentroSalud = uuidCentroSalud;
	}

	public Date getFechaYHoraInicio() {
		return fechaYHoraInicio;
	}

	public void setFechaYHoraInicio(Date fechaYHoraInicio) {
		this.fechaYHoraInicio = fechaYHoraInicio;
	}

	public int getTamanoActual() {
		return tamanoActual;
	}

	public void setTamanoActual(int tamanoActual) {
		this.tamanoActual = tamanoActual;
	}

	@Override
	public String toString() {
		return "Cupo [uuidCupo=" + uuidCupo + ", uuidCentroSalud=" + uuidCentroSalud + ", fechaYHoraInicio="
				+ fechaYHoraInicio + ", tamanoActual=" + tamanoActual + "]";
	}

	@Override
	public int compareTo(Cupo o) {
		return fechaYHoraInicio.compareTo(o.getFechaYHoraInicio());
	}
}
