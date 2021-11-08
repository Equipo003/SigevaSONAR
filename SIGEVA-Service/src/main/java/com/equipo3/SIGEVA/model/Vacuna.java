package com.equipo3.SIGEVA.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Vacuna {

	@Id
	@Field
	private String id;
	@Field
	private String nombre;
	@Field
	private int diasEntreDosis;
	@Field
	private int numDosis;

	public Vacuna() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDiasEntreDosis() {
		return diasEntreDosis;
	}

	public void setDiasEntreDosis(int diasEntreDosis) {
		this.diasEntreDosis = diasEntreDosis;
	}

	public int getNumDosis() {
		return numDosis;
	}

	public void setNumDosis(int numDosis) {
		this.numDosis = numDosis;
	}

	@Override
	public String toString() {
		return "Vacuna [id=" + id + ", nombre=" + nombre + ", diasEntreDosis=" + diasEntreDosis + ", numDosis="
				+ numDosis + "]";
	}

}
