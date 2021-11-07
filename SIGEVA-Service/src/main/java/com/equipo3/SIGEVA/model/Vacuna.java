package com.equipo3.SIGEVA.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;
import java.util.UUID;

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

	public Vacuna(String nombre, int diasEntreDosis, int numDosis) {
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre; // "Pfizer"
		this.diasEntreDosis = diasEntreDosis; // 21 (3 semanas)
		this.numDosis = numDosis; // 2
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
		return "Vacuna [nombre=" + nombre + ", diasEntreDosis=" + diasEntreDosis + ", numDosis=" + numDosis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(diasEntreDosis, nombre, numDosis, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacuna other = (Vacuna) obj;
		return diasEntreDosis == other.diasEntreDosis && Objects.equals(nombre, other.nombre)
				&& numDosis == other.numDosis && Objects.equals(id, other.id);
	}

}
